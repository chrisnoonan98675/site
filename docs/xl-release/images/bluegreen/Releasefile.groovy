// Exported from:        http://HesBook.xebialabs.com:5516/#/templates/Foldere0a164530122451b94505af4529186b2-Release5225171a46ee49dcbd0c55886239e546/releasefile
// XL Release version:   7.6.0
// Date created:         Fri Mar 23 09:12:40 CET 2018

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
        possibleValues variable('global.blue-green.environments')
      }
    }
    description 'This template implements the **Blue/Green Deployment Pattern**.\n' +
                '\n' +
                'Blue-green deployment is a pattern in which identical production environments known as Blue and Green are maintained, one of which is live at all times.\n' +
                'If the Blue is the live environment, applications or features are deployed to and tested on the non-live Green environment before user traffic is diverted to it.\n' +
                '\n' +
                'If it is necessary to roll back the release in the Green environment, you can route user traffic back to the Blue environment.\n' +
                '\n' +
                'When the Green environment is stable, the Blue environment can be retired and is ready to be updated in the next iteration, where it will take the role of the Green environment of this example.\n' +
                '\n' +
                'This XL Release template shows the basic steps and is the starting point of your Blue/Green deployment process.\n' +
                '\n' +
                'For more information, please read [Perform Blue/Green deployments](https://docs.xebialabs.com/xl-release/how-to/perform-blue-green-deployments.html) on our documentation site.'
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2018-01-23T09:00:00+0100')
    scriptUsername 'admin'
    scriptUserPassword '{b64}q8m7TF8W5TBK4oyFNjHjeQ=='
    phases {
      phase('Select new environment') {
        color '#009CDB'
        tasks {
          script('What is currently live?') {
            description 'This script inspects what environment is currently live and determines what will be the new environment to deploy to.\n' +
                        '\n' +
                        'It uses global variables to maintain the state.'
            script 'missingGlobalVarsMessage = """\n' +
                   '\n' +
                   '### Missing global variables\n' +
                   '    \n' +
                   'Please add the following global variables:\n' +
                   '    \n' +
                   '1. `global.blue-green.environments` of type **List** that contains the names of the Blue/Green environments in XL Deploy.\n' +
                   '2. `global.blue-green.live-environment` of type **List Box** that takes its values from **global.blue-green.environments**.\n' +
                   '3. `global.blue-green.live-version` of type **Text** that contains the current running version.\n' +
                   '    \n' +
                   'These global variables are required to track environment status.     \n' +
                   '\n' +
                   '---\n' +
                   '\n' +
                   '"""\n' +
                   '\n' +
                   '# Check if global variables are defined\n' +
                   'if (\'global.blue-green.live-environment\' not in globalVariables \n' +
                   '    or \'global.blue-green.live-environment\' not in globalVariables \n' +
                   '    or \'global.blue-green.live-version\' not in globalVariables):\n' +
                   '        \n' +
                   '    print missingGlobalVarsMessage\n' +
                   '    raise Exception("Missing global variables")\n' +
                   '\n' +
                   '# Switch environments\n' +
                   'if globalVariables[\'global.blue-green.live-environment\'] == globalVariables[\'global.blue-green.environments\'][0]:\n' +
                   '    releaseVariables[\'new-environment\'] = globalVariables[\'global.blue-green.environments\'][1]\n' +
                   'else:\n' +
                   '    releaseVariables[\'new-environment\'] = globalVariables[\'global.blue-green.environments\'][0]\n'
          }
          userInput('Confirm new environment') {
            description '**Live version:** `${global.blue-green.live-version}`   \n' +
                        '**Current live environment:** `${global.blue-green.live-environment}`\n' +
                        '\n' +
                        '---\n' +
                        '\n' +
                        '**New version:** `${application}/${version}`.'
            variables {
              variable 'new-environment'
            }
          }
        }
      }
      phase('Deploy and test') {
        color '#ff9e3b'
        tasks {
          custom('Deploy ${application}/${version} to ${new-environment}') {
            description 'Use XL Deploy to do the actual deployment.'
            owner 'admin'
            script {
              type 'xldeploy.Deploy'
              server server1
              deploymentPackage '${application}/${version}'
              deploymentEnvironment '${new-environment}'
            }
          }
          manual('Run tests on ${new-environment}') {
            description '### Run acceptance tests\n' +
                        '\n' +
                        'Perform the acceptance tests before taking this version into production.\n' +
                        '\n' +
                        '**Application version:** `${application}/${version}`   \n' +
                        '**Environment:** `${new-environment}`'
          }
          gate('OK to switch?') {
            description '**Current environment:** `${global.blue-green.live-environment}`  \n' +
                        '**Current version:** `${global.blue-green.live-version}`\n' +
                        '\n' +
                        '---\n' +
                        '\n' +
                        '**New environment:** `${new-environment}`  \n' +
                        '**New version:** `${application}/${version}`\n' +
                        '\n' +
                        'The new version has been tested and the environment is ready to be switched over.\n' +
                        '\n' +
                        '\n' +
                        '###  >> Please confirm the switch by completing this task <<'
          }
        }
      }
      phase('Switch to live') {
        color '#68b749'
        tasks {
          manual('Reconfigure load balancer') {
            description 'Configure the load balancer to point to the environment running the new version.\n' +
                        '\n' +
                        '**New environment:** `${new-environment}`  \n' +
                        '**New version:** `${application}/${version}`\n' +
                        '\n' +
                        'Complete this task when the switch has been made.'
          }
          script('Update registry with live environment') {
            description 'This script updates the global variables that keep track of the live environment and live version.'
            script 'globalVariables[\'global.blue-green.live-environment\'] = \'${new-environment}\'\n' +
                   'globalVariables[\'global.blue-green.live-version\'] = \'${application}/${version}\'\n'
          }
          notification('Send notification that new version is live') {
            description 'Send out a notification to announce the new version being live.'
            precondition 'False ## Remove this line to send the email'
            subject '${application}/${version} is now live!'
            body '**${application}/${version}** has been deployed to **${new-environment}** and is now live!'
          }
        }
      }
    }
  }
}