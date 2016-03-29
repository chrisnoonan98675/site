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
---

Templates are like blueprints for releases. You use them to model the ideal process of a release flow. 	You can import a template from a ZIP file that was previously [exported](/xl-release/how-to/using-the-release-flow-editor.html) from XL Release.

To import a template:

1. On the template overview, click **Import**.
2. Click **Choose File** and browse to the ZIP file.

    **Tip:** If you have exported templates from a version of XL Release before 4.0.0, you can also select a JSON file.

3. Click **Import**.

![Import template](../images/import-dialog-1.png)

After XL Release processes the file, the list of imported templates appears. If a template contains references to objects that do not exist on this server (for example, XL Deploy servers or gate dependencies), XL Release removes the references and shows a warning message.

If you import a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), all the triggers on the imported template will be disabled.
