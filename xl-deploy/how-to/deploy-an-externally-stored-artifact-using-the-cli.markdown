---
title: Deploying an externally stored artifact using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- cli
- tutorial
---

This tutorial will show you how to use the XL Deploy command-line interface (CLI) to deploy an artifact from a Maven repository such as Artifactory or Nexus. This tutorial uses [this sample application](http://search.maven.org/#artifactdetails%7Cio.brooklyn.example%7Cbrooklyn-example-hello-world-webapp%7C0.7.0-M1%7Cwar), which is a WAR file that you can deploy to middleware such as Apache Tomcat or JBoss AS/WildFly.

## Step 1 Identify the application by its GAV definition

First, in [Artifactory](https://www.jfrog.com/confluence/display/RTF/Searching+for+Artifacts) or [Nexus](https://books.sonatype.com/nexus-book/reference/using-sect-uploading.html), identify the application by its GAV definition in the following format:

    maven:groupId:artifactId:packaging:classifier:version

For example, for the [sample application](http://search.maven.org/#artifactdetails%7Cio.brooklyn.example%7Cbrooklyn-example-hello-world-webapp%7C0.7.0-M1%7Cwar), the GAV definition is:

    maven:io.brooklyn.example:brooklyn-example-hello-world-webapp:war:0.7.0-M1

## Step 2 Create and deploy the application

Next, create the application in the XL Deploy repository. You can use a `jee.War` configuration item (CI) to represent the application artifact; however, instead of uploading the artifact to XL Deploy, you can refer to its location in the external repository. Then, you can deploy the application to an environment by executing the following commands:

    admin > myApp = factory.configurationItem('Applications/myApp', 'udm.Application')
    admin > repository.create(myApp)
    PyInstance: Applications/myApp
    admin > myApp1_0 = factory.configurationItem('Applications/myApp/1.0', 'udm.DeploymentPackage')
    admin > repository.create(myApp1_0)
    PyInstance: Applications/myApp/1.0
    admin > myFile = factory.configurationItem('Applications/myApp/1.0/demo','jee.War', {'fileUri': 'maven:io.brooklyn.example:brooklyn-example-hello-world-webapp:war:0.7.0-M1'})
    admin > repository.create(myFile)
    PyInstance: Applications/myApp/1.0/demo
    admin > package = repository.read('Applications/myApp/1.0')
    admin > environment = repository.read('Environments/Dev/TEST')
    admin > deploymentRef = deployment.prepareInitial(package.id, environment.id)
    admin > depl = deployment.prepareAutoDeployeds(deploymentRef)
    admin > task = deployment.createDeployTask(depl)
    admin > deployit.startTaskAndWait(task.id)

**Note:** This example assumes that the `Environments/Dev/TEST` environment already exists and contains the appropriate infrastructure items (such as a Tomcat virtual host or a JBoss Domain). For information about using the CLI to create infrastructure items and environments, refer to [Work with configuration items in the XL Deploy CLI](/xl-deploy/how-to/work-with-cis-in-the-cli.html).

### Using a Python file

Alternatively, you can put the commands in a Python script and execute the script from the CLI. This allows you to modularize the code and pass in variables. For example:

    myApp = factory.configurationItem('Applications/myApp', 'udm.Application')
    repository.create(myApp)
    myApp1_0 = factory.configurationItem('Applications/myApp/1.0', 'udm.DeploymentPackage')
    repository.create(myApp1_0)
    myFile = factory.configurationItem('Applications/myApp/1.0/demo','jee.War',{'fileUri':'maven:io.brooklyn.example:brooklyn-example-hello-world-webapp:war:0.7.0-M1'})
    repository.create(myFile)
    package = repository.read('Applications/myApp/1.0')
    environment = repository.read('Environments/Dev/TEST')
    depl = deployment.prepareInitial(package.id, environment.id)
    depl2 = deployment.prepareAutoDeployeds(depl)
    task = deployment.createDeployTask(depl)
    deployit.startTaskAndWait(task.id)

**Tip:** Use the [`-f` option](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html#cli-startup-options) to run the CLI with a Python file.

## Step 3 Verify the deployment

If you deployed the application to a Tomcat or JBoss instance running on local port 8080, you can verify the deployed application at [http://localhost:8080/demo](http://localhost:8080/demo).
