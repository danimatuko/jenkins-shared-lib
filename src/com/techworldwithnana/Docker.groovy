package com.techworldwithnana

class Docker implements Serializable {
    def steps

    Docker(steps) {
        this.steps = steps
    }

    def buildAndPush(String imageName) {
        steps.echo 'Deploying the application...'
        steps.withCredentials([steps.usernamePassword(
            credentialsId: 'docker-creds',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]) {
            steps.sh "docker build -t ${imageName} ."
            steps.sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
            steps.sh "docker push ${imageName}"
        }
    }
}
