pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
        sonarQube 'SonarQube Scanner 3.0'
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
            steps {
               withSonarQubeEnv('SonarQube Scanner') {
                sh 'mvn sonar:sonar' 
               }
            }
        }

        stage('publish') {
            steps {
                waitForQualityGate()
            }
        }
    }
}