---
title: Introduction to the XL Deploy Command plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- command
weight: 351
---

The XL Deploy Command plugin allows you to execute scripts on remote systems, without requiring you to manually log on to each system, copy required resources, and executes scripts or commands. The Command plugin automates this process and makes it less error-prone.

You can also use the Command plugin to reuse existing deployment scripts with XL Deploy before you move the deployment logic to a more reusable, easily maintainable plugin form.

## Features

* Execute an operating system (Unix or Microsoft Windows) command on a host
* Execute a script on a host
* Associate undo commands
* Copy associated command resources to a host

## Plugin concepts

### Command

A command encapsulates an operating system-specific command, as you would enter at the command prompt of a native operating system (OS) command shell. The OS command is captured in the command's `commandLine` property; for example, `echo hello >> /tmp/hello.txt`.

The command can also upload dependent artifacts to the target system and make them available to the `commandLine` with the use of a placeholder in the `${filename}` format; for example, `cat ${uploadedHello.txt} >> /tmp/hello.txt`.

### Undo command

An undo command has the same characteristics as a command, except that it reverses the effect of the original command it is associated with. An undo command usually runs when the associated command is undeployed or upgraded.

In XL Deploy 5.1.0 and later, the preferred way to define an `undo` command is by using the following undo attributes of a command:

* `undoCommandLine`: Used to define a command to be executed on the host machine; for example, `ls -la`
* `undoOrder`: Specifies the order of execution of undo command
* `undoDependencies`: Specifies the dependent artifacts that undo command requires

**Note:** It is also possible to define an undo command by referring to an existing command. This approach is deprecated in favor of the approach described above, which has the advantage that it can resolve placeholders used in the undo command. If `undoCommandLine` and a reference undo command are both defined, then `undoCommandLine` will take precedence.

### Command order

The command order is the order in which the command is run in relation to other commands. The order allows you to chain commands to create a logical sequence of events. For example, an "install Tomcat" command would execute before an "install web application" command, while a "start Tomcat" command would be the last in the sequence.

### Limitations

* Only single-line commands are supported.

* Command lines are always split on spaces (that is, `' '`), even if the target shell supports a syntax for treating strings containing a space as a single argument. For example, `echo "Hello World"` is interpreted as a command `echo` with _two_ arguments, `"Hello` and `World"`.

* Excess spaces in commands are converted to empty string arguments. For example, <code>ifconfig&nbsp;&nbsp;&nbsp;&nbsp;-a</code> is executed as `ifconfig "" -a`.

* Characters in commands that are special characters of the target shell are *escaped* when executed. For example, the command `ifconfig && echo Hello` is executed as _three_ commands `ifconfig \&\& echo Hello` on a Unix system.

* [Placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) in dependent artifacts will not be replaced.

## Usage in deployment packages

This is an example of a deployment package (DAR) manifest that defines a package that can provision and un-provision a Tomcat server using an install and uninstall script. This example is valid for XL Deploy 5.1.0 and later.

{% highlight xml %}
<cmd.Command name="install-tc-command">
    <order>50</order>
    <commandLine>/bin/sh ${install-tc.sh} ${tomcat.zip}</commandLine>
    <dependencies>
        <ci ref="install-tc.sh" />
        <ci ref="tomcat.zip" />
    </dependencies>
    <undoCommandLine>/bin/sh ${uninstall-tc.sh}</undoCommandLine>
    <undoOrder>45</undoOrder>
    <undoDependencies>
        <ci ref="uninstall-tc.sh" />
    </undoDependencies>
</cmd.Command>
<file.File name="tomcat.zip" location="tomcat.zip" targetPath="/tmp"/>
<file.File name="install-tc.sh" location="install-tc.sh" targetPath="/tmp" />
<file.File name="uninstall-tc.sh" location="uninstall-tc.sh" targetPath="/tmp" />
{% endhighlight %}

## Sample scenario: Provision a Tomcat server

This is an example of a simple way of installing Apache Tomcat, which is distributed as a ZIP file. This example creates an installation script to unzip the distribution file on the host. The uninstall script shuts down a running Tomcat server and deletes the installation directory.

### Step 1 Create the installation script

Create a script that will install Tomcat. This is a sample installation script (`install-tc.sh`):

    #!/bin/sh
    set -e
    if [ -e "/apache-tomcat-6.0.32" ]
    then
	    echo "/apache-tomcat-6.0.32 already exists. remove to continue."
	    exit 1
    fi
    unzip $1 -d /
    chmod +x /apache-tomcat-6.0.32/bin/*.sh

### Step 2 Create the uninstall script

Create a script that will uninstall Tomcat. This is a sample uninstall script (`uninstall-tc.sh`):

    #!/bin/sh
    set -e
    /apache-tomcat-6.0.32/bin/shutdown.sh
    rm -rf /apache-tomcat-6.0.32

### Step 3 Define the command to install

Define a command that will trigger the execution of the installation script for the initial deployment. In the following example from a `deployit-manifest.xml` file, the command will be executed at [order 50](/xl-deploy/concept/steps-and-steplists-in-xl-deploy.html#steplist) in the generated step list. On the host, `/bin/sh` is used to execute the installation script. It takes a single parameter, the path to the `tomcat.zip` file on the host. When the command is undeployed, `uninstall-tc-command` will be executed.

{% highlight xml %}
<cmd.Command name="install-tc-command">
    <order>50</order>
    <commandLine>/bin/sh ${install-tc.sh} ${tomcat.zip}</commandLine>
    <commandLine>uninstall-tc-command</commandLine>
    <undoOrder>45</undoOrder>
    <dependencies>
        <ci ref="install-tc.sh" />
        <ci ref="tomcat.zip" />
    </dependencies>
</cmd.Command>
{% endhighlight %}

### Step 4 Define the command to uninstall

Define a command that will trigger the execution of the uninstall script for the undeployment. In the following example from a `deployit-manifest.xml` file, the undo command will be executed at order 45 in the generated step list. Note that it has a lower order than the `install-tc-command` command. This ensures that the undo command will always run before `install-tc-command` during an upgrade.

{% highlight xml %}
<cmd.Command name="uninstall-tc-command">
    <order>45</order>
    <commandLine>/bin/sh ${uninstall-tc.sh}</commandLine>
    <dependencies>
        <ci ref="uninstall-tc.sh" />
    </dependencies>
</cmd.Command>
{% endhighlight %}
