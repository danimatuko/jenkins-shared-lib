# Jenkins Shared Library

Shared Jenkins pipeline steps for testing, building, and deploying an application.

## Structure

```text
.
└── vars/
    ├── buildApp.groovy
    ├── deployApp.groovy
    └── testApp.groovy
```

Files in `vars/` become global pipeline steps. The file name is the step name.

## Steps

- `testApp()` prints a test stage message.
- `buildApp()` packages the application with Maven.
- `deployApp()` builds and pushes a Docker image.

## Requirements

- Maven must be available on the Jenkins agent.
- Docker must be available on the Jenkins agent.
- Jenkins must have a username/password credential with ID `docker-creds`.

## Jenkinsfile Usage

Configure this repository as a Jenkins Global Pipeline Library, then use it from a `Jenkinsfile`:

```groovy
@Library('jenkins-shared-lib') _

pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                testApp()
            }
        }

        stage('Build') {
            steps {
                buildApp()
            }
        }

        stage('Deploy') {
            steps {
                deployApp()
            }
        }
    }
}
```

The Docker image is pushed as `DOCKER_USER/java-maven-app:latest`, where `DOCKER_USER` comes from the `docker-creds` credential.
