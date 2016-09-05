---
title: XL Deploy task type
---

The XL Deploy task provides integration with [XL Deploy](/xl-deploy), XebiaLabs' Application Release Automation solution. It is an automated task that tells XL Deploy to deploy a certain application to an environment. Both the application and environment must be configured in XL Deploy. The task gives live updates of the deployment process and completes automatically when the deployment succeeds.

**Note:** If the deployment fails, it is automatically rolled back.

![XL Deploy Task Details](/xl-release/images/deployit-task-details.png)

The options for the XL Deploy task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Server | XL Deploy server to which XL Release connects. You can configure XL Deploy servers in **Settings** > **[XL Deploy Servers](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html)**. |
| Deployment package | Version of the application to be deployed. Start typing and XL Release will retrieve the list of applications from the XL Deploy server. |
| Environment | Environment to which to deploy. Start typing and XL Release will retrieve the list of environments from the XL Deploy server. |
| Username | User name to use when connecting to the XL Deploy server. |
| Password | Password to use when connecting to the XL Deploy server. |

You can also use [variables](/xl-release/concept/variables-in-xl-release.html) in the **Deployment package** and **Environment** fields. This allows you to reuse application version and environment across tasks in XL Release. For example, when using variables, you can mention the name of the application and the environment to which you deployed in a [Notification task](/xl-release/how-to/create-a-notification-task.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), XL Deploy tasks have a green border.

## Configure XL Deploy servers

To configure connections between XL Release and [XL Deploy](/xl-deploy/) servers, select **Settings** > **XL Deploy servers** from the top menu. The XL Deploy servers page is only available to users who have the *Admin* [global permission](/xl-release/how-to/configure-permissions.html).

![XL Deploy Servers](/xl-release/images/deployit-servers.png)

To add a server:

1. Click **New server**.
2. In the **Server Name** box, enter a name for the server. This is how the server will be identified in XL Release.
3. In the **URL** box, enter the address at which the server is reachable. This is the same address you use to access the XL Deploy user interface.
4. In the **Username** and **Password** boxes, enter the credentials of the XL Deploy user that XL Release will use to log in. XL Releases uses this user to query XL Deploy for its available applications and environments, to support code completion in the XL Deploy task and variables section.

    It is recommended that you create an XL Deploy user with read-only rights for XL Release to use. To perform deployments, specify a user and password directly in the XL Deploy task. This provides fine-grained access control from XL Release to XL Deploy. If you do not specify a user in the XL Deploy task, then XL Release will use the user configured on the XL Deploy server to perform deployments (provided that user has deployment rights in XL Deploy).

5. Click **Test** to test if XL Release can log in to the XL Deploy server with the configured address and credentials.
