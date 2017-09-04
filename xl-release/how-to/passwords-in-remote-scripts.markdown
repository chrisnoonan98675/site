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

* In your custom remote script, define a token for the password you want to use (For example `[PASSWORD]`).
* In the `synthetic.xml` file, you must modify an existing task type.

        <type-modification type="remoteScript.RemoteScript">
            <property name="customPassword" kind="string" password="true" category="input"/>
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

* You can also create a new Python script file, for example `MyRemoteScript.py`, and save it in `XL_RELEASE_SERVER_HOME/ext/remoteScript`:

      import sys
      from com.xebialabs.xlrelease.plugin.overthere import RemoteScript

      # Replace password in script
      import string
      updatedScript = string.replace(script, '##PASSWORD##', customPassword)
      task.pythonScript.setProperty('script', updatedScript)

      # Execute script
      script = RemoteScript(task.pythonScript)
      exitCode = script.execute()

      output = script.getStdout()
      err = script.getStderr()

      # Mask password
      output = string.replace(output, customPassword, '********')

      if (exitCode == 0):
          print output
      else:
          print "Exit code "
          print exitCode
          print
          print "#### Output:"
          print output

          print "#### Error stream:"
          print err
          print
          print "----"

          sys.exit(exitCode)         

* In the `synthetic.xml` file, you must modify an existing task type by adding a new `customPassword` property and modifying the location of the python script file:

          <type-modification type="remoteScript.RemoteScript">
                <property name="customPassword" kind="string" password="true" category="input"/>
                <property name="scriptLocation" hidden="true" default="remoteScript/MyRemoteScript.py" />
          </type-modification>
