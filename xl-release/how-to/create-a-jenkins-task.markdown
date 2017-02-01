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

The Jenkins task allows you to run a Jenkins job that is triggered when the task becomes active.

The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail.

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

## Change the poll interval (XL Release 4.8.x, 5.0.x, and 6.0.x)

While the task is running, it polls the Jenkins server every five seconds. You can change this behavior by editing the `conf\deployit-default.properties` file in the XL Release installation directory.

Locate the following lines:

    # Frequency of job progress requests, in seconds
    #jenkins.Server.pollInterval=5

Remove the `#` sign before `jenkins.Server.pollInterval` and edit the value. For example, to set the poll interval to 30 seconds, use:

    # Frequency of job progress requests, in seconds
    jenkins.Server.pollInterval=30

After saving `conf\deployit-default.properties`, restart the XL Release server for the changes to take effect.

## **Using Jenkinsfile**

* In XL Release 6.1.0 and later, you can use the Jenkins Pipeline feature with the XL Release plugin for Jenkins. This feature allows you to create a "pipeline as code" in a Jenkinsfile, using the Pipeline DSL. You can then store the Jenkinsfile in a source control repository.

* Example:
  The following Jenkinsfile can be used to build the pipeline and deploy a simple web-application to a tomcat environment configured in  xl-release.

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

## **Configuration:**
  In jenkins add the above Jenkinsfile file content to the pipeline script in jenkins and make sure that the xl-release plugin is added to the
  jenkins setup.


## **Configuration**

* There are two places to configure the XL Release Jenkins Plugin: the global Jenkins configuration and job configuration.

**Plugin configuration**

* At Manage Jenkins \-> Configure System you can specify the XL Release server URL and one or more sets of credentials. Different credentials can be used for different jobs.

![plugin configuration](../images/jenkins_plugin_config.png)

**Job configuration**

* In the Job Configuration page, choose Post-build Actions \-> Add post-build action \-> Release with_ XL Release. To get an explanation of each setting, click on the question mark sign located right next to each setting.

Type the template name in the box and select a value from the drop down.
*Note* For XL Release version later than 6.0.0, type the '/' suffix in front of the folder name. This allows you to see the subfolders and template names in the specified folder.

  ![plugin configuration](../images/jenkins_job_config.png)

After selecting the appropriate template click on verify template to validate it.
  ![plugin configuration](../images/jenkins_validate_template.png)
