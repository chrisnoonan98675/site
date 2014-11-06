---
title: Configuring your own XL Deploy file.File extension
categories:
- xl-deploy
tags:
- extension
---

An XL Deploy user recently asked: "How can I extend [`file.File`](http://docs.xebialabs.com/releases/latest/deployit/filePluginManual.html#filefile)? I would like to create `my.ServiceFile` and inherit the functionality of [`file.DeployedFile`](http://docs.xebialabs.com/releases/latest/deployit/filePluginManual.html#filedeployedfile). It should be extended with the following functionality (marked as new):

* new = Stop service (`servicename` is a property of `my.ServiceFile`) 
* existing = [Delete file](http://docs.xebialabs.com/releases/latest/deployit/filePluginManual.html#deployed-actions-table)
* existing = [Copy file](http://docs.xebialabs.com/releases/latest/deployit/filePluginManual.html#deployed-actions-table)
* new = Start service (`servicename` is a property of `my.ServiceFile`)

This is the user's solution, using the XL Deploy [generic plugin](http://docs.xebialabs.com/releases/latest/deployit/genericPluginManual.html#executed-script):

    <type type="my.CopiedServiceFile" deployable-type="my.ServiceFile" container-type="overthere.Host" extends="generic.ExecutedScriptWithDerivedArtifact" description="Custom implementation of file.DeployedFile (Service executable)">
        <generate-deployable type="my.ServiceFile" extends="file.File" description="A file that is linked to a Windows Service" />
        <property name="createScript" default="my/file/Start-DependantService.bat" hidden="true" /> 
        <property name="createVerb" default="Install" hidden="true" /> 
        <property name="createOrder" default="75" kind="integer" hidden="true" /> 
        <property name="destroyScript" default="my/file/Stop-DependantService.bat" hidden="true" /> 
        <property name="destroyVerb" default="Remove" hidden="true" /> 
        <property name="destroyOrder" default="25" kind="integer" hidden="true" />
        <property name="dependantServiceName" kind="string" required="false" default="" label="Service to stop/start on deployment 10:40" description="If set, the corresponding service is stopped/started on every deployment of the file" />
    </type>

Note that, for the file copy to be executed, the `Start-DependantService.bat` and `Stop-DependantService.bat` files need to reference `${deployed.file}`. This will cause the file to be uploaded, and the variable will refer to the temporary location of the file on the target system.

In the script, we will need to do whatever is required with the file. For example, to move it to the desired location:

    # targetPath inherited from file.DeployedFile
    copy ${deployed.file} ${deployed.targetPath}
