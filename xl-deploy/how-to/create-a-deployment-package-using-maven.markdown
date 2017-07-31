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
weight: 213
---

To enable continuous deployment, the [XL Deploy Maven plugin](/xldeploy-maven-plugin/latest/index.html) enables you to integrate XL Deploy with the Maven build system. Specifically, the plugin supports:

* Creating a deployment package containing artifacts from the build
* Performing a deployment to a target environment
* Undeploying a previously deployed application

**Note:** The XL Deploy Maven plugin cannot set values for hidden CI properties.

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
                <include>** /* </include>
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

### Managing application dependencies

You can declare your application dependencies in Maven by defining the properties in the `deploymentPackageProperties` node. This is a sample snippet you can add to the `pom.xml` file using your specific properties:

    <deploymentPackageProperties>
        <applicationDependencies>
          <entry key="BackEnd">[2.0.0,2.0.0]</entry>
        </applicationDependencies>
        <orchestrator>parallel-by-container</orchestrator>
        <satisfiesReleaseNotes>true</satisfiesReleaseNotes>
    </deploymentPackageProperties>

Make sure that the dependent package is already present in XL Deploy and has the correct version as configured in the `pom.xml` file.     

For more information about application dependencies, refer to [Application dependencies in XL Deploy](/xl-deploy/concept/application-dependencies-in-xl-deploy.html).
