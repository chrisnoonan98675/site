---
title: Using template version control
categories:
- xl-release
subject:
- Releases
tags:
- template
- version control
since:
- XL Release 7.6.0
---

You can use the template version control feature to track changes to a template and to revert past changes. While XL Release has always automatically tracked template changes for compliance and auditing purposes, template version control allows you to save specific versions of a template and to roll back changes.

Template Version Control also enables you to label templates and to compare saved template versions by viewing them as code in XL Release’s Releasefile format.

## Enable automated template versioning

To use the version control feature, you must enable automated template versioning. By default, the automated template versioning is disabled in XL Release.

To enable automated template versioning, go to **Settings** > **General** tab, and in the **Track changes in template version control** section, click **Enable track changes**.

## View template changes and save template version

To view the changes performed on a template, open the template and select **Show** > **Version control**. The version control displays a list of all the changes with the latest at the top.

![Version control](../images/version-control.png)

You can filter the list by setting a start date and an end date for the changes or by specifying a user in the *Filter by user...* field.

To save a new template version with the latest change, click **Save as new version**, specify a name and a description, and then click **Save**. You can view the revisions for all the versions in the list by clicking **View all changes**. You can also view the revisions for a specific version by clicking **View all changes** on the version row in the list. If tracking changes in the template version is enabled, all your changes since the last version are visible.

## Restore and compare templates

To restore a template to a specific revision, click **Restore** from the **Actions** column. This creates a new change in the list that is identical to the revision you selected.

To compare two versions of a template, click the square on the left side next to the desired versions to select them, and then click **Compare**. This shows the two selected versions as code in XL Release’s Releasefile format and highlights the differences.
