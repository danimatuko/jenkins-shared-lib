package com.techworldwithnana

class Maven implements Serializable {
    def steps

    Maven(steps) {
        this.steps = steps
    }

    def build() {
        steps.echo "Building the application from branch: ${steps.env.GIT_BRANCH}"
        steps.sh 'mvn package'
    }
}
