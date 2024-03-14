pipeline {
    agent any
    triggers {
        pollSCM('*/2 * * * *')
    }
    stages {
//                 stage('Build') {
//                     steps {
//                         sh 'mvn clean install'
//                     }
//                 }
                stage('Build') {
                    withMaven(maven: 'mvn') {
                                sh "mvn clean package"
                            }
                }
                stage('Test') {
                    steps {
                        sh 'mvn test'
                    }
                }
                stage('Deploy') {
                    steps {
                        sh 'cp target/*.jar /warungposbe'
                    }
                }
    }
}