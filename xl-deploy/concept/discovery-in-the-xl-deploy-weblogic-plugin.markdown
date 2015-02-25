---
title: Discovery in the XL Deploy WebLogic plugin
categories:
- xl-deploy
subject:
- WebLogic plugin
tags:
- weblogic
- oracle
- middleware
- plugin
- discovery
---

After you specify the host and domain of a server running Oracle WebLogic, you can use the XL Deploy WebLogic plugin to discover the following middleware containers:

* Cluster (`wls.Cluster`)
* Server (`wls.Server`)
* JMS server (`wls.JmsServer`)

## Discovery in the user interface

To discover a domain from the user interface, do the following:

* Create a `overthere.Host` under the Infrastructure node (in the Repository tab) with the appropriate connection credentials.
* Right-click the Infrastructure node and choose **Discover** > **wls** > **wls.Domain**.
* Configure the needed properties for the `wls.Domain` and follow the steps in the wizard.

## Discovery with the CLI

This is an sample XL Deploy command-line interface (CLI) script which discovers a sample WLS domain:

	adminServerHost = repository.create(factory.configurationItem('Infrastructure/adminServerHost','overthere.SshHost', {'os':'UNIX','connectionType':'SFTP', 'address':'wls-103', 'username':'demo-user', 'password':'demo-password'}))

	wlsDomain = factory.configurationItem('Infrastructure/demoWlsDomain', 'wls.Domain', {'wlHome':'/opt/bea-10.3/wlserver_10.3', 'domainHome':'/opt/bea-10.3/user_projects/domains/demoWlsDomain', 'port':'7001', 'username':'weblogic', 'password':'weblogic', 'adminServerName':'adminServer', 'startMode':'NodeManager', 'host':'Infrastructure/adminServerHost'})



    taskId = deployit.createDiscoveryTask(wlsDomain)
    deployit.startTaskAndWait(taskId)
    discoveredItems = deployit.retrieveDiscoveryResults(taskId)

	deployit.print(discoveredItems)

	# Discovery just discovers the topology and keeps the configuration items in memory. 
	# Let's save them in Deployit repository!
	repository.create(discoveredItems)

## Limitations

The WLS topology discovery does not discover/associate the Host associated with the managed servers (`wls.Server`). So if a `wls.Cluster` is spanned on multiple Hosts, the creation of the managed server's Host and its association with the server is a manual process. This can be done using the CLI or more easily, using the UI. This may be needed for certain deployment scenarios where knowledge of the server's host is needed (like NoStage deployments) . 
