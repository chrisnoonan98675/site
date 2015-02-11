---
layout: pre-rules
title: Sample Java-based XL Deploy plugin
categories:
- xl-deploy
subject:
- Plugins
tags:
- java
- plugin
---

This example describes some classes from a test plugin we use at XebiaLabs, the Yak plugin.

We'll use the following sample deployment in this example:

* The YakApp 1.1 deployment package.
* The application contains two deployables: "yakfile1" and "yakfile2". Both are of type _YakFile_.
* An environment that contains one container: "yakserver", of type _YakServer_.
* An older version of the application, YakApp/1.0, is already deployed on the container.
* YakApp/1.0 contains an older version of yakfile1, but yakfile2 is new in this deployment.

## Deployable: YakFile

The _YakFile_ is a deployable CI representing a file. It extends the built-in _BaseDeployableFileArtifact_ class.

    package com.xebialabs.deployit.plugin.test.yak.ci;

    import com.xebialabs.deployit.plugin.api.udm.BaseDeployableFileArtifact;

    public class YakFile extends BaseDeployableFileArtifact {
    }

In our sample deployment, both yakfile1 and yakfile2 are instances of this Java class.

## Container: YakServer

The _YakServer_ is the container that will be the target of our deployment.

    package com.xebialabs.deployit.plugin.test.yak.ci;

    // imports omitted...

    @Metadata(root = Metadata.ConfigurationItemRoot.INFRASTRUCTURE)
    public class YakServer extends BaseContainer {

        @Contributor
        public void restartYakServers(Deltas deltas, DeploymentPlanningContext result) {
            for (YakServer yakServer : serversRequiringRestart(deltas.getDeltas())) {
                result.addStep(new StopYakServerStep(yakServer));
                result.addStep(new StartYakServerStep(yakServer));
            }
        }

        private static Set<YakServer> serversRequiringRestart(List<Delta> operations) {
            Set<YakServer> servers = new TreeSet<YakServer>();
            for (Delta operation : operations) {
                if (operation.getDeployed() instanceof RestartRequiringDeployedYakFile && operation.getDeployed().getContainer() instanceof YakServer) {
                    servers.add((YakServer) operation.getDeployed().getContainer());
                }
            }
            return servers;
        }
    }

This class shows several interesting features:

* The YakServer extends the built-in _BaseContainer_ class.
* The @Metadata annotation specifies where in the XL Deploy repository the CI will be stored. In this case, the CI will be stored under the Infrastructure node. (see the XL Deploy Reference Manual for more information on the repository).
* The `restartYakServers()` method annotated with @Contributor is invoked when any deployment takes place (also deployments that may not necessarily contain an instance of the YakServer class). The method `serversRequiringRestart()` searches for any YakServer instances that are present in the deployment and that requires a restart. For each of these YakServer instances, a _StartYakServerStep_ and _StopYakServerStep_ is added to the plan.

When the _restartYakServers_ method is invoked, the _deltas_ parameter contains operations for both yakfile CIs. If either of the yakfile CIs was an instance of _RestartRequiringDeployedYakFile_, a start step would be added to the deployment plan.

## Deployed: DeployedYakFile

The _DeployedYakFile_ represents a _YakFile_ deployed to a _YakServer_, as reflected in the class definition. The class extends the built-in _BaseDeployed_ class.

    package com.xebialabs.deployit.plugin.test.yak.ci;

    // imports omitted...

    public class DeployedYakFile extends BaseDeployedArtifact<YakFile, YakServer> {

       @Modify
       @Destroy
       public void stop(DeploymentPlanningContext result) {
         logger.info("Adding stop artifact");
         result.addStep(new StopDeployedYakFileStep(this));
       }

       @Create
       @Modify
       public void start(DeploymentPlanningContext result) {
         logger.info("Adding start artifact");
         result.addStep(new StartDeployedYakFileStep(this));
       }

       @Create
       public void deploy(DeploymentPlanningContext result) {
         logger.info("Adding deploy step");
         result.addStep(new DeployYakFileToServerStep(this));
       }

       @Modify
       public void upgrade(DeploymentPlanningContext result) {
         logger.info("Adding upgrade step");
         result.addStep(new UpgradeYakFileOnServerStep(this));
       }

       @Destroy
       public void destroy(DeploymentPlanningContext result) {
         logger.info("Adding undeploy step");
         result.addStep(new DeleteYakFileFromServerStep(this));
       }

       private static final Logger logger = LoggerFactory.getLogger(DeployedYakFile.class);
}

This class shows how to use the @Contributor to contribute steps to a deployment that includes a configured instance of the DeployedYakFile. Each annotated method annotated is invoked when the specified operation is present in the deployment for the YakFile.

In our sample deployment, yakfile1 already exists on the target container CI so a *MODIFY* delta will be present in the delta specification for this CI, causing the _stop_, _start_ and _upgrade_ methods to be invoked on the CI instance. Because yakfile2 is new, a *CREATE* delta will be present, causing the _start_, and _deploy_ method to be invoked on the CI instance.

## Step: StartYakServerStep

Steps are the actions that will be executed when the deployment plan is started.

    package com.xebialabs.deployit.plugin.test.yak.step;

    import com.xebialabs.deployit.plugin.api.flow.ExecutionContext;
    import com.xebialabs.deployit.plugin.api.flow.Step;
    import com.xebialabs.deployit.plugin.api.flow.StepExitCode;
    import com.xebialabs.deployit.plugin.test.yak.ci.YakServer;

    @SuppressWarnings("serial")
    public class StartYakServerStep implements Step {

       private YakServer server;

       public StartYakServerStep(YakServer server) {
         this.server = server;
       }

       @Override
       public String getDescription() {
         return "Starting " + server;
       }

       @Override
       public StepExitCode execute(ExecutionContext ctx) throws Exception {
         return StepExitCode.SUCCESS;
       }


       public YakServer getServer() {
         return server;
       }

       @Override
       public int getOrder() {
         return 90;
       }
    }
