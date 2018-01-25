---
title: Perform blue-green deployments
categories:
- xl-release
- xl-deploy
subject:
- Calling XL Deploy from XL Release
tags:
- blue-green
- deployment pattern
---

This guide explains how to perform ["blue-green" deployments](http://martinfowler.com/bliki/BlueGreenDeployment.html) using XL Release and XL Deploy. 

Blue-green deployment is a pattern in which identical production environments known as Blue and Green are maintained, one of which is live at all times. 
Suppose Blue is the live environment. Then, applications or features are deployed to and tested on the non-live environment Green before user traffic is diverted to it.

If it is necessary to roll back the release in the Green environment, you can simply route user traffic back to the Blue environment.

When the Green environment is stable, the Blue environment can be retired and is ready updated in the next iteration, where it will take the role of the Green environment of this example. 

This guide has the following parts

1. Defining the pattern in XL Release.
2. Defining the environments in XL Deploy.
3. Putting it all together and making it work.

## Defining the Blue/Green pattern in XL Release

XL Release makes it easy to define your release process and is an excellent tool to prototype the process that is immediatley usable. As you evolve and get experience with the process, you can easily refine the defined release flow in XL Release and integrate your toolchain.

First, we will make a sketch of the process. This gives a nice high-level overview of the Blue/Green deployment pattern.

![Blue/green sketch](../images/bluegreen/bluegreen-sketch.png)

This XL Release template shows the basic steps. It can be made quickly by adding manual tasks as placeholders. In this example we will use XL Deploy, so also stub XL Deploy tasks are added.

This high level sketch is the starting point of your Blue/Geen deployment process. It reads as a more fine grained description of Blue/Green deployments, but can immediately be used as a workflow model.

_Download the Blue/Green deployment template in [XLR format](../images/bluegreen/BlueGreen-template.xlr) (for XL Release 7.5 and higher) or as [Releasefile](../images/bluegreen/Releasefile.groovy) to import it in your XL Release server._

There are four phases:

#### 1. **Select new environment**

This is a preparation phase to make sure that the software is deployed to the correct environment.

 * **What is currently running?**. Determine the environment that is currently live ('Blue') and deduce the new environment ('Green'). Since 'Blue' and 'Green' can switch roles, we will use the the terms 'current live environment' for 'Blue' and 'new environment' for 'Green' in this example.

 * **Confirm new environment**. The Release Manager confirms that we will deploy to the right environment. This is modeled in XL Release by way of a User Input Task.

#### 2. **Deploy and test**

We are ready to deploy the new application version to the environment that is not in use.

 * **Deploy to new environment**. XL Deploy takes care of the deployment of the new version to the environment that is not currently live. The [XL Deploy task in XL Release](/xl-release/how-to/xld-plugin.html) takes care of communicating with the XL Deploy server.
 * **Run tests**. In the sketch this is modeled as a manual activity. This allowes you to run the template without having all the integrations in place. Later, this task can be replaced wqith an automated task that integrates with a testing tool.
 * **OK to switch?** This is a *gate* in the process, where the release manager confirms we can proceed to bring the new environment ('Green') live.

#### 3. **Switch to live** 

Now that the deployment is running and fully tested we can make the switch.

* **Change load balancer**. For now we have to manually do this step and mark this task as done in XL Release before the process continuess. Later we can replace it with an automated integration.

* **Update registry with live environment**. Remember which environment is currently live so we know the next time we will run this template.

* **Send notification**. Announce the availability of the new features.

#### 4. Retire old environment

When the new environment is running fine and there is no need for a rollback, the old environment that is no longer live can be turned off and recycled for a subsequent update.

* **New environment is stable?**. Make the decision that the old environment is no longer needed for rollback and can be discarded.

* **Undeploy old environment**. Turn of old environment. We are using XL Deploy to do this.


## Refine the process 

We will now setup XL Deploy to have the environments ready and link everything together in XL Release using variables to communicate between tasks.

### XL Deploy setup

In XL Deploy, you will have two nearly identical environments, 'Blue' and 'Green'.

![Blue and Green environments in XL Deploy](../images/bluegreen/bluegreen-xldeploy.png)

In this example, they live in the 'PROD' folder.

In XL Release, [add an XL Deploy Server item](/xl-release/how-to/xld-plugin.html#configure-xl-deploy-server-shared-configuration) under Shared Configuration and point it to your XL Deploy installation.

### Variables in XL Release

We will define the following variables in XL Release that maintain the state of the Blue/Green process.

* A [global variable](https://docs.xebialabs.com/xl-release/how-to/configure-global-variables.html) `${global.bluegreen-environments}`. This is the list of available environments. This list will be used by various other variables. We want to configure the list of environments once, so we create a global variable for it. It is a variable of type 'List' that has two values: 'Blue' and 'Green', corresponding to the names of the environment in XL Deploy. 

![${global.live-environment} configuration](../images/bluegreen/bluegreen-globalvariable-list.png)

* A global variable `${global.live-environment}`. We use a global variable as a registry of which environment is currently live. This variable can have two values: 'Blue' or 'Green'. It is modeled as a variable of type ListBox and we link it to the list of environments we created earlier:

![${global.live-environment} configuration](../images/bluegreen/bluegreen-globalvariable.png)

* [Release variables](/xl-release/how-to/create-release-variables.html) `${new-environment}` -- which is the environment we are going to deploy to; and `${old-environment}` -- which is the environment that is currently active and that will be retired at the end of the process. Both are modeled as a ListBox that links to the list of available environments in `${global.bluegreen-environments}`. The values of these variables are determined during the release process and are not filled in by the user. Therefore we need to uncheck the option 'Show on Create release form'.

![New environment variable](../images/bluegreen/bluegreen-release-variable.png)

* Release variables `${app}` and `${version}`. The version of the application to deploy is filled in when the release process starts and is passed to XL Deploy to indicate which version to deploy. The values of these variables are filled in by the release manager when the release starts, so the option 'Show en Create release form' should be enabled.

We use these variables in the release flow. Here are some examples of tasks using the variable.

In **What is currently live?**, we use a simple script to determine the new environment:
	
	if globalVariables['global.live-environment'] == 'Blue':
	    releaseVariables['new-environment'] = 'Green'
	    releaseVariables['old-environment'] = 'Blue'
	
	if globalVariables['global.live-environment'] == 'Green':
	    releaseVariables['new-environment'] = 'Blue'
	    releaseVariables['old-environment'] = 'Green'
    
Next, the User Input task **Confirm new environment** confirms the environments with the release manager. The Release manager may override this choice by choosing another value from the drop down. Note that you can use the variables in the description in order to render a helpful message.

![Confirm environment dialog](../images/bluegreen/bluegreen-userinput.png)

The **Deploy** task takes the variable to determine which environment to deploy to.

![Deploy task](../images/bluegreen/bluegreen-deploytask.png)

When the deployment was successful, the registry of what is running is updated by setting the global variables in a the Script task **Update registry with live  environment**.

	globalVariables['global.live-environment'] = '${new-environment}'
	globalVariables['global.live-version'] = '${application}/${version}'

Finally, the `${old-environment}` variable is needed in the **Undeploy old environment** task.

![Undeploy task](../images/bluegreen/bluegreen-undeploy.png)



### Users & Teams in XL Release

In order to run the scripts with proper permissions, make sure a **Run automated tasks as user** is set in the template properties page. This user needs the global permission **Edit global variables**.

Furthermore, you should create teams and assign them to the appropiate tasks. For the example, we will assign each task to the Release admin team. This will make it easier to run the examples. The easiest way to do this is to do a bulk edit from the table view:

![Assign team in bulk](../images/bluegreen/bluegreen-bulk-assignment.png)


## Running the release 

We are now ready to run the release. Create the release and fill in the values for the application name and version. Note that they should match the application name and version as defined in XL Deploy.

![Create new release](../images/bluegreen/bluegreen-create-release.png)

When the release reaches the task, you enter the environment to use:

![Sample blue-green release](../images/bluegreen/bluegreen-choose-target.png)

The correct deployment will be triggered in XL Deploy.

![Sample blue-green release](../images/bluegreen/bluegreen-deployment.png)


----

<!-- NOTES



#### Refine process: Retire old environment OR rollback

* User Input boolean or list box
* Precondition on block 'Undeploy' or 'Rollback'

Integrate load balancer


#### Blue/Green with Kubernetes

* Don't recycle the old env. 


#### Why not everything in XL Deploy?

* Some steps may need time
* Integrations with tools that are not related to deployment, e.g. sending email.

-->
