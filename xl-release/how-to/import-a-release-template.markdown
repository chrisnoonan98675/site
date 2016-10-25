---
title: Import a release template
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- import
- export
weight: 422
---

Templates are like blueprints for releases. You use them to model the ideal process of a release flow. 	You can import a template from an `.xlr` file that was previously [exported](/xl-release/how-to/using-the-release-flow-editor.html) from XL Release. In the case of a template that was exported from a version of XL Release prior to 4.0.0, you can import a `.json` file.

To import a template:

1. Start in one of the following places:

    * Go to **Templates** > **All templates** and click **Import**
    * Go to **Templates** > **Folders**, select a folder, and click **Add template** > **Import template**
    * Click **Import** on the template overview (prior to XL Release 6.0.0)

2. Click **Browse** and browse to the `.xlr` file.

    ![Import template](../images/import-dialog-1.png)

3. Click **Import**. If a template contains references to objects that do not exist on the server (for example, XL Deploy servers or gate dependencies), XL Release removes the references and shows a warning message. Also, if you import a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), the triggers will be disabled.

You can also use the [XL Release REST API](https://docs.xebialabs.com/xl-release/latest/rest-api/) to import templates. For templates that were exported from XL Release 4.0.0 and later, ensure that you use the `importTemplateAsXlr` endpoint.
