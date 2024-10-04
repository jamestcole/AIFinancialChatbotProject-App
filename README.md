# AIFinancialChatbotProject-App

## Project Overview

This project is a Financial Advice Chatbot that provides users with basic financial tips and advice. It operates in two tiers:
1. **Tier 1**: The chatbot retrieves relevant answers from a MySQL database containing FAQs related to financial advice.
2. **Tier 2**: If the relevant FAQ is not found, the chatbot sends the query to the OpenAI API to generate a response.

The chatbot is designed to help users with general budgeting tips, saving advice, and understanding basic financial products (e.g., savings accounts, loans, etc.). **No personalized financial or investment advice is provided.**

The application is hosted on **AWS** and can be accessed via the hosted URL or by running the project locally through Spring Boot.

---

## Features
- **FAQ Database (MySQL)**: Contains frequently asked questions and answers about financial topics.
- **Two-Tier Response System**:
    - First, checks the database for relevant FAQs.
    - If no FAQ matches the user query, forwards the query to OpenAI's API **(hosted on Azure)** for a response.
- **AWS Hosted**: The chatbot is deployed and hosted on AWS, accessible via a web interface.
- **Spring Boot Application**: Easily start the project locally using Spring Boot.

---

## Prerequisites

To run the project locally, ensure you have the following installed:
- **Java 21+**
- **Maven** (for managing dependencies)
- **MySQL** (for the FAQ database)
- **Spring Boot** (Java-based framework)
- **AWS CLI** (if hosting or deploying to AWS)

---

## Getting Started

### 1. **Access the Hosted Version**

The project is hosted on AWS. To use the chatbot, simply navigate to the hosted URL in a web browser:

http://3.251.65.214:8080/chat


### 2. **Run Locally via Spring Boot**

If you prefer to run the project locally, follow these steps:

#### Step 1: Clone the Repository


#### Step 2: Configure MySQL Database

Ensure MySQL is installed and running. Create a database for the FAQs, and import your data.

```sql
CREATE DATABASE chatbot_faqs;
USE chatbot_faqs;

-- Import your FAQ data here
```
#### Step 3: Update the application.properties file
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/question_bank_chatbot
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

#### Step 4: Start the app using Maven
```bash
cd path/to/project
mvn spring-boot:run
```

---
## Documentation

All documentation can be found in the 'Documentation' directory in the project root. Access via: [Documentation](https://github.com/jamestcole/AIFinancialChatbotProject-App/tree/main/Documentation)

A link to the demo video can be found via: [Video](https://github.com/jamestcole/AIFinancialChatbotProject-App/blob/main/Documentation/SG_FinancialChatbotDemoV3.mp4)

For a link to the data repo, see here: [Data](https://github.com/Yoonhee-Uni/AI_chatbot_project)

## Note:

This project has now concluded and won't be available at the hosted URL from 04/10/2024. 