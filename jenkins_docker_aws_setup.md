# Jenkins Setup on AWS EC2 with Docker
This guide provides step-by-step instructions to set up Jenkins on an AWS EC2 instance, configure security groups for HTTP and Jenkins access, and install Docker for Jenkins job execution.

## Prerequisites
An AWS EC2 instance running Ubuntu.
Appropriate permissions to modify security groups and install software.

## Setup EC2 Instance
Configure Security Groups for Jenkins Access. 
In the AWS Management Console, navigate to EC2 > Security Groups.
Find your EC2 instance's security group and click Edit inbound rules.

### Add the following rules:
- SSH (port 22): Allows SSH access.
- HTTP (port 80): Allows web traffic (optional).
- Custom TCP (port 8080): Allows Jenkins access.
- Source: Set to 0.0.0.0/0 (for public access) or restrict to your IP.

## Install Jenkins on EC2
Update system and install Java (Jenkins requires Java 11 or 8):

```
sudo apt update
sudo apt install openjdk-11-jdk -y
```

### Add Jenkins repository and install Jenkins:

```
curl -fsSL https://pkg.jenkins.io/debian/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt update
sudo apt install jenkins -y
```

### Start Jenkins:

```
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

## Access Jenkins
Open a browser and navigate to http://<EC2-PUBLIC-IP>:8080.

### Retrieve the initial admin password:
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
Use this password to unlock Jenkins and complete the setup.

### Install Docker for Jenkins
Install Docker:
```
sudo apt update
sudo apt install docker.io -y
```

### Add your user to the Docker group:

```
sudo usermod -aG docker $USER
```

Restart Jenkins:

```
sudo systemctl restart jenkins
```

Apply group changes (log out and log back in, or run):

```
newgrp docker
```

## Verify Docker with Jenkins

Run the following to confirm Docker is installed and working:
```
docker --version
```
Now, Jenkins is set up on your AWS EC2 instance, and Docker is ready for use in Jenkins pipelines.