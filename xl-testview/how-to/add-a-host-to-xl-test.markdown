---
layout: beta
title: Add a host to XL TestView
categories:
- xl-testview
subject:
- Test specification
tags:
- host
- ssh
- cifs
- jenkins
---

A host provides a a generic mechanism to connect to local or remote machines and:

* Collect test results
* Execute test tooling

Hosts are based on the open-source [Overthere](https://github.com/xebialabs/overthere) library.

XL TestView includes the following types of hosts:

 * Local host: points to the machine XL TestView is running on.
 * SSH host: Point to a remote machine over SSH, typically used for UNIX based systems.
 * CIFS host: Point to a remote Windows machine.
 * SSH jumpstation host: Connect to a SSH host, via another host (the jumpstation).
 * Jenkins host: Point to a Jenkins job. *Will be removed in the next release*
 
 For information about host configuration options, refer to the [XL Deploy documentation on the Remoting plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-remoting-plugin.html).
 
## Add a host

Normally hosts are created automatically when you import test results using the import wizard or when you use the Jenkins XL TestView plugin.

To manually add a host:
 
1. Click **Hosts** in the top navigation bar.
1. Click **Add a host**.
2. Select a host type from the **Select type** list. The settings that are available depend on the host type that you select.
3. In the **Host name** box, enter a name.
4. Select the host's operating system from the **Operating system** list.
5. Click **Add host** to save the host.

The host can be referenced from a [test specification](/xl-testview/how-to/add-a-test-specification.html).
