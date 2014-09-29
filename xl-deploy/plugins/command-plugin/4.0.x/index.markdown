---
title: Command Plugin Manual
---

## Preface

This document describes the functionality provided by the Command Plugin.

Refer to the [XL Deploy Reference Manual](/xl-deploy/4.0.x/referencemanual.html) for background information on XL Deploy and deployment concepts.

## Overview

As a system administrator, the need occasionally arises to execute ad hoc scripts or OS commands on remote systems.
The process usually entails having to manually login to each system, copy any required resources to said system and
finally executing scripts/commands to process the resources or configure the remote system.

The process is acceptable for a single system, but tends not to scale when performing the tasks on entire server farms.
The manual intensive process becomes tedious and error prone. The Command Plugin helps with these tedious processes and
significantly reduces the chances of errors.

A system administrator could also use the Command Plugin to reuse existing deployment scripts with XL Deploy, before choosing to move the deployment logic to a more reusable, easily maintainable plugin form.

## Features
* Execute an OS (Unix, Windows) command on a host
* Execute a script on a host
* Associate undo commands
* Copy associated command resources to a host

## Plugin Concepts

### Command

A Command encapsulates an OS specific command, as one would enter at the command prompt of a native OS command shell.
The OS command is captured in the Command's `commandLine` property; e.g. `echo hello >> /tmp/hello.txt`.
The Command also has the capability of uploading any dependent files to the target system and make those available to the
`commandLine` with the use of a placeholder; e.g. `cat ${uploadedHello.txt} >> /tmp/hello.txt`.

### Undo Command

An undo Command has the same characteristics as a Command, except that it reverses the effect of the original Command it
is associated with. An undo Command usually runs when the associated Command is undeployed or upgraded.

### Command Order

The order in which the Command is run in relation to other commands. The order allows for the chaining of commands to create
a logical sequence of events. For example, an install tomcat command would execute before an install web application command, while
a start tomcat command would be the last in the sequence.

## Requirements
This plugin requires:

* **XL Deploy**: version 4.0+

## Usage in Deployment Packages

Please refer to  [Packaging Manual](packagingmanual.html) for more details about the DAR packaging format.

Sample DAR manifest entries defining a package that can (un)provision a tomcat server using an install and uninstall script

    <udm.DeploymentPackage version="1.0" application="CommandPluginSample">
        <cmd.Command name="install-tc-command">
            <order>50</order>
            <commandLine>/bin/sh ${install-tc.sh} ${tomcat.zip}</commandLine>
            <undoCommand>uninstall-tc-command</undoCommand>
            <dependencies>
                <ci ref="install-tc.sh" />
                <ci ref="tomcat.zip" />
            </dependencies>
        </cmd.Command>
        <cmd.Command name="uninstall-tc-command">
            <order>45</order>
            <commandLine>/bin/sh ${uninstall-tc.sh}</commandLine>
            <dependencies>
                <ci ref="uninstall-tc.sh" />
            </dependencies>
        </cmd.Command>
        <file.File name="tomcat.zip" file="tomcat-6.0.32.zip" />
        <file.File name="install-tc.sh" file="install-tc.sh" />
        <file.File name="uninstall-tc.sh" file="uninstall-tc.sh" />
    </udm.DeploymentPackage>


## Using the deployables and deployeds

### Deployable vs. Container Table

The following table describes which deployable / container combinations are possible.
Note that the CIs can only be targeted to containers derived from [Host](#overthere.Host).

<table class="table table-bordered">
<tr>
	<th>Deployables</th> <th>Containers</th> <th>Generated Deployed</th>
</tr>
<tr>
	<td>cmd.Command</td> <td>overthere.Host</td> <td>cmd.DeployedCommand</td>
</tr>
</table>

### Deployed Actions Table

The following table describes the effect a deployed has on its container.

<table class="table table-bordered">
<tr>
	<th>Deployed</th><th align="center">Create</th> <th align="center">Destroy</th> <th align="center">Modify</th>
</tr>
<tr>
	<td>cmd.DeployedCommand</td>
	<td>
	    <ul>
	        <li>Upload command resources to host</li>
	        <li>Resolve command line placeholder references with absolute paths to the uploaded resource files on host</li>
	        <li>Execute command line on host</li>
	    </ul>
	</td>
	<td>
	    <ul>
	        <li>Run the undo command associated with the deployed command, if exists. Actions are same as described for <em>Create</em></li>
	    </ul>
	</td>
	<td>
	    <ul>
	        <li>Run the undo command associated with the deployed command, if exists. Actions are same as described for <em>Create</em></li>
	        <li>Run the modified command. Actions are same as described for <em>Create</em></li>
	    </ul>
	</td>
</tr>
</table>


## Sample Usage Senario - Provision a Tomcat server

For illustration purposes, we take a simplistic view of installing Tomcat. In reality however, your installation of
Tomcat would take on a far more comprehensive form.

Tomcat is distributed as a zip. For this example, we create an installation script to unzip the distribution
on the host. The uninstall script simply shuts down a running Tomcat and deletes the installation directory.

**Create the installation script (install-tc.sh)**

    #!/bin/sh
    set -e
    if [ -e "/apache-tomcat-6.0.32" ]
    then
	    echo "/apache-tomcat-6.0.32 already exists. remove to continue."
	    exit 1
    fi
    unzip $1 -d /
    chmod +x /apache-tomcat-6.0.32/bin/*.sh

**Create the uninstall script (uninstall-tc.sh)**

    #!/bin/sh
    set -e
    /apache-tomcat-6.0.32/bin/shutdown.sh
    rm -rf /apache-tomcat-6.0.32

**manifest snippet defining the command to trigger the execution of the install script for the initial deployment**

The following command will be executed at order 50 in the generated step list. `/bin/sh` is used on the host to execute
the install script which takes a single parameter, the absolute path to the `tomcat.zip` on the host. When the command is undeployed, `uninstall-tc-command` will be executed.

    <cmd.Command name="install-tc-command">
        <order>50</order>
        <commandLine>/bin/sh ${install-tc.sh} ${tomcat.zip}</commandLine>
        <undoCommand>uninstall-tc-command</undoCommand>
        <dependencies>
            <value>install-tc.sh</value>
            <value>tomcat.zip</value>
        </dependencies>
    </cmd.Command>

**manifest snippet defining the undo command to trigger the execution of the uninstall script for the undeploy**

The undo command will be executed at order 45 in the generated step list. Note that it has a lower order than the
`install-tc-command`. This ensures that the undo command will always run before the `install-tc-command` during an upgrade.

    <cmd.Command name="uninstall-tc-command">
        <order>45</order>
        <commandLine>/bin/sh ${uninstall-tc.sh}</commandLine>
        <dependencies>
            <value>uninstall-tc.sh</value>
        </dependencies>
    </cmd.Command>

See the Usage in Deployment Packages section for the complete manifest file.