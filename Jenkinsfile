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
                    // Deploy the application using nohup
                    sh 'cd /var/lib/jenkins/workspace/WARUNGPOSBE/target && BUILD_ID=dontKillMe nohup java -jar warungposbe-0.0.1-SNAPSHOT.jar > /var/lib/jenkins/workspace/WARUNGPOSBE/nohup.out &'
                }
            }
        }
    }
}