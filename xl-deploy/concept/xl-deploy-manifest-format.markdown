---
title: XL Deploy manifest format
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- manifest
since:
- XL Deploy 3.9.0
---

The manifest file included in a deployment package (DAR file) describes the contents of the archive for XL Deploy. When importing a package, the manifest is used to construct CIs in XL Deploy's repository based on the contents of the imported package. The format is based on XML.

A valid XL Deploy XML manifest contains at least the following tags:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="PetClinic">
      <deployables>
        ...
      </deployables>
    </udm.DeploymentPackage>

## Adding artifacts

Within the deployable tags you can add the deployables that make up your package. Suppose that we have an ear file and a directory containing configuration files in our package. We specify those as follows.

    <deployables>
      <jee.Ear name="AnimalZooBE" file="AnimalZooBE-1.0.ear">
      </jee.Ear>
      <file.Folder name="configuration-files" file="conf">
      </file.Folder>
    </deployables>

The element name is the type of configuration item that will be created in XL Deploy. The name attribute corresponds to the name the configuration item will get, and the file attribute points to an actual resource found in the package.

## Adding resource specifications

The deployables element can contain more than just the artifacts that make up the package. You can also add resource specifications to it. For instance the specification for a datasource. These are defined in a similar manner as artifacts, but they do not contain the file attribute:

    <was.OracleDatasourceSpec name="petclinicDS">
      <url>jdbc:mysql://localhost/petclinic</url>
      <username>petclinic</username>
      <password>my$ecret</password>
    </was.OracleDatasourceSpec>

This will create a `was.OracleDatasourceSpec` with the properties `url`, `username` and `password` set to their corresponding values. <!--See the **Command Line Interface (CLI) Manual** for information or how to obtain the list of properties that a particular CI type supports, or consult the relevant CI reference documentation.-->

## Setting Complex Properties

The above example showed how to set string properties to a certain value. In addition to strings, XL Deploy also supports references to other CIs, sets of strings, maps of string to string, booleans and enumerations. Here are some examples.

### Properties referring to other CIs

    <sample.Sample name="referencing">
      <ciReferenceProperty ref="AnimalZooBE" />
      <ciSetReferenceProperty>
        <ci ref="AnimalZooBE" />
      </ciSetReferenceProperty>
      <ciListReferenceProperty>
        <ci ref="AnimalZooBE" />
      </ciListReferenceProperty>
    </sample.Sample>

### Set of strings properties

To set a _set of strings_ property to contain strings "a" and "b":

    <sample.Sample name="setOfStringSample">
      <setOfStrings>
        <value>a</value>
        <value>b</value>
      </setOfStrings>
    </sample.Sample>

### List of strings properties

To set a _list of strings_ property to contain strings "a" and "b":

    <sample.Sample name="listOfStringSample">
      <listOfStrings>
        <value>a</value>
        <value>b</value>
      </listOfStrings>
    </sample.Sample>

### Map of string to string properties

To set a _map of string to string_ property to contain pairs "key1", "value1" and "key2", "value2":

    <sample.Sample name="mapStringStringSample">
      <mapOfStringString>
        <entry key="key1">value1</entry>
        <entry key="key2">value2</entry>
      </mapOfStringString>
    </sample.Sample>

### Boolean and enumeration properties

To set a *Boolean* property to true or false:

    <sample.Sample name="booleanSample">
      <booleanProperty>true</booleanProperty>
      <anotherBooleanProperty>false</anotherBooleanProperty>
    </sample.Sample>

To set an *enum* property to a specific value:

    <sample.Sample name="enumSample">
      <enumProperty>ENUMVALUE</enumProperty>
    </sample.Sample>

## Embedded CIs

It is also possible to include embedded CIs in a deployment package. Embedded CIs are nested under their parent CI and property. Here is an example:

    <iis.WebsiteSpec name="NerdDinner-website">
      <websiteName>NerdDinner</websiteName>
      <physicalPath>C:\inetpub\nerddinner</physicalPath>
      <applicationPoolName>NerdDinner-applicationPool</applicationPoolName>
      <bindings>
        <iis.WebsiteBindingSpec name="NerdDinner-website/88">
          <port>8080</port>
        </iis.WebsiteBindingSpec>
      </bindings>
    </iis.WebsiteSpec>

The embedded CI `iis.WebsiteBindingSpec` CI type is an embedded CI under it's parent, `iis.WebsiteSpec`. The property `bindings` on the parent stores a list of `iis.WebsiteBindingSpec` instances.

## Using Placeholders in CI properties

XL Deploy supports the use of _placeholders_ to customize a package for deployment to a specific environment. CI properties specified in a manifest file can also contain placeholders. These placeholders are resolved from _dictionary_ CIs during a deployment. This is an example of using placeholders in CI properties in a `was.OracleDatasourceSpec` CI:

    <was.OracleDatasourceSpec name="petclinicDS">
      <url>jdbc:mysql://localhost/petclinic</url>
      <username>{% raw %}{{DB_USERNAME}}{% endraw %}</username>
      <password>{% raw %}{{DB_PASSWORD}}{% endraw %}</password>
    </was.OracleDatasourceSpec>

Please note that placeholders can also be used in the _name_ of a CI:

    <was.OracleDatasourceSpec name="{% raw %}{{PETCLINIC_DS_NAME}}{% endraw %}">
      <url>jdbc:mysql://localhost/petclinic</url>
      <username>{% raw %}{{DB_USERNAME}}{% endraw %}</username>
      <password>{% raw %}{{DB_PASSWORD}}{% endraw %}</password>
    </was.OracleDatasourceSpec>

XL Deploy also supports an alternative way of using dictionary values for CI properties. If the dictionary contains keys of the form `deployedtype.property`, these properties are automatically filled with values from the dictionary (provided they are not specified in the deployable). This makes it possible to use dictionaries without specifying placeholders. For example, the above could also have been achieved by specifying the following keys in the dictionary:

    was.OracleDatasource.username
    was.OracleDatasource.password

## Scanning for placeholders in artifacts

XL Deploy scans files in packages for the presence of placeholders. These will be added to the placeholders field in the artifact, so that they can be replaced upon deployment of said package.

The default behavior is to scan text files only. It's also possible to scan inside archives (Ear, War or Zip files), but this option is not active by default.

You can enable or disable placeholder scanning by setting the `scanPlaceholders` flag on an artifact.

    <file.File name="sample" file="sample.txt">
      <scanPlaceholders>false</scanPlaceholders>
    </file.File>

Using this technique, you can enable placeholder scanning inside a particular archive.

    <jee.Ear name="sample Ear" file="WebEar.ear">
      <scanPlaceholders>true</scanPlaceholders>
    </jee.Ear>

It's also possible to enable placeholder scanning for all archives. To do this edit `deployit-defaults.properties` and add the following line:

    udm.BaseDeployableArchiveArtifact.scanPlaceholders=true

To avoid scanning of binary files, only files with the following extensions are scanned:

    cfg, conf, config, ini, properties, props, txt, asp, aspx,
    htm, html, jsf, jsp, xht, xhtml, sql, xml, xsd, xsl, xslt

You can change this list by setting the `textFileNamesRegex` property on the `udm.BaseDeployableArtifact` in the `deployit-defaults.properties` file. Note that it takes a regular expression. It is also possible to change this on any of its subtypes which is important if you only want to change that for certain types of artifacts.

If you want to enable placeholder scanning, but the package contains several files that should *not* be scanned, use the `excludeFileNamesRegex` property on the artifact:

    <jee.War name="petclinic" file="petclinic-1.0.ear">
      <excludeFileNamesRegex>.*\.properties</excludeFileNamesRegex>
    </jee.War>

Note that the regular expression is only applied to the *name* of a file in a folder, not to its *path*. To exclude an entire folder, use a regular expression such as `.*exclude-all-files-in-here` (instead of `.*exclude-all-files-in-here/.*`).

## Custom deployment package support

If you have defined your own type of Deployment Package, or have added custom properties to the deployment package, you can import these by changing the manifest. Say that you've extended `udm.DeploymentPackage` as `myorg.PackagedApplicationVersion` which has additional properties such as `releaseDate` and `tickets`:

    <?xml version="1.0" encoding="UTF-8"?>
    <myorg.PackagedApplicationVersion version="1.0" application="PetClinic">
      <releaseDate>2013-04-02T16:22:00.000Z</releaseDate>
      <tickets>
        <value>JIRA-1</value>
        <value>JIRA-2</value>
      </tickets>
      <deployables>
        ...
      </deployables>
    </myorg.PackagedApplicationVersion>

## Specifying the location of the application

It is possible to specify where to import the package. This is useful during initial imports when the application does not yet exist. You can specify the path to the application like this:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="directory1/directory2/PetClinic">
        ...
    </udm.DeploymentPackage>

XL Deploy will then try to import the package into Application called `PetClinic` located at `Applications/directory1/directory2/PetClinic`. It will also perform the following checks:

- The user should have the correct import rights (import#initial or import#upgrade) for the directory.
- The path should exist. XL Deploy will not create it.
- The Application needn't exist yet, for an initial import XL Deploy will create it for you.
- If XL Deploy finds an application called PetClinic in another path, it will fail the import as application names should be unique.
