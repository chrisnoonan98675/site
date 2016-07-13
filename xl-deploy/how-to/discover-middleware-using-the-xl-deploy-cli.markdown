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
weight: 114
---

The _discovery_ feature allows you to automatically create an accurate model of your infrastructure in XL Deploy. During discovery, XL Deploy scans your middleware environment and creates configuration items (CIs) in the repository. Discovery is part of the XL Deploy plugin suite, and the exact discovery functionality available depends on the middleware platforms that are present in your environment.

Note that the CIs discovered during discovery may not be complete; some CIs contain properties that cannot be discovered automatically, such as passwords. You must fill in these properties manually.

Discovery has the following steps:

1. Create a CI representing the starting point for discovery (this is often a host CI).
2. Start discovery with this CI.
3. Store the discovered CIs in the repository.
4. Complete the discovered CIs by filling in missing properties manually (if needed).
5. Add the discovered CIs to an environment (can be done later).

## Create a starting point

The first step in discovery is to create a starting point. This is usually a host CI. Depending on the middleware you are trying to discover, additional parameters may be needed.

In this example, a CI is created for localhost, then a CI is created for a demo server. This CI will be the starting point for discovery.

    host = factory.configurationItem('Infrastructure/demoHost', 'overthere.LocalHost')
    host.os = 'UNIX'
    repository.create(host)

    server = factory.configurationItem('Infrastructure/demoHost/demoServer', 'demo.Server')
    server.host = host.id

## Execute discovery task

Now discovery task can be created and executed.

	taskId = deployit.createDiscoveryTask(server)
	deployit.startTaskAndWait(taskId)
	discoveredCIs = deployit.retrieveDiscoveryResults(taskId)

The result of these commands will be an object containing a list of discovered CIs.

## Store the discovered CIs

XL Deploy returns a list of discovered middleware CIs. Note that these are not yet persisted. To store them in the repository, execute:

	repository.create(discoveredCIs)

## Complete discovered middleware CIs

The easiest way to find out which CIs require additional information is by printing them. Any CI that contains passwords (displayed as `********`) will need to be completed. To print the stored CIs, the following code can be used:

	for ci in discoveredCIs: deployit.print(repository.read(ci.id));

**Tip:** If the CIs have already been stored in the repository, you can also edit them in the XL Deploy GUI.

## Adding CIs to environments

Middleware that is used as a deployment target must be grouped in an environment. Environments are CIs of type `udm.Environment` and, like all CIs, can be created from the CLI by using the factory object. The following command can be used for this:

	env = factory.configurationItem('Environments/DiscoveredEnv', 'udm.Environment')

Add the discovered CIs to the environment:

	env.values['members'] = [ci.id for ci in discoveredCIs]

Note that not all of the discovered CIs should necessarily be stored in an environment. For example, in the case of WAS, some nested CIs may be discovered of which only the top-level one must be stored.

Don't forget to store the new environment in the repository:

	repository.create(env)

The newly created environment can now be used as a deployment target.

**Note:** The user needs specific permission to store CIs in the database.
