---
title: Perform a deployment using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- deployment
- script
---

You can do a deployment from the XL Deploy command-line interface (CLI). Here is an example of how to perform a simple deployment.

	# Import package
	deployit> package = deployit.importPackage('demo-application/1.0')

	# Load environment
	deployit> environment = repository.read('Environments/DiscoveredEnv')

	# Start deployment
	deployit> deploymentRef = deployment.prepareInitial(package.id, environment.id)
	deployit> deploymentRef = deployment.generateAllDeployeds(deploymentRef)
	deployit> taskID = deployment.deploy(deploymentRef).id
	deployit> deployit.startTaskAndWait(taskID)

Undeployment follows the same general flow:

	deployit> taskID = deployment.undeploy('Environments/DiscoveredEnv/demo-application').id
	deployit> deployit.startTaskAndWait(taskID)
