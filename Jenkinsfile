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
                echo "======== building docker imnage ========"
                bat "docker build -t jankinsspr ."
            }
        }
        stage("Push To DockerHub & Run Container"){
           parallel{
                stage("Push to DockerHub") {
                    steps{
                        echo "======== pushing docker image to docker hub ========"
                        bat "docker image tag jankinsspr prateek/jankinsspr"
                        bat "docker image  push prateek/jankinsspr"
                    }
                }
                stage("Run DockerContainer") {
                    steps{
                        echo "======== running docker image as SpringJenkinsMockContainer ========"
                        bat "docker run --name SpringJenkinsMockContainer -it -d jankinsspr"
                    }
                }
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