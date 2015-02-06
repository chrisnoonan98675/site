---
title: Set up XL Deploy security using the GUI
categories:
- xl-deploy
subject:
- Security
tags:
- security
- system administration
- gui
---

XL Deploy security can also be configured in the GUI. This section describes how this is done.

## Setting up global permissions

The **Admin** screen is used to maintain _roles_ and _global permissions_ in XL Deploy. Only users that are permitted to maintain security will have access to the Admin screen.

### Global permissions

The global permissions tab is used to assign global permissions to roles. The following screenshot shows an example of the global permissions tab:

![Global Permissions on the Admin screen](images/admin-screen-global-permissions.png)

The table shows, for each role, which global permissions are assigned to it. If a permission is assigned to the role, a check mark is shown. If the permission is not assigned, a dot is shown. 

To add a role and assign permissions to it, select it from the drop-down list in the bottom-left corner of the table. Click on a dot or check mark to assign or unassign the permission to the role.

Note that changes made to the global permissions table are not stored until you click **Save**.

The screen contains the following buttons:

* **Save button**: Save the changes you have made to the table.
* **Reset button**: Cancel all changes to the table and reload the table and roles from the server.
* **Refresh button**: Cancel all changes to the table and reload the table and roles from the server.

### Roles

The roles tab is used to create and maintain roles in XL Deploy. The following screenshot shows an example of the roles tab:

![Roles on the Admin screen](images/admin-screen-roles.png)

The table displays, for each role, which _principals_ are a member of the role.

To create a role, click the **plus** sign on the top-right corner of the table. A new role is added to the table. Enter a role name and press the **Tab** key to move to the principals field. Enter all principals that are a member of the role, separated by _commas_.

To remove a role, select it in the table and click the **minus** sign on the top-right corner of the table.

To rename a role, double-click on the role name, type the new role name and click **Save**.

To edit a role, double-click the principal list, edit the list and click **Save**.

Note that changes made to the role table are not stored until you click **Save**.

The screen contains the following buttons:

* **Save button**: Save the changes you have made to the table.
* **Reset button**: Cancel all changes to the table and reload the table and roles from the server.
* **Refresh button**: Cancel all changes to the table and reload the table and roles from the server.

## Setting up local permissions

Local permissions (permissions per CI) can be configured in the Repository screen. To edit the permissions on a root node or directory, right-click on the node and select the _Permissions_ item from the context menu.

The table displays, for each role, which permissions are assigned to it on the selected CI. If a permission is assigned to the role, a check mark is shown. If the permission is not assigned, a dot is shown.

To add a role and assign permissions to it, select it from the drop-down list in the bottom-left corner of the table. Click on a dot or check mark to assign or unassign the permission to the role.

Note that changes made to the permissions table are not stored until you press the **Save button**.

The screen contains the following buttons:

* **Save button**: Save the changes you have made to the table.
* **Reset button**: Cancel all changes to the table and reload the table and roles from the server.
* **Refresh button**: Cancel all changes to the table and reload the table and roles from the server.
