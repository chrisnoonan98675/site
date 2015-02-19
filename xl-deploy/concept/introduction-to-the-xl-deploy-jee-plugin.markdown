---
title: Introduction to the XL Deploy JEE plugin
categories:
- xl-deploy
subject:
- JEE plugin
tags:
- jee
- java
- plugin
---

The XL Deploy JEE plugin provides support for Java EE archives such as EAR files and WAR files, as well as specifications for resources such as JNDI and mail session resources.

For information about the configuration items (CIs) that the JEE plugin provides, refer to the [JEE plugin reference](/xl-deploy/latest/jeePluginManual.html).

## Use in deployment packages

This is a sample of a deployment package (DAR) manifest that defines an EAR file, a WAR file, and a datasource:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="SampleApplication">
        <deployables>
            <jee.Ear name="earExample" file="earExample/example.ear">
            </jee.Ear>
            <jee.DataSourceSpec name="datasourceExample">
                <jndiName>jndi/datasource</jndiName>
            </jee.DataSourceSpec>
            <jee.War name="warExample" file="warExample/example.war">
            </jee.War>
        </deployables>
    </udm.DeploymentPackage>
