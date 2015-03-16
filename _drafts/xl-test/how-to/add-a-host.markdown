---
layout: beta
title: Add a host
categories:
- xl-test
subject:
- Test specification
tags:
- host
- specification
---
Hosts provide a generic mechanism to connect to local or remote machines in order to:

1. collect test results
2. execute test tooling

Hosts are based on the open-source [Overthere](https://github.com/xebialabs/overthere) library.

XL Test ships with the following types of hosts pre-installed:

 * _Local host_: points to the machine XL Test is running on.
 * _SSH host_: Point to a remote machine over SSH, typically used for UNIX based systems.
 * _CIFS host_: Point to a remote Windows machine.
 * _SSH jump station host_: Connect to a SSH host, via another host (the jump station).
 * _Jenkins host_: Point to a Jenkins job.
 
 To know more about the host configuration options, please refer to the [XL Deploy documentation on the Remoting plugin](/xl-deploy/how-to/index.html).
 
## Add a host

Normally hosts are created automatically when importing test results through the import wizard or when using the Jenkins XL Test plugin.

Here the manual process is described. This may be necessary, since not all configuration options are available through the wizard.
 
1. Click **Hosts** in the top menu bar.
1. Click **Add a host**.
2. Select a host type in the **Select type**. Once selected a list of settings are displayed. Those settings depend on the selected host type.
3. Provide a name in the **Host name** box.
4. For all host types define the target operating system in the **Operating system** box.
5. After filling in all required information, click **Add host** to save the host. The host can be referenced from a [test specification](add-a-test-specification.html).
