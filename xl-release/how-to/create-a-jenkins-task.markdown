---
title: Create a Jenkins task
categories:
- xl-release
subject:
- Task types
tags:
- task
- jenkins
---

The Jenkins task allows you to run a Jenkins job that is triggered when the task becomes active. The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail.

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Server | The Jenkins server to which XL Release connects. You can configure Jenkins servers in **Settings** > **Shared configuration** (**Settings** > **Configuration** prior to XL Deploy 6.0.0). |
| Username | Optional user name to use when connecting to the Jenkins server. Use this property to override the user that was configured on the Jenkins server. |
| Password | Optional password to use when connecting to the Jenkins server. Use this property to override the password that was configured on the Jenkins server. |
| Job Name | The name of the job that will be triggered. This job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add a `job` segment between each folder. For example, for a job that is located at `Applications/web/my portal`, use `Applications/job/web/job/my portal`. |
| Job Parameters | If the Jenkins job expects parameters, you can provide them, one parameter per line. The names and values of the parameters are separated by the first `=` character. |

The output properties of the task are **Build Number** and the **Build Status**. They can be stored in a variable of choice; in the example above, they are stored in the `${buildNumber}` and `${buildStatus}` release variables.

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Jenkins tasks have a blue border.

## Change the poll interval

While the task is running, it polls the Jenkins server every five seconds. In XL Release 4.8.0 and later, you can change this behavior by editing the `XL_RELEASE_SERVER_HOME/conf/deployit-default.properties` file. Locate the following lines:

    # Frequency of job progress requests, in seconds
    #jenkins.Server.pollInterval=5

Remove the `#` sign before `jenkins.Server.pollInterval` and edit the value. For example, to set the poll interval to 30 seconds, use:

    # Frequency of job progress requests, in seconds
    jenkins.Server.pollInterval=30

After saving `XL_RELEASE_SERVER_HOME/conf/deployit-default.properties`, restart the XL Release server for the changes to take effect.

## Configuration in Jenkins

There are two places to configure the XL Release plugin for Jenkins.

In the global Jenkins configuration at **Manage Jenkins** > **Configure System**, you can specify the XL Release server URL and one or more sets of credentials. Different credentials can be used for different jobs.

![XL Release plugin - global configuration](../images/jenkins_plugin_config.png)

In the job configuration page, select **Post-build Actions** > **Add post-build action** > **Release with XL Release**. Enter the template name in the box and select a value from the drop-down list.

**Tip:** In XL Release 6.0.0 and later, type `/` in front of a folder name to see the subfolders and template names in the specified folder.

![XL Release plugin - select a job](../images/jenkins_job_config.png)

After you select the template, click **Validate template** to validate it.

![XL Release plugin - validate template](../images/jenkins_validate_template.png)

To get information about each setting, click **?** located next to the setting.

## Using Jenkinsfile

In version 6.1.0 and later, you can use the [Jenkins Pipeline](https://jenkins.io/solutions/pipeline/) feature with the XL Release plugin for Jenkins. This feature allows you to create a "pipeline as code" in a Jenkinsfile, using the Pipeline DSL. You can then store the Jenkinsfile in a source control repository.

### Create a Jenkinsfile

To start using Jenkinsfile, create a pipeline job and add the Jenkinsfile content to the **Pipeline** section of the job configuration.

For information about the Jenkinsfile syntax, refer to the [Jenkins Pipeline documentation](https://jenkins.io/doc/book/pipeline/jenkinsfile/#creating-a-jenkinsfile). For information about the items you can use in the Jenkinsfile, click **Check Pipeline Syntax** on the job.

### Jenkinsfile example

The following Jenkinsfile can be used to build a pipeline and deploy a simple web application to a Tomcat environment configured in XL Release.

      node {  
      stage('Package') {  
        xldCreatePackage artifactsPath: 'build/libs', manifestPath: 'deployit-manifest.xml', darPath: '$JOB_NAME-$BUILD_NUMBER.0.dar'  
      }  
      stage('Publish') {  
        xldPublishPackage serverCredentials: '<user_name>', darPath: '$JOB_NAME-$BUILD_NUMBER.0.dar'  
      }  
      stage('Deploy') {  
        xldDeploy serverCredentials: '<user_name>', environmentId: 'Environments/Dev', packageId: 'Applications/<app_name>/$BUILD_NUMBER.0'  
      }
      stage('Start XLR Release') {
           xlrCreateRelease serverCredentials: '<user_name>', template: 'Release <app_name>', version: 'Release for $BUILD_TAG', variables: [[propertyName: 'version', propertyValue: '$BUILD_NUMBER.0']], startRelease: true
      }
    }  

## Release notes

### Version 6.1.0

#### Improvements

* Support Jenkins Pipeline, which allows you to create a "pipeline as code" in a Jenkinsfile that can be checked into source control

### Version 6.0.0

#### Improvements

* Support for XL Release 6.0.0 with folders
* Variable names shown without '${}'

### Version 5.0.0

#### Improvements

* REL-3468 - Support Jenkins credentials in XL Release plugin for Jenkins

**Note:** The Jenkins Credentials Plugin must be installed and enabled in Jenkins.
