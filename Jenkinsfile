pipeline {
    agent any
    triggers {
        pollSCM('*/2 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                script {
//                     sh 'kill $(lsof -t -i:6060)'
                    // Deploy the application using nohup
                    sh 'cd /var/lib/jenkins/workspace/WARUNGPOSBE && BUILD_ID=dontKillMe nohup ./mvnw spring-boot:run &'
                }
            }
        }
    }
}