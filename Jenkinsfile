pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('sonar') {
      steps {
        sh 'mvn sonar:sonar'
      }
    }
    stage('publish') {
      steps {
        waitForQualityGate()
      }
    }
  }
}