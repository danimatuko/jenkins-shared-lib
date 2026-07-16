def call(String imageName) {
    echo 'Deploying the application...'
    withCredentials([usernamePassword(
        credentialsId: 'docker-creds',
        usernameVariable: 'DOCKER_USER',
        passwordVariable: 'DOCKER_PASS'
    )]) {
        sh "docker build -t ${imageName} ."
        sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
        sh "docker push ${imageName}"
    }
}
