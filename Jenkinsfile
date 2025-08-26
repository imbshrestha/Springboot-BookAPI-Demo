pipeline {
    agent any

    // Use the Maven tool configured in Jenkins
    tools {
        maven 'Maven-3.9.11'
    }

    environment {
        DOCKER_IMAGE_NAME = "book-api"
        DOCKER_IMAGE_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
            }
        }

        stage('Deploy (Simulated)') {
            steps {
                echo 'Deploying the application...'
                sh "docker stop book-api-container || true"
                sh "docker rm book-api-container || true"
                sh "docker run --name book-api-container -d -p 8080:8080 ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
            sh 'docker image prune -f'
        }
    }
} // <-- THIS IS THE CLOSING BRACE THAT WAS LIKELY MISSING