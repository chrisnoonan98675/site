---
title: Introduction to the XL Deploy OSB plugin
categories:
- xl-deploy
subject:
- Oracle Service Bus plugin
tags:
- osb
- oracle
- plugin
- middleware
---

The XL Deploy Oracle Service Bus (OSB) plugin supports importing and deleting OSB projects.

For information about the configuration items (CIs) that the OSB plugin provides, refer to the [OSB Plugin Reference](/xl-deploy/latest/osbPluginManual.html).

## Features

* OSB configuration (import and delete)
* OSB customization files (during import) with placeholder replacement

## Use in deployment packages

This is a sample deployment package (DAR) manifest file (`deployit-manifest.xml`). It contains declarations for an `osb.Configuration` CI that contains `project1` and `project2`.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="2.0" application="SampleApplication">
        <deployables>
            <osb.Configuration name="osbConfiguration" file="osbConfiguration/osbConfiguration.jar">
                <projectNames>
                    <value>project2</value>
                    <value>project1</value>
                </projectNames>
            </osb.Configuration>
        </deployables>
    </udm.DeploymentPackage>

`osb.Configuration` is a folder that contains one JAR file containing the projects and one or more customization files in XML.
