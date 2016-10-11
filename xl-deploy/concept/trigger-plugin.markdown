---
title: Introduction to the XL Deploy Trigger plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- trigger
- email
weight: 362
---

The Trigger plugin allows you to configure XL Deploy to send emails for certain events. For example, you can add rules to send an email whenever a step fails, or when a deployment has completed successfully.

When XL Deploy resolves a deployment plan, a task comprising of multiple steps is created and subsequently executed.

The task, along with the steps, transition through various states before finally completing. The Trigger plugin allows a user to associate actions, example sending an email, to be triggered for one of these state transitions.

## Actions

With the trigger plugin, you can define notification actions for certain events. Certain XL Deploy objects are available to the actions.

### Deployed applications

The entire deployed application (`udm.DeployedApplication`), containing application and environment configuration items, is available to the actions.

### Task

The task object contains information about the task. The following properties are available:

* `id`
* `state`
* `description`
* `startDate`
* `completionDate`
* `nrSteps`: The number of steps in the task
* `currentStepNr`: The current step been executed
* `failureCount`: The number of times the task has failed
* `owner`
* `steps`: The list of steps in the task. Not available when action triggered from `StepTrigger`.

### Step

The step object contains information about a step. It is not available when the action is triggered from `TaskTrigger`. The following properties are available:

* `description`
* `state`
* `log`
* `startDate`
* `completionDate`
* `failureCount`

### Action

The action object is a reference to the executing action itself.

## Email Action

This section describes how to configure an email action.

First, you will need to define a `#mail.SmtpServer` CI under the **Configuration** root.

The `trigger.EmailNotification` CI is used to define the message template for the emails that will be sent. Under the **Configuration** root, define a `trigger.EmailNotification` configuration item. In the CLI you can do something like:

	myEmailAction = factory.configurationItem("Configuration/MyFailedDeploymentNotification", "trigger.EmailNotification")
	myEmailAction.mailServer = "Configuration/MailServer"
	myEmailAction.subject = "Application ${deployedApplication.version.application.name} failed."
	myEmailAction.toAddresses = ["support@mycompany.com"]
	myEmailAction.body = "Deployment of ${deployedApplication.version.application.name} was cancelled on environment ${deployedApplication.environment.name}"
	repository.create(myEmailAction)

The `subject`, `toAddresses`, `fromAddress`, `body` properties accept [FreeMarker template syntax](http://freemarker.org/docs/ref.html) and can access the following XL Deploy objects:

* `${deployedApplication}`
* `${task}`
* `${step}`

For example, `${deployedApplication.version.application.name}` refers to the name of the application being deployed.

The email body can also be defined in an external template file. Set the path to the file in the `bodyTemplatePath` property. This can be either an absolute path, or a relative path that will be resolved via XL Deploy's classpath. By specifying a relative path, XL Deploy will look in the `ext` directory of the XL Deploy Server and in all (packaged) plugin jar files.

## State transitions

To enable a trigger for deployments, add it to the `triggers` property of an environment. The trigger will then listen to state transitions in tasks and steps that occur during a deployment. When the state transition described by the trigger matches, the associated actions are executed.

XL Deploy 4.0 ships with the `EmailNotification` trigger. Custom trigger actions can be written in Java.

### Task state transitions

The task state transitions can be derived from the task state diagram below. The ANY state is a wildcard state that matches any state.

![Task State](images/task-state-diagram.png "Task State Diagram")

A `trigger.TaskTrigger` can be defined under the **Configuration** root and associated with the environment on which it should be triggered.

	taskTrigger = factory.configurationItem("Configuration/TriggerOnCancel","trigger.TaskTrigger")
	taskTrigger.fromState = "ANY"
	taskTrigger.toState   = "CANCELLED"
	taskTrigger.actions   = [myEmailAction]
	repository.create(taskTrigger)

	env = repository.read("Environments/Dev")
	env.triggers = ["Configuration/TriggerOnCancel"]
	repository.update(env)

### Step state transitions

The step state transitions can be derived from the step state diagram in [Steps and step lists in XL Deploy](/xl-deploy/concept/steps-and-steplists-in-xl-deploy.html). The ANY state is a wildcard state that matches any state.

A `trigger.StepTrigger` can be defined under the **Configuration** root and associated with the environment on which it should be triggered.

	stepTrigger = factory.configurationItem("Configuration/TriggerOnFailure","trigger.StepTrigger")
	stepTrigger.fromState = "EXECUTING"
	stepTrigger.toState   = "FAILED"
	stepTrigger.actions   = [myEmailAction]
	repository.create(stepTrigger)

	env = repository.read("Environments/Dev")
	env.triggers = ["Configuration/TriggerOnFailure"]
	repository.update(env)
