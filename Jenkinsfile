pipeline {
    agent any

    tools {
        // Configured with Jenkins Global Tool Configuration
        maven "M3"
        jdk "JDK11"
    }

    stages {
        stage('Build Maven') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Ayyoub-Abdelhak/izicap.git']]])
                bat "mvn clean install"
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker build -t ayyoub/izicap .'
                }
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker-hub-pwd', variable: 'dockerHubPwd')]) {
                        bat "docker login -u lemowsky -p ${dockerHubPwd}"
                        bat 'docker tag ayyoub/izicap lemowsky/izicap:latest'
                        bat 'docker push lemowsky/izicap:latest'
                    }
                }
            }
        }
    }
}
