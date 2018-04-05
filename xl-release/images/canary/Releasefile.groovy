// Exported from:        http://HesBook:5516/#/templates/Foldere0a164530122451b94505af4529186b2-Release50a4114f5b6f4caba0cdb7b97f7c0b17/releasefile
// XL Release version:   7.6.0
// Date created:         Fri Mar 02 13:30:44 CET 2018

def server(type, title) {
    def cis = configurationApi.searchByTypeAndTitle(type, title)
    if (cis.isEmpty()) {
        throw new RuntimeException("No CI found for the type '${type}' and title '${title}'")
    }
    if (cis.size() > 1) {
        throw new RuntimeException("More than one CI found for the type '${type}' and title '${title}'")
    }
    cis.get(0)
}

def server1 = server('xldeploy.XLDeployServer','XL Deploy')
def server2 = server('xldeploy.XLDeployServer','XL Deploy')

xlr {
  release('Canary Release') {
    variables {
      stringVariable('application') {
        label 'Application name'
        description 'The name of the application in XL Deploy.'
        value 'Rest-o-rant'
      }
      stringVariable('version') {
        label 'Version'
        description 'The version of the application in XL Deploy that will be deployed in this release.'
        value '1.0'
      }
    }
    description 'Canary deployment pattern.'
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-01-23T09:00:00+0100')
    phases {
      phase('Canary phase') {
        color '#ff9e3b'
        tasks {
          custom('Deploy to Canary environment') {
            owner 'admin'
            script {
              type 'xldeploy.Deploy'
              server server1
              deploymentPackage '${application}/${version}'
              deploymentEnvironment 'Canary Deployment/Canary'
            }
          }
          manual('Run tests') {
            team 'QA'
          }
          gate('Tests have passed and ready to roll out') {
            team 'Release Manager'
          }
        }
      }
      phase('Rollout phase') {
        color '#68b749'
        tasks {
          custom('Deploy to Main environment') {
            owner 'admin'
            script {
              type 'xldeploy.Deploy'
              server server2
              deploymentPackage '${application}/${version}'
              deploymentEnvironment 'Canary Deployment/Main'
            }
          }
          notification('Send notification that new version is live') {
            precondition 'False'
          }
        }
      }
    }
  }
}