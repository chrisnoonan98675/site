---
title: Using the View As feature
categories:
- xl-deploy
subject:
- Customization
tags:
- view
- customization
since: XL Deploy 7.5.0
---

The XL Deploy *View As* feature allows you, as an `Admin` user, to view XL Deploy and navigate through the UI as a specific user or role. This allows you to see the permissions for a user or view and find CIs from another user perspective. You can use this information to decide if a user's environment needs to be modified, add or remove permissions, or configure what a user or role can view in a CI tree.

To view XL Deploy from a different user perspective:
1. Click the gear icon menu in the top right corner and select **View As**.
1. Select one of the two options: **View as user** or **View as roles**.
1. Select a user name from the list or specify a role name in the text field.  
1. Click **Change view**.

![View As](images/view-as.png)

The XL Deploy view is filtered by the read permissions of the selected user or role. When you are in the *View As* mode, you still have the admin permissions.

**Important:**
* If you want to view XL Deploy as an existing LDAP user, the LDAP user will not listed for autocompletion in the drop down list.
* If you try to view as another SSO user, a message will inform you that the user could not be found because roles cannot be queried for other SSO users.
