pipeline {
    agent any
    tools {
        maven "Maven 3.9.4"
    }

    stages {

        stage('Build') {
            steps {
                script {
                    sh 'cd service-registry && mvn package'
                    sh 'cd api-gateway && mvn package'
                    sh 'cd department-service && mvn package'
                    sh 'cd user-service && mvn package'
                }
            }
        }

        stage('Scan') {
            steps {
                script {
                    def microservices = ['service-registry', 'api-gateway', 'department-service', 'user-service']

                    microservices.each { ms ->
                        dir(ms) {
                            withSonarQubeEnv('SonarQube') {
                                sh "mvn sonar:sonar -Dsonar.projectKey=${ms}"
                            }
                        }
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                        
                        withDockerRegistry([ credentialsId: "docker-credentials", url: "" ]) {
                        sh 'cd service-registry && docker build -t edvinas9870/service-registry:latest . && docker push edvinas9870/service-registry:latest'
                        sh 'cd api-gateway && docker build -t edvinas9870/api-gateway:latest . && docker push edvinas9870/api-gateway:latest'
                        sh 'cd department-service && docker build -t edvinas9870/department-service:latest . && docker push edvinas9870/department-service:latest'
                        sh 'cd user-service && docker build -t edvinas9870/user-service:latest . && docker push edvinas9870/user-service:latest'
                        }
                }
            }
        }


        stage('Deploy') {
            steps {
                script {
                        
                    kubernetesDeploy(configs: "service-registry/deployment.yml", kubeconfigId: "kubernetes")
                    kubernetesDeploy(configs: "api-gateway/deployment.yml", kubeconfigId: "kubernetes")
                    kubernetesDeploy(configs: "department-service/deployment.yml", kubeconfigId: "kubernetes")
                    kubernetesDeploy(configs: "user-service/deployment.yml", kubeconfigId: "kubernetes")

                }
            }
        }


    }
}