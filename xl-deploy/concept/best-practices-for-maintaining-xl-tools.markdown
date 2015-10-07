---
title: Best practices for maintaining XebiaLabs tools
categories:
- xl-deploy
- xl-release
subject:
- System administration
tags:
- system administration
---

## How to store and version different parts of an XebiaLabs installation

It is recommended that you store the following items in your artifact storage repositories:

* Application versions
* The XebiaLabs application `/lib`, `/plugins` and `/hotfix` directories

For the configuration items in XebiaLabs applications, store the following in your source control management repositories:

* The `/conf` and `/ext` directories

Overall, this approach ensures that you can build a running version of the XebiaLabs application, including all plugin content and configurations.

For configuration items, you should define a versioning scheme for the contents of these directories. Also, it is recommended that you have separate 'units' for `/conf` and `/ext`, because these directories may have a different lifecycle.

You can also consider the following:

* Ensure that you have commit policies in place for clear commit messages. This ensures that people who are introducing changes clearly communicate what the changes are intended to do.
* Optionally introduce a branching scheme in which you first check in a configuration change on a development branch. Then, introduce a test setup that uses the development branch configuration and run smoke tests. 
* If you use a build system such as Salt, Ansible, Puppet, or Chef, you can consider scripting this process. For example, it is reasonable to see how you could script the download of various artifacts from your artifact storage, unpack them together, then start the XebiaLabs application instance. You could even use scripting to talk to the XebiaLabs application instances to insert content.

### XL Release

An additional artifact to consider versioning is your XL Release templates. After your Release Administrator has created a template that is considered final, he/she can easily export the template from XL Release using the **Export** button. This exported file is actually an archive file with the `.xlr` extension. If you are following the above storage repository approach, you should also consider storing the XL Release template binaries in the same fashion. 

## How to provision a new XL instance

It is recommended that you create sandbox versions of XebiaLabs tools so you can test changes locally before introducing these changes to the larger team. At a high level, you should:

1. Copy the appropriate version of the application from artifact storage and install it. 
1. Copy the `/lib` and `/plugins` directories from artifact storage.
1. Check out the `/ext` and `/conf` directories from source control management into new server directory.

### XL Deploy

After you create a sandbox environment, you can create the infrastructure and environment definition(s) that you need for testing. You can automate this process by creating versioned scripts and executing them using the command-line interface (CLI) or the REST API.

### XL Release

After you create a sandbox environment, you can check out the template(s) that you would like to work with.

## Tips for setting up development/sandbox instances

When a new version of a XebiaLabs product is available, you can download it from the link provided in the [support forum](https://support.xebialabs.com/forums/324570-Announcements). At a high level, you should:

1. Download the new version from download site and store it in Nexus
1. Create a sandbox version of the new XebiaLabs product (ensure that you have the correct plugins for your installation)

You are now ready to test the new version.
