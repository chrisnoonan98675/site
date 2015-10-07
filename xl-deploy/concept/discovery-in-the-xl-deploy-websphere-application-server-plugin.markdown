---
title: Discovery in the XL Deploy WebSphere Application Server plugin
categories:
- xl-deploy
subject:
- WebSphere Application Server plugin
tags:
- websphere
- middleware
- plugin
- discovery
---

*Discovery* is the process of scanning your environment (as much as possible) and creating configuration items (CIs) in the XL Deploy repository based on the configurations that are found. Discovery allows you to easily set up your infrastructure. However, the discovered CIs may not always be completed, as some CIs contain properties that cannot be automatically discovered, such as passwords. You must enter these properties manually.

The WebSphere Application Server (WAS) plugin can only discover WebSphere CIs via a Deployment Manager. Discovery via Administrative Agents is not supported.

## Discovery process

Discover involves the following steps:

1. Create a CI that represents the starting point for discovery (often a *host* CI).
2. Start discovery passing this CI.
3. Store the discovered CIs in the repository.
4. Complete the discovered CIs manually by providing missing properties.
5. Add the discovered CIs to an environment; this step is optional. The discovered CIs are stored in the repository under **Infrastructure**, so you can add them to an environment at a later time.

### Create a CI starting point

First, you must create a starting point from which you can start the discovery process. The starting point is a CI that specifies the host where discovery should start. Depending on the middleware, additional parameters may be needed.

This is an example of a way to use the command-line interface (CLI) to start discovery for WAS. First, a CI is created for the host, then a CI is created for the deployment manager running on the host, which will be the starting point for the discovery.

    # Create a CI with the required discovery parameters filled in
    wasHost = factory.configurationItem('Infrastructure/rs94asob.k94.corp.com', 'overthere.SshHost', {
            'address': 'was-61',
            'username': 'root',
            'password': 'rootpwd',
            'os': 'UNIX',
            'connectionType': 'SFTP'})

    repository.create(wasHost)

    # Now create a WAS deployment manager
    dmManager = factory.configurationItem(wasHost.id + '/wasDM', 'was.DeploymentManager', {
            'host':'Infrastructure/rs94asob.k94.corp.com',
            'version':'WAS_61',
            'wasHome':'/opt/ws/6.1/profiles/dmgr',
            'username':'wsadmin',
            'password':'wsadmin'})

The possible values for `version` are: `WAS_61`, `WAS_70`, `WAS_80`, and `WAS_85`. The final segment of the CI ID (`wasDM` in this example) must match the WebSphere cell name.

### Start discovery passing a CI

After the CI starting point has been created, you can use it to perform discovery. The XL Deploy CLI discovery functionality is synchronous, which means that the CLI will wait until the discovery process finishes.

The process of discovery works exactly like a regular task:

    deployit> taskId = deployit.createDiscoveryTask(dmManager)
    deployit> deployit.startTaskAndWait(taskId)
    deployit> discoveredCIs = deployit.retrieveDiscoveryResults(taskId)

Note there are no single or double quotes around `dmManager`, because it is an object and not a string. The result of this command will be an object containing a list of discovered CIs.

### Store the CIs in the repository

XL Deploy returns a list of discovered middleware CIs. They are not yet persisted; to store them in the repository, use the following code:

    repository.create(discoveredCIs)

**Note:** You need permission to store CIs in the database.

### Complete discovered middleware CIs

The easiest way to see which discovered CIs require additional information is by printing them. Any CIs that contain passwords (displayed as `********`) will need to be completed. To print the stored CIs, the following code can be used:

    for ci in discoveredCIs: deployit.print(repository.read(ci.id));

You can edit the created CIs in the **Repository** part of the GUI.

### Adding CIs to environments

Middleware that is used as a deployment target must be grouped together in an environment. To create an environment using the CLI:

    env = factory.configurationItem('Environments/DiscoveredEnv', 'udm.Environment')

To add the discovered CIs to the environment:

    env.values['members'] = [ci.id for ci in discoveredCIs]

Note that not all of the discovered CIs should necessarily be stored in an environment. For example, in the case of WAS, some nested CIs may be discovered of which only the top-level one must and can be stored.

Store the new environment:

    repository.create(env)

The newly created environment can now be used as a deployment target.

## Enable discovery for additional CI types

The following line in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file defines the CI types for which discovery is enabled:

    #was.DeploymentManager.deployedsToDiscover=was.VirtualHost,was.SharedLibrary

To add more CIs, uncomment the line and add the CI names. For example:

    was.DeploymentManager.deployedsToDiscover=was.VirtualHost,was.SharedLibrary, was.StringNameSpaceBinding
