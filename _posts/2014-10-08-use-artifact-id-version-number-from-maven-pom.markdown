---
title: Use artifact ID and version number from Maven POM file in XL Deploy
categories:
- xl-deploy
tags:
- jenkins
- maven
---

You might want to deploy Maven-built EAR and WAR files to XL Deploy using the artifact ID and version number from the Maven [POM](http://maven.apache.org/pom.html) file; that is, have Maven deploy packages to XL Deploy as application `${artifactId}` with version `${version}`.

In this example, we will use the [Jenkins EnvInject Plugin](https://wiki.jenkins-ci.org/display/JENKINS/EnvInject+Plugin) to enrich the Jenkins environment with additional values. Then, we will use [Groovy](http://groovy.codehaus.org/) to get data from the POM file. This script (stored at `~jenkins/bin/eval-pom`) outputs the artifact and version values to the standard output as Unix environment strings:

    #!/usr/bin/env groovy
    def root = new XmlParser().parse(new File("pom.xml"))
    def artifactId = root.artifactId.text()
    def version = root.version.text()
 
    print """
    MAVEN_ARTIFACTID=$artifactId
    MAVEN_VERSION=$version
    """

Now, we need to add two new post steps to the Jenkins build job:

* Execute shell, calling `/home/jenkins/bin/eval-pom | tee target/pom.properties` to write the environment properties to the desired temporary file
* Inject environment variables to read them into the environment from that temporary file (`target/pom.properties`)

![Jenkins post steps]({{ site.url }}/images/use-artifact-id-version-number-from-maven-pom-post-steps.png)

Now we have two environment variables set (`$MAVEN_ARTIFACTID` and `$MAVEN_VERSION`) and can create a Jenkins post-build action **Deploy with Deployit**:

* Set the **Application** to `Applications/$MAVEN_ARTIFACTID`
* Set the **Version** to `$MAVEN_VERSION` and optionally extend it with `-$BUILD_NUMBER"` (so there will be a reference to the Jenkins build)
* Select **Package application**"; for example, for the type `was.Ear`:
    * Name: `$MAVEN_ARTIFACTID`
    * Location `target/$MAVEN_ARTIFACTID-$MAVEN_VERSION.ear`
    * Select **Publish package to Deployit**

![Jenkins post-build action]({{ site.url }}/images/use-artifact-id-version-number-from-maven-pom-post-build-action.png)