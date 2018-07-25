---
title: Using placeholders in deployments
categories:
- xl-deploy
subject:
- Dictionaries
tags:
- placeholder
- dictionary
- package
- environment
weight: 160
---

Placeholders are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable. At deployment time, you can provide values for placeholders manually or they can be resolved from [dictionaries](/xl-deploy/how-to/create-a-dictionary.html) that are assigned to the target environment.

When you *update* an application, XL Deploy will resolve the values for placeholders again from the dictionary. For more information, refer to [Resolving properties during application updates](/xl-deploy/concept/resolving-properties-during-application-updates.html).

**Important:** Placeholders are designed to be used for small pieces of data, such as a user name or file path. Although XL Deploy does not limit the length of placeholder values stored in dictionaries, it is recommended that you avoid dictionary keys or values over 100 characters in length. Longer values will degrade performance.

This topic describes placeholders using for deployments; for information about placeholders that can be used with the [XL Deploy provisioning feature](/xl-deploy/concept/provisioning-through-xl-deploy.html), refer to [Using placeholders with provisioning](/xl-deploy/how-to/using-placeholders-with-provisioning.html).

## Placeholder format

XL Deploy recognizes placeholders using the following format:

	{% raw %}{{ PLACEHOLDER_KEY }}{% endraw %}

## File placeholders

_File_ placeholders are used in artifacts in a deployment package. XL Deploy scans packages that it imports for files and searches them files for file placeholders. It determines which files need to be scanned based on their extension. The following items are scanned:

* File-type CIs
* Folder-type CIs
* Archive-type CIs

Before a deployment can be performed, a value must be specified for **all** file placeholders in the deployment.

### Special file placeholder values

There are two special placeholder values for file placeholders:

* `<empty>` replaces the placeholder key with an empty string
* `<ignore>` ignores the placeholder key, leaving it as-is

The angle brackets (`<` and `>`) are required for these special values.

**Note:** A file placeholder that contains other placeholders does not support the special `<empty>` value.

### Using different file placeholder delimiters

If you want to use delimiters other than {% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} in artifacts of a specific configuration item (CI) type, [modify the CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html) and change the hidden property `delimiters`. This property is a five-character string that consists of two different characters identifying the leading delimiter, a space, and two different characters identifying the closing delimiter; for example, `%# #%`.

### Enabling placeholder scanning for additional file types

The list of file extensions that XL Deploy recognizes is based on the artifact's configuration item (CI) type. This list is defined by the CI type's `textFileNamesRegex` property in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.

If you want XL Deploy to scan files with extensions that are not in the list, you can change the `textFileNamesRegex` property for the files' CI type.

For example, this is the regular expression that XL Deploy uses to identify [`file.File`](/xl-deploy/concept/file-plugin.html) artifacts that should be scanned for placeholders:

    #file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

To change this, remove the number sign (`#`) at the start of the line and modify the regular expression as needed. For example, to add the `test` file extension:

    file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | test | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

After changing `deployit-defaults.properties`, you must restart XL Deploy for the changes to take effect.

**Tip:** For information about disabling scanning of artifacts, refer to [Disable placeholder scanning in XL Deploy](/xl-deploy/how-to/disable-placeholder-scanning-in-xl-deploy.html).

### Placeholder scanning using the Jenkins plugin

When you import of a package, XL Deploy applies placeholder scanning and checksum calculation to all the artifacts in the package. The CI tools can pre-process the artifacts in the deployment archive and perform the placeholder scanning and the checksum calculation. With this change, the XL Deploy server is no longer required to perform these actions on the deployment archive.

Scanning for all placeholders in artifacts is provisioned to be performed by the [XL Deploy Jenkins plugin](/xl-deploy/concept/jenkins-xl-deploy-plugin.html) at the time of packaging the `DAR` file. An artifact in a deployable must have the `scanPlaceholders` property set as `true` to be scanned. For example: when the XL Deploy Jenkins plugin creates the artifacts, it sets the `scanPlaceholders` to `true` for the artifact before packaging the `DAR`. After a successful scanning, the deployment manifest contains the scanned placeholders for the corresponding artifact and sets the `preScannedPlaceholders` property to `true`. When the package is imported in XL Deploy, it will not be scanned for placeholders.

If do not want to use the XL Deploy Jenkins plugin to scan placeholders and you want to scan the packages while importing, you can modify the deployment manifest and change the `preScannedPlaceholders` to `false` with `scanPlaceholders` set as `true`.

## Property placeholders

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, property placeholders do not necessarily need to get a value from a dictionary. If the placeholder can not be resolved from a dictionary:

* If the property `kind` is `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, or `list_of_string`, the placeholder is left as-is.
* If the property is of any other `kind` (for example, `string`), the placeholder is replaced with an empty string. Note that, if the property is required, this will cause an error and XL Deploy will require you to provide a value at deployment time.

## Debugging placeholder scanning

To debug placeholder scanning, edit the `XL_DEPLOY_SERVER_HOME/conf/logback.xml` file and add the following line:

    <logger name="com.xebialabs.deployit.engine.replacer.Placeholders" level="debug" />

When importing a deployment package (DAR file), you will see debug statements in the `deployit.log` file as follows:

     ...
     DEBUG c.x.d.engine.replacer.Placeholders - Determined New deploymentprofile.deployment to be a binary file
     ...
