node {

    withMaven(maven:'maven') {

        stage('Checkout') {
            git url: 'giturl', credentialsId: 'test', branch: 'dev'
        }

		stage('Build') {
            sh 'mvn package shade:shade'
            def pom = readMavenPom file:'pom.xml'
            print pom.version
            env.version = pom.version
        }

        stage('Image') {
                sh 'docker stop restassured || true && docker rm restassured || true'
                cmd = "docker rmi restassured:${env.version} || true"
                sh cmd
                docker.build "restassured:${env.version}"
            
        }

        stage ('Run') {
            docker.image("restassured:${env.version}").run('-p 8081:8081 -h restassured --name restassured --net host -m=500m')
        }

    }

}