---
title: Introduction to the XL Deploy Puppet plugin
categories:
xl-deploy
subject:
Puppet
tags:
puppet
plugin
since:
XL Deploy 5.1.0
---

The Puppet plugin is an XL Deploy plugin that uses the Puppet provisioning tool to install modules and manifests on hosts.

For information about Puppet requirements and the configuration items (CIs) that the plugin supports, refer to the [Puppet Plugin Reference](/xl-deploy-xld-puppet-plugin/latest/puppetPluginManual.html).

**Important:** In XL Deploy 6.0.2 and later, the Puppet plugin includes the functionality that was previously implemented by the [XL Deploy Provision Puppet plugin](/xl-deploy/concept/xl-deploy-provision-puppet-plugin.html).

## Features

* Apply manifest file for provisioning using `puppet apply` command.
* Install and uninstall Puppet modules from the Puppet Forge.
* Install and uninstall Puppet tarball modules by uploading module TAR files.
* Apply individual manifests files present in a Puppet manifest folder.
* Provision an environment using Puppet (supported in XL Deploy 6.0.2 and later).
