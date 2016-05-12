---
title: Using the Jython step delegate in XL Deploy
categories:
- xl-deploy
subject:
- Customization
tags:
- control task
- task
- jython
- python
---

In XL Deploy, you can define [control tasks](/xl-deploy/how-to/using-control-tasks-in-xl-deploy.html) that allow you to execute actions from the XL Deploy GUI or CLI. One way to create a custom control task is to use a [delegate](/xl-deploy/how-to/create-a-custom-control-task.html). XL Deploy includes a predefined delegate called `JythonDelegate` that accepts a Jython script that it will execute. This topic describes how to use `JythonDelegate` to create a custom control task that prints all environment variables on the host.

## Define a control task

First, define a control task in the `XL_DEPLOY/ext/synthetic.xml` file. This example adds a method to `overthere.LocalHost` using a type modification. The `method` tag is used to define a control task named `showEnvironmentVariables`. The `delegate` parameter defines the type of delegate and the `script` parameter defines the Python script that will perform the action.

{% highlight xml %}
<type-modification type="overthere.LocalHost">
    <method name="ShowEnvironmentVariables"
            description="Show environment variables"
            delegate="jythonScript"
            script="scripts/env.py">
    </method>
</type-modification>
{% endhighlight %}

## Create a Jython script

This is an example of a Jython script that will print the environment variables that are available on a host:

{% highlight python %}
import os

for env in os.environ:
    print("{0}={1}".format(env, os.environ[env]))
{% endhighlight %}

After defining the control task and creating the script, restart the XL Deploy server.

## Run the control task

In XL Deploy, go to the Repository and right-click an `overthere.LocalHost` configuration item (CI). You will see the new control task in the menu.

![showEnvironmentVariables control task in menu](images/jython-delegate.png)

Click `ShowEnvironmentVariables` to see the steps of the control task. After it executes, it returns the environment variables on the host.

![showEnvironmentVariables control task steps](images/jython-delegate-steps.png)

## Define a control task with parameters

The `showEnvironmentVariables` control task defined above prints all environment variables on a host. Suppose you want to limit the control task results. You can do so by defining a method parameter that will be passed to the Jython script.

### Update the control task

Change the definition in `XL_DEPLOY/ext/synthetic.xml` as follows:

{% highlight xml %}
<type-modification type="overthere.LocalHost">
    <method name="ShowEnvironmentVariables" description="Show environment variables" delegate="jythonScript" script="scripts/env.py">
        <parameters>
            <parameter name="limit" kind="integer" description="number of environment variables to expect" default="-1"/>
        </parameters>
    </method>
</type-modification>
{% endhighlight %}

This defines a parameter called `limit` of type _integer_. The default value of `-1` means that all environment variables will be listed.

### Update the Jython script

Now the Jython script can access the method parameter using the `params` object. This is an implicit object that is available to the Jython script that stores all method parameters. Other implicit objects that are available to the script are `args`, which is a dictionary that contains arguments passed to the script, and `thisCi`, which refers to the configuration item on which the control action is defined.

{% highlight python %}
import os

print("Environment variables on the host with name {0}".format(thisCi.name))

limit = params["limit"]
env_var_keys = []
if limit == -1:
    env_var_keys = os.environ.keys()
else:
    env_var_keys = os.environ.keys()[:limit]

for env in env_var_keys:
    print("{0}={1}".format(env, os.environ[env]))
{% endhighlight %}

### Run the control task

After restarting the XL Deploy server and selecting the `ShowEnvironmentVariables`, you will be able to provide a limit for the control task results.

![showEnvironmentVariables control task with limit parameter](images/jython-delegate-parameters.png)
