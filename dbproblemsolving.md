# Troubleshooting Database
from app instance if cant connect to db (replace with db private_ip):
```
mysql -h 10.0.1.167 -u root -p 
```
```
SELECT User, Host FROM mysql.user;
```
```
USE question_bank_chatbot;
SHOW TABLES;
```
## create privaleged user
```
CREATE USER 'app_user'@'%' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON question_bank_chatbot.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
```
## setting up database
-- Access MySQL
sudo mysql

-- List users
SELECT User, Host FROM mysql.user;

-- Delete an unwanted user
DROP USER 'old_user'@'%';

-- Create a new user
CREATE USER 'new_user'@'%' IDENTIFIED BY 'new_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON *.* TO 'new_user'@'%' WITH GRANT OPTION;

-- Flush privileges
FLUSH PRIVILEGES;

-- Verify the user and privileges
SHOW GRANTS FOR 'new_user'@'%';

-- Exit MySQL
EXIT;
## Database connection settings
spring.datasource.url=jdbc:mysql://<DB_HOST>:3306/question_bank_chatbot
spring.datasource.username=app_user  # The username you created
spring.datasource.password=your_password  # The password you set
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

## Other application properties...

DROP TABLE IF EXISTS conversation_ids;

CREATE TABLE conversation_ids (
    conversation_id INT NOT NULL AUTO_INCREMENT,
    -- Other fields can be added here, for example:
    field1 VARCHAR(255),
    field2 VARCHAR(255),
    PRIMARY KEY (conversation_id)
);

INSERT INTO conversation_ids (field1, field2) VALUES ('val1', 'val2');


Steps to Uninstall MySQL:
Stop MySQL Service:

bash
Copy code
sudo systemctl stop mysql
Uninstall MySQL:

bash
Copy code
sudo apt remove --purge mysql-server mysql-client mysql-common mysql-server-core-* mysql-client-core-*
Remove MySQL Data Directory:

bash
Copy code
sudo rm -rf /var/lib/mysql
Remove MySQL Configuration Files:

bash
Copy code
sudo rm -rf /etc/mysql
Reinstall MySQL:

bash
Copy code
sudo apt update
sudo apt install mysql-server
Secure MySQL Installation: After reinstalling, run:

bash
Copy code
sudo mysql_secure_installation