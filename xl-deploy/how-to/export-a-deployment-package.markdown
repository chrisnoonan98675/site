---
title: Export a deployment package
subject:
- Packaging
categories:
- xl-deploy
tags:
- package
- application
- gui
- repository
weight: 216
---

To export a deployment package (DAR file) using the XL Deploy GUI:

1. Go to the **Explorer**.
1. Expand **Applications**, then expand the desired application, and over the desired deployment package or provisioning package.
1. Click ![Explorer action menu](/images/menu_three_dots.png) and select **Export**.

To export a deployment package (DAR file) using the XL Deploy legacy interface:

1. Go to the **Repository**.
2. Expand **Applications**, then expand the desired application.
3. Right-click the desired package and select **Export**.

## Using the command line

To export a deployment package (DAR file) from the XL Deploy Repository using the CLI, execute this command:

        repository.exportDar('/example/folder','/Applications/app_sample/1.0')

The displayed output:

        admin > repository.exportDar('/example/folder','/Applications/app_sample/1.0')
        finished writing file to /example/folder/app_sample-1.0.dar
