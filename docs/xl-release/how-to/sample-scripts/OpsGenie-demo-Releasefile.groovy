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
def OpsGenieConfig = server('opsgenie.config','opsgenie')

xlr {
  release('OpsGenie Demo') {
    variables {
      stringVariable('alert_id') {
        required false
        showOnReleaseStart false
      }
      stringVariable('nagios_integration_id') {
        label 'Nagios integration Id in OpsGenie'
      }
      stringVariable('alert_id_1') {
        required false
        showOnReleaseStart false
      }
      stringVariable('fail_alert') {
        required false
        showOnReleaseStart false
      }
      booleanVariable('create_alert') {
        required false
        showOnReleaseStart false
        label 'Create Alert'
      }
    }
    scheduledStartDate Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", '2017-12-06T09:00:00+0530')
    phases {
      phase('Production Deployment') {
        color '#009CDB'
        tasks {
          gate('Start Prod Deployment Prechecks') {
            conditions {
              condition('UAT Sign Off')
              condition('Users Notified of scheduled downtime')
            }
          }
          custom('Disable Nagios Integration in OpsGenie') {
            script {
              type 'opsgenie.disableIntegration'
              'opsGenieConfig' OpsGenieConfig
              integrationId '${nagios_integration_id}'
            }
          }
          manual('Move traffic from DC1 to DC2') {
            
          }
          custom('Create alert for server maintenance') {
            script {
              type 'opsgenie.createAlert'
              'opsGenieConfig' OpsGenieConfig
              message 'App Server in DC1 is down'
              alias 'app_server_dc1'
              description 'App Server in DC1 is down'
              teams 'Dev_T', 'Operation_T'
              recipients 'Operation_T':'team','Dev_T':'team'
              actions 'ping'
              tags 'appServer', 'DC1'
              entity 'APP server'
              priority 'P2'
              user 'tmehra@xebia.com'
              note 'created from XLR'
              alertId variable('alert_id_1')
            }
          }
          custom('Deployment on DC1') {
            script {
              type 'xldeploy.Deploy'
              
            }
          }
          userInput('Create deployment fail alert ?') {
            variables {
              variable 'create_alert'
            }
          }
          custom('Create alert if deployment fails') {
            precondition 'if ${create_alert}:\n' +
                         '    result = True\n' +
                         'else:\n' +
                         '    result = False'
            script {
              type 'opsgenie.createAlert'
              'opsGenieConfig' OpsGenieConfig
              message 'Deployment failed on DC1 in release ${release.title}'
              alias 'App_Deployment'
              description 'Deployment failed on DC1 in release ${release.title}'
              teams 'DEV_T'
              actions 'restart'
              tags 'xld', 'app'
              entity 'App server'
              priority 'P1'
              user 
              alertId variable('fail_alert')
            }
          }
          custom('Snooze server maintenance alert for 30 minutes') {
            script {
              type 'opsgenie.snoozeAlert'
              'opsGenieConfig' OpsGenieConfig
              alertIdentifier '${alert_id_1}'
              endTime 
              snoozeTime 30
            }
          }
          custom('Execute custom action on alert') {
            script {
              type 'opsgenie.executeCustomAction'
              'opsGenieConfig' OpsGenieConfig
              alertIdentifier '${fail_alert}'
              action 'restart'
            }
          }
          gate('Proceed to complete deployment and close alerts') {
            
          }
          custom('Close deployment fail alert') {
            script {
              type 'opsgenie.closeAlert'
              'opsGenieConfig' OpsGenieConfig
              alertIdentifier '${fail_alert}'
            }
          }
          custom('Close server maintenance alert') {
            script {
              type 'opsgenie.closeAlert'
              'opsGenieConfig' OpsGenieConfig
              alertIdentifier '${alert_id_1}'
            }
          }
          custom('Enable Nagios integration in OpsGenie') {
            script {
              type 'opsgenie.enableIntegration'
              'opsGenieConfig' OpsGenieConfig
              integrationId '${nagios_integration_id}'
            }
          }
          custom('Delete fail alert') {
            script {
              type 'opsgenie.deleteAlert'
              'opsGenieConfig' OpsGenieConfig
              alertIdentifier '${fail_alert}'
            }
          }
        }
      }
    }
  }
}