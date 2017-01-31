---
title: Introduction to the Jenkins XL Deploy plugin
categories:
- xl-deploy
subject:
- Jenkins
tags:
- jenkins
- package
- deployment
---

The XL Deploy plugin for [Jenkins CI](/xl-deploy/how-to/create-a-deployment-package-using-jenkins.html) adds three post-build actions that you can use independently or together:

* Package an application
* Publish a deployment package to XL Deploy
* Deploy an application

For more information about using the plugin, refer to:

* [Create a deployment package using Jenkins](/xl-deploy/how-to/create-a-deployment-package-using-jenkins.html)
* [The XL Deploy plugin on the Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin)

## Features

* Deploy package (DAR) packaging:
    * With artifacts created by the Jenkins job
    * With other artifacts or resources
* Publish DAR packages to XL Deploy
* Trigger deployments in XL Deploy
* Auto-scale deployments to modified environments
* Execute on Microsoft Windows or Unix slave nodes

## Using Jenkinsfile

In XL Deploy 6.1.0 and later, you can use the [Jenkins Pipeline](https://jenkins.io/solutions/pipeline/) feature with the XL Deploy plugin for Jenkins. This feature allows you to create a "pipeline as code" in a [Jenkinsfile](https://jenkins.io/doc/book/pipeline/jenkinsfile/), using the Pipeline DSL. You can then store the Jenkinsfile in a source control repository.

### Configuration

In Jenkins, ensure that the [XL Deploy plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin) is installed in and add the Jenkinsfile content to the pipeline script.

### Example

The following Jenkinsfile can be used to build the pipeline and deploy a simple web application to a Tomcat environment configured in XL Deploy:

     node {  
     stage('Checkout') {  
        git url: 'https://github.com/xebialabs/rest-o-rant-api.git'  
     }  
     stage('Build') {  
        if (isUnix()) {  
            sh "./gradlew clean build"  
        } else {  
           bat("gradlew.bat clean build")  
        }  
      }  
      stage('Package') {  
        xldCreatePackage artifactsPath: 'build/libs', manifestPath: 'deployit-manifest.xml', darPath: '$JOB_NAME-$BUILD_NUMBER.0.dar'  
      }  
      stage('Publish') {  
        xldPublishPackage serverCredentials: 'Admin', darPath: '$JOB_NAME-$BUILD_NUMBER.0.dar'  
      }  
      stage('Deploy') {  
        xldDeploy serverCredentials: 'Admin', environmentId: 'Environments/Dev', packageId: 'Applications/rest-o-rant-api/$BUILD_NUMBER.0'  
      }  
    }  
