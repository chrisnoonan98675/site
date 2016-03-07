---
title: Deploy an application using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- deployment
- script
---

You can do a deployment from the XL Deploy [command-line interface (CLI)](/xl-deploy/how-to/execute-tasks-from-the-xl-deploy-cli.html).

## Sample deployment (XL Deploy 5.0.0 or later)

This is an example of a simple deployment in XL Deploy 5.0.0 or later:

    # Load package
    package = repository.read('Applications/forCustomer/CUSTOM_deployment_package')

    # Load environment
    environment = repository.read('Environments/forLocal')

    # Start deployment
    deploymentRef = deployment.prepareInitial(package.id, environment.id)
    depl = deployment.prepareAutoDeployeds(deploymentRef)
    task = deployment.createDeployTask(depl)
    deployit.startTaskAndWait(task.id)

This is an example of a deployment that sets the [`orchestrator`](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) property:

    environment = repository.read('Environments/sampleEnv')
    package = repository.read('Applications/myApp/2')
    depl = deployment.prepareInitial(package.id, environment.id)
    depl2 = deployment.prepareAutoDeployeds(depl)
    depl2.deployedApplication.values['orchestrator'] = 'parallel-by-container'
    task = deployment.createDeployTask(depl2)
    deployit.startTaskAndWait(task.id)

## Sample deployment (XL Deploy 4.5.x or earlier)

This is an example of a simple deployment in XL Deploy 4.5.x or earlier:

	# Import package
	package = deployit.importPackage('demo-application/1.0')

	# Load environment
	environment = repository.read('Environments/DiscoveredEnv')

	# Start deployment
	deploymentRef = deployment.prepareInitial(package.id, environment.id)
	deploymentRef = deployment.generateAllDeployeds(deploymentRef)
	taskID = deployment.deploy(deploymentRef).id
	deployit.startTaskAndWait(taskID)

## Sample undeployment

Undeployment follows the same general flow as deployment:

	taskID = deployment.undeploy('Environments/DiscoveredEnv/demo-application').id
	deployit.startTaskAndWait(taskID)
