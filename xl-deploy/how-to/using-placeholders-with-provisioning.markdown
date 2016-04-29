---
layout: beta-noindex
title: Using placeholders with provisioning
since:
- XL Deploy 5.5.0
---

{% comment %}
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- blueprint
since:
- XL Deploy 5.5.0
{% endcomment %}

You can use placeholders for configuration item (CI) properties that will be replaced with values during [provisioning](/xl-deploy/concept/provisioning-through-xl-deploy.html). This enables you to create provisioning packages that are environment-independent and reusable.

Placeholder values can be provided:

* By [dictionaries](/xl-deploy/how-to/create-a-dictionary.html)
* By the user who sets up a provisioning
* From provisioneds that are assigned to the target provisioned environment

## Placeholder format

The XL Deploy provisioning feature recognizes placeholders using the following formats:

{:.table .table-striped}
| Placeholder type | Format |
| ---------------- | ------ |
| Property placeholders | `{% raw %}{{ PLACEHOLDER_KEY }}{% endraw %}` |
| Contextual placeholders | `{% raw %}{{% PLACEHOLDER_KEY %}}{% endraw %}` |
| Literal placeholders | `{% raw %}{{' PLACEHOLDER_KEY '}}{% endraw %}` |

## Property placeholders

_Property_ placeholders are used to make properties of CIs configurable in a provisioning package. XL Deploy scans provisioning packages and searches the CIs for dictionary placeholders. The properties of following items are scanned:

* Bound Templates on Provisioning Package
* Bound Templates on Provisionable (CI type that extends `upm.Provisionable`)
* Provisioners on Provisionable (CI type that extends `upm.Provisionable`)
* Provisioning Package

Before you can provision a provisioning package to a target provisioning environment, values must be provided for *all* property placeholders. A value of a placeholder can be provided via `upm.Dicitionary` assigned to `upm.ProvisioningEnvironment` or via `Provisioning Properties` when executing the provisioning via UI or via `placeholders` parameter in [CLI](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html).

## Contextual placeholders

_Contextual_ placeholders serve the same purpose as property placeholders; the difference is that the values for contextual placeholders are not know before provisioning plan execution and are resolved during plan execution. Contextual placeholders might be resolved when executing a provider or in the finalization of the provisioning plan. Contextual placeholder is resolved from property with the same name of the provisioned it is linked to.

*Note*: The name of the contextual placeholder is case sensitive and must equal to property name of the provisioned.

For example, a provisioning step might require the public IP address of the instance that is created during provisioning; however, this value is only available after the instance is actually created and XL Deploy has fetched its public IP address.

If value of placeholder is not resolved, a resolution of a templates with such a placeholder will fail.

## Literal placeholders

_Literal_ placeholders allow you to insert placeholders in a dictionary that should only be resolved when a deployment package is deployed to the created environment. The resolution of these placeholders does not depend on provisioned, dictionary, or a manual user entry.

The value `{% raw %}{{'XYZ'}}{% endraw %}` will resolve to `{% raw %}{{XYZ}}{% endraw %}`.
