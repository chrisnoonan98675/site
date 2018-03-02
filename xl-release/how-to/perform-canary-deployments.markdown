---
title: Perform Canary Release deployments
categories:
- xl-release
- xl-deploy
subject:
- Calling XL Deploy from XL Release
tags:
- canary
- deployment pattern
---

This guide explains how to perform the [Canary Release deployment pattern](https://martinfowler.com/bliki/CanaryRelease.html) using XL Release and XL Deploy.

The Canary Release deployment pattern is a pattern where a small subset oy ruour production environment is used to test out new features. The environment is split into a Canary part and a Main part. the canary part is typically small and allows for fast deployments. A load balancer is used to direct traffic to all parts of the application, deciding which users go to the new version in the Canaray section and which users remain on the stable Main section.

First the new version is deployed to the Canary part. If successful, the new verion is rolled out over the rest of the environment. 

If the changes are not successful, only the smaller environment needs to be rolled back.

Note the this pattern, like most deployment patterns, requires that at least two versions of the software are active in the same environment at the same time. This adds requirements to the architecture of the software, for example the two versions need to be able to connect to the same database, so database upgrades need to be managed more carefully. This is outside the scope of this article. <!-- ADD SOME LINK -->

This guide has the following parts:

1. Defining the pattern in XL Release.
2. Defining the environments in XL Deploy.
3. Combining the defined components and setting up the process.

## Defining the Canary Release deployment pattern in XL Release

With XL Release you can define your release process in simple steps and create a process prototype that is immediately usable. With advance knowledge of the process, you can refine the defined release flow in XL Release and integrate your toolchain.

The first step is to create a sketch of the process. This gives you a high-level overview of the Canary deployment pattern.

![Canary release sketch](../images/canary/canary-sketch.png)

This XL Release template shows the basic steps. You can create it by adding manual tasks as placeholders. XL Deploy will be used this example, so XL Deploy deployment tasks are added.

This high level sketch is the starting point of your Canary Release process. The primary purpose is to be detailed description of the Canary Release deployment pattern, but it can also be used as a workflow model.

_Download the Canary Release deployment pattern in [XLR format](../images/canary/Canary-Release.xlr) (for XL Release 7.5 and higher) or as [Releasefile](../images/canary/Releasefile.groovy) to import it in your XL Release server._

There are two phases:

#### 1. **Canary phase**

The new version is deployed to the Canary section of the environment and tests are performed. At the end of the test, confirmation is given to roll out the change to the Main environment.

#### 2. **Main phase**

The new version is rolled out into the Main environment and a notification is sent that the new version is available.

### Variables in XL Release

The following variables are defined in the XL Release Canary Release template.

* Release variables `${app}` and `${version}`. The version of the application to deploy is filled in when the release process starts and is passed to XL Deploy to indicate which version to deploy. The values of these variables are filled in by the release manager when the release starts. This is why the option `Show en Create release form` should be enabled.

The variables are marked with 'Show on Create Release form'. 

![application variable](../images/canary/application-variable.png)

This means that they need to be filled in when the release starts.

The variables are used in the XL Deploy tasks to tell XL Deploy which version to deploy.

![XL Deploy task to Canary environment](../images/canary/deploy-to-canary.png)

Now let's define the environments in XL Deploy.


## XL Deploy setup

In XL Deploy, you will have two environments: 'Canary' and 'Main'. In this example, they are located in the 'Canary Deployment' folder.

![Canary environments in XL Deploy](../images/canary/xld-environments.png)

The Canary environment contains a small subset of the available servers. The Main environment contains the servers that can handle the full production load. You will need to have at least two nodes that can operate simultaneously. If resources are limited, the node of the Canary environment can be lighter. Make sure to configure your loadbalancer to route traffice accordingly.

In XL Release, [add an XL Deploy Server item](/xl-release/how-to/xld-plugin.html#configure-xl-deploy-server-shared-configuration) under Shared Configuration and point it to your XL Deploy installation.

## Configure the XL Deploy tasks in XL Release

You can now configure the environments in the XL Deploy tasks of the release template in XL Release.

The environments will appear as type-ahead suggestions in the task configuration screen:

![Canary environments type ahead](../images/canary/type-ahead.png)



## Running the release

You can now to run the release. Create the release and fill in the values for the application name and version. These should match the application name and version as defined in XL Deploy.

![Create new release](../images/canary/create-release.png)

The correct deployments will be triggered in XL Deploy.

![Canary main](../images/canary/main-deployment.png)




