provider "aws" {
  region = "eu-west-1"
}

# VPC creation
resource "aws_vpc" "main_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "main-vpc"
  }
}

# Public Subnet creation
resource "aws_subnet" "public_subnet" {
  vpc_id            = aws_vpc.main_vpc.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "eu-west-1a"
  map_public_ip_on_launch = true
  tags = {
    Name = "public-subnet"
  }
}

# Internet Gateway creation
resource "aws_internet_gateway" "main_gw" {
  vpc_id = aws_vpc.main_vpc.id
  tags = {
    Name = "main-gateway"
  }
}

# Route Table and Route creation
resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.main_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main_gw.id
  }

  tags = {
    Name = "public-route-table"
  }
}

resource "aws_route_table_association" "subnet_association" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_route_table.id
}

# Security Group for the EC2 Instances (App and DB)
resource "aws_security_group" "app_and_db_sg" {
  vpc_id = aws_vpc.main_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 5000
    to_port     = 5000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "app_and_db_sg"
  }
}

# EC2 Instance for the Java App
resource "aws_instance" "app_server" {
  ami           = var.ec2_ami_id
  instance_type = var.ec2_instance_type
  subnet_id     = aws_subnet.public_subnet.id
  associate_public_ip_address = true
  key_name      = var.key_pair_name

  vpc_security_group_ids = [aws_security_group.app_and_db_sg.id]

  user_data = <<-EOF
              #!/bin/bash
              sudo apt update -y
              sudo apt install -y openjdk-11-jdk maven apache2 git

              # Clone your GitHub repository
              git clone -b terra https://github.com/jamestcole/AIFinancialChatbotProject-App.git /var/www/html/app

              # Navigate to the project directory and build the Java project
              cd /var/www/html/app

              # Compile and run your Java project
              #./mvnw clean install
              sudo mvn clean install -DskipTests

              # Copy the built HTML files to the Apache web server
              cp -r /var/www/html/app/src/main/resources/templates/* /var/www/html/
              
              
              # Start Apache server# Start the Spring Boot application
              java -jar target/AIFinancialChatbotProject-App.jar &

              sudo systemctl start apache2
              sudo systemctl enable apache2
              EOF

  tags = {
    Name = "Java App Server"
  }
}

# EC2 Instance for the MySQL Database
resource "aws_instance" "db_server" {
  ami           = var.ec2_ami_id
  instance_type = var.ec2_instance_type
  subnet_id     = aws_subnet.public_subnet.id
  associate_public_ip_address = true
  key_name      = var.key_pair_name

  vpc_security_group_ids = [aws_security_group.app_and_db_sg.id]

  user_data = <<-EOF
              #!/bin/bash
              sudo apt update -y
              sudo apt install -y mysql-server openjdk-21-jdk

              # Configure MySQL to allow external access
              sudo sed -i "s/^bind-address.*/bind-address = 0.0.0.0/" /etc/mysql/mysql.conf.d/mysqld.cnf

              # Start MySQL server
              sudo systemctl start mysql
              sudo systemctl enable mysql

              # Set up MySQL database and user
              mysql -u root -e "CREATE DATABASE your_database_name;"
              mysql -u root -e "CREATE USER 'admin'@'%' IDENTIFIED BY 'your_password';"
              mysql -u root -e "GRANT ALL PRIVILEGES ON your_database_name.* TO 'admin'@'%';"
              mysql -u root -e "FLUSH PRIVILEGES;"
              EOF

  tags = {
    Name = "DB Server"
  }
}
