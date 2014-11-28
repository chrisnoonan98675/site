---
title: Build a package in XL Deploy from properties in files, dictionaries, and command arguments
categories:
- xl-deploy
tags:
- cli
- application
- package
---

Your build process might not feature a tool like [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin), which can integrate with XL Deploy. In this case, you can use [this CLI script](/sample-scripts/build-deployment-package-from-properties/createDAR.py) to build an XL Deploy deployment package (DAR file) from properties that you can specify in:

* A file
* An XL Deploy dictionary
* Command arguments

The script will create a package with an arbitrary number of artifacts and can optionally also deploy the package to an environment.

First, let's look at the properties format. This is an example that will build a package with two artifacts, a WAR artifact and a JBoss datasource:

	artifact.type=jee.War
	artifact.fileLocation=/Users/tom/qb_repo/builds/17/artifacts/dist/Cars_Sample_App.war
	artifact.name=Cars_Sample_App
	artifact.tags=['WAR']
	datasource.type=jbossas.NonTransactionalDatasourceSpec
	datasource.name=Tom_Datasource
	datasource.jndiName=jndi/tomsds
	datasource.userName={% raw %}{{DB_USERNAME}}{% endraw %}
	datasource.password={% raw %}{{DB_PASSWORD}}{% endraw %}
	datasource.connectionUrl={% raw %}{{DB_CONNECTION_URL}}{% endraw %}

Note that:

* Keys are always in the format `artifactID.property`, where `artifactID` is a identifier used by the script for each artifact and `property` is the property to set on that artifact.
* All artifacts must have `name` and `type` properties.
* When a `fileLocation` property is set, it must refer to the path of a file on the file system where the script is being executed.
* When specifying something that is of type `list` in XL Deploy (such as tags), you must specify it using the Python `list` literal format.
* You can use placeholders, just as when creating artifacts in the XL Deploy interface.

You can use the format above to specify properties in an XL Deploy dictionary. The dictionary must be stored in `Environments/AppDictionaries/<AppName>` by convention. For example:

![Sample dictionary](/images/build-deployment-package-from-properties/sample-dictionary.png)

You can also set properties as command-line arguments to the script. When using this approach, you must set these properties using the Python `dict` literal format. For example:

    "{'webContent.type' : 'www.WebContent', 'webContent.name' : 'myWebContent', 'webContent.fileLocation' : '/Users/tom/scratch/PetPortal_pages.zip'}"

To use the script:

    cli.sh -host <XLDeployHost> -username <username> -password <password> -f $ScriptPath/createDar.py -- -n <appName> -b <buildID> -p <propertiesFile> -a <autoDeploy true|false> -e <deployEnv> -x <extraProperties>

For example:

    cli.sh -username admin -password deploy -source /Users/tom/Documents/CreateDARcli/createDAR.py -- -n TestApp -b 1.0-6 -p /Users/tom/builds/propertiesSample.properties -x "{'webContent.type' : 'www.WebContent', 'webContent.name' : 'myWebContent', 'webContent.fileLocation' : '/Users/tom/scratch/PetPortal_pages.zip'}" -a true -e "MyEnvironment"

This will create a package called `1.0-6` (from the build argument) for application `TestApp`. It will also deploy `TestApp` to `MyEnvironment`.
