---
title: Using rules to interact with WebSphere cluster members
author: tom_batchelor
categories:
- xl-deploy
tags:
- extension
- websphere
- middleware
- rules
---

When working with WebSphere (WAS) in a cluster, XL Deploy usually communicates with domain managers, which in turn interact with nodes. However, you might want to execute a script on the nodes themselves; for example, to clean up some files. You can automate this with the rules feature, which was introduced in XL Deploy 4.5.0.

This example shows how you can run a cleanup script on WebSphere nodes. It requires:

* A rule definition in `ext/xl-rules.xml`
* A Jython file that contains the rule definition logic
* FreeMarker templates to perform the cleanup on the host
* Overthere host defined for the WAS nodes

## `xl-rules.xml`

We have to add a rule entry:

    <rule name="CleanUpServersForEarDeployment" scope="deployed">
        <conditions>
            <type>was.EarModule</type>
            <operation>CREATE</operation>
            <operation>DESTROY</operation>
            <operation>MODIFY</operation>
        </conditions>
        <planning-script-path>rules/server-cleanup.py</planning-script-path>
    </rule>

This rule indicates that we want to run a script on a `was.ear.module` for the `CREATE`, `DESTROY`, and `MODIFY` operations. Also, it identifies the path to the Jython script that should be run.

## `server-cleanup.pl`

The Jython file contains logic for the rule. This method uses Jython over XML because it allows us to loop through the nodes in the cluster and extract the server for each one.

	# Function to find the hosts in a WebSphere Custer
	def findHosts(container):
		hosts = []
		ctype = str(container.type)
		if ctype == "was.ManagerServer":
			hosts.push(container.node.host)
		elif ctype == "was.Cluster":
			hosts = [server.node.host for server in container.servers]
		return hosts

	# Function to create a cleanup step
	def createCleanupStep(host):
		return steps.os_script(order = 72, script='rules/cleanupWebsphere', freemarker_context = {"host":host}, target_host=host, description = "Perform clean up on host " + host.name)

	# Main
	for host in findHosts(deployed.container):
		context.addStep(createCleanupStep(host))

The `findHosts` function gathers the host for a `ManagedServer` in a single server environment or gathers all hosts in a cluster environment. These will be the targets for the cleanup.

`createCleanupStep` will create a deployment step of type `os-script`, which invokes the `cleanupWebsphere` script. This step requires the host variable to be added to the `freemarker_context` so we can use it during execution. The order of the step is 72 so it will occur between synchronization and application start.

## `cleanWebsphere`

These FreeMarker templates will perform the cleanup. There are templates for Windows and Unix. These are just examples; you can add to them to perform the actions that you need.

Windows:

    REM Delete all text files in data directory
    del e:\IBM\WAS\profiles\${host.name}\data\*.txt

Unix:

    #!/bin/bash
    #
    # Delete all txt files in data directory
    rm /opt/IBM/WAS/profiles/${host.name}/data/*.txt

These examples use `${host.name}` in the delete command to identify a target directory.

## Overthere Host

Finally, an Overthere host must be defined for the WebSphere nodes. To do so, set the host for each `was.NodeAgent`. For example, the screenshot below shows that the host has been set to `vagrantNode1`.

![WAS node agent](/images/using-rules-to-interact-with-websphere-cluster-members.png)

You can download a compete implementation of this example [here](sample-scripts/using-rules-to-interact-with-websphere-cluster-members.zip). Extract it in the `ext` directory of your XL Deploy installation (ensure that you do not overwrite any existing rules!).

For more information, refer to the [Rules Manual](http://docs.xebialabs.com/releases/latest/xl-deploy/rulesmanual.html), the [Rules Tutorial](http://docs.xebialabs.com/releases/latest/xl-deploy/rulestutorial.html), and the [WebSphere Plugin Manual](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html).
