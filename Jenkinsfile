pipeline {
    // 1. Define the agent (where the pipeline will run)
    agent any

    // Define environment variables, including the Docker image name
    environment {
        DOCKER_IMAGE_NAME = "book-api"
        DOCKER_IMAGE_TAG = "latest"
    }

    // 2. Define the stages of the pipeline
    stages {
        stage('Checkout') {
            steps {
                // Fetches the source code from the Git repository
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Builds the project and creates the JAR file
                echo 'Building the application...'
                // Using 'install' instead of 'package' is a good practice in CI
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Runs all JUnit tests
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Builds a Docker image using the Dockerfile in the repository
                echo 'Building Docker image...'
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
            }
        }

        stage('Deploy (Simulated)') {
            steps {
                // In a real-world scenario, this would deploy to a server.
                // Here, we'll run it locally to prove it works.
                echo 'Deploying the application...'
                // Stop and remove any old container with the same name
                sh "docker stop book-api-container || true"
                sh "docker rm book-api-container || true"

                // Run the new container
                sh "docker run --name book-api-container -d -p 8080:8080 ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
            }
        }
    }

    // 3. Post-build actions (always run)
    post {
        always {
            echo 'Pipeline finished.'
            // Clean up old Docker images to save space
            sh 'docker image prune -f'
        }
    }
}