pipeline {
    agent any
        environment {
            // Sesuaikan dengan konfigurasi Anda
            APP_NAME = 'warungposbe-0.0.1-SNAPSHOT'
            JAR_FILE = "target/${APP_NAME}.jar"
            DEPLOYMENT_DIR = '/var/lib/jenkins/workspace/WARUNGPOSBE'
        }
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
                    // Check if the process is already running
                    def isProcessRunning = sh(script: 'ps aux | grep warungposbe-0.0.1-SNAPSHOT.jar | grep -v grep', returnStatus: true)
                    if (isProcessRunning == 0) {
                        // If process is running, kill it
                        sh 'pkill -f warungposbe-0.0.1-SNAPSHOT.jar '
                    }
                    sh "cp ${JAR_FILE} ${DEPLOYMENT_DIR}/${APP_NAME}.jar"
                    sh "sudo systemctl start warungposbe-0.0.1-SNAPSHOT.service"
                }
            }
        }
    }
}