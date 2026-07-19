import com.techworldwithnana.Docker

def call(String imageName) {
    new Docker(this).buildAndPush(imageName)
}
