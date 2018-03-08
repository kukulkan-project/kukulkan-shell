pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
            }
            post {
                success {
                    junit '**/target/surefire-reports/**/*.xml' 
                }
            }
        }

        stage ('Sonar') {
            when {
                branch 'master'
            }
            steps {
               withSonarQubeEnv('SonarQube') {
                sh 'mvn sonar:sonar'
               }
            }
        }

        stage('Quality Gate') {
            when {
                branch 'master'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'curl -u $SONAR_AUTH_TOKEN $SONAR_HOST_URL/api/user_tokens/search'
                    waitForQualityGate()
                }
            }
        }
    }
}