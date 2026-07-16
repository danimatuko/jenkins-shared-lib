def call() {
    echo "Building the application from branch: ${env.GIT_BRANCH}"
    sh 'mvn package'
}
