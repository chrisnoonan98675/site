---
title: Old-style manifest format
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- manifest
since:
- 3.5.0
deprecated:
- 4.5.0
removed:
- 5.0.0
---

The new XML Manifest has been introduced in XL Deploy 3.9. In previous versions the manifest was specified using the `MANIFEST.MF` format described in the [JAR file specification](http://download.oracle.com/javase/6/docs/technotes/guides/jar/jar.html). XL Deploy can still import DAR packages containing this manifest format. This chapter is for reference purposes only as we strongly encourage you to write the manifests in the new XML format.

A valid XL Deploy manifest starts with the following preamble:

    Manifest-Version: 1.0
    XL Deploy-Package-Format-Version: 1.3

This identifies the Java manifest version and the XL Deploy package format version.

## Specifying the Application

A deployment package contains a specific version of an application. These entries tell XL Deploy that the package contains the _AnimalZoo_ application version _4.0_:

    CI-Application: AnimalZoo-ear
    CI-Version: 4.0

These entries are part of the manifest preamble.

## Specifying Artifacts

Artifacts are represented by files or folders in the deployment package. To import an artifact as a CI in XL Deploy, use:

    Name: AnimalZooBE-1.0.ear
    CI-Type: jee.Ear
    CI-Name: AnimalZooBE

The standard `Name` entry must refer to an actual file included in the DAR. The `CI-Type` specifies a CI type name that is available in the system. The required `CI-Name` property indicates the name of the CI to be created.

Similarly, this construct can be used to create a folder CI:

    Name: conf
    CI-Type: file.Folder
    CI-Name: configuration-files

## Specifying Resource Specifications

Resource specifications are not represented by files in the deployment package. They represent resources that must be created on the target middleware when the application is deployed. Resource specifications are constructed completely based on the information in the manifest. The manifest also specifies values for properties of the CI.

For example, this manifest snippet constructs a _was.OracleDatasourceSpec_ CI:

    Name: petclinicDS
    CI-Type: was.OracleDatasourceSpec
    CI-driver: com.mysql.jdbc.Driver
    CI-url: jdbc:mysql://localhost/petclinic
    CI-username: petclinic
    CI-password: my$ecret

The `Name` entry specifies the name of the CI to be created. In contrast to the manifest specification, the `Name` property in a XL Deploy manifest does not refer to a physical file present in the DAR in the case of a resource specification. The `CI-Type` specifies a CI type that is available in the system.

**Note:** the names of artifacts in your package must conform to platform requirements. For instance, a _file.Folder_ CI with name "q2>2" cannot be deployed to a Windows host, because ">" may not be part of a file or directory name in Windows.

The other entries, `CI-url`, `CI-username` and `CI-password` refer to properties on the datasource CI. These properties will be set to the values specified. In general, any property on a CI can be set using the `CI-<propertyname>` notation. See the **Command Line Interface (CLI) Manual** for information or how to obtain the list of properties that a particular CI type supports, or consult the relevant CI reference documentation.

Note that it is also possible to add resource specifications to a package that is already imported in XL Deploy. See the **Command Line Interface (CLI) Manual** for more information.

## Setting Complex Properties

The above example showed how to set string properties to a certain value. In addition to strings, XL Deploy also supports references to other CIs, sets of strings, maps of string to string, booleans and enumerations. Here are some examples.

### Properties referring to other CIs

    Name: myResourceSpecName
    ...

    Name: my-artifact-file-name.ext
    CI-Name: myArtifactCiName
    ...

    ...
    CI-artifactRefProperty: my-artifact-file-name.ext
    CI-resourceSpecRefProperty: myResourceSpecName

    ...
    CI-setOfCIProperty-EntryValue-1: myResourceSpecName
    CI-setOfCIProperty-EntryValue-2: my-artifact-file-name.ext

    ...
    CI-listOfCIProperty-EntryValue-1: myResourceSpecName
    CI-listOfCIProperty-EntryValue-2: my-artifact-file-name.ext

_Note that references use the referent's **Name** property._

### _Set of strings_ properties

To set a _set of strings_ property to contain strings "a" and "b":

    CI-setOfStringProperty-EntryValue-1: a
    CI-setOfStringProperty-EntryValue-2: b

### _List of strings_ properties

To set a _list of strings_ property to contain strings "a" and "b":

    CI-listOfStringProperty-EntryValue-1: a
    CI-listOfStringProperty-EntryValue-2: b

### _Map of string to string_ properties

To set a _map of string to string_ property to contain pairs "key1", "value1" and "key2", "value2":

    CI-mapOfStringToStringProperty-key1: value1
    CI-mapOfStringToStringProperty-key2: value2

### _Boolean_ and _enumeration_ properties

TO set a boolean property to true or false:

    CI-boolProperty: true
    CI-boolProperty: false

To set an enum property to a specific value:

    CI-enumProperty: ENUMVALUE

## Using Placeholders in CI properties

Similar to the XML format, you can use placeholders to fill values from Dictionaries.

    Name: petclinicDS
    CI-Type: was.OracleDatasourceSpec
    CI-driver: com.mysql.jdbc.Driver
    CI-url: jdbc:mysql://localhost/petclinic
    CI-username: {{DB_USERNAME}}
    CI-password: {{DB_PASSWORD}}

Please note that placeholders can also be used in the _name_ of a CI but they cannot be specified in the _Name_ field in the `MANIFEST.MF` format. Use the _CI-Name_ format instead, e.g.:

    Name: petclinicDS
    CI-Name: {{PETCLINIC_DS_NAME}}
    CI-Type: was.OracleDatasourceSpec
    CI-driver: com.mysql.jdbc.Driver
    CI-url: jdbc:mysql://localhost/petclinic
    CI-username: {{DB_USERNAME}}
    CI-password: {{DB_PASSWORD}}
