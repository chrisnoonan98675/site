---
title: Use tags to configure deployments
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- tag
- deployable
weight: 185
---

In XL Deploy, you can use the tagging feature to configure deployments by marking which deployables should be mapped to which containers. By using tagging, in combination with [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html), you can prepare your deployment packages and environments to automatically map deployables to containers and configuration details at deployment time.

To perform a deployment using tags, assign tags to deployables and containers. You can do so in an imported deployment package or in the XL Deploy user interface.

**Note:** It is not possible to use an environment variable in a tag.

## How XL Deploy matches tags

When deploying an application to an environment, XL Deploy will match the deployables and containers based on the following rules:

1. Both have **no** tags
1. Either is tagged with an asterisk (`*`)
1. Either is tagged with a plus sign (`+`) and the other has at least one tag
1. Both have at least one tag in common

If none of these rules apply, XL Deploy will not generate a deployed for the deployable-container combination.

This table shows which tags match when:

{:.table .table-striped}
| Deployable/container | No tags | Tag `*` | Tag `+` | Tag `X` | Tag `Y` |
| -------------------- | ------- | ------- | ------- | ------- | ------- |
| No tags | &#9989; | &#9989; | &#10060; | &#10060; | &#10060; |
| Tag `*` | &#9989; | &#9989; | &#9989; | &#9989; | &#9989; |
| Tag `+` | &#10060; | &#9989; | &#9989; | &#9989; | &#9989; |
| Tag `X` | &#10060; | &#9989; | &#9989; | &#9989; | &#10060; |
| Tag `Y` | &#10060; | &#9989; | &#9989; | &#10060; | &#9989; |

## Setting tags in the manifest file

This is an example of assigning a tag to a deployable in the `deployit-manifest.xml` file in a deployment package (DAR file):

{% highlight xml %}
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
{% endhighlight %}

For an example of tagged deployables in a Maven POM file, refer to the [Maven documentation](/xl-deploy/latest/maven-plugin/index.html).

## Tagging example

Assume you have a deployment package that contains two artifacts:

* An EAR file that represents a back-end application
* A WAR file that represents a front-end application

You want to deploy it to an environment that contains two containers:

* A [JBoss AS/WildFly](/xl-deploy/concept/jboss-domain-plugin.html) server where you want to deploy the back-end application (EAR file)
* An [Apache Tomcat](/xl-deploy/concept/tomcat-plugin.html) server where you want to deploy the front-end application (WAR file)

By default, XL Deploy maps the EAR and WAR files to the WildFly server, because WildFly can run both types of files. To prevent the WAR file from being deployed to the WildFly server, you would need to manually remove it from the mapping.

![Default mapping](images/tagged-deployment-no-tags.png)

To prevent XL Deploy from mapping the WAR file to the WildFly server, tag the WAR file and the Tomcat virtual host with the same tag.

![Tagged jee.War and tomcat.VirtualHost](images/tagged-deployment-artifact-and-container.png)

XL Deploy then maps the WAR file to the Tomcat virtual host only.

![Mapping with tags](images/tagged-deployment-with-tags.png)
