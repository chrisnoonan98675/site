---
title: Add an XL Deploy endpoint in Visual Studio Team Services
categories:
- xl-deploy
subject:
- Team Foundation Server
tags:
- tfs
- vsts
- microsoft
since:
- XL Deploy 7.0.0
---
The [XL Deploy extension for Visual Studio Team Services (VSTS)](/xl-deploy/concept/team-foundation-server-2015-plugin.html) provides automated deployment functionality through an XL Deploy build task for Microsoft TFS 2015, Microsoft TFS 2017, and Visual Studio Team Services (VSTS), which is also known as Visual Studio Online.

To get started, you must [upload the XL Deploy build task](/xl-deploy/how-to/install-a-build-task-in-tfs-2015.html) to your on-premises TFS or to your VSTS server. This task connects to XL Deploy through an endpoint. In TFS 2015, TFS 2017, and VSTS/VSO, endpoints are managed in a common place. This means you do not have to create endpoints in build tasks multiple times, making maintenance and centralized credentials management easier.

You create endpoints on a per-project basis. To create an endpoint for XL Deploy in VSTS:

1. In the **XL Deploy Showcase** window, click **Services** in the right pane.

    ![XL Deploy Showcase Services](images/vsts_services.png)

1. Click **New Service Endpoint** and choose **XL Deploy**.

    ![](images/vsts_add_xld_endpoint.png)

1. In the **Add new XL Deploy Connection** window:

    ![Add a new XL Deploy connection](images/vsts_new_endpoint.png)

    1. In the **Connection name** field, enter a connection name such as *XL Deploy Server*.

    1. In the **Server URL** field, enter a URL such as *http://my.xldeploy.server:4516/*. Ensure that the URL includes the correct port number.

    1. In the **User name** and **Password** boxes, enter the user name and password used to authenticate with XL Deploy.

    1. Click **OK**.

You can refer to this endpoint from any of the XL Deploy build tasks.

To create an XL Deploy endpoint in Team Foundation Server, refer to [Add an endpoint in Team Foundation Server 2015](/xl-deploy/how-to/add-an-endpoint-in-tfs-2015.html).

For more information, refer to [Introduction to the VSTS/TFS plugin](/xl-deploy/concept/team-foundation-server-2015-plugin.html).
