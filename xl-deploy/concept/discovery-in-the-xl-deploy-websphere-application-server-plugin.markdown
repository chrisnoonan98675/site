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

The WebSphere Application Server (WAS) plugin can only discover WebSphere configuration items (CIs) via a Deployment Manager. Discovery via Administrative Agents is not supported.

XL Deploy can scan your environment for as far as possible and create CIs in its repository based on the configurations it encounters during the scan. This process is known as _discovery_.

The CIs discovered during discovery will help you set up your infrastructure in an easy way. However, they need not be complete: some CIs contain properties that
can not be automatically discovered, like passwords. These properties must be entered manually.

Because discovery is part of the XL Deploy plugin suite, the exact discovery functionality available varies depending on the middleware platforms present in your environment.

The following steps comprise discovery:

1. Create a CI representing the starting point for discovery (often a _Host_ CI).
2. Start discovery passing this CI.
3. Store the discovered CIs in the repository.
4. Complete the discovered CIs manually by providing missing needed properties.
5. Add the discovered CIs to an environment.

Note that the last step of discovery is optional. The discovered CIs will be stored under the **Infrastructure** root node in the repository and may be added to an environment at some later time.

## Create a CI starting point

The first step taken in discovery is to create a starting point to kick off the process from. This starting point consists of a CIs specifying at least the host where discovery should start. Depending on the middleware you are trying to discover, additional parameters may be needed.

Following is an example of how to start discovery for a WebSphere Application Server (WAS). First a CI is created for the host itself, then a CI is created for the deployment manager running on that host. The deployment manager CI will
be the starting point for discovery.

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

**Note 1:** Possible values for `version` are: `WAS_61`, `WAS_70`, `WAS_80`, `WAS_85`.

**Note 2:** The final segment of the CI ID (`wasDM` in this example) should match your WebSphere cell name.

## Start discovery passing a CI

After the CI starting point has been created, it can be used to perform discovery. The XL Deploy CLI discovery functionality is synchronous, which means that the CLI will wait until the discovery process finishes.

The process of discovery works exactly like a regular task:

    deployit> taskId = deployit.createDiscoveryTask(dmManager)
    deployit> deployit.startTaskAndWait(taskId)
    deployit> discoveredCIs = deployit.retrieveDiscoveryResults(taskId)

Note there are no single or double quotes around `dmManager`, because it is an object and not a string. The result of this command will be an object containing a list of discovered CIs.

## Store the CIs in the repository

XL Deploy returns a list of discovered middleware CIs. Note that these are not yet persisted. To store them in the repository,
use the following code:

    repository.create(discoveredCIs)

## Complete discovered middleware CIs

The easiest way to see the discovered CIs require additional information is by printing them. Any CIs that contain passwords (displayed as `********`) will need to be completed. To print the stored CIs, the following code can be used:

    for ci in discoveredCIs: deployit.print(repository.read(ci.id));

**Note:** You can edit the created CIs in the **Repository** part of the GUI.

## Adding CIs to environments

Middleware that is used as a deployment target must be grouped together in an environment. Environments are CIs and like all
CIs, they can be created from the CLI. The following command can be used for this:

    env = factory.configurationItem('Environments/DiscoveredEnv', 'udm.Environment')

Add the discovered CIs to the environment:

    env.values['members'] = [ci.id for ci in discoveredCIs]

Note that not all of the discovered CIs should necessarily be stored in an environment. For example, in the case of WAS, some
nested CIs may be discovered of which only the top-level one must and can be stored.

Store the new environment:

    repository.create(env)

The newly created environment can now be used as a deployment target.

**Note:** You need permission to store CIs in the database.
