// Exported from:        http://HesBook.xebialabs.com:5516/#/templates/Foldere0a164530122451b94505af4529186b2-Release5225171a46ee49dcbd0c55886239e546/releasefile
// XL Release version:   7.6.0
// Date created:         Mon Mar 19 15:59:09 CET 2018

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

xlr {
  release('Blue / Green') {
    variables {
      stringVariable('application') {
        label 'Application name'
        description 'The name of the application in XL Deploy.'
        value 'Rest-o-rant'
      }
      stringVariable('version') {
        label 'Version'
        description 'The version of the application in XL Deploy that will be deployed in this release.'
      }
      listBoxVariable('new-environment') {
        required false
        showOnReleaseStart false
        label 'Environment to deploy to'
        possibleValues variable('global.bluegreen-environments')
      }
    }
    description 'Blue /green deployment pattern.'
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-01-23T09:00:00+0100')
    scriptUsername 'admin'
    scriptUserPassword '{b64}q8m7TF8W5TBK4oyFNjHjeQ=='
    phases {
      phase('Select new environment') {
        color '#009CDB'
        tasks {
          script('What is currently live?') {
            description 'This task inspects what the environment is that is currently live and determines what will be the new environment to deploy to.'
            script 'if globalVariables[\'global.live-environment\'] == \'Blue\':\n' +
                   '    releaseVariables[\'new-environment\'] = \'Green\'\n' +
                   '    releaseVariables[\'old-environment\'] = \'Blue\'\n' +
                   '\n' +
                   'if globalVariables[\'global.live-environment\'] == \'Green\':\n' +
                   '    releaseVariables[\'new-environment\'] = \'Blue\'\n' +
                   '    releaseVariables[\'old-environment\'] = \'Green\'\n' +
                   '    '
          }
          userInput('Confirm new environment') {
            description 'The current live environment is **${global.live-environment}** and is running **\n' +
                        '${global.live-version}**.\n' +
                        '\n' +
                        'Please confirm to deploy **${application}/${version}**.'
            owner 'admin'
            variables {
              variable 'new-environment'
            }
          }
        }
      }
      phase('Deploy and test') {
        color '#ff9e3b'
        tasks {
          custom('Deploy to new environment') {
            owner 'admin'
            script {
              type 'xldeploy.Deploy'
              server server1
              deploymentPackage '${application}/${version}'
              deploymentEnvironment 'PROD/${new-environment}'
            }
          }
          manual('Run tests') {
            
          }
          gate('OK to switch?') {
            
          }
        }
      }
      phase('Switch to live') {
        color '#68b749'
        tasks {
          manual('Change load balancer') {
            
          }
          script('Update registry with live environment') {
            script 'globalVariables[\'global.live-environment\'] = \'${new-environment}\'\n' +
                   'globalVariables[\'global.live-version\'] = \'${application}/${version}\'\n'
          }
          notification('Send notification that new version is live') {
            precondition 'False'
          }
        }
      }
    }
  }
}