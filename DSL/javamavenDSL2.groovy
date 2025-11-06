job('Java Maven App con DSL desde GIT') {
    description('Job creado desde el DSL en el repositorio de Git')
    scm {
        git('https://github.com/pkgo1001/jenkinsMavenApp.git', 'main') { node ->
            node / gitConfigName('pkgo1001')
            node / gitConfigEmail('pokegoacc1001@gmail.com')
        }
    }
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
      	mailer('pokegoacc1001@gmail.com', false, false)
    }
}
