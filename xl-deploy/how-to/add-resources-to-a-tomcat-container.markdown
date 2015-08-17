---
title: Add resources to an Apache Tomcat container
categories:
- xl-deploy
subject:
- Tomcat plugin
tags:
- tomcat
- middleware
- plugin
---

The XL Deploy [Apache Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html) can deploy to a virtual host (`tomcat.VirtualHost`) or common context (`tomcat.CommonContext`) container. WAR files are deployed to virtual hosts, while resources such as datasources are deployed to the global Tomcat configuration, which is stored in the common context.

## Configure a common context

To configure a common context, create it in XL Deploy:

1. Go to the Repository.
2. Expand **Infrastructure** and locate the Tomcat host.
3. Expand the host and locate the Tomcat server.
4. Right-click the server and select **New** > **CommonContext**.

    ![Add a CommonContext](images/add-tomcat-common-context-in-repository.png)

5. Enter a name for the common context in the **Name** box.
6. Click **Save**.

    ![CommonContext create screen](images/tomcat-common-context-create.png)

7. Expand **Environments** and open the environment where you want to add the common context.
8. Under **Containers**, add the common context to the **Members** list.

    ![Adding CommonContext to an environment](images/add-tomcat-context-to-environment.png)

9. Click **Save**.

## Confine resources to the common context

In an environment that contains a virtual host and a common context, XL Deploy will automatically map resources to both containers. However, you may want to deploy resources only to the common context. To do so, use the tagging feature; refer to [Using tags to configure deployments](using-tags-to-configure-deployments.html) for more information.

For example, add the tag "Common" to the common context:

![Add tag to tomcat.CommonContext](images/add-tag-to-tomcat-context.png)

Then add the same tag to a resource (for example, a `tomcat.DataSourceSpec` CI):

![Add tag to tomcat.DataSourceSpec](images/add-tag-to-tomcat-datasource.png)

XL Deploy will then automatically map the resource to the common context only:

![Deployment with tag](images/tomcat-context-with-tag-deployment.png)

For more information about the Tomcat plugin, refer to [Introduction to the XL Deploy Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html) and the [Tomcat reference](/xl-deploy/latest/tomcatPluginManual.html).
