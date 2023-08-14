pipeline {
  //agent any
agent{
label 'k8s-jnlp-jdk11' // da beygy mn el devops team  el agent eli beyrun jenkins
}
  tools {
    maven 'maven'
  }

  parameters {
      choice(name: 'DOCKER_REGISTRY', choices: ['registry.sumerge.local'], description: 'Docker Registry')
      //portal.csr.gov.local
  }

  options {
    skipStagesAfterUnstable()
    disableConcurrentBuilds()
  }

  stages {
    stage('Maven Build') {
      steps {
        sh 'mvn clean -Ptesting -DemailProperties=debugMailConfig -DrunOnRemoteServer=true package'
      }
    }

    stage('Docker Build') {
      steps {
//         script {
//           if(params.ENV == "dev") {
            sh '''
            export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
            export POM_ARTIFACTID=`mvn --non-recursive help:evaluate -Dexpression=project.artifactId -q -DforceStdout`
            docker build -t ${DOCKER_REGISTRY}/csr/${POM_ARTIFACTID}:${POM_VERSION} .
            '''
            // -t register.... da esm el image aw el tag
//           } else if(params.ENV == "staging") {
//             sh '''
//             export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
//             export POM_ARTIFACTID=`mvn --non-recursive help:evaluate -Dexpression=project.artifactId -q -DforceStdout`
//             docker build -t registry.momra.gov.sa/momra/${POM_ARTIFACTID}:${POM_VERSION} .
//             '''
//           }
//         }
      }
    }

    stage('Docker Push') {
      steps {
//         script {
//           if(params.ENV == "dev") {
            sh '''
            export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
            export POM_ARTIFACTID=`mvn --non-recursive help:evaluate -Dexpression=project.artifactId -q -DforceStdout`
            docker push ${DOCKER_REGISTRY}/csr/${POM_ARTIFACTID}:${POM_VERSION}
            '''

//           } else if(params.ENV == "staging") {
//             sh '''
//             export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
//             export POM_ARTIFACTID=`mvn --non-recursive help:evaluate -Dexpression=project.artifactId -q -DforceStdout`
//             docker push registry.momra.gov.sa/momra/${POM_ARTIFACTID}:${POM_VERSION}
//             '''
            //docker pus ${DOCKER_REGISTERY} da esm el choice eli foo2 fel parameters
//           }//el if statement hatefre2 lama na3mel build ba2a nghaayr command el build nghayar el registery
//         }
      }
    }

    stage ('K8s Prepare') {
      steps {
//         script {
//           if(params.ENV == "dev") {
            sh '''
            export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
            sed -i "s|VERSION|${POM_VERSION}|g" $WORKSPACE/k8s/csr-automation-job.yaml
            sed -i "s|DOCKER-REGISTRY|${DOCKER_REGISTRY}|g" $WORKSPACE/k8s/csr-automation-job.yaml
            '''
//           } else if(params.ENV == "staging") {
//             sh '''
//             export POM_VERSION=`mvn --non-recursive help:evaluate -Dexpression=project.version -q -DforceStdout`
//             sed -i "s|VERSION|${POM_VERSION}|g" $WORKSPACE/k8s/e2e-tests-job.yaml
//             sed -i "s|DOCKER-REGISTRY|registry.momra.gov.sa/momra|g" $WORKSPACE/k8s/e2e-tests-job.yaml
//             '''
//           }
//         }
      }
    }

    stage ('K8s Deploy') {
      steps {
        sh '''
        kubectl delete --ignore-not-found -f $WORKSPACE/k8s/csr-automation-job.yaml -n csr
        kubectl create -f $WORKSPACE/k8s/csr-automation-job.yaml -n csr
        '''
      }
    }
  }
}
