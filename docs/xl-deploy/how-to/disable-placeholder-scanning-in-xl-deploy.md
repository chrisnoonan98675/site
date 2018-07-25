---
title: Disable placeholder scanning
subject:
Dictionaries
categories:
xl-deploy
tags:
package
dictionary
placeholder
artifacts
---

When importing a package, XL Deploy by default scans the artifacts it contains for placeholders that need to be resolved during a deployment. If you want to turn off placeholder scanning, there are various ways to do this.

## Disabling placeholder scanning for one file extension on a particular artifact type

XL Deploy looks for files to scan in artifact configuration items (CIs) based on the file extension. It is possible to exclude certain extensions from this process. To do this, edit the `deployit-defaults.properties` file and set the `excludeFileNamesRegex` property on the artifact CI type you want to exclude. For example:

	file.Archive.excludeFileNamesRegex=.+\.js

Restart the XL Deploy server for the change to take effect.

## Disabling placeholder scanning for one file extension on all artifacts

XL Deploy looks for files to scan in artifact CIs based on the file extension. It is possible to exclude certain extensions from this process. To do this, edit the `deployit-defaults.properties` file and set the `excludeFileNamesRegex` property on the artifact CI type you want to exclude. For example:

	udm.BaseDeployableArchiveArtifact.excludeFileNamesRegex=.+\.js

Restart the XL Deploy server for the change to take effect.

## Disabling placeholder scanning for one CI instance

Edit the deployment package manifest and change the `scanPlaceholders` property of the particular artifact:

	<file.File name="sample" file="sample.txt">
		<scanPlaceholders>false</scanPlaceholders>
	</file.File>

## Disabling placeholder scanning for one CI type

Edit the `deployit-defaults.properties` file and set the `scanPlaceholders` property for the CI type you want to exclude. For example:

	file.Archive.scanPlaceholders=false

Restart the XL Deploy server for the change to take effect.

## Disabling placeholder scanning completely

Edit the `deployit-defaults.properties` file and set the following property:

	udm.BaseDeployableArtifact.scanPlaceholders=false

Restart the XL Deploy server for the change to take effect.
