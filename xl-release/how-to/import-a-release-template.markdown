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

Templates are like blueprints for releases. You use them to model the ideal process of a release flow. 

You can import templates from a ZIP file. Templates are exported with the **Export** button in the Release Flow editor for templates.

Click **Import**, then select a ZIP file that was previously exported from XL Release, then click **Import**. If you have exported templates from a version of XL Release before version 4.0.0 you can also select a JSON file.

![Import template](../images/import-dialog-1.png)

After the file is processed, the list of imported templates appears. If a template contains references to objects that do not exist on this server (for example, XL Deploy servers or gate dependencies), XL Release removes the references and shows a warning message.

When importing a template that contains release triggers, all the triggers on the imported template will be disabled.
