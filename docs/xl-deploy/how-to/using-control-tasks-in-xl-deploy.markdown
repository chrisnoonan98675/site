---
title: Using control tasks in XL Deploy
categories:
- xl-deploy
subject:
- Control task
tags:
- middleware
- control task
- task
weight: 140
---

Control tasks are actions that you can perform on middleware or middleware resources; for example, checking the connection to a host is a control task. When you trigger a control task, XL Deploy starts a task that executes the steps associated with the control task.

To view a list of control task in the default GUI, click **Task Monitor** and select **Control Tasks**. By default, the Task Monitor only shows the tasks that are assigned to you. To see all tasks, click **All** in the Tasks field of the filters section.

## Trigger a control task from the GUI

To trigger a control task on a configuration item (CI) in the default GUI:

1. In the top menu bar, click **Explorer**.
1. Locate the CI for which you want to trigger a control task. Click ![Explorer action menu](/images/menu_three_dots.png) to see the control tasks that are available.
2. Select the control task to trigger it. Some control tasks will require you to provide values for parameters before XL Deploy executes the task.

To trigger a control task on a configuration item (CI) in the legacy GUI:

1. In the top menu bar, click **Repository**.
1. Locate the CI for which you want to trigger a control task. Right-click it to see the control tasks that are available.
2. Select the control task to trigger it. Some control tasks will require you to provide values for parameters before XL Deploy executes the task.

## Trigger a control task from the CLI

You can execute control tasks from the XL Deploy command-line interface (CLI). You can find the control tasks that are available in the configuration item (CI) reference documentation for each plugin. For example, the [`glassfish.StandaloneServer` CI](/xl-deploy-glassfish-plugin/5.0.x/glassfishPluginManual.html#glassfishstandaloneserver) includes a `start` control task that starts a GlassFish server. To execute it:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> deployit.executeControlTask('start', server)

Some control tasks include parameters that you can set. For example:

    deployit> server = repository.read('Infrastructure/demoHost/demoServer')
    deployit> control = deployit.prepareControlTask(server, 'methodWithParams')
    deployit> control.parameters.values['paramA'] = 'value'
    deployit> taskId = deployit.createControlTask(control)
    deployit> deployit.startTaskAndWait(taskId)

## Add a control task to an existing CI type

To add a control task to an existing configuration item (CI) type such as `Host`, you can extend the [Generic plugin](/xl-deploy/concept/generic-plugin.html) as follows:

1. Define a custom container that extends the generic container. The custom container should define the control task and the associated script to run. The script is a [FreeMarker](http://freemarker.incubator.apache.org/) template that is rendered, copied to the target host, and executed. For example, in `synthetic.xml`:

        <type type="mycompany.ConnectionTest" extends="generic.Container">
	        <!-- inherited hidden -->
	        <property name="startProcessScript" default="mycompany/connectiontest/start" hidden="true"/>
	        <property name="stopProcessScript" default="mycompany/connectiontest/stop" hidden="true"/>
	        <!-- control tasks -->
	        <method name="start" description="Start some process"/>
	        <method name="stop" description="Stop some process"/>
	    </type>

2. In the XL Deploy Library (XL Deploy Repository if you are using the legacy UI), create the container under the host that you want to test.
3. Execute the control task.

## Create a custom control task

For information about writing your own XL Deploy control task, refer to [Create a custom control task](/xl-deploy/how-to/create-a-custom-control-task.html).
