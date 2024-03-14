pipeline {
    agent any
    triggers {
        pollSCM('*/2 * * * *')
    }
    tools {
        maven 'Maven 3.9.5'
    }
    stages {
                stage('Build') {
                    steps {
                        sh 'sudo mvn clean install'
                    }
                }
                stage('Test') {
                    steps {
                        sh 'sudo mvn test'
                    }
                }
                stage('Deploy') {
                    steps {
                        sh 'cp target/*.jar /warungposbe'
                    }
                }
    }
}