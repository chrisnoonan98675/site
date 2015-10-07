---
title: Create a custom trigger type
categories:
- xl-release
subject:
- Releases
tags:
- trigger
- customization
---

Releases in XL Release can be kicked off in many different ways, such as directly from the user interface or via an upstream CI server or ALM tool.

You can also use triggers, which allow you to create and start new releases based on external events from within XL Release. When you create a trigger for a release template and enable it, XL Release will execute the script associated with the trigger at a specified interval. If the trigger "fires", it will create and start a new release from the template.

Polling an SCM is a standard example of a trigger. But you can also easily define and configure your own triggers if you would like to kick off releases based on other events. Here's how:

## Defining your trigger

To start, you first need to define your new trigger type in XL Release's type system. You do this by adding the definition to XL Release's type definition file in `<XL_RELEASE_HOME>/ext/synthetic.xml`:

    <type type="demo.MyFirstTrigger" extends="xlrelease.ReleaseTrigger" >
        <!-- if we omit this property, XL Release will look for 'demo/MyFirstTrigger.py', based on the name of the type -->
        <property name="scriptLocation" default="demo/find-events-to-trigger-release.py" hidden="true"/>
        <!-- don't forget 'category="variables"' here! -->
        <property name="triggerValueForUse" category="variables" required="false" />
    </type>

Here, we have defined both a script for the trigger to run, as well as a variable which will be set by the trigger and which we can use when creating the new release. The SVN trigger plugin, for example, makes the commit ID available in this way.

Next, we need to create the script that is executed each time the trigger executes. Based on the definition above, we will need to save this script as `<XL_RELEASE_HOME>/ext/demo/find-events-to-trigger-release.py`:

    import string
    import random
    import time

    # an example of creating a value for use within your releases
    def id_generator(size=6, chars=string.ascii_uppercase + string.digits):
        return''.join(random.choice(chars) for _ in range(size))

    # this is the 'output' variable of the trigger from the type definition
    triggerValueForUse = id_generator()

    # the trigger will 'fire' when the value of this variable differs from the
    # value that was set the last time the trigger was invoked
    triggerState = triggerValueForUse

Once we've restarted the server to pick up the new trigger definition, you can create triggers of this new type attached to our templates.

Here, we'll define a trivial template with one task, which simply displays the value of triggerValueForUse set by the trigger. We will do this by defining a regular template variable `${valFromTrigger}`, and by setting its value automatically whenever the trigger kicks off a new release from the template.

![Template variable](../images/task-to-showcase-value.png)

We'll configure the trigger by using the dropdown on the template to switch from the Release Flow view to the Triggers view.

![Triggers view](../images/create-trigger.png)

We can now define our new custom trigger. Note how we assign the `${triggerValueForUse}` value set by the trigger to the template variable `${valFromTrigger}`. We could also use `${triggerValueForUse}` in the Release Title field, of course:

![Trigger definition](../images/trigger-definition.png)

To activate the trigger, we need to create one release from the template via the XL Release UI. Then the trigger will be running in the background (unless we disable it, of course), and will set the value of `triggerState` each time it is run.

Whenever that value is different from the previous trigger invocation (which in the case of our example should be every time), a new release using our template will be created and started, with the `${valFromTrigger}` template variable set to the value returned from the trigger. In our simple example, the release will finish almost immediately, showing the value set by the trigger:

![Value set by target](../images/values-from-trigger-executing.png)

Points to remember:

* Ensure that `triggerState` is set correctly inside your trigger script: it should be set to a different value from the previous execution exactly when you want the trigger to "fire" and create and start a new release.
* To disable a trigger, simply uncheck the **Enabled** checkbox on the trigger configuration page.
* To delete a trigger, click the X on the right hand side of the trigger configuration page.
* You can see all releases created by a trigger by clicking on the **related releases** link on the trigger configuration page
