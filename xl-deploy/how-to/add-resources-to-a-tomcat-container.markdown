---
title: Add Resources to a Tomcat Container
categories:
- xl-deploy
subject:
- Tomcat plugin
tags:
- tomcat
- middleware
- plugin
---

## Background

There are two types of container the Tomcat plugin can deploy resources to, a VirtualHost or to a CommonContext. Users of XLD usually become familiar with working with VirtualHosts as this is where WARs are deployed to. However it is often required to deploy resources, such as Data Sources, to the global Tomcat configuration. To achieve this the CommonContext container is used.

## Configuring a CommonContext

To setup a common context we need to create this under infrastructure under a Tomcat Server:

![Adding a CommonContext](images/add-tomcat-common-context-in-repository.png)

This requires one attribute to be set, Name, this is set to "tomcat-context" in the example below:

![CommonContext create screen](images/tomcat-common-context-create.png)

Once this is saved it has to be added to an environment so it's available for deployment:

![Adding tomcat-context to an environment](images/add-tomcat-context-to-environment.png)

## Confining resources to the CommonContext

In a environment with both a VirtualHost and a CommonContext XL Deploy will automatically map resources to both of these which is often not the desired behavior. This can be resolved by using tagging, explained in [Using tags to configure deployments](using-tags-to-configure-deployments.html)

This could be implemented using the tag "Context". To do so first add the tag "Context" to the "tomcat-context":

![Add tag to tomcat-context](images/add-tag-to-tomcat-context.png)

Then add the same tag to a resource, for example a DataSource:

![Add tag to Tomcat DataSourceSpec](images/add-tag-to-tomcat-datasource.png)

This then will automatically map the DataSource to the "tomcat-context" only:

![Deployment with tag](images/tomcat-context-with-tag-deployment.png)

For more information about the Tomcat plugin, refer to [Introduction to the XL Deploy Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html).
