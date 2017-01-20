---
title: Create a custom trigger type
categories:
- xl-release
subject:
- Triggers
tags:
- trigger
- customization
weight: 426
---

Releases in XL Release can be kicked off in many different ways, such as directly from the user interface or via an upstream CI server or ALM tool.

You can also use triggers, which allow you to create and start new releases based on external events from within XL Release. When you create a trigger for a release template and enable it, XL Release will execute the script associated with the trigger at a specified interval. If the trigger "fires", it will create and start a new release from the template.

Polling an SCM is a standard example of a trigger. But you can also easily define and configure your own triggers if you would like to kick off releases based on other events. Here's how:

## Step 1 Define the trigger in the type system

To start, define a new trigger type in XL Release's type system. You do this by adding the definition to XL Release's type definition file in `<XL_RELEASE_SERVER_HOME>/ext/synthetic.xml`:

    <type type="demo.MyFirstTrigger" extends="xlrelease.ReleaseTrigger" >
        <!-- if we omit this property, XL Release will look for 'demo/MyFirstTrigger.py', based on the name of the type -->
        <property name="scriptLocation" default="demo/find-events-to-trigger-release.py" hidden="true"/>
        <!-- don't forget 'category="variables"' here! -->
        <property name="triggerValueForUse" category="variables" required="false" />
    </type>

This example defines a script for the trigger to run, and as a variable that will be set by the trigger, and that you can use when creating a new release. As an example, the [SVN Trigger plugin](/xl-release/concept/introduction-to-the-xl-release-svn-trigger-plugin.html) makes the commit ID available in this way.

## Step 2 Create the trigger script

Next, create the script that will be executed each time the trigger executes. Based on the definition above, save the script as `<XL_RELEASE_SERVER_HOME>/ext/demo/find-events-to-trigger-release.py`:

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

Restart the XL Release server to register the new trigger definition.

## Step 3 Attach the trigger to a template

Now, you can attach the trigger to a release template. In this example, the template has one task, which simply displays the value of `{{triggerValueForUse}}`, as set by the trigger.

1. Define a template variable called `${valFromTrigger}`.

    ![Template variable](../images/task-to-showcase-value.png)

1. Then, choose **Triggers** from the **Show** menu to go to the triggers page.
1. Under **Template variables**, assign the `${triggerValueForUse}` value (which will be set by the trigger) to the `${valFromTrigger}` template variable. Note that you could also use `${triggerValueForUse}` in the **Release Title** field.

    ![Trigger definition](../images/trigger-definition.png)

1. Save the changes.

## Step 4 Activate the trigger

To activate the trigger, create one release from the template, using the XL Release UI. Then the trigger will be running in the background (unless you disable it), and it will set the value of `triggerState` each time it is run.

Whenever that value is different from the previous trigger invocation (which, in the case of this example, should be every time), a new release using the template will be created and started, with the `${valFromTrigger}` template variable set to the value returned from the trigger.

In this simple example, the release will complete almost immediately, showing the value set by the trigger:

![Value set by target](../images/values-from-trigger-executing.png)

## Notes

Points to remember:

* Ensure that `triggerState` is set correctly inside your trigger script; it should be set to a different value from the previous execution exactly when you want the trigger to "fire" and create and start a new release.
* To disable a trigger, deselect **Enabled** on the trigger configuration page.
* To delete a trigger, click the **X** on the right side of the trigger configuration page.
* You can see all releases created by a trigger by clicking **Releases linked to this trigger** on the trigger configuration page.
