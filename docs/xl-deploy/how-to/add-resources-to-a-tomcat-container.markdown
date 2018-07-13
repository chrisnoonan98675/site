---
title: Add resources to an Apache Tomcat container
categories:
- xl-deploy
subject:
- Tomcat
tags:
- tomcat
- plugin
---

The XL Deploy [Apache Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html) can deploy to a virtual host (`tomcat.VirtualHost`) or common context (`tomcat.CommonContext`) container. WAR files are deployed to virtual hosts, while resources such as datasources are deployed to the global Tomcat configuration, which is stored in the common context.

## Configure a common context

To configure a common context, create it in XL Deploy:

1. Go to the Repository.
2. Expand **Infrastructure** and locate the Tomcat host.
3. Expand the host and locate the Tomcat server.
4. On the right of the Tomcat server, click ![Menu button](images/menuBtn.png), then select **New** > **tomcat** > **CommonContext**.

    ![Add a CommonContext](images/add-tomcat-common-context-in-repository-new-ui.png)

5. In the **Name** field, enter a name for the common context.
6. Click **Save**.

    ![CommonContext create screen](images/tomcat-common-context-create-new-ui.png)

7. Expand **Environments** and open the environment where you want to add the common context.
8. In the **Containers** field, add the common content.

    ![Adding CommonContext to an environment](images/add-tomcat-context-to-environment-new-ui.png)

9. Click **Save**.

## Confine resources to the common context

In an environment that contains a virtual host and a common context, XL Deploy will automatically map resources to both containers. To deploy resources to the common context only, you must use the tagging feature. For more information, see [Use tags to configure deployments](/xl-deploy/how-to/use-tags-to-configure-deployments.html).

For example, add the tag "Common" to the common context:

![Add tag to tomcat.CommonContext](images/add-tag-to-tomcat-context-new-ui.png)

Then add the same tag to a resource (for example, a `tomcat.DataSourceSpec` CI):

![Add tag to tomcat.DataSourceSpec](images/add-tag-to-tomcat-datasource-new-ui.png)

When you deploy your application, during the configuration phase, XL Deploy will now automatically map the resource to the common context only.

For more information about the Tomcat plugin, refer to [Introduction to the XL Deploy Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html) and the [Tomcat reference](/xl-deploy/latest/tomcatPluginManual.html).
