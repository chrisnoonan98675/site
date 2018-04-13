---
title: Using the XL Release OpenShift plugin
categories:
- xl-release
subject:
tags:
- plugin
- openshift
- task
since:
- XL Release 7.5.0
---

The XL Release [OpenShift](https://openshift.io/) plugin allows you to work with resources on an OpenShift host from the XL Release UI. The following task types are included:

* **OpenShift: Create resource**
* **OpenShift: Remove resource**
* **OpenShift: Start Build**
* **OpenShift: Image Tag**
* **OpenShift: Start Deployment**
* **OpenShift: Check Service**

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), the Openshift tasks have a blue border.

## Features

* Create a new OpenShift resource
* Remove an OpenShift resource
* Wait for a created resource to be in a running state
* Start an OpenShift build
* Tag an OpenShift image stream
* Start an OpenShift deployment
* Check for an OpenShift service accessibility

## Requirements

The OpenShift plugin requires the following:

1. The XL Release [Remoting plugin](/xl-release/how-to/remoting-plugin.html) to be installed.
2. OpenShift command line client: `oc` to be installed on a Unix host machine.


## Set up an OpenShift client

Setup a connection to a Unix server with the installed OpenShift client:

1. In XL Release, go to **Settings** > **Configuration**, and click **Add Unix Host**.
2. In the **Address** field, enter the IP address or host name of the remote machine with the installed OpenShift client.
3. In the **Port** field, enter the SSH port of the remote machine.
4. In the **Username** and **Password** fields, specify the user name and password of the SSH user that XL Release uses when connecting to the remote machine.
5. In the **Sudo Username** field, enter the user name of the `sudo` user on the remote machine (for example: `root`).

    ![Create Unix host](../images/xlr-openshift-plugin/openshift-unix-host.png)

Setup an OpenShift client:

1. In XL Release, go to **Settings** > **Configuration**, and click **Add Openshift Client (OC) Configuration**.
2. In the **Client Host** field, select Unix host created.
3. In the **OC Path** field, enter the path for OC.
4. In the **Openshift Server** field, enter URL to connect with the OpenShift Server.
5. In the **Username** field, enter username to connect with the OpenShift Server.
6. In the **Password** field, enter password to connect with the OpenShift Server.

    ![Create OC Client](../images/xlr-openshift-plugin/create-openshift-host.png)


## Create resource task type

The **OpenShift: Create Resource** task type creates a resource in the default project in OpenShift. This requires you to specify a configuration in JSON format. You can specify the configuration:

* By providing a JSON in the task
* By providing a URL to a JSON file

You can enter the JSON configuration as plain text in the **Configuration file** field:

![OpenShift Create Resource task with configuration file](../images/xlr-openshift-plugin/openshift-create-resource-config-file.png)

You can also provide a URL for the configuration file in the **Url** field. If the URL is secure, you must provide credentials in the **Username** and **Password** fields.

![OpenShift Create Resource task with URL to configuration file](../images/xlr-openshift-plugin/openshift-create-resource-url.png)

* If the output of this command is needed in JSON format, add -o json to the cmdParams.

* In the **Pattern** field, you can specify a regular expression to match the output of the specified OC command. When the output matches the regular expression, the task will succeed and the release moves to the next task.

![OpenShift Wait for Resource task](../images/xlr-openshift-plugin/openshift-wait-resource.png)

## Remove resource task type

The **OpenShift: Remove Resource** task type destroys a resource in the default project in OpenShift. This requires you to specify a configuration in JSON format. As in the [Create Resource task type](#create-resource-task-type), you can enter the JSON in the task or provide a URL to a configuration file.


## Start build task type

The **OpenShift: Start Build** task type starts a build in default project in OpenShift. You must specify the build name that already exists on OpenShift.

![OpenShift Start Build](../images/xlr-openshift-plugin/openshift-start-build.png)

## Image tag task type

The **OpenShift: Image Tag** task type tags an image stream in OpenShift. You must specify the source image and target image name that already exists on OpenShift. This also provides an optional source and a target project. If not specified, it uses the default project in OpenShift.

![OpenShift Image Tag](../images/xlr-openshift-plugin/openshift-image-tag.png)

## Start deployment task type

The **OpenShift: Start Deployment** task type starts a deployment in the default project in OpenShift. You must specify the deployment name that already exists on OpenShift.

![OpenShift Start Deployment](../images/xlr-openshift-plugin/openshift-start-deployment.png)

## Check service task type

The **OpenShift: Check Service** task type checks accessibility of a service in the default project in OpenShift. You must specify the service name that already exists on OpenShift.

![OpenShift Check Service](../images/xlr-openshift-plugin/openshift-check-service.png)
