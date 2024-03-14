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
                    def isProcessRunning = sh(script: 'ps aux | grep warungposbe-0.0.1-SNAPSHOT.jar | grep -v grep', returnStatus: true)
                    if (isProcessRunning == 0) {
                        // If process is running, kill it
                        sh 'pkill -f warungposbe-0.0.1-SNAPSHOT.jar '
                    }
                    sh "cp ${JAR_FILE} ${/var/lib/jenkins/workspace/WARUNGPOSBE/warungposbe-0.0.1-SNAPSHOT.jar}/${}.jar"
                    // Deploy the application using nohup
                    sh "sudo systemctl start warungposbe-0.0.1-SNAPSHOT.jar"
                }
            }
        }
    }
}