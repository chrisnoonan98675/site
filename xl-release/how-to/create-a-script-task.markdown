---
title: Create a Script task
categories:
- xl-release
subject:
- Task types
tags:
- task
- script
- jython
---

A Script task contains a Python script that is executed on the XL Release server. This is an automated task that completes automatically when the script finishes successfully.

![Script Task Details](../images/script-task-details.png)

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
