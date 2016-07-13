---
title: Export items from or import items into the repository
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- ci
- repository
- script
- system administration
since:
- XL Deploy 5.0.0
weight: 117
---

The `repository` object in the XL Deploy [command-line interface (CLI)](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html) allows you to export the XL Deploy repository tree to a ZIP file that can be imported into the same XL Deploy server or into another XL Deploy server. The ZIP file contains all configuration item (CI) properties, including artifact files.

For example, you can use this feature to create CIs in a sandbox or test instance of XL Deploy, and then import them into a production XL Deploy instance.

Administrative permissions are required to perform repository export or import. You can monitor the server log files to see the progress.

## What is exported

The `repository` object exports the current versions of the CIs that are stored in the XL Deploy repository; for example, applications, environments, infrastructure CIs such as hosts and servers, and configuration CIs such as triggers and deployment pipelines. This includes artifact files in deployment packages.

It does not export previous versions of CIs. It also does not export XL Deploy items that are not CIs, such as user roles, global permissions, CI-level permissions, deployment tasks, and so on.

## Export CIs

To export all CIs in the repository, use `'/'` or `None` as the argument:

    repository.exportCis('/')

To export the CIs under an internal root (`Applications`, `Environments`, `Infrastructure`, or `Configuration`), specify the root:

    repository.exportCis('Applications')

For example, this command will export all applications to a ZIP file called `<XLDEPLOY_SERVER_HOME>/export/Applications-<date>.zip`:

	deployit> fileName = repository.exportCisAndWait('Applications')

**Important:** During the export process, do not change the repository items that are being exported. Doing so may interrupt the export or corrupt the output.

Exporting large number of configuration items can take a long time, so it is recommended to do it when no deployments are running.

## Control the export process

The export process is a task, so you can control it using the same objects you use for a deployment. For example, if you want to have more control over the export task:

    taskId = repository.exportCis('Applications')
    task2.start(taskId)
    print(str(task2.get(taskId).state))
    deployit.waitForTask(taskId)

## Import previously exported content

To import previously exported content, use either:

    repository.importCisAndWait(fileName)

Or:

    taskId = repository.importCis(fileName)
    task2.start(taskId)
    print(str(task2.get(taskId).state))
    deployit.waitForTask(taskId)

The content of the exported location will be removed before import. For example, if you export a directory called `Applications/myApps`, then this directory will be removed before the archive is imported. If you exported the whole repository by passing `/` as a root ID, then all CIs except the internal roots (`Applications`, `Environments`, `Infrastructure`, and `Configuration`) will be removed from the repository.
