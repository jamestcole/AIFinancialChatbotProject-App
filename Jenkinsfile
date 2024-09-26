pipeline {
    agent any
    stages {
        stage('Check Push') {
            steps {
                // Use 'bat' instead of 'sh' for Windows , linux is different
                bat 'echo Push event received!'
            }
        }
    }
}