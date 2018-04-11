---
title: Introduction to the XL Deploy Terraform plugin
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- terraform
- cloud
- plugin
since:
- XL Deploy 8.0.0
---

The XL Deploy Terraform plugin supports:

* Applying Terraform resources
* Destroying Terraform resources

{% comment %} Insert link to Terraform CI reference doc {% endcomment %}

## Create the Terraform client

To create a Terraform client in XL Deploy:

1. Under **Infrastructure** create an `overthere.SshHost` or `overthere.LocalHost` CI, depending on the location of the Terraform client.
2. Under the host, create a `terraform.TerraformClient` CI. Specify the following properties:
    * `path`: The path where the Terraform client executable is available.
    * `pluginDirectory`: The path where Terraform's pre-installed plugins are available. This is an optional property. If not provided, the required plugins will be  downloaded by `terraform init`.
    * `workingDirectory`: The path where Terraform maintains its state for incremental deployments. This is an optional property.

## Configure Terraform resources using artifact-based deployables

To configure Terraform resources:

1. Under **Applications**, create an application (`udm.Application`) and a deployment package (`udm.DeploymentPackage`).
2. Under the deployment package, create a `terraform.Module` CI. Specify the following properties:
    * `file`: The ZIP file that contains Terraform template files. Terraform does not support a nested directory structure for these files, so all files must be placed at the root of the ZIP file.
    * `targets`: The list of resource names that user wants to create or modify. It will skip other resources defined in Terraform template files.
    * `inputVariables`: The map of name and value of input variables whose values will be resolved in Terraform template files.
    * `outputVariables`: The map of name and value of output variables. This will be  populated with the outputs defined in Terraform template files after the deployment.
