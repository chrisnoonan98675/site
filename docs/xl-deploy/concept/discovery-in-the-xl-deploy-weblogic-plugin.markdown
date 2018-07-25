---
title: Discovery in the XL Deploy WebLogic plugin
categories:
- xl-deploy
subject:
- WebLogic
tags:
- weblogic
- oracle
- plugin
- discovery
---

After you specify the host and domain of a server running Oracle WebLogic, you can use the [XL Deploy WebLogic (WLS) plugin](/xl-deploy/concept/weblogic-plugin.html) to discover the following middleware containers:

* Cluster (`wls.Cluster`)
* Server (`wls.Server`)
* JMS server (`wls.JmsServer`)

## Discovery in the user interface

To discover a domain from the XL Deploy user interface:

1. Go to the Repository.
1. Under **Infrastructure**, create an `overthere.Host` configuration item (CI) with the appropriate connection credentials.
1. Right-click **Infrastructure** and select **Discover** > **wls** > **Domain**.
1. Configure the required properties for the `wls.Domain` and follow the steps in the discovery wizard.

## Discovery with the CLI

This is an sample XL Deploy command-line interface (CLI) script that discovers a sample WebLogic domain:

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

The WebLogic topology discovery does not discover/associate the host associated with the managed servers (`wls.Server`). So if a `wls.Cluster` is spanned on multiple hosts, you must manually create the managed server's hosts and its association with the server. You can do this using the CLI or the user interface. This may be needed for certain deployment scenarios where knowledge of the server's host is needed (such as NoStage deployments).
