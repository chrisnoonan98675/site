---
title: Discover middleware using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- middleware
- discovery
- script
---

In order to do effective deployments, you need to have an accurate model of your infrastructure in XL Deploy. This can be done automatically by XL Deploy by using the _discovery_ feature. Using discovery, XL Deploy will populate your infrastructure repository by scanning your middleware environment and creating configuration items (CIs) in the repository.

The CIs discovered during discovery will help you in setting up your infrastructure. However, they need not be complete: some CIs contain properties that can not be automatically discovered, like passwords. These kind of properties will still need to be entered manually.

Discovery is part of the XL Deploy plugin suite, and the exact discovery functionality available varies depending on the middleware platforms present in your environment. Please refer to the appropriate plugin manual for more detailed information about discovery on a certain middleware platform, including examples.

The following steps comprise discovery:

1. Create a CI representing the starting point for discovery (often a _Host_ CI).
2. Start discovery with this CI.
3. Store the discovered CIs in the repository.
4. Complete the discovered CIs by providing missing properties manually if needed.
5. Add the discovered CIs to an environment.

The last step of discovery is optional. The discovered CIs will be stored under the `Infrastructure` root node in the repository and may be added to an environment at some later time.

## Create a starting point

The first step taken in discovery is to create a starting point. The starting point is a configuration item that discovery process needs to get going. This is usually a server host. Depending on the middleware you are trying to discover, additional parameters may be needed.

Following is an example of how to perform discovery based on the demo-plugin, an example plugin that is part of XebiaLabs `community-plugins` repository. First a CI is created for localhost. Then we create a CI for the demo server we want to discover. This CI will be the starting point for discovery.

	# Create the host CI
	deployit> host = factory.configurationItem('Infrastructure/demoHost', 'overthere.LocalHost')
	deployit> host.os = 'UNIX'
	deployit> repository.create(host)

	deployit> server = factory.configurationItem('Infrastructure/demoHost/demoServer', 'demo.Server')
	deployit> server.host = host.id

## Execute discovery task

Now discovery task can be created and executed.

	deployit> taskId = deployit.createDiscoveryTask(server)
	deployit> deployit.startTaskAndWait(taskId)
	deployit> discoveredCIs = deployit.retrieveDiscoveryResults(taskId)

The result of these commands will be an object containing a list of discovered CIs.

## Store the discovered CIs

XL Deploy returns a list of discovered middleware CIs. Note that these are not yet persisted. To store them in the repository, use the following code:

	deployit> repository.create(discoveredCIs)

## Complete discovered middleware CIs

The easiest way to find out which of the discovered CIs require additional information is by printing them. Any CI that contains passwords (displayed as `********`) will need to be completed. To print the stored CIs, the following code can be used:

	deployit> for ci in discoveredCIs: deployit.print(repository.read(ci.id));

**Note:** The created CIs can also be edited in the GUI using the Repository Browser if they have been stored in the repository.

## Adding CIs to environments

Middleware that is used as a deployment target must be grouped together in an environment. Environments are CIs of type `udm.Environment` and, like all CIs, can be created from the CLI by using the factory object. The following command can be used for this:

	deployit> env = factory.configurationItem('Environments/DiscoveredEnv', 'udm.Environment')

Add the discovered CIs to the environment:

	deployit> env.values['members'] = [ci.id for ci in discoveredCIs]

Note that not all of the discovered CIs should necessarily be stored in an environment. For example, in the case of WAS, some nested CIs may be discovered of which only the top-level one must be stored.

Don't forget to store the new environment in the repository:

	deployit> repository.create(env)

The newly created environment can now be used as a deployment target.

**Note:** The user needs specific permission to store CIs in the database.
