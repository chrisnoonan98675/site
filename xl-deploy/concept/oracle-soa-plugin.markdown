---
title: Introduction to the XL Deploy Oracle SOA plugin
categories:
- xl-deploy
subject:
- Oracle SOA
tags:
- soa
- oracle
- plugin
- middleware
since:
- XL Deploy 5.0.0
---

The XL Deploy Oracle Service-Oriented Architecture (SOA) plugin supports:

- Importing and deleting MDS items
- Deploying and undeploying SOA composites
- Updating SOA technology adapters in Oracle Fusion
- Updating extension libraries for SOA Suite
- Uploading XSL Resources to the SOA server hosts
- Deploying user message drivers to the SOA servers

For information about the configuration items (CIs) that the Oracle SOA plugin provides, refer to the [Oracle SOA Plugin Reference](/xl-deploy-xld-oracle-soa-plugin/latest/oracleSoaPluginManual.html).

## Features

* Import and delete MDS folders
* Deploy SOA composite services
* Add resources to technology adapters
* Add XSL resources to use in transformations
* Define user messaging drivers

## Using the plugin configuration items

The `soa.MdsSpec` CI is a JAR file that contains:

* One MDS folder with its subfolders and elements

The `soa.CompositeSpec` CI is a ZIP file that contains:

* One deployable SCA archive file
* One configuration plan XML file

When you use the XL Deploy user interface to upload a `soa.CompositeSpec` artifact, you must ensure that the SCA JAR file and the configuration plan XML file are at the root of the ZIP file.

The `soa.<adapter>ConnectionFactorySpec` CI:

* Contains no artifact
* Declares the properties for the corresponding <*adapter*>: DB, File, FTP, MQ or JMS

The `soa.ExtensionLibrarySpec` CI is a JAR file that contains:

* Custom java classes

The SOA ext folder, where the ANT build is executed is specified by the *AntHomeDirectory* property.

The `soa.XSLResourcesSpec` CI is a ZIP file that contains:

* A folder with XSL resources

The `soa.UserMessagingdriverEmail` CI:

* Contains no artifact
* Describes a driver email specification by its properties

## Use in deployment packages

This is a sample deployment package (DAR) manifest file (`deployit-manifest.xml`). It contains declarations for an `soa.CompositeSpec`.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="SOA11TestApp">
      <orchestrator />
      <application />
      <deployables>
    <soa.CompositeSpec name="MyPOProcessing" file="MyPOProcessing/sca_MyPOProcessing_rev1.0.zip">
      <tags />
      <scanPlaceholders>true</scanPlaceholders>
      <checksum>1.0</checksum>
      <partition>default</partition>
      <forcedefault>true</forcedefault>
    </soa.CompositeSpec>
      </deployables>
      <applicationDependencies />
    </udm.DeploymentPackage>
