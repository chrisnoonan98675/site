---
title: Integrating TeamCity with XL Deploy
categories:
- xl-deploy
tags:
- teamcity
- deployment
- package
---

[TeamCity](https://www.jetbrains.com/teamcity/) is JetBrains' Continuous Integration server, and we've recently received a couple of requests from users to explain how they can connect TeamCity to XL Deploy to start moving toward continuous delivery without having to script all their deployments themselves.

You can fully control every aspect of XL Deploy from TeamCity via XL Deploy's [HTTP API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html). The actions most commonly requested are those you may be familiar with from our [integrations](http://xebialabs.com/products/plugins/) with Jenkins, Bamboo, Team Foundation Server and more:

* Publishing a deployment package to XL Deploy
* Deploying a package already published to an XL Deploy server to an environment
* Publishing and deploying a package via XL Deploy in a single step

## Configuring TeamCity to integrate with XL Deploy

Getting TeamCity ready to use the XL Deploy commands requires only a couple of simple steps:

1. Install the XL Deploy CLI on TeamCity build agent machine(s)
1. Add XL Deploy CLI configuration parameters

### Installing the XL Deploy CLI on TeamCity build agent machine(s)

TeamCity's build agents will interact with the XL Deploy server via the XL Deploy CLI, which needs to be installed on all the machines from which a build agent will attempt to execute build steps that connect to XL Deploy. See the [XL Deploy System Administration Manual](http://docs.xebialabs.com/releases/latest/deployit/systemadminmanual.html#installing-the-cli) for information on installing the CLI.

In order to help target build steps to suitable agents, it is useful to add a configuration parameter such as `deployit.cli.installed` to build agents with access to an XL Deploy CLI.

![Agent properties](/images/integrating_teamcity_agent_properties.png)

This property can then be used as an agent requirement for your XL Deploy-accessing build configurations.

![Agent requirements](/images/integrating_teamcity_agent_requirements.png)

### Adding XL Deploy CLI configuration parameters

Invoking the XL Deploy CLI from TeamCity follows the default CLI invocation pattern (see the [CLI Manual](http://docs.xebialabs.com/releases/latest/deployit/climanual.html) for details):

    %DEPLOYIT_CLI_EXEC% -host %DEPLOYIT_SERVER_HOST% -port %DEPLOYIT_SERVER_PORT% -username %DEPLOYIT_USERNAME% -password %DEPLOYIT_PASSWORD% -f cli-command-file.py -- more-args

This requires the following configuration parameters to be defined for your build configuration:

* `DEPLOYIT_CLI_EXEC`: For example, `/opt/deployit/cli/bin/cli.sh`
* `DEPLOYIT_SERVER_HOST`: For example, `deployit.acme.com`
* `DEPLOYIT_SERVER_PORT`: For example, 7890. The `-port %DEPLOYIT_SERVER_PORT%` segment of the invocation can be omitted if the default port 4516 is used. In that case, this parameter is not required
* `DEPLOYIT_USERNAME`: For example, `teamcity-user`
* `DEPLOYIT_PASSWORD`: The password for the `DEPLOYIT_USERNAME` user. Use a parameter of type [*Password*](http://confluence.jetbrains.com/display/TCD7/Typed+Parameters) to restrict access to the password

![CLI configuration parameters](/images/integrating_teamcity_cli_config_params.png)

If you are planning to access the same XL Deploy server in all your build configurations, you may wish to define variables for the XL Deploy server host and port [in the agent configuration](http://confluence.jetbrains.com/display/TCD3/System+Properties+of+a+Build+Configuration#SystemPropertiesofaBuildConfiguration-agent).

![Agent parameters](/images/integrating_teamcity_define_specific_agent_params.png)

## Example

Let's go through an example of building an application, creating a deployment package and publishing the package to XL Deploy.

![Project with steps](/images/integrating_teamcity_project_with_steps.png)

Here, we're combining the build, packaging and publishing in one build configuration with two steps. Since the publishing step has an agent requirement for an XL Deploy CLI, you may alternatively wish to split these actions into separate build configurations so that the build and packaging can be carried out by a larger set of build agents.

![Build steps](/images/integrating_teamcity_build_steps.png)

The first step invokes Maven to build the PetClinic EAR file and uses the [XL Deploy Maven Plugin](http://tech.xebialabs.com/deployit-maven-plugin/) to create a deployment package, or DAR file (short for Deployment ARchive). If you'd rather have the packaging done by TeamCity than by your build tool that's easy too, of course: a DAR is simply a ZIP with a metadata description, so [creating one](http://docs.xebialabs.com/releases/latest/deployit/packagingmanual.html) is no great shakes.

XL Deploy can also import "plain" ZIP files without metadata and handles simple EAR and WAR files out-of-the-box, too. You will generally want to use TeamCity's [predefined `%build.number%` parameter](http://confluence.jetbrains.com/display/TCD7/Predefined+Build+Parameters) or a similar mechanism to ensure that each package has a unique version.

![Publish to XL Deploy step configuration](/images/integrating_teamcity_publish_to_xl_deploy_step_config.png)

The second step is a command-line step that invokes XL Deploy's CLI using the standard pattern and, in this case, [this `publish-package.py` file](/sample-scripts/publish-package.py) with the path to the generated package as its argument. `publish-package.py` is very straightforward:

    import sys

    packagePath = sys.argv.pop(1)
    print "Importing '%s'" % (packagePath)
    deployit.importPackage(packagePath)

See the XL Deploy CLI Manual for [more information about the available commands](http://docs.xebialabs.com/releases/latest/deployit/climanual.html#deployit-objects-in-the-cli).

![Build log](/images/integrating_teamcity_build_log.png)

Running this build configuration results in the new deployment package being successfully published to XL Deploy.

![Imported deployment package](/images/integrating_teamcity_imported_deployment_package.png)
