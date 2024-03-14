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
                    // Check if the process is already running
                    def processId = sh(script: "pgrep -f WARUNGPOSBE-0.0.1-SNAPSHOT.jar", returnStdout: true).trim()
                    if (processId) {
                        echo "Process is already running, killing it"
                        sh "kill $processId"
                    }
                    // Start the process using nohup
                    sh 'cd /var/lib/jenkins/workspace/WARUNGPOSBE/target && nohup java -jar WARUNGPOSBE-0.0.1-SNAPSHOT.jar &'
                }
            }
        }
    }
}