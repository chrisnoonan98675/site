---
layout: pre-rules
title: XL Deploy plugin tutorial
subject:
- Customization
categories:
- xl-deploy
tags:
- plugin
- java
- synthetic
- tutorial
---

This tutorial will explain the basic case of deploying a file to a target Container and doing something on the target Container with that file. 

## Define the new type ##

Open the `synthetic.xml` file that is located under the XL Deploy server `ext` folder and put the following new type definition in it:

	...
	<type type="cp.Server" extends="generic.Container">
	    <property name="home" default="/opt/cp/container"/>
	    <property name="targetDirectory" default="${container.home}/apps" hidden="true"/>
	</type>

	<type type="cp.DeployedApp" extends="generic.ExecutedScriptWithDerivedArtifact" deployable-type="cp.App"
	      container-type="cp.Server">
	    <generate-deployable type="cp.App" extends="generic.Archive"/>
	    <property name="createScript" default="cp/install-app.sh" hidden="true"/>
	    <property name="modifyScript" default="cp/reinstall-app.sh" hidden="true"/>
	    <property name="destroyScript" default="cp/uninstall-app.sh" hidden="true"/>
	</type>
	...

Start (or restart) the XL Deploy server, and open the UI. 

1. Go to the repository view and create a new `overthere.LocalHost` under Infrastructure.
2. Right click on the just created `overthere.LocalHost` and create a new `cp.Server` under it.
3. Notice that you can set the `home` property as defined in the `synthetic.xml`.
4. Right click on Applications and create a new `Application`
5. Right click on the just created application and create a new `Deployment Package` (1.0) under it.
6. Add under 1.0 a new deployable `cp.App`
7. Upload an archive (zip, jar, ...) to it and click `Save`.

## Create the create, modify and destroy script ##

1. Go into the `ext` folder and create a directory `cp` under it.
2. Put the following scripts under the `cp` folder:
	* install-app.sh    
	`echo Installing archive ${deployed.deployable.file} in ${deployed.container.home}`
	* reinstall-app.sh    
	`echo reinstalling archive ${deployed.deployable.file} in ${deployed.container.home}`
	* uninstall-app.sh    
	`echo uninstalling archive ${deployed.deployable.file} in ${deployed.container.home}`
3. When modifying scripts, there's no need to restart the XL Deploy server.

## Run the first deployment ##

1. Go to the XL Deploy UI, and open the Repository view.
2. Right click on `Environments` and create a new environment.
3. Add the `cp.Server` created in one of the previous steps to the Environment.
4. Open the deployment view in the UI.
5. Deploy version `1.0` to the new created environment and notice the echo messages.
6. If you want you can also try the rollback, modify and uninstall functionality.
