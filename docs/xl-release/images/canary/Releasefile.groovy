// Exported from:        http://HesBook.xebialabs.com:5516/#/templates/Foldere0a164530122451b94505af4529186b2-Release50a4114f5b6f4caba0cdb7b97f7c0b17/releasefile
// XL Release version:   7.6.0
// Date created:         Tue Mar 27 12:14:00 CEST 2018

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
def server3 = server('xldeploy.XLDeployServer','XL Deploy')

xlr {
  release('Deployment pattern: Canary Release') {
    variables {
      stringVariable('application') {
        label 'Application name'
        description 'The name of the application in XL Deploy.'
        value 'PetClinic-war'
      }
      stringVariable('newVersion') {
        label 'New version'
        description 'The version of the application in XL Deploy that will be deployed.'
      }
      stringVariable('currentVersion') {
        label 'Current version'
        description 'The version of the application in XL Deploy that is currently running.'
      }
      stringVariable('canaryEnvironment') {
        label 'Canary environment'
        description 'The name of the Canary environment in XL Deploy, without the \'Environments/\' prefix.'
        value 'Canary Deployment/Canary'
      }
      stringVariable('mainEnvironment') {
        label 'Main environment'
        description 'The name of the Main environment in XL Deploy, without the \'Environments/\' prefix.'
        value 'Canary Deployment/Main'
      }
      listBoxVariable('action') {
        required false
        showOnReleaseStart false
        label 'Action'
        possibleValues 'Deploy to Main', 'Rollback Canary environment'
        value 'Deploy to Main'
      }
    }
    description 'This template implements the **Canary Deployment Pattern**\n' +
                '\n' +
                'The Canary Release deployment pattern is a pattern where a small subset on your production environment is used to test out new features. The environment is split into a Canary part and a Main part. The canary part is typically small and allows for fast deployments. A load balancer is used to direct traffic to all parts of the application, deciding which users go to the new version in the Canary section and which users remain on the stable Main section.\n' +
                '\n' +
                'First, the new version is deployed to the Canary part. If successful, the new version is rolled out over the rest of the environment.\n' +
                '\n' +
                'If the changes are not successful, only the smaller environment needs to be rolled back.\n' +
                '\n' +
                'This XL Release template shows the basic steps and is the starting point of your Canary deployment process.\n' +
                '\n' +
                'For more information, please read [Perform canary deployments](https://docs.xebialabs.com/xl-deploy/how-to/perform-canary-deployments.html) on our documentation site.'
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-01-23T09:00:00+0100')
    phases {
      phase('Canary phase') {
        color '#ff9e3b'
        tasks {
          custom('Deploy to Canary environment') {
            description 'Use XL Deploy to deploy to the Canary environment.\n' +
                        '\n' +
                        'Configure the XL Deploy server in **Settings > Shared Configuration**.'
            script {
              type 'xldeploy.Deploy'
              server server1
              deploymentPackage '${application}/${newVersion}'
              deploymentEnvironment '${canaryEnvironment}'
            }
          }
          manual('Run tests') {
            description '### Run acceptance tests\n' +
                        '\n' +
                        'Perform the acceptance tests before taking this version into production.\n' +
                        '\n' +
                        '**Application version:** `${application}/${newVersion}`   \n' +
                        '**Environment:** `${canaryEnvironment}`'
          }
          userInput('Confirm full deployment') {
            description '**Main environment:** `${mainEnvironment}`  \n' +
                        '**Current version:** `${application}/${currentVersion}`  \n' +
                        '**New version:** `${application}/${newVersion}`\n' +
                        '\n' +
                        'The new version has been tested on the Canary environment and is ready to be deployed to the Main environment.\n' +
                        '\n' +
                        'If tests were not successful, the Canary environment can be rolled back.\n' +
                        '\n' +
                        'Please confirm the switch by selecting the action below and completing this task'
            variables {
              variable 'action'
            }
          }
        }
      }
      phase('Rollout phase') {
        color '#68b749'
        tasks {
          sequentialGroup('Deploy to Main environment') {
            description 'This block is executed only if tests were successful and the new version can be deployed to the Main environment.'
            precondition 'releaseVariables[\'action\'] == \'Deploy to Main\''
            tasks {
              custom('Deploy to Main environment') {
                description 'Use XL Deploy to deploy to the Main environment.'
                script {
                  type 'xldeploy.Deploy'
                  server server2
                  deploymentPackage '${application}/${newVersion}'
                  deploymentEnvironment '${mainEnvironment}'
                }
              }
              notification('Send notification that new version is live') {
                precondition 'False'
                subject '${application}/${newVersion} is now live!'
                body '**${application}/${newVersion}** has been deployed to **${mainEnvironment}** and is now live!'
              }
            }
          }
          sequentialGroup('Rollback Canary environment') {
            description 'This block is executed only if tests failed and the Canary environment needs to be rolled back.'
            precondition 'releaseVariables[\'action\'] == \'Rollback Canary environment\''
            tasks {
              custom('Rollback Canary environment') {
                description 'Use XL Deploy to rollback the Canary environment to the original version.'
                script {
                  type 'xldeploy.Deploy'
                  server server3
                  deploymentPackage '${application}/${currentVersion}'
                  deploymentEnvironment '${canaryEnvironment}'
                }
              }
            }
          }
        }
      }
    }
  }
}