---
title: Using Go's XL Deploy's custom commands
categories:
- xl-deploy
tags:
- integration
- api
---

[Go](http://www.thoughtworks.com/products/go-continuous-delivery) is the continuous delivery pipeline orchestrator made by our friends at ThoughtWorks Studios. Go helps you define and visualize your pipelines, but is not intended to provide out-of-the-box automation for all the "low-level" build, integration, and deployment steps that are required.

Since we regularly help our customers introduce continuous delivery, we're well aware how many deployment activities the typical pipeline requires. So in order to eliminate the custom scripting that normally needs to be put in place to automate these steps, we've added a couple of XL Deploy commands to Go's command repository to make your deployments a simple "no scripting" affair.

The XL Deploy Go commands match those you're hopefully already familiar with from our integrations with Jenkins, Bamboo, Team Foundation Server and more:

* Publishing a deployment package to XL Deploy (Go command `deployit publish DAR`)
* Deploying a package already published to a XL Deploy server to an environment (Go command `deployit deploy`)
* Publishing and deploying a package via XL Deploy in a single step (Go command `deployit publish and deploy DAR`)

## Configuring Go to use the XL Deploy commands

Getting Go ready to use the XL Deploy commands requires only a couple of simple steps:

1. Install the XL Deploy CLI on the Go agent machine(s)
1. Configure Go pipeline parameters

## Installing the XL Deploy CLI on Go agent machine(s)

Go agents will interact with the XL Deploy server via the XL Deploy CLI, which needs to be installed on all the machines from which a Go agent will attempt to execute a XL Deploy command. See the [XL Deploy System Administration Manual](http://docs.xebialabs.com/releases/latest/deployit/systemadminmanual.html#installing-the-cli) for information on installing the CLI.

In addition, you will need to extract the XL Deploy Go CLI extensions pack into the home directory of the CLI installation. This simply adds a couple of extensions into `CLI_HOME` and `CLI_HOME/ext`. Contact XL Deploy support for download instructions.

## Configuring Go pipeline parameters

The XL Deploy Go commands all follow the default CLI invocation pattern (see the [CLI Manual](http://docs.xebialabs.com/releases/latest/deployit/climanual.html#starting-the-deployit-cli) for details):

    #{DEPLOYIT_CLI_HOME}/bin/cli -host #{DEPLOYIT_SERVER_HOST} -port #{DEPLOYIT_SERVER_PORT} -username #{DEPLOYIT_USERNAME} -password #{DEPLOYIT_PASSWORD} -f cli-extension-file.py -- more-args

This assumes that the following [pipeline parameters](http://www.thoughtworks.com/products/docs/go/current/help/admin_use_parameters_in_configuration.html) have been defined in Go:

* `DEPLOYIT_CLI_HOME`: For example, `/opt/deployit/cli`. If `CLI_HOME/bin` is on the agent's PATH, this parameter is not required and the invocation can be changed from `#{DEPLOYIT_CLI_HOME}/bin/cli -host ...` to simply `cli -host ...`
* `DEPLOYIT_SERVER_HOST`: For example, `deployit.acme.com`
* `DEPLOYIT_SERVER_PORT`: For example, 7890. The `-port #{DEPLOYIT_SERVER_PORT}` segment of the invocation can be omitted if the default port 4516 is used. In that case, this parameter is not required
* `DEPLOYIT_USERNAME`: For example, `go-user`
* `DEPLOYIT_PASSWORD`: The password for the `DEPLOYIT_USERNAME` user. You may wish to consider using a [secure environment variable](http://www.thoughtworks.com/products/docs/go/current/help/dev_use_current_revision_in_build.html) in Go instead. In that case, the segment of the invocation should be `-password ${DEPLOYIT_PASSWORD}`

## Examples

### `deployit publish DAR`

    #{DEPLOYIT_CLI_HOME}/bin/cli -host #{DEPLOYIT_SERVER_HOST} -port #{DEPLOYIT_SERVER_PORT} -username #{DEPLOYIT_USERNAME} -password #{DEPLOYIT_PASSWORD} -f publish-dar.py -- /usr/local/go/materials/MyApp-1.0.dar

### `deployit deploy`

    cli -host #{DEPLOYIT_SERVER_HOST} -port #{DEPLOYIT_SERVER_PORT} -username #{DEPLOYIT_USERNAME} -password #{DEPLOYIT_PASSWORD} -f deploy-app.py -- Applications/Frontend/MyApp/1.0 Environments/Dev/Dev-MyApp

Note: Here, `cli` is on the agent's `PATH`!

### `deployit publish and deploy DAR`

    #{DEPLOYIT_CLI_HOME}/bin/cli -host #{DEPLOYIT_SERVER_HOST} -username #{DEPLOYIT_USERNAME} -password ${DEPLOYIT_PASSWORD} -f publish-dar-and-deploy.py -- /usr/local/go/materials/MyApp-1.2.dar Environments/Test/Test-MyApp

Note: Here, the server is running on default port 4516, and we're using a secure environment variable rather than a parameter for `DEPLOYIT_PASSWORD`.
