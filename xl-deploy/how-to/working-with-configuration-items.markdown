---
title: Working with configuration items
categories:
- xl-deploy
subject:
- Configuration items
tags:
- ci
- type system
- artifacts
weight: 251
---

XL Deploy stores all of its information in the repository. The **Repository** screen gives you access to the configuration items (CIs) in the repository and allows you to edit them manually.

## Create a new CI

To create a new CI in the repository, do the following:

1. **Select the type of CI to create**. In the Repository Browser navigate the repository to find the CI under which you want to create a new CI. Right-click on the CI and select **New**. A nested menu will appear that will allow you to choose a CI to create. Select the CI type you want to create.
2. **Create a new CI**. The selected CI type will be opened in the CI Editor Tab so you can fill out its properties to create a new CI. **Note**: the **Id** field of the CI is a special non-editable property that determines the place of the CI in the repository. For more information about the **Id** property, see the **XL Deploy Reference Manual**.
3. **Save the new CI**. Click on the **Save** button to save the new CI in the repository. XL Deploy will perform validation on the CI to ensure that all properties have appropriate values. An error message is shown if not.

If the CI is an _artifact_ CI representing a binary file, you can upload the file from your local machine into XL Deploy. If the CI contains a directory structure then you must first add it to a ZIP file, then upload it.

## Duplicate a CI

Sometimes it is easier to make a new CI by starting from a copy of an existing CI as a template. For this it is possible to duplicate an existing CI:

1. **Browse to the existing CI**. In the Repository Browser, navigate to the existing CI you want to use as a template for a new CI.
2. **Right-click the CI**. By right-clicking the CI, its context menu will be shown
3. **Select 'Duplicate'**. This makes a deep copy of the existing CI. The copy will have the same name as the original, with the word 'Copy' appended.

The duplicate can be modified at will, such as by changing its name or its properties.

## Modify a CI

To modify an existing CI, follow these steps:

1. **Select the CI in the Repository Browser**. Navigate the repository to find the CI you want to modify. For more information about the repository structure, see the **XL Deploy Reference Manual**.
2. **Edit the CI**. Use double click or select **Edit** from the context-menu on the CI to edit it in the CI Editor Tab.
3. **Save the CI**. Click on the **Save** button to save the new CI in the repository. XL Deploy will perform validation on the CI to ensure that all properties have appropriate values. An error message is shown if not.

## Delete a CI

To delete an existing CI, follow these steps:

1. **Select the CI in the Repository Browser**. Navigate the repository to find the CI you want to modify. For more information about the repository structure, see the **XL Deploy Reference Manual**.
2. **Delete the CI**. Select **Delete** from the context menu. XL Deploy will confirm whether you want to delete the CI and, if yes, the CI will be deleted.

Note that deleting a CI will also delete all nested CIs. For example, by deleting an environment CI, all deployments on that environment will also be deleted. The deployment package that was deployed on the environment, however, will remain under the **Applications** root node.

Note that you can't recover a deleted CI.

## Compare CIs

### Comparing against other CIs

Depending on your environment, deploying the same application to multiple environments may use different settings. With all these differences, it is easy to lose track of what is running where and how it is configured. XL Deploy's CI comparison feature makes it easy to spot the differences between two or more deployments.

To compare multiple CIs, follow these steps:

1. **Select the reference CI in the Repository Browser**. The reference CI is the basis for the comparison, the CI that the other CIs are compared against. Select it in the Repository Browser and select **Compare > With other CI** from the context menu.
2. **Drag comparison CIs into the Comparison Tab**. To add more CIs into the comparison, locate them in the Repository Browser and drag them into the Comparison Tab. XL Deploy will mark the properties that are different in red.

You can only compare CIs that have the same type.

### Comparing against previous versions

Whenever you make changes to a CI, XL Deploy creates a record of the previous version of the CI. You can see and compare a CIs current and previous versions with the comparison feature.

First some basic versioning concepts. The current version of a CI is always called 'current' in XL Deploy. Only CIs that are persisted get a version number, starting from 1.0. The date and time reported are the creation or modification date and time of the CI. The user reported is the user that created or modified the CI.

Please note that the comparison will not show properties that are declared "as containment" on child CIs pointing upwards to their parent.

To compare different versions, follow these steps:

1. **Select the CI you want to compare in the Repository Browser**. Select **Compare > With previous version** from the context menu. If there are previous versions available, a comparison workspace will be displayed. By default, XL Deploy will compare the current version with the previous version.
2. **Select different versions**. You can change the version shown in the left and right hand side of the comparison window by using the version dropdown list.

You can only compare versions of one specific CI against itself. It is not possible to see CI renames and security permission changes in the CI history, this information can be found in the auditing logs.

### Comparing a CI tree

The XL Deploy Compare feature allows you to compare two or more CI trees. That means, in addition to comparing the chosen configuration items, it recursively traverses the CI tree and compares each CI from one tree with matching configuration items from other trees. For information about using the Compare feature, refer to [Compare configuration items](/xl-deploy/how-to/compare-configuration-items.html).

## CIs and security

Access to CIs is determined by local permissions set on Repository nodes. For information about local permissions, refer to [Roles and permissions in XL Deploy](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#local-permissions).

## Customizing CI types

For information about the ways that you can customize the XL Deploy CI type system, refer to:

* [Customize an existing CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html)
* [Define a new CI type](/xl-deploy/how-to/define-a-new-ci-type.html)
* [Define a synthetic method](/xl-deploy/how-to/define-a-synthetic-method.html)
