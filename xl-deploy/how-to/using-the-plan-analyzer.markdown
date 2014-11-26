--
title: Using the Plan Analyzer
category:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- planning
--- 

Ever wondered how XL Deploy comes up with a deployment plan? To give you more insight in XL Deploy's planning logic, XL Deploy 3.9 features a Plan Analyzer that allows you to inspect a deployment plan while it's being built up.

## Setup

For this cookbook, we will use a simple Deployment Package containing an EAR file and a DataSource and deploy it to JBoss Application Server v5.

Here's the setup in XL Deploy:

![image](images/planalyzer-setup.png)

Our application is called **ConsumerApp/1.0** and we will deploy it to the **QA** environment. This is a simple environment with one JBoss server running on a unix host.

We start the deployment by dragging the **1.0** package from the Packages window to the Deployment Workspace, followed by the **QA** environment. 

So far, so good, this is what we always do in XL Deploy.

## Analyzing the Plan

To inspect the deployment plan while it is being created, we click the Analyze button on the bottom of the screen.

![image](images/planalyzer-analyze.png)

We now see our deployment specification on the left-hand side and the generated plan on the right. Currently it contains only one step, called "Update the repository with your Deployment". This is the default step that XL Deploy creates when there is an empty plan in order to do its housekeeping.

![image](images/planalyzer-emptyplan.png)

We now drag the **consumer-app.ear** deployable onto **JBoss Server** to see how the plan changes.

![image](images/planalyzer-deployear.png)

With the addition of a single deployed, five steps are added to the plan. Some steps have an preview icon in front of them. These steps have scripts attached to them. By double-clicking on the step, we can see the contents of the generated script. With this information, you will know in advance exactly what actions XL Deploy will execute on the remote server.

![image](images/planalyzer-stopjboss.png)

Apart form the contents of the script that will be executed, the Preview popup offers the following valuable information:

 * Step number - The position of the step in the deployment plan
 * Description - the name of the step 
 * Order - The order associated with the step. The order determines the sequence of steps in the plan, with lower order numbers coming before higher ones. The selected Orchestrator can influence this - see the section on the [Planning Stage](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#the-planning-stage) of the XL Deploy documentation. 
 * Source path - The location of the script template relative to XL Deploy's classpath. For example, relative to `SERVER_HOME/ext` or packaged in the relevant plugin.
 
Note that the step order for steps that don't have previews can also be found by hovering over the Step text and waiting for the tooltip.

![image](images/planalyzer-tooltip.png)

## Updating the plan

Now let's see what happens when we add more deployeds to the mix. Let's add the **Datasource**. This time, we click on it and then click on the Map Single Deployable button - the single green arrow - above it. This will automatically map the datasource to the JBoss server in our environment, the only applicable target here. The plan is updated accordingly. 

![image](images/planalyzer-createdatasource.png)

The Create Datasource also has a Step Preview. Clicking on it will not reveal a shell script, but the datasource definition that will be added to the JBoss server.

![image](images/planalyzer-datasource.png)

The values for the properties like 'check-valid-connection', 'jndi-name', etc are taken from the Datasource. Double click on the Datasource to edit the values of these properties.

![image](images/planalyzer-editdeployed.png)

Any changes made here are of course immediately updated in the Step Preview.

## Using orchestrators

Another great way to use the Plan Analyzer is when using [Orchestrators](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#orchestrators). Orchestrators are used to control the sequence of the generated plan. They are used mainly when dealing with more than one server.

When we deploy to an environment with two JBoss servers, the plan will look like this:

![image](images/planalyzer-twoservers.png)

In this plan, both servers go down simultaneously. This happens because the default orchestrator treats all target middleware as one big "pool", so everything will be stopped, started, updated etc. together.  We can change this by picking another orchestrator. We do this by clicking on the Deployment Properties button and selecting the container-by-container-serial orchestrator. This orchestrator treats each target server as it's own "deployment group", handling all changes to a server in one block and then moving on to the next server.

![image](images/planalyzer-orchestrator.png)

The plan is immediately updated, and now the servers are stopped one by one.

![image](images/planalyzer-onebyone.png)

It is also useful to see which steps are related to which item in your deployment package. The Plan Analyzer also helps in this case: simply click on the deployed. For example when clicking on consumer-app.ear under JBoss Server 1, the associated steps are highlighted.

![image](images/planalyzer-clickondeployed.png)

Vice-versa, when clicking on a step, the relevant deployed is highlighted.

## Starting the Deployment

When you are satisfied with the generated plan, simply click Next to start the deployment. 

![image](images/planalyzer-deploy.png)

This is the definite plan that will be executed to perform the deployment. Note that not until here, you are able to rearrange the steps of the plan by way of drag-and-drop. This is not possible in the Plan Analyzer, because the definite sequence of steps is not known yet at that stage.

## Conclusion

The Plan Analyzer gives you insight in how a deployment plan is built up in XL Deploy, and shows a detailed view of generated scripts. It's a great tool to help you understand XL Deploy when deploying applications or developing your custom plugin.
