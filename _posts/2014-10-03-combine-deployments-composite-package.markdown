---
title: Combine parallel and sequential deployments in a composite package
categories:
- xl-deploy
tags:
- deployment
- package
---

When using XL Deploy to deploy multiple deployment packages (DAR files) in a single deployment action, you might want to deploy some groups in parallel, moving on to the next group after all packages are successfully deployed. For example, assume we have applications in two groups:

* Group1
  * AppA
  * AppB
* Group2
  * AppY
  * AppZ

We want to deploy Group1 members AppA and AppB in parallel. If the deployment of both succeeds, we want to move on to Group2 and deploy AppY and AppZ. We can do this through a combination of [composite packages](http://docs.xebialabs.com/releases/latest/xl-deploy/referencemanual.html#composite-packages) and a [command-line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/referencemanual.html#configuration-items-cis) (CLI) script.

First, we need to create composite packages for Group1 and Group2, containing their respective applications. Then, we'll create a third composite package called SuperGroup that contains Group1 and Group2.

The SuperGroup cannot be deployed with the XL Deploy user interface, but can be deployed using [this CLI script](/sample-scripts/compositeGroupDeployer.py). Use it as follows:

    cli.sh -host <XLDeployHost> -username <username> -password <password> -f $ScriptPath/compositeGroupDeployer.py -- -p <package> -e <environment>

For example:

    cli.sh -username admin -password deploy -source /Users/tom/Documents/CompositDeployScript/compositeGroupDeployer.py -- -p "Composite/SuperGroup" -e "MyEnvironment"

This will deploy all elements of Group1 and Group2, as they are members of SuperGroup.
