pipeline {
    agent any

    environment {
        // Define SSH key credentials ID
        SSH_KEY_CREDENTIALS = 'ec2Nice'
        // Define EC2 instance details
        EC2_INSTANCE_IP = '16.170.251.71'
        EC2_INSTANCE_USER = 'ubuntu'  // Update with your EC2 instance user
        FILE_CONTENT = 'nothing, just Hi.'
        FILE_PATH = 'home/ubuntu/file.txt'
       
    }

    stages {
        stage('Connect to EC2 and Create File') {
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: SSH_KEY_CREDENTIALS, keyFileVariable: 'SSH_PRIVATE_KEY')]) {
                        sh """
                            ssh -o StrictHostKeyChecking=no -i "${SSH_PRIVATE_KEY}" ${EC2_INSTANCE_USER}@${EC2_INSTANCE_IP} "echo '${FILE_CONTENT}' > ${FILE_PATH}"
                        """
                    }
                }
            }
        }
    }
}
