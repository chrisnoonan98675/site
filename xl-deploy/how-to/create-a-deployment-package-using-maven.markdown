---
title: Create a deployment package using Maven
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- maven
---

## Using the Maven XL Deploy plugin

To enable continuous deployment, the [Maven XL Deploy plugin](/xl-deploy/latest/maven-plugin/index.html) enables you to integrate XL Deploy with the Maven build system. Specifically, the plugin supports:

* Creating a deployment package containing artifacts from the build
* Performing a deployment to a target environment
* Undeploying a previously deployed application

## Using the Maven `jar` plugin

The standard Maven `jar` plugin can also be used to create a XL Deploy package.

* Create a manifest file conforming to the XL Deploy manifest standard
* Create a directory structure containing the files as they should appear in the package

In the Maven POM, configure the `jar` plugin as follows:

    <project>
      ...
      <build>
        <plugins>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            ...
            <configuration>
              <includes>
                <include>**/*</include>
              </includes>
            </configuration>
            ...
          </plugin>
        </plugins>
      </build>
      ...
    </project>

To generate an XL Deploy package, execute:

    mvn package
