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
                        sh 'chown -R aji:aji /var/lib/jenkins/workspace/warungposbe/target'
                        sh 'systemctl stop spring-boot-warungpos.service'
                    }
                    // Deploy the application using nohup
                    sh 'systemctl status spring-boot-warungpos.service'
                }
            }
        }
        stage('Verify') {
                    steps {
                        script {
                            // Add a verification step to check if the application is running
                            def isAppRunning = sh(script: 'ps aux | grep warungposbe-0.0.1-SNAPSHOT.jar | grep -v grep', returnStatus: true)
                            if (isAppRunning != 0) {
                                // If the application is not running, print an error message
                                error "The application is not running after deployment."
                            } else {
                                echo "The application is running successfully."
                            }
                        }
                    }
        }
    }
}