---
title: Execute a control task from the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- control task
- script
---

You can execute [control tasks](/xl-deploy/concept/understanding-control-tasks-in-xl-deploy.html) from the XL Deploy command-line interface (CLI). You can find the control tasks that are available in the configuration item (CI) reference documentation for each plugin.

For example, the [`glassfish.StandaloneServer` CI](/xl-deploy-glassfish-plugin/5.0.x/glassfishPluginManual.html#glassfishstandaloneserver) includes a `start` control task that starts the server. To execute it:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> deployit.executeControlTask('start', server)

Some control tasks include parameters that you can set. For example:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> control = deployit.prepareControlTask(server, 'methodWithParams')
    deployit> control.parameters.values['paramA'] = 'value'
    deployit> taskId = deployit.createControlTask(control)
    deployit> deployit.startTaskAndWait(taskId)
