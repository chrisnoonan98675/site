---
title: Set up roles and permissions in XL Deploy
categories:
- xl-deploy
subject:
- Security
tags:
- security
- system administration
- permissions
- roles
- principals
weight: 261
---

XL Deploy provides fine-grained security settings based on [roles and permissions](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html) that you can configure in the GUI and through the command-line interface (CLI). To configure security in the GUI, click **Admin** in the top menu bar.

## Assign principals to roles

Use the **Roles** tab to create and maintain roles in XL Deploy. To add a role, click ![Add role](/images/button_add_security_role.png). To delete a role, click ![Remove role](/images/button_remove_security_role.png).

*Principals* are assigned to roles. To assign a principal to the role, double-click the **Principals** column and type the principal name. Separate principals with commas. To delete a principal, double-click the column and delete the principal name.

Be sure to click **Save** to save the roles and principals.

![Roles on the Admin screen](images/admin-screen-roles.png)

## Assign global permissions to roles

Use the **Global permissions** tab to assign global permissions to *roles* in XL Deploy. To add global permissions to a role:

1. Select the role from the **Select to add role** list.
2. Select the permissions that you want to assign to the role.
3. Click **Save** to save the permissions.

    ![Global Permissions on the Admin screen](images/admin-screen-global-permissions.png)

## Assign local permissions to roles

Use the **Repository** to assign local permissions to roles. To add local permissions to a role:

1. Right-click a root node (such as Applications or Environments) or a directory and select **Permissions**.
1. Select the role from the **Select to add role** list.
2. Select the permissions that you want to assign to the role.
3. Click **Save** to save the permissions.

## Sample security set up

This example shows how you can set up security roles using the XL Deploy GUI. The example environment has two applications, OnlineOrders and SiteSearch. They are both deployed to a test server before going to production.

There are two teams developing and deploying the applications. One team can't see the other's team application. Also, developers can only deploy to the test environment, while deployers can deploy to the test environment and to production.

We'll set up different security roles for the different users in the organization.

This example assumes that there are already users and groups present in the system, for example, by way of LDAP integration.

This is an overview of the environment for this example:

![image](images/security-setup-overview.png)

### Create roles

The list of roles we want to have in the system are:

* OnlineOrders developer: May update the OnlineOrders application
* SiteSearch developer: May update the SiteSearch application
* Lead developer: May update and install any application on the test environment
* OnlineOrders deployer: May update OnlineOrders application in test and production
* SiteSearch deployer: May update SiteSearch application in test and production
* Lead deployer: May install any application on any environment

![image](images/security-setup-roles.png)

### Create directories

Now let's add some directories. In **Repository**:

1. Right-click **Applications** and select **New** > **Directory**.
2. Name the new directory "Orders".
3. Drag-and-drop the OnlineOrder package into the Order directory.
4. Repeat this procedure to create a directory for the SiteSearch application and for the Production and Test environments.

![image](images/security-setup-directories.png)

### Assign roles for applications

To assign roles, right-click a directory and select **Permissions**.

![image](images/security-setup-click-permissions.png)

Assign roles to the **Orders** directory:

![image](images/security-setup-permissions-app.png)

This means that the lead developer and the lead deployer are allowed to add and remove applications in the Orders directory, but regular developers and deployers assigned to the project may only upgrade an existing deployment, say from OnlineOrders 1.0 to 2.0.

You can do a similar setup for the **Search** directory, substituting the OnlineOrders developer and deployer roles with the corresponding SiteSearch roles.

Note that if a role is not added to this screen, it effectively has no permissions. To remove a role, simply deselect all of its permissions and click **Save**.

### Assign roles for environments

Now, set permissions for the test environment. This environment is can be accessed by both development and operations teams. Simply add all roles and grant all permissions to the lead developers and deployers. Regular developers and deployers have more restricted access.

![image](images/security-setup-permissions-test.png)

For production, only deployers should have access. Only the lead deployer may alter the environment from XL Deploy.

![image](images/security-setup-permissions-prod.png)

### Refine security levels

In the current setup, the test and production environments are shared for the OnlineOrders and SiteSearch applications. That means that the OnlineOrders deployers can see the SiteSearch application and vice versa.

To avoid this, you must split the environments into application-specific environments. So the production environment will be split into an "Online Order Production environment" and "SiteSearch Production environment". Note that they will still refer to the same physical servers. But, by splitting them up, you can effectively hide them from different sets of users in XL Deploy.

This is the new setup:

![image](images/security-setup-environment-refined.png)

The permissions for **Environment** > **Orders** > **Production** look like:

![image](images/security-setup-permissions-prod-orders.png)

And the permissions for **Environments** > **Orders** look like:

![image](images/security-setup-read-permissions-in-parent.png)
