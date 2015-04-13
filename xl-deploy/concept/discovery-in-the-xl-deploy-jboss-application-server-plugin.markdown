---
title: Discovery in the XL Deploy JBoss Application Server plugin
categories:
- xl-deploy
subject:
- JBoss Application Server plugin
tags:
- jboss
- middleware
- plugin
- discovery
- infrastructure
---

After you specify the JBoss server home location and the host on which the JBoss server is running, you can use the JBoss Application Server plugin to discover the following properties on a running JBoss server:

* JBoss version
* Control port
* HTTP port
* AJP port

This is a sample XL Deploy command-line interface (CLI) script which discovers a sample JBoss server:

	host = repository.create(factory.configurationItem('Infrastructure/jboss-51-host', 'overthere.SshHost',
		{'connectionType':'SFTP','address': 'jboss-51','username': 'root','password':'centos','os':'UNIX'}))
	jboss = factory.configurationItem('Infrastructure/jboss-51-host/jboss-51', 'jbossas.ServerV5',
		{'home':'/opt/jboss/5.1.0.GA', 'host':'Infrastructure/jboss-51-host'})

	taskId = deployit.createDiscoveryTask(jboss)
    deployit.startTaskAndWait(taskId)
    cis = deployit.retrieveDiscoveryResults(taskId)

	deployit.print(cis)

	#discovery just discovers the topology and keeps the configuration items in memory. Save them in Deployit repository
	repository.create(cis)
	
Note:

* Hosts are created under the Infrastructure tree, so the host ID is kept as `Infrastructure/jboss-51-host`
* Host address can be the host IP address or the DNS name defined for the host
* The JBoss server has a containment relation with a host (created under a host), so the server ID is kept as `Infrastructure/jboss-51-host/jboss-51`
