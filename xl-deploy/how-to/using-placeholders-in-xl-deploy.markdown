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

**Important:** Placeholders are designed to be used for small pieces of data, such as a user name or file path. Although XL Deploy does not limit the length of placeholder values stored in dictionaries, it is recommended that you avoid dictionary entries over 100 characters. Longer values will degrade performance.

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

If you want to use delimiters other than {% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} in artifacts of a specific configuration item (CI) type, [modify the CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html) and change the hidden property `delimiters`. This property is a five-character string that consists of two characters identifying the leading delimiter, a space, and two characters identifying the closing delimiter; for example, `%% %%`.

### Enabling placeholder scanning for additional file types

The list of file extensions that XL Deploy recognizes is based on the artifact's configuration item (CI) type. This list is defined by the CI type's `textFileNamesRegex` property in the `<XLDEPLOY_SERVER_HOME>/conf/deployit-defaults.properties` file.

If you want XL Deploy to scan files with extensions that are not in the list, you can change the `textFileNamesRegex` property for the files' CI type.

For example, this is the regular expression that XL Deploy uses to identify [`file.File`](/xl-deploy/concept/file-plugin.html) artifacts that should be scanned for placeholders:

    #file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

To change this, remove the number sign (`#`) at the start of the line and modify the regular expression as needed. For example, to add the `test` file extension:

    file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | test | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

After changing `deployit-defaults.properties`, you must restart XL Deploy for the changes to take effect.

**Tip:** For information about disabling scanning of artifacts, refer to [Disable placeholder scanning in XL Deploy](/xl-deploy/how-to/disable-placeholder-scanning-in-xl-deploy.html).

## Property placeholders

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, property placeholders do not necessarily need to get a value from a dictionary. If the placeholder can not be resolved from a dictionary, the placeholder is left as-is.

## Debugging placeholder scanning

To debug placeholder scanning, edit the `<XLDEPLOY_SERVER_HOME>/conf/logback.xml` file and add the following line:

    <logger name="com.xebialabs.deployit.engine.replacer.Placeholders" level="debug" />

When importing a deployment package (DAR file), you will see debug statements in the `deployit.log` file as follows:

     ...
     DEBUG c.x.d.engine.replacer.Placeholders - Determined New deploymentprofile.deployment to be a binary file
     ...
