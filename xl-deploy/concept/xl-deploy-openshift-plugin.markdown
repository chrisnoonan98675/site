---
title: Introduction to the XL Deploy OpenShift plugin
categories:
- xl-deploy
subject:
- OpenShift
tags:
- openshift
- plugin
since:
- XL Deploy 7.1.0
---

The XL Deploy OpenShift plugin allows you to deploy OpenShift and Kubernetes resource types directly from XL Deploy.

For information about the plugin requirements and the supported OpenShift version, refer to the [OpenShift Plugin Reference](/xld-openshift-plugin/latest/puppetPluginManual.html).

The supported basic resource types are:

* `Project` - a Kubernetes namespace with additional annotations, and the main entity used by users to deploy and manage resources
* `Pod` - one or more Docker containers running on a host machine
* `Service` - an internal load balancer that exposes a number of pods connected to it
* `Route` - a route exposes a service at a hostname and makes it available externally
* `ImageStream` - a number of Docker container images identified by a tag
* `BuildConfig` - the definition of a build process, which involves taking input parameters or source code and producing a runnable Docker image
* `DeploymentConfig` - the definition of a deployment strategy, which involves the creation of a Replication Controller, the triggers to create a new deployment, the strategy for transitioning between deployments, and the life cycle hooks

## Setup in OpenShift

To deploy on OpenShift, you must have two parameters:

* the OpenShift instance URL
* the authentication token

To retrieve the parameters:
1. Log in to the web interface of OpenShift, click on the **?** symbol on the top right of the page, and select **Command Line Tools**.
1. Click on the copy link next to *After downloading and installing it, you can start by logging in using this current session token:*.

This provides you with a command string in the copy buffer. Paste the string in a location to display it. The string should look like this: `oc login <server url> --token=<token>`.

Your instance URL will be (example): https://api.starter-us-west-2.openshift.com

And your token will be (example): `MF7tvOr8PR2F2WvkrJ11flPAiGW6u98hkPuORusyqTC`

## Initial deployment

To deploy on OpenShift, you must first create a *Project*.

To create a new project:
1. Hover over **Infrastructure**, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **OpenShift** > **Server**.
1. Enter the **Name**, **Server URL**, and the **OpenShift Token**. Un-check the **Verify Certificates** checkbox if the server is self-hosted and does not have a valid HTTPS certificate.
1. Hover over **Environments**, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **Environment**, and add it as a member of the previously created **Infrastructure**.
1.  Hover over **Applications**, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **Application** and call it *Projects*. Under the new application, create a **New** > **Provisioning Package** and call it *First Project*.

Inside *First Project* you can create a **New** > **OpenShift** > **ProjectSpec**.

You can use the same string for all parameters (Name, Project Name, Description, and Project Display Name), for this example you can use: `xld-first-project`.

To deploy your first project on OpenShift:

Hover over the *First Project*, click ![Explorer action menu](/images/menu_three_dots.png),select **Deploy**, and the select the previously created environment to deploy the project.

### Deploying resources

With a project already deployed, you can deploy resources to it.

Create a new application with the name *Resources* and create a New -> Deployment Package with the name *First Resources*. Under *First Resources*, create a **New** > **OpenShift** > **PodResource**.


Specify the name **hello-pod** for the new **PodResource** and do not enter information in the other text fields. Add the following code to the new `hello-pod.json` file, and load it as an artifact:

    {
      "kind": "Pod",
      "apiVersion": "v1",
      "metadata": {
        "name": "hello-openshift",
        "creationTimestamp": null,
        "labels": {
          "name": "hello-openshift"
        }
      },
      "spec": {
        "containers": [
          {
            "name": "hello-openshift",
            "image": "openshift/hello-openshift",
            "ports": [
              {
                "containerPort": 8080,
                "protocol": "TCP"
              }
            ],
            "resources": {},
            "volumeMounts": [
              {
                "name":"tmp",
                "mountPath":"/tmp"
              }
            ],
            "terminationMessagePath": "/dev/termination-log",
            "imagePullPolicy": "IfNotPresent",
            "capabilities": {},
            "securityContext": {
              "capabilities": {},
              "privileged": false
            }
          }
        ],
        "volumes": [
          {
            "name":"tmp",
            "emptyDir": {}
          }
        ],
        "restartPolicy": "Always",
        "dnsPolicy": "ClusterFirst",
        "serviceAccount": ""
      },
      "status": {}
    }

Load the new artifact into XL Deploy and save it.

Click on *First Resources* and deploy the pod. When the pod is running, you can create a service that maps to it.

Under the *First Resources* deployment package, create a **New** > **OpenShift** > **ServiceResource** and enter the name *hello-service*. Add the following code to the new `hello-service.json` file and load it as an artifact:

    {
        "metadata": {
            "name": "hello-openshift"
        },
        "kind": "Service",
        "spec": {
            "sessionAffinity": "None",
            "ports": [
                {
                    "targetPort": 8080,
                    "nodePort": 0,
                    "protocol": "TCP",
                    "port": 80
                }
            ],
            "type": "ClusterIP",
            "selector": {
                "name": "hello-openshift"
            }
        },
        "apiVersion": "v1"
    }

Load the artifact into XL Deploy and save it. You can re-deploy the *First Resources* deployment package to add the `hello-service` service to the OpenShift instance.

#### Create a route resource

The new pod has the port 8080 exposed and the service connected to it exposes port 80. To make the pod and service externally reachable, you must create a new route.

To create a route, click **New** > **OpenShift** > **RouteResource** and enter the name *hello-route*. Add the following code into the new `hello-route.json` file and load it as an artifact:

    {
        "metadata": {
            "name": "hello-route"
        },
        "kind": "Route",
        "spec": {
            "to": {
                "kind": "Service",
                "name": "hello-openshift"
            }
        },
        "apiVersion": "v1"
    }

Load the artifact into XL Deploy and save it. Re-deploy the *First Resources* deployment package to allow the new route to expose the service connected to a pod. If you go to the OpenShift Console, it should show the public URL. Click the URL to display the `Hello Openshift!` message.
