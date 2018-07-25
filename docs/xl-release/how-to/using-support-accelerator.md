---
title: Using the support accelerator
categories:
xl-release
subject:
System administration
tags:
release
gather
support
since:
XL Release 6.1.0
---

Using the XL Release support accelerator you can gather data that can help the XebiaLabs Support Team troubleshoot issues.
When you select **Help** > **Get data support** and click **Download**, XL Release creates a support analytics ZIP file. You can send the file to the XebiaLabs Support Team to provide data for troubleshooting issues.

The name of the file is `xlr-support-package.zip` and it contains information about your XL Release installation, including the contents of the `conf`, `ext`, `plugins`, `hotfix`, `log`, and `bin` directories.

XL Release will attempt to remove configuration passwords from the ZIP file. Please inspect the ZIP file contents to ensure that it does not contain passwords or other sensitive data.

The XL Release support accelerator is only available for users that have the Admin global permission.

See [Support](http://support.xebialabs.com) for more information or to open a support request.
