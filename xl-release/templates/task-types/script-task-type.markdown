---
title: Script task type
---

A Script task contains a Python script that is executed on the XL Release server. This is an automated task that completes automatically when the script finishes successfully.

![Script Task Details](/xl-release/images/script-task-details.png)

Type or paste a Python script in the **Script** field of the Script task details. XL Release 4.7.0 and later supports Jython 2.7. Jython is the Java implementation of Python. This means that you have access to standard Python as well as the Java libraries included in Java 7.

You can access and modify [release variables](/xl-release/concept/variables-in-xl-release.html) in your scripts using the dictionary named `releaseVariables`. This sample script shows how to access and modify a variable:

{% highlight python %}
print(releaseVariables['xldeployPackage'])
releaseVariables['xldeployPackage'] = 'XL Release'
{% endhighlight %}

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Script tasks have a gray border.

## Security and Script tasks

When a Script task becomes active, the script is executed in a sandbox environment on the XL Release server. This means that the script has very restricted permissions. By default, access to the file system and network are not allowed.

To remove these restrictions, add a `script.policy` file to the `XL_RELEASE_HOME/conf` directory. This is a standard [Java Security Policy file](http://docs.oracle.com/javase/7/docs/technotes/guides/security/PolicyFiles.html) that contains the permissions that a script should have. You must restart the XL Release server after creating or changing the `XL_RELEASE_HOME/conf/script.policy` file.

## Sample scripts

This sample script shows how to download a file from a web site and save it locally:

{% highlight python %}
import httplib
url = 'www.xebialabs.com'
file = '/tmp/xebialabs.html'
xl = httplib.HTTPConnection(url)
xl.request('GET', '/')
response = xl.getresponse()
myFile = open(file, 'w')
myFile.write(response.read())
myFile.close()
print "Save %s to %s" % (url, file)
{% endhighlight %}

This example allows access to the network using Python `httplib` and read/write access to the `/tmp` directory on the XL Release server:

{% highlight python %}
grant {
    // Network access
    permission  java.lang.RuntimePermission "accessClassInPackage.sun.nio.ch";
    permission  java.net.SocketPermission "*", "connect, resolve";

    // File access
    permission java.io.FilePermission "/tmp/*", "read, write";
    permission java.util.PropertyPermission "line.separator", "read";
};
{% endhighlight %}

For an extended example, refer to [Creating XL Release Tasks Dynamically Using Jython API](http://blog.xebialabs.com/2015/08/11/creating-xl-release-tasks-dynamically-using-jython-api/).

## Using the XL Release API in scripts

XL Release has an [API](/jython-docs/#!/xl-release/5.0.x/) that you can use to manipulate releases and tasks. You can access the API from [Script tasks](/xl-release/how-to/create-a-script-task.html) and from XL Release plugin scripts. This is an example of a simple Jython script in a Script task. It uses a script to add a comment to the task.

1. Go to the [release overview](/xl-release/how-to/using-the-release-overview.html) and click **New release** (because this is a simple example, you can create an empty release that is not based on a template).
1. In the release properties, set the **Release Name** to _Script example_.
1. Click **Create** to create the release.
1. In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), click the name of the first phase and change it to _Test_.
1. In the _Test_ phase, add two tasks:
    * A Script task called _Add a comment_
    * A Gate task called _Check result_

    ![Script test tasks](/xl-release/images/script-test/phase.png)

1. Click the _Add a comment_ task and add the following **Script**:

        task = getCurrentTask()
        comment = Comment()
        comment.setComment("Hello World!")
        taskApi.commentTask(task.id, comment)
1. Close the task and click **Start Release**.

    The _Add a comment_ task fails. Click the task to see the reason for failure in the **Comments** section:

    ![Script test tasks](/xl-release/images/script-test/no-run-as-user.png)

    This message indicates that you must specify the user name under which to run the script. This is because the API gives you access to releases and tasks, so XL Release needs to check the user's credentials to verify whether certain operations are allowed.
1. Close the task and select **Properties** from the **Show** menu.
1. Enter your user name and password in the **Run automated tasks as user** and **Password** boxes.

    **Tip:** For a production configuration, it is recommended that you create a global "script runner" user that can be used for this purpose, rather that using your own credentials.

1. Click **Save** to save the release properties.
1. Select **Release flow** from the **Show** list.
1. Open the _Add a comment_ task and click **Retry**.

    ![Script test tasks](/xl-release/images/script-test/retry.png)

    The task succeeds and the release waits at the Gate task, giving you time to inspect the script result.

    ![Script test tasks](/xl-release/images/script-test/check-result.png)

1. Open the _Add a comment_ task and check the **Comments** section for the "Hello World!" comment:

    ![Script test tasks](/xl-release/images/script-test/hello-world.png)

### Looking at the script

The first line of the sample script uses the `getCurrentTask()` method to get a reference to the task that is currently executing (that is, the _Add a comment_ Script task) and store it in a variable called `task`.

    task = getCurrentTask()

To set a comment on the task, the script uses the [`taskApi.commentTask`](/jython-docs/#!/xl-release/5.0.x//service/com.xebialabs.xlrelease.api.v1.TaskApi) endpoint. The `commentTask` method takes the task ID and a [`Comment`](/jython-docs/#!/xl-release/5.0.x//service/com.xebialabs.xlrelease.api.v1.forms.Comment) object as parameters. The `Comment` object is a simple wrapper around a string.

    comment = Comment()
    comment.setComment("Hello World!")

Then, the script calls the `commentTask()` method to add the comment to the task:

    taskApi.commentTask(task.id, comment)

**Note:** For any call to an API endpoint, such as `taskApi` or `releaseApi`, you must set the **Run automated tasks as user** property on the release. The scripts will be executed with the permissions that that user has on that particular release.
