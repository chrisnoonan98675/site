---
title: Create an External Script task
categories:
- xl-release
subject:
- Task types
tags:
- task
- script
since:
- XL Release 6.1.0
---

An External Script task points to a Jython or Groovy script that will be executed on the XL Release server. This is an automated task that completes automatically when the script finishes successfully. The task detects the language of the script by the file name extension.

The External Script task type only supports basic HTTP authentication; you should provide an HTTP/HTTPS URL that points to a script file. The task type does not support other protocols.

For an example showing how you can use the External Script task type, refer to [Create a release from a Git repository](/xl-release/how-to/create-a-release-from-a-git-repository.html).

![External Script Task Details](../images/external-script-task.png)

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), External Script tasks have a gray border.

## Security and External Script tasks

When a External Script task becomes active, the script is executed in a sandbox environment on the XL Release server. This means that the script has very restricted permissions. By default, access to the file system and network are not allowed.

To remove these restrictions, add a `script.policy` file to the `XL_RELEASE_SERVER_HOME/conf` directory. This is a standard [Java Security Policy file](http://docs.oracle.com/javase/7/docs/technotes/guides/security/PolicyFiles.html) that contains the permissions that a script should have. You must restart the XL Release server after creating or changing the `XL_RELEASE_SERVER_HOME/conf/script.policy` file.
