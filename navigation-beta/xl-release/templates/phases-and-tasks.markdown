---
title: Work with phases and tasks
---

In XL Release, the phases in a template or release represent blocks of work that happen in succession. To add a phase to a template or release, use the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html); it shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

In XL Release, the activities in a template or release are modeled as tasks, which are logically grouped in phases. To add a task to phase to a template or release, use the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html); it shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

## Work with phases

### Add a phase

To add a phase:

1. In the template or release, select **Release Flow** from the **Show** menu.
1. Click **Add Phase**. A new phase with title 'New Phase' appears.

    ![New Phase](/xl-release/images/new-phase.png)

1. Click the phase title to change it.
1. Optionally move the phase by dragging it to the desired position.

    **Tip:** You can expand or collapse a phase by clicking the arrow in the header.

1. Optionally select a different color for the phase.
1. Click ![Phase details icon](/xl-release/images/phase-details-icon.png) to view a description of the phase and its start date, due date, and duration.

     XL Release uses the dates and duration when displaying the phase in the planner and calendar; if they are not consistent with the release dates (as defined in the [release properties](/xl-release/how-to/configure-release-properties.html)), a warning appears.

    ![Phase details popup](/xl-release/images/phase-details-popup.png)

### Copy a phase

To copy a phase, click ![Duplicate icon](/xl-release/images/duplicate-icon.png). This is useful during modeling when you have similar or identical phases. When you duplicate a phase that is active and has running and completed tasks, the tasks in the new phase are all in the *planned* state.

### Delete a phase

To delete a phase, click ![Delete icon](/xl-release/images/delete-icon.png). Note that you cannot delete a completed or active phase of a running release.

## Work with tasks

### Add a task to a phase

To add a task to a phase:

1. In the template or release, select **Release Flow** from the **Show** menu.
1. Click **Add task** at the bottom of the phase.

    ![New Phase](/xl-release/images/add-task.png)

1. Click the task title to change it.
1. Select the task type.
1. Click **Add** to create the task and add it to the phase.
1. Optionally move the task by dragging it to the desired position. You can also drag the task to another phase.

    ![Task in release flow editor](/xl-release/images/task-in-release-flow-editor.png)

1. Click the task to edit its details.

In a task:

* An orange triangle indicates that the task is currently active in the release (if the release is active).
* If the task is manual, the user or group to whom the task is assigned appears at the lower left. If the task is automated, the type of script appears at the lower left (for example, *Webhook: JSON webhook*).
* At the upper right, you can see:
    * An icon that indicates the task type
    * The current state of the task (if the release is active)
    * An alert icon, if action is needed
* The task due date appears at the lower right (if a date is set)

### Add a task to a parallel or sequential group

[Parallel Groups](/xl-release/how-to/create-a-parallel-group.html) and [Sequential Groups](/xl-release/how-to/create-a-sequential-group.html) are containers for other tasks.

Within a Parallel Group, all tasks are started simultaneously. The Parallel Group task finishes when all of its children are complete.

Within a Sequential Group, tasks are executed in order. The Sequential Group task finishes when its last child is complete. The Sequential Group task is available in XL Release 5.0.0 and later.

To add a task to a task group, click **Add task** at the bottom of the group. To move a task into a task group, drag and drop it. To collapse or expand a task group, click its arrow.

![Add Parallel Group task](/xl-release/images/add-parallel-task.png)

### Set a precondition on a task

You can control the execution flow by setting a precondition on a task. A precondition is an `if` statement for tasks, written in Jython script code.

If the precondition evaluates to **True**, the task is started. If the precondition evaluates to **False**, the task is skipped. If an exception is raised or a compilation error occurs when XL Release is evaluating the precondition script, the task is **failed**. You can fix the script and **retry** the task.

![Manual Task With Precondition](/xl-release/images/manual-task-details.png)

There are two ways of writing preconditions.

#### Boolean expression

A Boolean expression is restricted to single statement script. For example:

    releaseVariables['jobStatus'] == 'Success'

The task will only be started if the variable `jobStatus` is equal to 'Success'.

#### Multi-line script

If you need a more complicated script, you must set the **result** variable. For example:

    print("Executing precondition")

    ...

    result = True

#### Disabling preconditions

You can disable precondition on certain task types. For example, to disable precondition on parallel groups, modify `synthetic.xml` as follows:

    <type-modification type="xlrelease.ParallelGroup">
        <property name="preconditionEnabled" kind="boolean" required="true" default="false" hidden="true"
            description="Whether preconditions should be enabled"/>
    </type-modification>

### Add comments to a task

In the XL Release GUI, you can add comments to any [type of task](/xl-release/concept/types-of-tasks-in-xl-release.html) in a template or release by opening the task and clicking **Add comment**.

You can also use the [REST API](/xl-release/latest/rest-api/index.html) and [Jython API](/jython-docs/#!/xl-release/4.8.x/) to automatically add comments to tasks; for an example, refer to [Using the XL Release API in scripts](/xl-release/how-to/using-the-xl-release-api-in-scripts.html).

The **Comments** heading in a task shows the number of comments on the task.

#### Comment size limit

To prevent performance issues, comments are limited to 32,768 characters. If you manually add a comment that exceeds the limit, XL Release will truncate the comment. If a [Script task](/xl-release/how-to/create-a-script-task.html) tries to add a comment that exceeds the limit, XL Release will truncate the comment and attach the full output of the script to the task.

You can change the comment size limit for each task type in the `XL_RELEASE_SERVER_HOME/conf/deployit-defaults.properties` file. For example:

    #xlrelease.Task.maxCommentSize=32768

To change the limit, delete the number sign (`#`) at the beginning of the relevant line, change the limit as desired, then save the file and restart the XL Release server.

### Edit a task

To edit a task in a template or release, go to the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) and click the task.

#### Editing task fields

To edit a field in a task, click it and make the desired changes, then press ENTER or click outside the field to finish editing. In large text fields, you can press ENTER to start a new line and click the checkmark icon to save.

To cancel your changes, press ESC. To cancel your changes in a large text field, click the round cancel icon.

Your edits to the task details are saved immediately.

#### Title and description

The task title appears in the top colored bar. To change the title, click it.

Below the task title, you can see the release and phase that the task belongs to. In this example, the release is "Consumer Portal 3.2" and the phase is "Release Deployit". Click the release name to go to the Release Flow editor.

![Edit Task Description](/xl-release/images/edit-task-description.png)

To edit the task description, click it. Use the description to describe the purpose of the task and instructions on how to complete it.

XL Release supports the Markdown syntax for styled text (headers, bold text, italicized tex, hyperlinks, bulleted lists, and so on). Refer to [Use Markdown in XL Release](/xl-release/how-to/use-markdown-in-xl-release.html) for more information.

**Tip:** Use hyperlinks to refer to documents published elsewhere, such as on a wiki or SharePoint server.

#### Task status and transitions

Use the following buttons to indicate that something has happened on the task (only available when the task is assigned to you):

* **Complete**: The task is done. You can optionally add a comment about the completion of the task. After completion, XL Release advances to the next task in the phase and sends the appropriate notifications.

* **Skip**: No work was needed or could be done, and you have moved on to another task. You can use this option for tasks that are not relevant to the release. You are required to enter a comment explaining why you skipped the task. XL Release marks the task as complete and moves on to the next task.

* **Fail**: An unforeseen event is impeding the completion of the task or you do not know how to complete the task. This stops the release flow, and XL Release notifies the [release owner](/xl-release/concept/core-concepts-of-xl-release.html).

#### Task attachments

The **Attachments** section is located below the comments.

![Task attachments](/xl-release/images/task-attachments.png)

#### Task properties

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| Status | To signal that the timely completion of a task is at risk or that you need some help, without failing the task, use the **Status** flags in the upper right corner of the task properties. The status flag appears in all overviews to alert the release manager and other users. |
| Scheduled start date | Use the scheduled start date to schedule a task to be performed at an exact time. For example, a deployment with [XL Deploy](/xl-release/how-to/create-an-xl-deploy-task.html) that must start at midnight or an email must be sent to all stakeholders at 9:00 AM on Monday.<br /><br />Note that the task will _not_ start on the scheduled start date if it is not yet active in the flow. All previous tasks must be complete before a scheduled task is started and the scheduled start date is reached. If the previous tasks are completed after the scheduled start date, the task will start the moment it becomes active.<br /><br />Click the scheduled start date to select a date and time. When executing the release flow, XL Release will wait until this time (if set) to execute the task. While waiting, the task is marked as "Pending".<br /><br />Dates in XL Release are displayed using the clients operating system's timezone. Dates and times are formatted according to the browser's language settings. |
| Due date | Set a due date on a task to mark the time that the task must be completed. |
| Duration | Instead of explicitly setting the due date, you can use the duration property to indicate how long a task should last.<br /><br />**Note:** If no dates or duration are set, the "inferred" dates and duration appear in grey. |
| Assigned to | This section indicates the user who is the owner of the task and who is responsible for completing it. It also indicates the [team](/xl-release/how-to/configure-teams-for-a-release.html) that the task is assigned to. Teams are a way to group users who are involved in the release and who have the same role. For example, you could have a DEV team, QA team and an OPS team. During planning, it can be useful to assign a task to a team because you do not know in advance who will participate in a certain release. If a task is assigned to a team but not to a user, all team members receive an email when the task becomes active.<br /><br />To remove an assignment to a user or a team, click the cross icon. |

#### Using Markdown

XL Release allows your to style your text using Markdown syntax. Markdown is a way to indicate headers, hyperlink, italics, and so on in your text. In XL Release, markdown is supported in task descriptions and comments.

This is a brief overview of the most common Markdown commands.

##### Headers

When you start a line with one or more `#` characters, it becomes a header.

	# Header 1

	## Header 2

	###### Header 6

##### Hyperlinks

The simplest way to create a clickable hyperlink is to enter a web site address starting with `http://` or `https://`.

    Visit our documentation at https://docs.xebialabs.com/xl-release/

You can also create a link text as follows:

    Please visit [the XebiaLabs website](http://xebialabs.com/)

##### Bold and italic

To italicize text, surround it with a single underscore (`_`). For bold, use a double asterisk (`*`).

    Choose between **bold** and _italic_.

##### Lists

To create a bulleted list, begin each list item with an asterisk (`*`) followed by a space.

    Bulleted list:

    * One item
    * Another item

To create a numbered list, begin each list item with a number and a period (`.`). The numbering does not have to be exact, Markdown will calculate the proper order for you.

    Numbered list:

    1. First item
    2. Second item
    10. Last item

##### More information

Markdown has more options. For a full explanation, refer to [the Markdown guide by John Gruber](http://daringfireball.net/projects/markdown/syntax).

### Change a task's type

In XL Release 4.8.0 and later, you can change the [type of a task](/xl-release/concept/types-of-tasks-in-xl-release.html) in:

* A template
* A planned release that has not started yet
* An active release, if the task that you want to change has not started yet

To change the type of a task in a template, you need the **Edit Template** permission on the template. To change the type of a task in a release, you need the **Edit Task** permission on the release.

To change a task's type:

1. Open the template or release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).
1. Click ![Task action menu](/images/menu_three_dots.png) on the task you want to change.
1. In the menu, hover over **Change type**.
1. Select the desired task type.

    XL Release will copy the values of properties that are shared between the task's previous type and the type that you selected.

Note that:

* You cannot change the type of an active, completed, failed, or skipped task.
* You can change a task to a Parallel or Sequential Group, but you cannot change a Parallel or Sequential Group to a different type of task.

### Copy a task

To copy a task, click the button in the upper right corner of the task. You cannot copy tasks that are completed.

### Delete a task

To delete a task, click the button in the upper right corner of the task. In an active release, you can only remove tasks that are planned (that is, tasks that are not in progress).
