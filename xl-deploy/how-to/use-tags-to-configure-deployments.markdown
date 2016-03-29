---
title: Use tags to configure deployments
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- tag
- application
- package
---

XL Deploy's tagging feature allows you to configure deployments by marking which deployables should be mapped to which containers. Tagging, in combination with [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html), allows you to prepare your deployment packages and environments so you do not have to manually map deployables to containers or provide configuration details at deployment time.

For information about how XL Deploy matches tagged deployables and containers, refer to [How XL Deploy matches tags](/xl-deploy/concept/how-xl-deploy-matches-tags.html).

To perform a deployment using tags, you assign tags to deployables and containers. You can do so in an imported deployment package or in the XL Deploy user interface.

**Note:** It is not possible to use an environment variable in a tag.

## Setting tags in the manifest file

This is an example of assigning a tag to a deployable in the `deployit-manifest.xml` file in a deployment package (DAR file):

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="MyApp">
        <orchestrator />
        <deployables>
            <jee.War name="Frontend-WAR" file="Frontend-WAR/MyApp-1.0.war">
                <tags>
                    <value>FRONT_END</value>
                </tags>
                <scanPlaceholders>false</scanPlaceholders>
                <checksum>7e21b7dd23d96a0b1da9abdbe1a2b6a56467e175</checksum>
            </jee.War>
        </deployables>
    </udm.DeploymentPackage>

For an example of tagged deployables in a Maven POM file, refer to the [Maven documentation](/xl-deploy/latest/maven-plugin/index.html).

## Tagging example

Assume you have a deployment package that contains two artifacts:

* An EAR file that represents a back-end application
* A WAR file that represents a front-end application

You want to deploy it to an environment that contains two containers:

* A [JBoss AS/WildFly](/xl-deploy/concept/introduction-to-the-xl-deploy-jboss-domain-plugin.html) server where you want to deploy the back-end application (EAR file)
* An [Apache Tomcat](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html) server where you want to deploy the front-end application (WAR file)

By default, XL Deploy maps the EAR and WAR files to the WildFly server, because WildFly can run both types of files. To prevent the WAR file from being deployed to the WildFly server, you would need to manually remove it from the mapping.

![Default mapping](images/tagged-deployment-no-tags.png)

To prevent XL Deploy from mapping the WAR file to the WildFly server, tag the WAR file and the Tomcat virtual host with the same tag.

![Tagged jee.War and tomcat.VirtualHost](images/tagged-deployment-artifact-and-container.png)

XL Deploy then maps the WAR file to the Tomcat virtual host only.

![Mapping with tags](images/tagged-deployment-with-tags.png)

