pipeline {
    agent any
    tools {
        maven 'Maven 3.3.9'
        jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            options {
                timeout(time: 1, unit: 'HOURS')
            }
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
            options {
                timeout(time: 1, unit: 'HOURS')
            }
            steps {
               withSonarQubeEnv('SonarQube') {
                sh 'mvn sonar:sonar -Dsonar.projectVersion=${POM_VERSION}-${BUILD_NUMBER}'
               }
            }
        }

        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}