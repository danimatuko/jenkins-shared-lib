# Jenkins Shared Library Template

Smallest useful skeleton for a Jenkins shared library.

## Structure

```text
.
└── vars/
    └── hello.groovy
```

Files in `vars/` become global pipeline steps. The file name is the step name.

## Example Step

```groovy
// vars/hello.groovy
def call(String name = 'Jenkins') {
    echo "Hello, ${name}"
}
```

## Jenkinsfile Usage

Configure this repository as a Jenkins Global Pipeline Library, then use it from a `Jenkinsfile`:

```groovy
@Library('your-library-name') _

pipeline {
    agent any

    stages {
        stage('Example') {
            steps {
                hello('World')
            }
        }
    }
}
```

## Add More Steps

Add another file under `vars/`:

```groovy
// vars/buildApp.groovy
def call() {
    sh 'mvn package'
}
```

Then call it in a Jenkinsfile:

```groovy
buildApp()
```
