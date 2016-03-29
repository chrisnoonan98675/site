---
title: Enable placeholder scanning for additional file types
subject:
- Packaging
categories:
- xl-deploy
tags:
- package
- dictionary
- placeholder
- artifacts
---

When importing a package, XL Deploy scans the files (artifacts) it contains for [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) that need to be resolved during deployment. It determines which files need to be scanned based on their extension.

The list of file extensions that XL Deploy recognizes is based on the artifact's configuration item (CI) type. This list is defined by the CI type's `textFileNamesRegex` property in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file.

If you want XL Deploy to scan files with extensions that are not in the list, you can change the `textFileNamesRegex` property for the files' CI type.

For example, this is the regular expression that XL Deploy uses to identify [`file.File`](/xl-deploy/concept/file-plugin.html) artifacts that should be scanned for placeholders:

    #file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

To change this, remove the number sign (`#`) at the start of the line and modify the regular expression as needed. For example, to add the `test` file extension:

    file.File.textFileNamesRegex=.+\.(cfg | conf | config | ini | properties | props | test | txt | asp | aspx | htm | html | jsf | jsp | xht | xhtml | sql | xml | xsd | xsl | xslt)

After changing `deployit-defaults.properties`, you must restart XL Deploy for the changes to take effect.

**Tip:** For information about disabling scanning of artifacts, refer to [Disable placeholder scanning in XL Deploy](/xl-deploy/how-to/disable-placeholder-scanning-in-xl-deploy.html).
