---
title: Introduction to the XL Deploy Kubernetes plugin
categories:
xl-deploy
subject:
Kubernetes
tags:
plugin
kubernetes
since:
XL Deploy 7.2.0
---

The XL Deploy Kubernetes (K8s) plugin supports:

* Creating Namespaces
* Deploying Kubernetes Namespaces and Pods
* Deploying Deployment Configs
* Mounting volumes on Kubernetes Pods
* Deploying containers in the form of Pods, Deployments, and StatefulSets including all the configuration settings such as environment variables, networking, and volume settings, as well as liveness and readiness probes
* Deploying volume configuration through PersistentVolumes, PersistentVolumeClaims, and StorageClasses
* Deploying proxy objects such as Services and Ingresses
* Deploying configuration objects such as ConfigMaps and Secrets

For more information about the XL Deploy Kubernetes plugin requirements and the configuration items (CIs) that the plugin supports, refer to the [Kuberenetes Plugin Reference](/xl-deploy-xld-kubernetes-plugin/latest/kubernetesPluginManual.html).

## Using the XL Deploy Kubernetes plugin

The XL Deploy Kubernetes plugin can create and destroy Kubernetes resources on a Kubernetes host. To use the plugin:

1. Download the XL Deploy Kubernetes plugin ZIP from the [distribution site](https://dist.xebialabs.com/customer/xl-deploy/plugins/xld-kubernetes-plugin).
1. Unpack the plugin inside the `XL_DEPLOY_SERVER_HOME/plugins/` directory.
1. Restart XL Deploy.

With this plugin, types such as Kubernetes host and tasks specific for creating and removing Kubernetes resources are available to use in XL Deploy.

### Set up the `k8s.Master` with minikube

1. Under **Infrastructure**, click **New** and select `k8s.Master`.
1. Set up the `k8s.Master` authentication using one of these methods:

    * Client certificate authentication. Specify the following required properties:
        * `apiServerURL`: The URL for RESTful interface provided by the API Server
        * `skipTLS`: Do not verify using TLS/SSL
        * `caCert`: Certification authority certificate for server (for example, `.../.minikube/ca.crt`)
        * `tlsCert`: TLS certificate for master server (for example, `.../.minikube/apiserver.crt`)
        * `tlsPrivateKey`: TLS private key for master server (for example, `.../.minikube/apiserver.key`)

        ![image](images/key-auth-k8s-master.png)

    * Username/password authentication
        * `username`: Username used for authentication
        * `password`: Password used for authentication

        ![image](images/user-auth-k8s-master.png)    

    * Token authentication
        * `token`: Token used for authentication

        ![image](images/token-auth-k8s-master.png)    

### Set up the `k8s.Master` with GKE

1. Under **Infrastructure**, click **New** and select `k8s.Master`.
1. Set up the `k8s.Master` authentication using one of these methods:
    * Client Certificate Authentication
    * Username/password Authentication
    * Token Authentication
1. Follow the instructions described in [Set up the `k8s.Master` with minikube](#set-up-the-k8smaster-with-minikube). You only need to collect the authentication information from the Google Cluster and create the `k8s.Master`.

### Verify the Kubernetes cluster connectivity

You can verify the connection with the `k8s.Master` using the **Check Connection** control task. If the task succeeds, the connectivity is working.

![image](images/connectivity-k8s-master.png)

### Create a new `k8s.Namespace` before any resource can be deployed to it

* The `k8s.Namespace` is the container for all Kubernetes resources. You must deploy the Namespace through XL Deploy. The target Namespace must be deployed in different package than the one containing other Kubernetes resources such as Pod and Deployment.
* The `k8s.Namespace` CI only requires the Namespace name. If the Namespace name is not specified, XL Deploy uses the CI name as namespace name.
* The `k8s.Namespace` CI does not allow namespace name modification.

### Use an existing or default namespace provided by the Kubernetes cluster

The Kubernetes cluster provides pre-created namespaces such as the `default` namespace. XL Deploy allows you to use these existing namespaces:

1. Create the `k8s.Namespace` CI in `k8s.Master` under **Infrastructure**.
1. Provide the `default` Namespace name when default Namespace is required so that there is no need to have a provisioning package containing a **Namespace**.

    ![image](images/use-existing-namespace.png)

### Configure Kubernetes resources using YAML-based deployables

* The Kubernetes cluster allows you to configure Kubernetes resources in XL Deploy.
* You can configure YAML-based Kubernetes resources using the `k8s.ResourcesFile` CI. This CI requires the YAML file containing the definition of the Kubernetes resources that will be configured on the Kubernetes cluster.
* The deployment order of Kubernetes resources through multiple YAML based CI is:
    * You can have separate YAML files for Kubernetes resource.
    * Deployment order and YAML files should match the resources dependency.
* The `k8s.ResourcesFile` CI supports multiple API versions in the resource file. The plugin parses the file and creates a client based on the API version for each Kubernetes resource.
* The YAML-based Kubernetes resources support multi-document YAML file for multiple Kubernetes resources in one file. Each resource within the YAML file is separated with dashes (---) and has its own API version. The deployment order of the Kubernetes resources within the YAML based CI depends on:
    * The plugin parses the YAML file and automatically generates the deployment step order for each resource within the file, based on the type of the resource.
    * For the resources of the same type within the file, the step order is generated on the basis of occurrence in the file. The step for the resource that occurs first is generated first and so on.

Example of a multi-document YAML with multiple API versions:

    apiVersion: extensions/v1beta1
    kind: Deployment
    metadata:
      name: hello-openshift
    spec:
      replicas: 7
      template:
        metadata:
          labels:
            app: hello
            tier: backend
            track: stable
        spec:
          containers:
            name: hello
              image: "gcr.io/google-samples/hello-go-gke:1.0"
              ports:
                name: http
                  containerPort: 80


    ---

    apiVersion: v1
    kind: Secret
    metadata:
      name: mysecret
    type: Opaque
    data:
      username: YWRtaW4=
      password: MWYyZDFlMmU2N2Rm


### Use CI-based deployables to configure Kubernetes resources

XL Deploy also provides CIs for Kubernetes resource deployment (for example: `k8s.Pod` and `k8s.Deployment`). XL Deploy handles the asynchronous create/delete operation of resources. The CI based deployables support the latest API version, based on the latest Kubernetes version.
