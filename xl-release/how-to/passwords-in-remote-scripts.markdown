---
title: Use hidden passwords in a remote script
subject:
- tasks
categories:
- xl-release
tags:
- script
- task
- password
---

XL Release prevents the improper usage of passwords by allowing password type variables to be used only in password fields.

You can use and mask password in custom script tasks like the [Jenkins task](/xl-release/how-to/create-a-jenkins-task.html), [Jython Script task](/xl-release/how-to/create-a-jython-script-task.html), or the [Remote Script task](/xl-release/how-to/remote-script-plugin.html).

## Adding a password variable and token

1. In your custom remote script, define a token for the password you want to use (For example `[PASSWORD]`).
1. In the `synthetic.xml` file, you must modify an existing task type.

        <type-modification type="remoteScript.RemoteScript">
            <property name="customPassword" kind="string" password="true" category="input"/>
            <property name="scriptLocation" hidden="true" default="remoteScript/MyRemoteScript.py" />
        </type-modification>

  This code snippet extends the Remote Script task type by adding a new `customPassword` property.

**Note:** After every change to the `synthetic.xml` file, make sure you restart your XL Release server.

* You must modify the python script file for the script task.

For the Remote Script task, you must go to `XL_RELEASE_SERVER_HOME/lib` and in the `xlr-remotescript-plugin` file you can find the `remoteScript` folder containing the `RemoteScriptWrapper.py` file. Modify the file by adding this snippet:

      import string
      updatedScript = string.replace(script, '[PASSWORD]', customPassword)
      task.pythonScript.setProperty('script', updatedScript)  

      This will replace the `[PASSWORD]` token in your custom script from the Remote Script task with the value in the `customPassword` variable.

* To mask the value of the password add this line in the `RemoteScriptWrapper.py` file:

        output = string.replace(output, customPassword, '********')
