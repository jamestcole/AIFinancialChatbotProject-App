pipeline {
    agent any

    environment {
        // Fetch the OpenAI key from Jenkins credentials (optional backup)
        OPENAI_API_KEY = "${params.OPENAI_API_KEY ?: credentials('openai-api-key')}"  
    }

    stages {
        // Stage to check the GitHub push event
        stage('Check Push') {
            steps {
                echo 'Push event received from GitHub!'
            }
        }

        // Pull the code from GitHub
        stage('Checkout Code') {
            steps {
                git branch: 'terra', url: 'https://github.com/jamestcole/AIFinancialChatbotProject-App.git'
            }
        }

        // Build the project using Maven
        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        // Custom commands for setting up the environment and running the app
        stage('Custom Startup Script') {
            steps {
                sh '''
                    # Custom startup commands

                    echo "Setting up OpenAI credentials..."
                    export OPENAI_API_KEY=$OPENAI_API_KEY

                    echo "Starting application..."
                    java -jar target/myapp.jar --spring.config.location=classpath:/application.properties
                '''
            }
        }

        // Deployment step (can be Docker or other custom deployment)
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sh '''
                    # For Docker, if applicable:
                    docker build -t myapp:latest .
                    docker run -d -e OPENAI_API_KEY=$OPENAI_API_KEY myapp:latest
                '''
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
        }
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
