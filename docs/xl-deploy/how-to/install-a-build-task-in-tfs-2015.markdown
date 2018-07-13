---
title: Install an XL Deploy build task in Visual Studio Team Services / Team Foundation Server 
categories:
- xl-deploy
subject:
- Team Foundation Server
tags:
- tfs
- vsts
- microsoft
- middleware
since:
- XL Deploy 5.1.0
---

Before you start to use the [XL Deploy extension for Visual Studio Team Services](/xl-deploy/concept/team-foundation-server-2015-plugin.html), you must install the XL Deploy build task in your on-premises Team Foundation Server (TFS) or in Microsoft Visual Studio Team Services (VSTS) server, which is also known as Visual Studio Online (VSO).

## Install the build task using the Visual Studio Marketplace

If you use Visual Studio Team Services (VSTS), it is recommended that you install the XL Deploy extension through the [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=xebialabs.tfs2015-xl-deploy-plugin).

## Install the build task using the TFS CLI

If you use on-premises TFS or VSTS/VSO, you can use the [Microsoft TFS Cross Platform Command Line Interface (CLI)](https://github.com/Microsoft/tfs-cli) to upload the XL Deploy build task. This application requires [NodeJS](http://nodejs.org/), [npm](https://www.npmjs.com/) (which is included with the NodeJS installer), and Java Runtime.

After installing NodeJS, install the TFS CLI with the following command:

    npm install -g tfx-cli

Next, you can log in using either:

* Personal access tokens for Visual Studio Online (VSO)
* Basic authentication for on-premises TFS

## Log in to VSO

To log in to Visual Studio Online (VSO):

1. Follow the instructions at [Using Personal Access Tokens to access Visual Studio Online](http://roadtoalm.com/2015/07/22/using-personal-access-tokens-to-access-visual-studio-online/) to create a personal access token.
1. Invoke the following command:

        tfs login

1. You will be prompted to enter the service URL. Provide a value such as `https://youraccount.visualstudio.com/DefaultCollection`.

## Log in to on-premises TFS

To log in to on-premises Team Foundation Server (TFS) 2015:

1. Follow the instructions at [Using `tfx` against Team Foundation Server (TFS) 2015 using Basic Authentication](https://github.com/Microsoft/tfs-cli/blob/master/docs/configureBasicAuth.md) to enable basic authentication in the TFS virtual application in IIS.
1. Invoke the following command:

        tfx login --auth-type basic

1. You will be prompted to enter the service URL and the user name and password of the account that has sufficient rights to upload build tasks. Note that you need to be in the top-level Agent Pool Administrators group to manipulate tasks.

## Upload the XL Deploy build task

Before uploading the XL Deploy build task, download the TFS 2015 XL Deploy ZIP file from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/public/xl-deploy/ci/tfs/tfs2015-xl-deploy-plugin/) and unzip it. Then, specify the directory (fully qualified or relative) that contains the files. Go to the `vsts-xld-buildtask` folder and execute the following command:

    tfx build tasks upload --task-path xld

## Development tooling

After you clone the `vsts-xld-buildtask` repository, you can script with any tool. It is recommended that you use [Visual Studio Code](https://code.visualstudio.com/Download), a free, cross-platform tool that includes an extension for writing PowerShell scripts. To install the PowerShell extension in Visual Studio Code:

1. Press CTRL+SHIFT+P.
1. Type `ext install`, then press ENTER.
1. Type `power`.
