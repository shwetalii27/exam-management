pipeline {
    agent any

    triggers {
        pollSCM('H/2 * * * *')
    }

    environment {
        APP_NAME = 'exam-app'
        IMAGE    = 'exam-management:latest'
    }

    stages {
        stage('Build & Test') {
            steps { sh 'mvn clean test' }
            post { always { junit 'target/surefire-reports/*.xml' } }
        }

        stage('Package') {
            steps { sh 'mvn package -DskipTests' }
        }

        stage('Publish to Nexus') {
            steps { sh 'mvn deploy -DskipTests -s /var/jenkins_home/settings.xml' }
        }

        stage('Docker Build') {
            steps { sh 'docker build -t $IMAGE .' }
        }

        stage('Deploy') {
            steps {
                sh '''
                  docker rm -f $APP_NAME || true
                  docker run -d --name $APP_NAME --network devops -p 8080:8080 $IMAGE
                '''
            }
        }

        stage('Verify') {
            steps { sh 'sleep 25 && curl -f http://exam-app:8080/actuator/health' }
        }
    }

    post {
        success { echo 'Pipeline succeeded: artifact in Nexus, app deployed.' }
        failure { echo 'Pipeline failed. Check the stage logs.' }
    }
}
