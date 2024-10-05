sudo apt install pymysql

in the following directory:
/var/www/html/app$ 
cat /home/ubuntu/nohup.out

to solve port 5000 occupied problem :
sudo lsof -t -i:5000
then
sudo kill -9 4926

to solve ports not allowed/ refused issue , on app instance and on database instance
sudo ufw allow 3306
sudo ufw allow 5000



To ensure that your database is accessible on port 5000, we need to clarify that databases typically listen on their own specific ports (like MySQL on 3306, PostgreSQL on 5432, etc.) rather than the application ports like 5000. However, if you're trying to ensure that your application can communicate with the database, follow these steps:

Step 1: Verify Database Service Configuration
Check the Database Listening Port:
Use lsof or similar commands to see which port your database is listening on. For MySQL, it should be 3306:
bash
Copy code
sudo lsof -i -P -n | grep mysqld
Step 2: Configure Your Database for External Access
If your application needs to connect to the database from another server or a different network interface, you’ll need to:

Edit the Database Configuration File:

For MySQL, edit /etc/mysql/mysql.conf.d/mysqld.cnf or similar configuration file.
Look for the line that starts with bind-address. Change it to:
plaintext
```
bind-address = 0.0.0.0
```
This allows the database to accept connections from any IP address.
Restart the Database Service:

Check the connection between the database and the App

In the app instance use this :

```
mysql -h <db_private_ip> -u <user> -p
```
Check the account exists and check the tables exist and have been built by script.py:
```
USE question_bank_chatbot;
EXIT
```

sudo systemctl restart mysql