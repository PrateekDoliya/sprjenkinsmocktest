pipeline{
    agent any
    tools {
        maven 'Maven'
    }
    stages{
        stage("Sonar"){
            steps{
                echo "======== sonar ========"
                bat "mvn --version"
            }
        }
        stage("Test"){
            steps{
                echo "======== Test ========"
                bat "mvn test"
            }
        }
        stage("Build"){
            steps{
                echo "======== build ========"
                bat "mvn package"
            }
        }
        stage("Docker Image"){
            steps{
                echo "======== building docker image ========"
                bat "docker build -t sprjenkinsmocktest ."
            }
        }
        stage("DockerHub"){
            steps{
                echo "======== pushing docker image to docker hub ========"
                bat "docker image tag sprjenkinsmocktest prateek/sprjenkinsmocktest"
                bat "docker image  push prateek/sprjenkinsmocktest"
            }
        }
    }
    post{
        always{
            echo "========always========"
        }
        success{
            echo "========pipeline executed successfully ========"
        }
        failure{
            echo "========pipeline execution failed========"
        }
    }
}