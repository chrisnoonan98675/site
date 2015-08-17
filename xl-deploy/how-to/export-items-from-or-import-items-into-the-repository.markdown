---
title: Export items from or import items into the repository
categories:
- xl-deploy
subject:
- Repository
tags:
- cli
- ci
- repository
- script
- system administration
since:
- 5.0.0
---

The `repository` object in the XL Deploy command-line interface (CLI) allows you to export the XL Deploy repository tree to a ZIP file so that it can be imported into the same XL Deploy server or into another XL Deploy server. The resulting ZIP file contains all configuration item (CI) properties, including artifact files. You can use this feature for backup or migration purposes.

Administrative permissions are required to perform repository export or import. You can monitor the server log files to see the progress.

## Export CIs

To export all CIs in the repository, use `'/'` or `None` as the argument:

    repository.exportCis('/')

To export the CIs under an internal root (`Applications`, `Environments`, `Infrastructure`, or `Configuration`), specify the root:

    repository.exportCis('Applications')

For example, this command will export all applications to a ZIP file called `XLDEPLOY_SERVER_HOME/export/Applications-<date>.zip`:

	deployit> fileName = repository.exportCisAndWait('Applications')

Note that:

* Only the current versions of configuration items are exported. Previous versions are not.
* The content of the exported location will be removed before import. For example, if you exported a folder `Applications/myApps`, then this folder will be removed before import of the archive. If you exported the whole repository by passing `/` as a root ID, then all the CIs except internal roots (`Applications`, `Environments`, `Infrastructure`, and `Configuration`) will be removed from the repository.
* During the export process, do not change the repository items that are being exported. Doing so may interrupt the export or corrupt the output.
* Exporting large number of configuration items can take a long time, so it is recommended to do it when no deployments are running.

## Control the export process

The export process is a task, so you can control it using the same objects you use for a deployment. For example, if you want to have more control over the export task:

	deployit> taskId = repository.exportCis('Applications')
	deployit> task2.start(taskId)
	deployit> print(str(task2.get(taskId).state))
	deployit> deployit.waitForTask(taskId)

## Import previously exported content

To import previously exported content, use either:

	deployit> repository.importCisAndWait(fileName)

Or:

	deployit> taskId = repository.importCis(fileName)
	deployit> task2.start(taskId)
	deployit> print(str(task2.get(taskId).state))
	deployit> deployit.waitForTask(taskId)
