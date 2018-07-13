---
title: Manage templates and releases using folders
categories:
- xl-release
subject:
- Folders
tags:
- release
- template
- folder
since:
- XL Release 6.0.0
---

Folders provide an intuitive way to organize your templates and releases by project, by team, or by any other model that fits your organization. With folders, you can easily apply security settings to a large number of templates and releases by setting role-based access control at any level of your folder hierarchy.

Folders enable you to:

* Organize templates and releases according to a logical, hierarchical structure
* Apply permissions to all assets in a folder at once
* Easily navigate among related templates and releases

## Get started with folders

To start using folders in XL Release 6.0.0, select **Design** > **Folders** in the top bar. Click **Add folder** to start adding folders.

If you have upgraded from an earlier version of XL Release, you can find your existing templates in **Design** > **Templates**. From there, you can move them into folders that you have created.

![Templates that are not in a folder](../images/templates-all-templates.png)

## Add a folder

To add a folder at the top level of the folder hierarchy, ensure that no other folder is selected, and click **Add folder** at the top of the Folders screen.

To add a folder in another folder, either:

* Select the parent folder and click **Add folder** at the top of the Folders screen, or
* Select the parent folder, click ![Action menu](/images/menu_three_dots.png), and select **Add folder**

## Rename a folder

To rename a folder:

1. Click the folder to select it.
2. Click ![Action menu](/images/menu_three_dots.png) and select **Rename**.
3. Type the new name and press ENTER.

## Delete a folder

To delete a folder:

1. Click the folder to select it.
2. Click ![Action menu](/images/menu_three_dots.png) and select **Delete**.
3. Confirm the deletion.

Note that:

* When you delete a folder, all folders within it are also deleted.
* You cannot delete a folder that contains active releases or [triggers](/xl-release/how-to/create-a-release-trigger.html).

## Create a template in a folder

To create a template in a folder:

1. Select the folder.
2. In the folder, click **Add template** and choose [**Create new template**](/xl-release/how-to/create-a-release-template.html) or [**Import template**](/xl-release/how-to/import-a-release-template.html).

## Move a template to a different folder

To move a template to a different folder:

1. Select the folder where the template is located.
2. Next to the template, click **Move**. A list of folders appears.
3. Select the destination folder and click **Move**. XL Release moves the template to the selected folder.

Note that:

* You cannot move a _release_ to a different folder.
* When you move a template, the releases that were created from that template are not moved. Releases that are created after the move will be stored in the new folder.
* If the template and the destination folder have different [release teams and/or permissions](/xl-release/how-to/configure-release-teams-and-permissions.html), you can choose whether to:
    * Add the template's teams and permissions to the folder
    * Replace the template's teams and permissions with those of the folder

## Folder security

In XL Release 6.0.0 and later, _template_ and _release_ permissions are managed at the folder level. For information about the permissions that are available, refer to [Configure release teams and permissions](/xl-release/how-to/configure-release-teams-and-permissions.html).

## Folder level configuration

With XL Release version 8.1.0, shared configurations can now be defined on folders. Releases and templates have access
to all configurations defined on the folder and inherit all configurations from parent folders, including configurations
defined globally. You must have the new "Edit configuration" permission to create or edit configurations on folders.
If you use the default set of folder permissions, the _Folder Owner_ team has this permission on new folders.  

### Add a custom configuration on a folder

To add a new configuration on a specific folder:
1. Go to **Design** > **Folders**.
1. Select a folder from the list and click **Configuration** tab.
1. To add a new instance of a configuration type, click ![image](/xl-release/images/add-button.png) on the desired configuration.
1. Enter the required information and click **Save**.

### Inheritance and referencing configurations

Configurations are inherited from all the parent folders in the folder hierarchy. A template defined in a folder has access to all configurations defined on the folder's ancestors, including globally defined configurations.

If you define a configuration for a folder, you can only view it in the UI in the folder where the configuration is defined. This is also the folder where you can edit or delete the configuration.

You can reference all inherited configurations on a task, trigger, or dashboard tile.

### Permissions and security

All configurations defined in the system, including folder configurations, are globally readable by an authenticated user. Any authenticated user can read the field values of configurations. The password values are encrypted. Do not store sensitive information in fields of configurations. Even if a configuration cannot be referenced by rules of inheritance, the user can read a configuration field when requesting it by ID through the API.

To add or edit folder configurations, a user or a team must have the [Edit configuration folder permission](/xl-release//how-to/configure-release-teams-and-permissions.html#folder-permissions) assigned.

### Moving templates or folders

You can move both templates and folders that contain configurations or refer to configurations.

If you move a template to a different folder, the configuration does not automatically move with the template. You must manually update the configuration. To avoid references becoming invalid, there is a check that verifies if referenced configurations are accessible at the new destination. If the check does not pass, the template cannot be moved. This is important to know when you plan where configurations will be defined.

When moving a folder, the configurations defined in that folder will also be moved. For the rest, the checks are the same as
for moving a template. You cannot move a folder if the references will be broken due to the inheritance rules.

### Use unique names for configurations

There are no naming restrictions for configurations. To help you uniquely identify individual configurations in the system, it is strongly recommended that you use unique names for configurations throughout the system.
