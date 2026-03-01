pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Rohitthorave12/two-tier-flask-app.git', branch: 'main'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', 
                                   passwordVariable: 'DOCKER_PASSWORD', 
                                   usernameVariable: 'DOCKER_USERNAME')]) {
                    sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                }
            }
        }

        stage('Pull Latest Images') {
            steps {
                sh "/usr/local/bin/docker-compose pull" 
            }
        }

        stage('Deploy Application') {
            steps {
                sh "/usr/local/bin/docker-compose up -d --remove-orphans"
            }
        }
    }
}