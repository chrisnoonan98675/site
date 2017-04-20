---
title: Using placeholders in provisioning
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- template
- cloud
- placeholder
since:
- XL Deploy 5.5.0
weight: 155
---

You can use placeholders for configuration item (CI) properties that will be replaced with values during [provisioning](/xl-deploy/concept/provisioning-through-xl-deploy.html). This enables you to create provisioning packages that are environment-independent and reusable.

Placeholder values can be provided:

* By [dictionaries](/xl-deploy/how-to/create-a-dictionary.html)
* By the user who sets up a provisioning
* From provisioneds that are assigned to the target provisioned environment

## Placeholder formats

The XL Deploy provisioning feature recognizes placeholders using the following formats:

{:.table .table-striped}
| Placeholder type | Format |
| ---------------- | ------ |
| Property placeholders | `{% raw %}{{ PLACEHOLDER_KEY }}{% endraw %}` |
| Contextual placeholders | `{% raw %}{{% PLACEHOLDER_KEY %}}{% endraw %}` |
| Literal placeholders | `{% raw %}{{' PLACEHOLDER_KEY '}}{% endraw %}` |

## Property placeholders

_Property_ placeholders allow you to configure the properties of CIs in a provisioning package. XL Deploy scans provisioning packages and searches the CIs for placeholders. The properties of following items are scanned:

* Bound templates on provisioning packages
* Bound templates on provisionables (items in provisioning packages)
* Provisioners on provisionables
* Provisioning packages

Before you can provision a package to a target provisioning environment, you must provide values for *all* property placeholders. You can provide values in several ways:

* In a dictionary that is assigned to the environment
* In the provisioning properties when you set up the provisioning in the GUI
* Via the `placeholders` parameter in the command-line interface (CLI)

## Contextual placeholders

_Contextual_ placeholders serve the same purpose as property placeholders; however, the values for contextual placeholders are not known before the provisioning plan is executed. For example, a provisioning step might require the public IP address of the instance that is created during provisioning; however, this value is only available after the instance is actually created and XL Deploy has fetched its public IP address.

XL Deploy resolves contextual placeholders when executing a [provider](/xl-deploy/how-to/create-a-provider.html) or when finalizing the provisioning plan.

Contextual properties are resolved from properties on the provisioneds they are linked to; therefore, the placeholder name must exactly match the provisioned property name (it is case-sensitive). For example, the contextual placeholders for the [public host name and IP address](/xl-deploy-xld-aws-plugin/latest/awsPluginManual.html) of an `aws.ec2.Instance` CI are `{% raw %}{{% publicHostname %}}{% endraw %}` and `{% raw %}{{% publicIp %}}{% endraw %}`.

If value of placeholder is not resolved, then resolution of templates that contain the placeholder will fail.

## Literal placeholders

_Literal_ placeholders allow you to insert placeholders in a dictionary that should only be resolved when a deployment package is deployed to the created environment. The resolution of these placeholders does not depend on provisioned, dictionary, or a manual user entry.

For example, the value `{% raw %}{{'XYZ'}}{% endraw %}` will resolve to `{% raw %}{{XYZ}}{% endraw %}`.
