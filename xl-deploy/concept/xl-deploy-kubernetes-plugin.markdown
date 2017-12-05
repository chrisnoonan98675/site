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

* Supports both property-based-deployables (form based) and artifact-based-deployable (yaml/resource file)
* Create namespace on Kubernetes container cluster
* Create k8s Pod on Kubernetes container cluster
* Create k8s Deployment
* Create config maps & secrets
* Configure liveness & readiness probes
* Create Persistent Volumes
* Generated plan shows separate step for each object created through a single resource file. On failure, they can be rollbacked.
* Supports platforms based on Kubernetes like Google Container Engine (GKE)
