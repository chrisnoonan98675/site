---
title: Introduction to the XL Deploy Kubernetes plugin
categories:
- xl-deploy
subject:
- Kubernetes
tags:
- plugin
- kubernetes
since:
- XL Deploy 7.2.0
---

The XL Deploy Kubernetes plugin adds the capability to create Pods, ReplicationController and Service on the Kubernetes container cluster manager.

For more information about the XL Deploy Kubernetes plugin requirements and the configuration items (CIs) that the plugin supports, refer to the [Kuberenetes Plugin Reference](/xl-deploy-xld-kubernetes-plugin/7.2.x/kubernetesPluginManual.html).

**Note:** The XL Deploy Kubernetes plugin is created using Python. To extend the plugin, knowledge of the Python programming language is required.

## Features

* Create namespace on Kubernetes container cluster
* Create k8s Pod on Kubernetes container cluster
* Create k8s Deployment on Kubernetes container cluster

## Using the XL Deploy Kubernetes plugin

The main purpose of the XL Deploy Kubernetes plugin is to create or destroy Kubernetes resources on a Kubernetes host. To use the plugin:

1. Download the XL Deploy Kubernetes plugin ZIP from the [distribution site](https://dist.xebialabs.com/customer/xl-deploy/plugins/xld-kubernetes-plugin).
1. Unpack the plugin inside the `XL_DEPLOY_SERVER_HOME/plugins/` directory.
1. Restart XL Deploy.

With this plugin, types such as Kubernetes host and tasks specific for creating or removing  Kubernetes resources are available to use in XL Deploy.

### Setup the `k8s.Master` with minikube

1. Under **Infrastructure**, click **New** and select `k8s.Master`.

![image](images/create_k8s_master.png)

1. Setup the `k8s.Master` authentication using one of these methods:

* Client Certificate Authentication. Specify the following required properties:

    * `apiServerURL`: The URL for RESTful interface provided by the API Server
    * `skipTLS`: Do not verify using TLS/SSL
    * `caCert`: Certification authority certificate for server (example `.../.minikube/ca.crt`)
    * `tlsCert`: TLS certificate for master server (example `.../.minikube/apiserver.crt`)
    * `tlsPrivateKey`: TLS private key for master server (example `.../.minikube/apiserver.key`)

![image](images/key-auth-k8s-master.png)

* Username/password Authentication

    * `username`: Username used for authentication
    * `password`: Password used for authentication

![image](images/user-auth-k8s-master.png)    

* Token authentication

    * `token`: Token used for authentication

![image](images/token-auth-k8s-master.png)    

### Setup the `k8s.Master` with GKE

1. Under **Infrastructure**, click **New** and select `k8s.Master`.
1. Setup the `k8s.Master` authentication using one of these methods: Client Certificate Authentication, Username/password Authentication, or Token Authentication.
1. Use the instructions described in the "Setup the `k8s.Master` with minikube" section above. You only need to collect the authentication information from the `google cluster` and create the `k8s.Master`.

### Verify the k8s cluster connectivity

The `k8s.Master` connectivity can be verified using the control task **Check Connection**. If the task execution is successful, the connectivity is working.

![image](images/connectivity-k8s-master.png)

### Create a new `k8s.Namespace` before any resource can be deployed to it

* The `k8s.Namespace` is the container for all the `k8s` resources. It is mandatory to have the namespace deployed through XL Deploy. The target namespace must be deployed in a separate package, different than the package containing other k8 resources such as pod, deployment.

* The `k8s.Namespace` CI requires only the namespace name. If the namespace name is not specified, XL Deploy uses the CI name as namespace name.

* The `k8s.Namespace` CI does not allow namespace name modification.

### Use an existing or default namespace provided by the `k8s` cluster

The `k8s` cluster also provides pre-created namespaces like the `default` namespace. XL Deploy allows you to use these existing namespaces as described here:

1. Create the `k8s.Namespace` in `k8s.Master` created under **Infrastruture**.
1. Provide the `default` namespace name when default namespace is required so that there is no need to have a provisioning package containing a **Namespace**.

![image](images/use-existing-namespace.png)

### Configure `k8s` resources using the YAML-based deployables

* The `k8s` cluster allows you to configure the `k8s` resources and XL Deploy.

* You can configure the YAML based `k8s` resources using the `k8s.ResourcesFile` CI. This CI requires the YAML file containing the definition of the `k8s` resources that will be configured on the `k8s` cluster.

* Deployment order of the `k8s` resources through multiple YAML based CI

    * You can have separate YAML files for `k8s` resource.
    * Deployment order and YAML files should be in accordance with the resources dependency.
    * Deployment order across YAML-based CI is managed by Create Order, Modify Order, and Destroy Order

### Use the CI-based deployables to configure k8s resources

XL Deploy also provides CIs for `k8s` resource deployment (example: `k8s.Pod`, `k8s.Deployment`). These CIs have some advantages over YAML-based CIs in terms of automatic deployment order: you do not need to specify the order and it also handles asynchronous create/delete operation of resources.    
