---
title: Get started with XL Release
categories:
- xl-deploy
subject:
- Getting started
tags:
- getting started
---

[XL Release](https://xebialabs.com/products/xl-release) is an end-to-end pipeline orchestrator for DevOps and continuous delivery. XL Release helps you automate and orchestrate your release processes and gives you visibility across your entire software release pipeline.

Below, you'll find some tips for your first-time XL Release experience.

## Step 1 Explore XL Release

First, read the [essential background information](/xl-release/concept/core-concepts-of-xl-release.html) about XL Release's key concepts and check out the [Visibility, Automation, and Control with XL Release and XL Deploy](https://www.youtube.com/watch?v=vyAGFcFjdxI) video.

When you log in to XL Release for the first time, you'll see the task overview screen. A task called _Welcome! Click me to get started_ is assigned to you. Click the name of the task to follow a guided tour of XL Release's main features.

![Welcome release](../images/xl-release-welcome-get-started.png)

## Step 2 Model a release pipeline

In XL Release, you model a release pipeline as a _template_. Each time you want to release your software, you create a _release_ based on the template. This ensures that each release follows a consistent set of steps.

### Create a template

To model your first release pipeline, select **Releases** > **Templates** from the top bar, then click **New template**. Enter a name for the template and click **Create** (you can fill in the other [template properties](/xl-release/how-to/create-a-release-template.html#create-a-template) later).

![Create a template](../images/create-new-template.png)

### Add phases and tasks

A template is made up of phases (columns) and tasks (the boxes in each column). In a running release, phases are executed in order from left to right, and tasks are executed in order from top to bottom.

![Release flow editor](../images/release-flow-editor.png)

A new template already has one phase in place. Click **New phase** to change its name; because this is the first phase in the process, you might choose a name such as _Prepare release_ or _Prerelease tasks_.

In the phase, click **Add task** to add the first task. XL Release supports many [types of tasks](/xl-release/concept/types-of-tasks-in-xl-release.html); some represent work that is done by a person or a team, while others represent automated work that is done by XL Release or by another tool.

![Add a Manual task](../images/add-task.png)

A quick and easy way to get started is to add each task in your process as a Manual type, which only requires you to enter a task title. Then, after you finish modeling the process, you can go back through the template and [convert tasks](/xl-release/how-to/change-a-task-type.html) that represent work that is or can be automated, such as sending email notifications, running Jenkins jobs, and querying JIRA tickets.

As you add tasks, you can create [release variables](/xl-release/how-to/create-release-variables.html#create-a-release-variable-in-the-release-flow-editor) on the fly by typing them in `${variable}` format. Variables are useful for information such as product names, version numbers, environment names, and so on. You can also choose to add variables later, after you've modeled the whole process.

### Check out the table and planner views

As you model a release process, you can click at the top of the release flow editor to switch to the [table view](/xl-release/how-to/using-the-table-view.html), which is helpful for templates that contain many tasks.

![Table view](../images/release-table-view.png)

You can also use the [release planner](/xl-release/how-to/using-the-xl-release-planner.html) to visualize the duration of each phase and task.

![Planner view](../images/planner-default-sequence.png)

## Step 3 Do a dry run

After you finish modeling a release process as a template, click **New release** to do a dry run of a release. Enter a release name and click **Create** to create the release. The release is now _planned_; to start it, click **Start Release** and confirm that you're ready to start.

Click the first task in the first phase. You can choose to **Complete** it or to **Skip** it. If you skip the task, you'll be required to enter a comment explaining why the expected work was not done.

You can choose to **Fail** a task, which also requires you to enter a comment. This will put the release in a _failed_ state. You can resume the release by either restarting or skipping the failed task.

### Check out the release dashboard

The [release dashboard](/xl-release/how-to/using-the-release-dashboard.html) gives you an overview of useful information about a template or release. Select **Release dashboard** from the **Show** menu to see the dashboard for the release. You can click tiles in the dashboard to drill down into more detailed information.

### Finish the release

Select **Release flow** from the **Show** menu, then complete or skip the remaining tasks in the release. When the final task in the final phase is done, the release is complete.

## Step 4 Set up security

In XL Release, there are two types of users:

* _Internal users_ that are managed by XL Release and can be added and removed by an XL Release administrator
* _External users_ that are maintained in an LDAP repository such as Active Directory

To get started with security, create an internal user account for yourself. Go to **User management** > **Users** and click **New user**. Enter a user name, email address, and password, then click **Save**.

**Note:** Prior to XL Release 6.0.0, select **Settings** > **Users**.

### Create a role

XL Release has a [role-based security system](/xl-release/how-to/configure-roles.html). You assign each user a role, and then assign global permissions to the role.

Go to **Settings** > **Roles** and click **New role**. Click the role name and enter _Administrators_. Then, click **Add...** under **Principals** and type the user name that you just created for yourself. Don't forget to click **Save** to save your changes.

### Assign permissions to the role

Now you can assign [global permissions](/xl-release/how-to/configure-permissions.html) to the role that you created. Go to **Settings** > **Permissions** and select all permissions next to the _Administrators_ role. Don't forget to click **Save** to save your changes.

![Global permissions](../xl-release/images/global-permissions.png)

Now, click the **Help** menu at the top right and select **Logout**, then log back in with the user name and password that you created for yourself.

### Configure a team on your template

In addition to global security, each template and release has its own permissions that you assign to _teams_. A team is made up of roles and/or individual users.

Go to **Releases** > **Templates** and click **View** under the template that you created earlier. Select **Teams** from the **Show** menu.

There are always two built-in teams on a template: the _Template Owner_ team and the _Release Admin_ team. Click **Add a member...** next to each one and add the _Administrators_ role.

Now you can assign [template-level](/xl-release/how-to/create-a-release-template.html#template-security) and [release-level](/xl-release/how-to/configure-release-teams-and-permissions.html) permissions to the teams. Select **Permissions** from the **Show** menu and assign permissions as follows:

* Template permissions
    * Template Owner: Create Release, View Template, Edit Template, Edit security
    * Release Admin: View Template
* Release permissions:
    * Template Owner: None
    * Release Admin: View Release, Edit Release, Edit Security, Start Release, Abort Release, Edit Task, Reassign Task  

## Step 5 Check out reports

XL Release has a variety of built-in reports that help you analyze your level of release automation and identify bottlenecks in your release processes.

Click **Reports** in the top menu bar and check out each type of report. You should see the release that you completed earlier.

## What's next

Now that you've gone through some of the basics of XL Release, you can:

* [Configure an SMTP server](/xl-release/how-to/configure-smtp-server.html) so that XL Release can send [email notifications](/xl-release/concept/notifications-in-xl-release.html) to users
* [Connect to your XL Deploy server](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html) so you can execute deployments as part of your release pipelines
* [Get external users](/xl-release/how-to/configure-ldap-security-for-xl-release.html) from your LDAP solution
* [Create global variables](/xl-release/how-to/configure-global-variables.html) that can be used in all templates and releases
* Read about the [calendar view](/xl-release/how-to/using-the-calendar-view.html), which provides an overview of all planned and running releases
