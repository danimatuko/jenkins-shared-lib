# Jenkins Shared Library

Shared Jenkins pipeline steps for testing, building, and deploying an application.

## Structure

```text
.
├── src/
│   └── com/
│       └── techworldwithnana/
│           ├── Docker.groovy
│           └── Maven.groovy
└── vars/
    ├── buildApp.groovy
    ├── deployApp.groovy
    └── testApp.groovy
```

Files in `vars/` become global pipeline steps. The file name is the step name.
Files in `src/` are regular Groovy classes that can be imported and used by the `vars/` steps.

The `vars/` files are thin wrappers around the classes in `src/`. They pass the Jenkins pipeline context with `this`, so the classes can call Jenkins steps through `steps`, for example `steps.sh`, `steps.echo`, and `steps.withCredentials`.

## Steps

- `testApp()` prints a test stage message.
- `buildApp()` packages the application with Maven.
- `deployApp(imageName)` builds and pushes a Docker image.

## Class-based Implementation

The shared library uses Groovy classes for the build and deploy logic:

- `com.techworldwithnana.Maven` contains the Maven build logic.
- `com.techworldwithnana.Docker` contains the Docker build, login, and push logic.

Example wrapper from `vars/buildApp.groovy`:

```groovy
import com.techworldwithnana.Maven

def call() {
    new Maven(this).build()
}
```

The `this` value is the Jenkins pipeline script context. It is passed into the class constructor so the class can use Jenkins pipeline steps:

```groovy
steps.echo 'Message'
steps.sh 'mvn package'
```

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
                deployApp('docker-user/java-maven-app:latest')
            }
        }
    }
}
```

The Docker image name is passed to `deployApp(imageName)`. Jenkins uses the `docker-creds` credential to log in before pushing the image.
