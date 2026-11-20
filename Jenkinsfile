pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {
        stage('Verify build tools') {
            steps {
                bat 'mvn -version'
            }
        }

        stage('Run API tests') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'site-api-qa',
                    usernameVariable: 'API_USERNAME',
                    passwordVariable: 'API_PASSWORD'
                )]) {
                    bat 'mvn -B clean test'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts(
                artifacts: 'target/logs/api-tests.log*',
                excludes: '**/*.lck',
                allowEmptyArchive: true
            )
            junit testResults: 'target/surefire-reports/TEST-*.xml'
        }
    }
}