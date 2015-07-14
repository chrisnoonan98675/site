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

You can use the XL Deploy command-line interface (CLI) to deploy, update, and undeploy applications, including applications with [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html) (supported in XL Deploy 5.1.0 and later).

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
