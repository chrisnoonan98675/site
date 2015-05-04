---
title: Create a release trigger
categories:
- xl-release
subject:
- Releases
tags:
- release
- trigger
---

Triggers offer you an automated way to create and run a release. A trigger is a kind of XL Release plugin executed periodically and able to create and run a release from a template.

The triggers page is accessible by clicking on **Triggers** from the **Show** menu of your template, and you will be on the triggers configuration page. This page is divided in two parts. The first one named **Settings** is used to configure some trigger settings:

![Trigger settings](../images/triggers-settings.png)

The configurable triggers options are:

*  **Allow concurrent triggered releases**: If this option is unchecked, the releases kicked off by the trigger's template will be run **sequentially** and not **concurrently**. For example, if this option is unchecked, and a trigger's template detects many releases to kick off in a short time, XL Release will run only one triggered release at a time.

The second part of the triggers page, will be used to list, create, and update the triggers list:

![Trigger list](../images/triggers-list.png)

To add a trigger, select the type of trigger you want to add.

You will arrive on the creation form, where you will be able to configure the trigger:

![Trigger form](../images/git-plugin-fields.png)

The first commons and mandatories fields are:

*  **Title**: The name you will use to identify the trigger in the previous the list.
*  **Release title**: The name given to releases you will create with this trigger. This field can contain [variables](/xl-release/concept/variables-in-xl-release.html).
*  **Poll type**: Type of polling interval as repeatable interval in seconds (i.e. each 10 seconds) or cron expression
*  **Poll interval**: The number of seconds between each execution of the trigger or cron expression
*  **Enabled**: Define if the trigger is currently active or not. A disabled trigger do nothing.

Then, you will find the configuration fields to configure the plugin.

Then, you will find the **Release tags** section, where you can define the tags to be added when the trigger creates the release.  This field can contain [variables](/xl-release/concept/variables-in-xl-release.html).

At the end of the form you will find the **Template Variables** section, where you can define the values to be used for template variables when the trigger creates a new release. By default, all template variables with the same name as a trigger variable are automatically bound. For example, a template variable named "commitId" will have the value "${commitId}", which will be replaced by the corresponding value of the trigger variable. You can also specify static values or others variables.

To finish the creation of a trigger, click on **Save** and you will be redirected to the list of triggers where you will see your own.

![Trigger list](../images/triggers-list.png)

Note: All releases created by a trigger are tagged with the id of the this trigger, you can find all releases related to a trigger by clicking on the link **related releases**, visible on each line of this list.

A trigger will not trigger a release during its first run, it will wait the next iteration.
