---
title: Introduction to the XL Deploy Command plugin
categories:
- xl-deploy
subject:
- Command plugin
tags:
- plugin
- command
---

The Command plugin allows you to execute scripts on remote systems, without requiring you to manually log on to each system, copy required resources, and execute the scripts or commands. The Command plugin automates this process and makes it less error-prone.

You can also use the Command plugin to reuse existing deployment scripts with XL Deploy before you move the deployment logic to a more reusable, easily maintainable plugin form.

## Features

* Execute an operating system (Unix, Microsoft Windows) command on a host
* Execute a script on a host
* Associate undo commands
* Copy associated command resources to a host

## Plugin Concepts

### Command

A command encapsulates an operating system-specific command, as one would enter at the command prompt of a native OS command shell. The OS command is captured in the command's `commandLine` property; e.g. `echo hello >> /tmp/hello.txt`.

The command also has the capability of uploading any dependent files to the target system and make those available to the `commandLine` with the use of a placeholder; e.g. `cat ${uploadedHello.txt} >> /tmp/hello.txt`.

### Undo command

An undo command has the same characteristics as a command, except that it reverses the effect of the original command it is associated with. An undo command usually runs when the associated command is undeployed or upgraded.

In XL Deploy 5.1.0 and later, the preferred way to define an `undo` command is by using the following undo attributes of a command:

* `undoCommandLine`: Used to define a command to be executed on the host machine; for example, `ls -la`
* `undoOrder`: Specifies the order of execution of undo command
* `undoDependencies`: Specifies the dependent artifacts that undo command requires

**Note:** It is also possible to define an undo command by referring to an existing command. This approach is deprecated in favor of the approach described above, which has the advantage that it can resolve placeholders used in the undo command.

### Command order

The order in which the command is run in relation to other commands. The order allows for the chaining of commands to create a logical sequence of events. For example, an install Tomcat command would execute before an install web application command, while a start Tomcat command would be the last in the sequence.

## Usage in deployment packages

This is an example of a deployment package (DAR) manifest that defines a package that can (un)provision a Tomcat server using an install and uninstall script. This example is valid for XL Deploy 5.1.0 and later.

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

### Sample scenario: Provision a Tomcat server

For illustration purposes, we take a simplistic view of installing Tomcat. In reality however, your installation of
Tomcat would take on a far more comprehensive form.

Tomcat is distributed as a ZIP. For this example, we create an installation script to unzip the distribution
on the host. The uninstall script simply shuts down a running Tomcat and deletes the installation directory.

#### Create the installation script (`install-tc.sh`)

    #!/bin/sh
    set -e
    if [ -e "/apache-tomcat-6.0.32" ]
    then
	    echo "/apache-tomcat-6.0.32 already exists. remove to continue."
	    exit 1
    fi
    unzip $1 -d /
    chmod +x /apache-tomcat-6.0.32/bin/*.sh

#### Create the uninstall script (`uninstall-tc.sh`)

    #!/bin/sh
    set -e
    /apache-tomcat-6.0.32/bin/shutdown.sh
    rm -rf /apache-tomcat-6.0.32

#### Manifest snippet defining the command to trigger the execution of the install script for the initial deployment

The following command will be executed at order 50 in the generated step list. `/bin/sh` is used on the host to execute the install script which takes a single parameter, the absolute path to the `tomcat.zip` on the host. When the command is undeployed, `uninstall-tc-command` will be executed.

    <cmd.Command name="install-tc-command">
        <order>50</order>
        <commandLine>/bin/sh ${install-tc.sh} ${tomcat.zip}</commandLine>
        <undoCommand>uninstall-tc-command</undoCommand>
        <dependencies>
			<ci ref="install-tc.sh" />
        	<ci ref="tomcat.zip" />
        </dependencies>
    </cmd.Command>

#### Manifest snippet defining the undo command to trigger the execution of the uninstall script for the undeploy

The undo command will be executed at order 45 in the generated step list. Note that it has a lower order than the
`install-tc-command`. This ensures that the undo command will always run before the `install-tc-command` during an upgrade.

    <cmd.Command name="uninstall-tc-command">
        <order>45</order>
        <commandLine>/bin/sh ${uninstall-tc.sh}</commandLine>
        <dependencies>
            <ci ref="uninstall-tc.sh" />
        </dependencies>
    </cmd.Command>
