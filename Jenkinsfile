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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage ('Sonar') {
            steps {
               withSonarQubeEnv('SonarQube') {
                sh 'mvn sonar:sonar'
                def props = getProperties("target/sonar/report-task.txt")
                env.SONAR_CE_TASK_URL = props.getProperty('ceTaskUrl')
               }
            }
        }

        stage('Quality Gate') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'curl -u $SONAR_AUTH_TOKEN $SONAR_CE_TASK_URL'
                    waitForQualityGate()
                }
            }
        }
    }
}