---
layout: beta-noindex
title: Provisioning and CI templates
since:
- XL Deploy 5.5.0
---

A [*provisioning package*](/xl-deploy/how-to/create-a-provisioning-package.html) can contain *templates*, which are used to create new configuration items (CIs) in the XL Deploy repository after provisioning is performed. Templates are needed to register the changes in your infrastructure that result from provisioning in XL Deploy.

## Creating template CIs

Templates are CIs that you create in the Repository or using the [command-line interface (CLI)](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html#step-3-create-a-provision). A template's type is the same as the type of CI it represents, with a `template.` prefix. For example, the template type that will create an `overthere.SshHost` CI is called `template.overthere.SshHost`.

Template properties are inherited from the original CI type, but simple property kinds are mapped to the `STRING` kind. This allows you to specify [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) in template properties. XL Deploy resolves the placeholders when it instantiates a CI based on the template.

## Resolving templates

For XL Deploy to resolve a template and create a CI based on it, you must assign the template as a *bound template* on a provisioning package (`upm.ProvisioningPackage`) *or* on a provisionable (such as `aws.ec2.InstanceSpec`). An important difference is that you cannot use [*contextual placeholders*](/xl-deploy/how-to/use-provisioning-outputs.html) in the properties of a template that is assigned to a provisioning package.

If you specify a template as a *host template* on a provider (`upm.Provider`) CI, XL Deploy will resolve the template but will not create a CI based on it. You can use contextual placeholders for this.

**Note:** Like other CIs, you can create a hierarchy of templates that have a parent-child relationship. In this case, only specify the root (parent) of the hierarchy as a bound template. XL Deploy will automatically create CIs based on all of the templates in the hierarchy.

## Generated CI names

The names of CIs that are generated based on templates follow this pattern:

    /Infrastructure/$DirectoryPath$/$ProvisioningId$-$rootTemplateName$-$ordinal$/$templateName$

Where:

* The root (in this example, `/Infrastructure`) is based on the CI type. It can be any repository root name.
* `$DirectoryPath$` is the value specified in `upm.ProvisinedBlueprint.directoryPath`.
* `$ProvisioningId$` is the value specified in `upm.ProvisinedBlueprint.provisiongId`.
* `$rootTemplateName$` is the name of the root template, if the template has a root template or is a root template.
* `$ordinal$` is the value specified in `provisioned.ordinal`. This is based on the [cardinality property](/xl-deploy/how-to/create-a-provisioning-package.html#add-a-provisionable-to-a-package). This is omitted when ordinal is 1.
* `$templateName$` is the name of the template when it is nested under a root template.

You can change this rule by specifying the optional *instance name* property on the [template](/xl-deploy/how-to/create-a-provisioning-package.html#add-a-template-to-a-package). The resulting ID would look like:

    /Infrastructure/$DirectoryPath$/$rootInstanceName$/$templateInstanceName$
