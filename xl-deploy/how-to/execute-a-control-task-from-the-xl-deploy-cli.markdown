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

You can execute control tasks from the XL Deploy command-line interface (CLI). Take, for example, the _start_ control task on a GlassFish server in the [GlassFish plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-glassfish-plugin.html). It can be executed as follows:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> deployit.executeControlTask('start', server)

## Executing a control task manually with parameters

Control tasks can also be executed manually. This allows you to set parameters for the control task, and to have more control over the execution of the task. For example:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> control = deployit.prepareControlTask(server, 'methodWithParams')
    deployit> control.parameters.values['paramA'] = 'value'
    deployit> taskId = deployit.createControlTask(control)
    deployit> deployit.startTaskAndWait(taskId)
