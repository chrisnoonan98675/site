---
no_index: true
title: Using placeholders and dictionaries
---

Placeholders are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable. At deployment time, you can provide values for placeholders manually or they can be resolved from [dictionaries](/xl-deploy/how-to/create-a-dictionary.html) that are assigned to the target environment.

When you *update* an application, XL Deploy will resolve the values for placeholders again from the dictionary. For more information, refer to [Resolving properties during application updates](/xl-deploy/concept/resolving-properties-during-application-updates.html).

**Important:** Placeholders are designed to be used for small pieces of data, such as a user name or file path. Although XL Deploy does not limit the length of placeholder values stored in dictionaries, it is recommended that you avoid dictionary keys or values over 100 characters in length. Longer values will degrade performance.

## Using placeholders

XL Deploy recognizes placeholders using the following format:

	{% raw %}{{ PLACEHOLDER_KEY }}{% endraw %}

### File placeholders

_File_ placeholders are used in artifacts in a deployment package. XL Deploy scans packages that it imports for files and searches them files for file placeholders. It determines which files need to be scanned based on their extension. The following items are scanned:

* File-type CIs
* Folder-type CIs
* Archive-type CIs

Before a deployment can be performed, a value must be specified for **all** file placeholders in the deployment.

#### Special file placeholder values

There are two special placeholder values for file placeholders:

* `<empty>` replaces the placeholder key with an empty string
* `<ignore>` ignores the placeholder key, leaving it as-is

The angle brackets (`<` and `>`) are required for these special values.

**Note:** A file placeholder that contains other placeholders does not support the special `<empty>` value.

#### Using different file placeholder delimiters

If you want to use delimiters other than {% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} in artifacts of a specific configuration item (CI) type, [modify the CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html) and change the hidden property `delimiters`. This property is a five-character string that consists of two different characters identifying the leading delimiter, a space, and two different characters identifying the closing delimiter; for example, `%# #%`.

#### Enable placeholder scanning for additional file types

The list of file extensions that XL Deploy recognizes is based on the artifact's configuration item (CI) type. This list is defined by the CI type's `textFileNamesRegex` property in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.

If you want XL Deploy to scan files with extensions that are not in the list, you can change the `textFileNamesRegex` property for the files' CI type.

For example, this is the regular expression that XL Deploy uses to identify [`file.File`](/xl-deploy/concept/file-plugin.html) artifacts that should be scanned for placeholders:

    #file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

To change this, remove the number sign (`#`) at the start of the line and modify the regular expression as needed. For example, to add the `test` file extension:

    file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | test | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

After changing `deployit-defaults.properties`, you must restart XL Deploy for the changes to take effect.

**Tip:** For information about disabling scanning of artifacts, refer to [Disable placeholder scanning in XL Deploy](/xl-deploy/how-to/disable-placeholder-scanning-in-xl-deploy.html).

### Property placeholders

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, property placeholders do not necessarily need to get a value from a dictionary. If the placeholder can not be resolved from a dictionary:

* If the property `kind` is `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, or `list_of_string`, the placeholder is left as-is.
* If the property is of any other `kind` (for example, `string`), the placeholder is replaced with an empty string. Note that, if the property is required, this will cause an error and XL Deploy will require you to provide a value at deployment time.

### Debugging placeholder scanning

To debug placeholder scanning, edit the `XL_DEPLOY_SERVER_HOME/conf/logback.xml` file and add the following line:

    <logger name="com.xebialabs.deployit.engine.replacer.Placeholders" level="debug" />

When importing a deployment package (DAR file), you will see debug statements in the `deployit.log` file as follows:

     ...
     DEBUG c.x.d.engine.replacer.Placeholders - Determined New deploymentprofile.deployment to be a binary file
     ...

### Disable placeholder scanning

When importing a package, XL Deploy by default scans the artifacts it contains for placeholders that need to be resolved during a deployment. If you want to turn off placeholder scanning, there are various ways to do this.

#### One file extension on a particular artifact type

XL Deploy looks for files to scan in artifact configuration items (CIs) based on the file extension. It is possible to exclude certain extensions from this process. To do this, edit the `deployit-defaults.properties` file and set the `excludeFileNamesRegex` property on the artifact CI type you want to exclude. For example:

	file.Archive.excludeFileNamesRegex=.+\.js

Restart the XL Deploy server for the change to take effect.

#### One file extension on all artifacts

XL Deploy looks for files to scan in artifact CIs based on the file extension. It is possible to exclude certain extensions from this process. To do this, edit the `deployit-defaults.properties` file and set the `excludeFileNamesRegex` property on the artifact CI type you want to exclude. For example:

	udm.BaseDeployableArchiveArtifact.excludeFileNamesRegex=.+\.js

Restart the XL Deploy server for the change to take effect.

#### One CI instance

Edit the deployment package manifest and change the `scanPlaceholders` property of the particular artifact:

	<file.File name="sample" file="sample.txt">
		<scanPlaceholders>false</scanPlaceholders>
	</file.File>

#### One CI type

Edit the `deployit-defaults.properties` file and set the `scanPlaceholders` property for the CI type you want to exclude. For example:

	file.Archive.scanPlaceholders=false

Restart the XL Deploy server for the change to take effect.

#### Disable placeholder scanning completely

Edit the `deployit-defaults.properties` file and set the following property:

	udm.BaseDeployableArtifact.scanPlaceholders=false

Restart the XL Deploy server for the change to take effect.

## Using dictionaries

Dictionaries are sets of key-value pairs that store environment-specific information such as file paths and user names, as well as sensitive data such as passwords. Dictionaries are designed to store small pieces of data, such as a user name or file path. Although XL Deploy does not limit the length of dictionary values, it is recommended that you avoid entries over 100 characters. Longer values will degrade performance.

You assign dictionaries to environments. The order of the dictionaries in an environment matters, because if the same entry exists in multiple dictionaries, then XL Deploy uses the first entry that it finds.

Starting in XL Deploy 5.0.0, a dictionary can contain both plain-text and encrypted entries. Prior to XL Deploy 5.0.0, you use dictionaries for plain-text entries and *encrypted dictionaries* for sensitive information.

### Create a dictionary

To create a dictionary:

1. Click **Repository** in the top menu.
1. Right-click **Environments** and select **Dictionary**.
1. In the **Name** box, enter a name for the dictionary.
1. Click ![Add button](/images/button_add_placeholder.png) under **Entries** to add a plain-text entry.
 1. Under **Key**, enter the placeholder without delimiters ({% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} by default). Under **Value**, enter the corresponding value.
 1. Repeat this process for each plain-text entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click ![Add button](/images/button_add_placeholder.png) under **Encrypted Entries** to add an encrypted entry (supported in XL Deploy 5.0.0 and later).
 1. Under **Key**, enter the placeholder without delimiters. Under **Value**, enter the corresponding value. Note that in encrypted entries, the value is always masked with asterisks (`*`).
 1. Repeat this process for each encrypted entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click **Save** to save the dictionary.

**Tip:** In XL Deploy 5.0.0 and later, you can create a dictionary [while creating a new environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).

### Create an encrypted dictionary

To create an encrypted dictionary:

1. Click **Repository** in the top menu.
2. Right-click **Environments** and select **Encrypted Dictionary**.
3. In the **Name** box, enter a name for the encrypted dictionary.
4. Click ![Add button](/images/button_add_placeholder.png) under **Entries** to add an entry.
1. Under **Key**, enter the placeholder without delimiters ({% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} by default). Under **Value**, enter the corresponding value. Note that in encrypted dictionary entries, the value is always masked with asterisks (`*`).
1. Repeat this process for each entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click **Save** to save the dictionary.

### Assign a dictionary to an environment

To assign a dictionary to an environment:

1. Click **Repository** in the top menu.
2. Expand **Environments** and double-click the desired environment.
3. Under **Dictionaries**, select the dictionary and click ![Add button](/images/button_add_container.png) to move it to the **Members** list.
4. Click **Save** to save the environment.

You can assign multiple dictionaries to an environment. Dictionaries are evaluated in order; XL Deploy resolves each [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) using the first value that it finds.

### Restrict a dictionary to containers or applications

You can restrict a dictionary so that XL Deploy will only apply it to specific containers, specific applications, or both. To restrict a dictionary:

1. Click **Repository** in the top menu.
1. Expand **Environments** and double-click the desired dictionary.
1. Go to the **Restrictions** tab.
1. Under **Restrict to containers**, select one or more containers and click ![Add button](/images/button_add_container.png) to move them to the **Members** list.
1. Under **Restrict to applications**, select one or more applications and click ![Add button](/images/button_add_container.png) to move them to the **Members** list.
1. Click **Save** to save the dictionary.

**Note:** An unrestricted dictionary cannot refer to entries in a restricted dictionary.

#### Restricted dictionary example

When you restrict a dictionary, it affects the way XL Deploy resolves placeholders at deployment time. For example, assume you have the following setup:

* A dictionary called DICT1 has an entry with the key `key1`. DICT1 is restricted to a container called CONT1.
* A dictionary called DICT2 has an entry with the key `key2` and value `key1`.
* An environment has CONT1 as a member. DICT1 and DICT2 are both assigned to this environment.
* An application called APP1 has a deployment package that contains a [`file.File`](/xl-deploy/latest/filePluginManual.html) CI. The artifact attached to the CI contains the placeholder `{% raw %}{{key2}}{% endraw %}`.

When you try to deploy the package to the environment, mapping of the CI will fail with the error *Cannot expand placeholder {% raw %}{{key1}}{% endraw %} because it references an unknown key key1*.

This has to do with the fact that, when XL Deploy resolves placeholders from a dictionary, it requires  that *all* keys in the dictionary be resolved. In this scenario, XL Deploy tries to resolve `{% raw %}{{key2}}{% endraw %}` with the value from `key1`, but `key1` is missing because DICT1 is restricted to CONT1. The restriction means that DICT1 is not available to APP1.

There are a few ways you can resolve or work around this scenario:

* Restrict DICT1 to APP1 (in addition to CONT1)
* Add `key1` to DICT2 and give it a "dummy" value (so the mapping will succeed)
* Create another unrestricted dictionary that will provide a default value for `key1`
