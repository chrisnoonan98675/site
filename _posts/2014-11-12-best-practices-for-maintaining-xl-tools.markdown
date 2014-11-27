---
title: Best practices for maintaining XL tools
author: tj_randall
categories:
- xl-deploy
- xl-release
tags:
- system administration
---

A common question that users ask is how to maintain their XL Release and/or XL Deploy applications in a more "continuous delivery" type manner. Generally speaking, users have challenges maintaining their existing COTS (commercial, off-the-shelf) products, so they'll look to us to provide direction on how to make it easier to maintain XebiaLabs products.

I'll break this article down into four main topic areas, to focus on various aspects of maintaining your XL instance. These areas are:

* Versioning
* Creating a sandbox version
* Upgrading to a new release (pre-prod)
* Upgrading your Production XL instance

For these topics, I will focus primarily on XL Release and XL Deploy, and highlight any application-specific needs as applicable.

## What goes where: how to store and version different parts of an XL installation

In general, most users I've worked with don't generally treat their COTS products the same way as their own internally-built applications. Users generally are doing a good job of storing their enterprise application binaries in artifact storage repositories. The struggle that users generally encounter is maintaining versions of COTS products as new versions are introduced.

For XL tools, it is a good approach to store the following in your artifact storage repositories:

* Application versions
* The XL-tool  `/lib`, `/plugins` and `/hotfix` directories

For the configuration items in XL tools, you will want to store the following in your SCM repositories:

* The `/conf` and `/ext` directories

Overall, this approach ensures that you can build a running version of the XL application, including all plugin content and configurations.

For your configuration items, you should define a versioning scheme for the contents of these directories. Also, you'll want to have separate 'units' for `/conf` and `/ext` since these directories may have a different lifecycle. You can also consider the following:

* Ensure that you have commit policies in place for clear commit messages. This ensures that people who are introducing changes clearly communicate what the changes are intended to do.
* You could introduce a branching scheme where you'd first check in a configuration change on a development branch. Then, introduce some test setup that uses the development branch configuration, and run some smoke tests. 
* If you're using build systems like Salt, Ansible, Puppet, Chef, etc, then you could probably script all of this.  For example, it is reasonable to see how you could script the download of various artifacts from your artifact storage, unpack them together, then start up the XL instance. You could even use scripting to talk to the XL instances to insert content.

### XL Release

One additional artifact to consider versioning is your XL Release templates. Once your Release Administrator has created a template that is considered final, he/she can easily export the template from XL Release using the **Export** button on the UI. This exported file is actually an archive file, with the `.xlr` extension. If you are following the above storage repository approach, you should also consider storing the XL Release template binaries in the same fashion. 

## How to provision a new XL instance

We highly encourage users to create sandbox versions of their XL tools, since it enables them to test their changes locally before introducing these changes to the larger team. From a high level, there is a three step approach for creating your sandbox:

1. Copy appropriate version of XL product from their artifact storage, and install [via guide](http://docs.xebialabs.com/releases/4.0/xl-release/systemadminmanual.html#xl-release-server-directory-structure). 
1. Copy `/lib` and `/plugins` from from their artifact storage
1. Checkout `/ext` and `/conf` from SCM into new server directory

### XL Deploy

Once the user has created their sandbox environment, they simply have to create the infrastructure they need for testing, as well as environment definition(s). Of course, you can always automate the creation for this infrastructure/environment information by creating versioned scripts, and executing those using the CLI or REST API interface.

### XL Release

Once the user has created their sandbox environment, they can also check out the appropriate template(s) that they want to work with. 

## Tips for setting up development/sandbox instances

When it's time for you to upgrade to a new version of XL product, you'll be able to download the appropriate version based on the announcement you received from the [support forum](https://support.xebialabs.com/forums/324570-Announcements). From a high level, you will:

1. Download new version from download site and store in Nexus
1. Create a sandbox version of new XL product (ensure the correct plugins for your installation)

You are now ready to test the new version. We find that users do this testing in various forms, from manual testing to more automated performance testing. 

## Tips for setting up production instances

We've outlined the process for upgrading your production server. ([Upgrading your server](http://docs.xebialabs.com/releases/4.0/xl-release/systemadminmanual.html#maintaining-xl-release)) If you're testing against upgrades in the sandbox environment, you're in a much better position to ensure a seamless upgrade to your production instance. 
