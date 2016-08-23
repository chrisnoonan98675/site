---
title: Release your software
---

## The release life cycle

A release can go through various stages. First a blueprint of a release is defined as a *template*. From the template, a *planned release* is created. The release is a copy of the template, but it has not started yet. When it is started, the release becomes *active* and the phases and the tasks are executed. When all is done, the release is *completed*.

This is a detailed breakdown of the states that a release can go through.

![Release life cycle](../images/release_lifecycle.png)

All releases are derived from a template (it can be an empty template), so the first state of a release is *template*.

When a release is created from a template, a copy is made from the template and the release is *planned*.

When the release starts, it enters the *in progress* state. Phases and tasks are started and notifications are sent to task owners to pick up their tasks.

The state of an *active* release is reflected in the state of the current task. In the case of multiple tasks running in a parallel group, the state of the topmost parallel group is used.

* *In progress*: The current task is in progress.
* *Failed*: The current task has failed. The release is halted until the task is retried.
* *Failing*: Some tasks in a parallel group have failed, but other tasks are still in progress.

You can restart a phase when the release is any active state ('in progress', 'failed', or 'failing'). After a phase is restarted, the release is in 'paused' state. This is a state in which no tasks are active. It is similar to the 'planned' state, and allows the release owner to change due dates and other task variables before the release is resumed.

There are two end states: *completed* and *aborted*. When the last task in a release finishes, the release enters the 'completed' state. A release can be manually aborted at any point, at which point it will be in the 'aborted' state.

### Running release

In a running release, XL Release starts planned tasks in the correct order and executes them if they are automated, or sends a notification to the users who are responsible for their completion if they are not automated.

Generally, one or more tasks will be active at any given time. Users who have tasks assigned to them will find these tasks in their task overview. Users are expected to mark tasks as complete in XL Release when done.

If an active task is failed, the release is paused. The release owner must then decide whether to assign the task to another user or to skip it.

Also, the release may be stalled after a phase is restarted. In this situation, tasks are copied and may have erroneous start and due dates, or may be assigned to the wrong users. The release owner should configure the tasks correctly before carrying on.

## Create and start a release

### Manually create a release from a template

To create a release from a template, either:

* Click **New Release from Template** on the template overview
* Click **New Release** on the template when it is open in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html)

When you create a release from a template, it is first in a *planned* state. This is important because, before the release starts and users are notified by email that tasks are assigned to them, the release owner must:

1. Assign values to [variables](/xl-release/concept/variables-in-xl-release.html)
2. Populate the [release teams](/xl-release/how-to/configure-teams-for-a-release.html) or revise the members of the teams
3. Optionally set scheduled start and due dates on tasks
4. Revise dependencies on other releases (dependencies can only be set on active releases, so they are not specified in the template)

### Create a release from another release

The [Create Release task type](/xl-release/how-to/create-a-create-release-task.html) allows you to automatically create and start a release based on a configured template. You can use the Create Release task for several different release orchestration scenarios.

### Create a release from a trigger

Triggers are an automated way to create and run a release. A trigger is a kind of XL Release plugin that is executed periodically and can create and run a release from a template.

**Important:** A trigger will not trigger a release during its first run; it will wait until the next iteration.

To see the triggers on a template, select **Triggers** from the **Show** menu in the release flow editor. To configure a trigger:

1. Under **Settings**, select **Allow concurrent triggered releases** to allow releases that are started by the trigger's template to run concurrently instead of sequentially.

    For example, if this option is not selected and a trigger's template detects that many releases need to be started in a short time, XL Release will only run one triggered release at a time.

    ![Trigger settings](../images/triggers-settings.png)

1. Select a trigger type from the list under **Triggers list**.
1. You can now configure the trigger settings. For example, for a *Git: Poll* trigger:

    ![Trigger form](../images/git-plugin-fields.png)

1. In the **Title** box, enter a name that identifies the trigger.
1. In the **Release Title** box, enter a name for releases that will be created by this trigger. This field can contain [variables](/xl-release/concept/variables-in-xl-release.html).
1. Select a polling interval from the **Poll type** list. This can be a repeatable interval in seconds (for example, every 10 seconds) or a [cron](https://en.wikipedia.org/wiki/Cron#CRON_expression) expression.

    For important information about using cron expressions, refer to [Time zone for cron jobs](/xl-release/how-to/time-trigger-plugin.html#time-zone-for-cron-jobs).

1. In the **Poll interval** box, enter the number of seconds between each execution of the trigger or cron expression.
1. To make the trigger active, select **Enabled**.
1. Configure the remaining properties, which are specific to the trigger type.
1. Next to **Tags**, optionally add tags that will be added to releases created by the trigger. This field can contain variables.
1. Under **Template Variables**, define values to use for template variables in releases created by the trigger.

    By default, all template variables with the same name as a trigger variable are automatically bound. For example, a template variable named `commitId` will have the value `${commitId}`, which will be replaced by the corresponding value of the trigger variable. You can also specify static values or other variables.

1. Click **Save** to save the configuration and return to the triggers page.

    ![Trigger list](../images/triggers-list.png)

**Note:** All releases created by a trigger are tagged with the ID of the trigger. You can find all releases created by a trigger by clicking **Releases linked to this trigger**.

#### Other trigger options

You can create a [custom trigger](custom-trigger.html) or use one of the trigger plugins:

* [Git trigger](git-trigger.html)
* [Nexus trigger](nexus-trigger.html)
* [Subversion (SVN) trigger](svn-trigger.html)
* [Time trigger](time-trigger.html)

## Create a release from the REST API

If you want to automate XL Release—for example, to trigger a release or pipeline run from an upstream system—the [XL Release REST API](/xl-release/latest/rest-api/) is good way to do so. This topic describes how to start a new release based on a release template using [cURL](http://curl.haxx.se/docs/manpage.html).

### Get the template ID

First, you need to know the release template ID. You can easily find this in the URL for the template itself.

![URL for template](../images/template-release-id.png)

### Start a release from the template

The [`/api/v1/templates/{templateId:.*?}/start`](/xl-release/4.6.x/rest-api/#!/templates/start) API call creates a release from a template and immediately starts it. For the simple template in this example, the cURL command to create the release would be:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release2994650/start -i -X POST -d '{"releaseTitle": "My Automated Release"}'

**Note:** In API calls, the release template ID is always prepended with `Applications/` for technical reasons. For more information about template IDs, refer to [How to find IDs](/xl-release/how-to/how-to-find-ids.html).

The response to the API contains the ID of the release that was created. For example:

    {"id":"Release1624834", ...}

**Tip:** In XL Release 4.8.0 and later, you can use the [`/api/v1/templates/{templateId:.*Release[^/]*}/create`](/xl-release/4.8.x/rest-api/#!/templates/create) API call to create a release without immediately starting it. You can then use the [`/api/v1/releases/{releaseId:.*Release[^/]*}/start`](/xl-release/4.8.x/rest-api/#!/releases/start) call to start the planned release.

#### Start a release with variables

A more complex example that includes the setting of variables would look like this:

    curl -u 'admin:admin' -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release612654/start -i -X POST -d '{"releaseTitle": "My Automated Release", "releaseVariables": {"${version}": "1.0", "${name}": "John"}}'

### Security when using cURL

To explicitly allow cURL to perform "unsecure" SSL connections and transfers, add this to the cURL command:

    -sslv3 -k

To use a certificate, add this to the cURL command:

    -sslv3  --cacert /path/to/certificate

If you created a keystore during the setup of XL Release, use this command to extract the certificate:

    keytool -exportcert -rfc -alias jetty -keystore conf/keystore.jks -file conf/cert.crt

## Scheduling releases

In XL Release, you can schedule your releases by setting start dates and times, end dates and times, and durations on templates, releases, phases, and tasks. When you set dates and durations on phases and tasks, XL Release automatically adjusts other phases and calculates the release duration and end date.

#### Default durations

By default, XL Release assigns these durations to tasks:

* One hour for manual tasks
* One minute for automated tasks

#### Viewing and setting dates and durations

When you select the **Show dates** option in the [planner](/xl-release/how-to/using-the-xl-release-planner.html):

* Calculated dates and durations appear in italicized gray text
* Dates and durations that are explicitly set appear in black text, and can be cleared by clicking **X** next to them
* Actual execution dates and times in a running release appear in regular gray text

In the planner, you can adjust the dates or duration of a phase or task by dragging its left or right edge.

In the details of a [phase](/xl-release/how-to/add-a-phase-to-a-release-or-template.html) or [task](/xl-release/how-to/add-a-task-to-a-phase.html):

* Calculated dates and durations appear in regular gray text
* Dates and durations that are explicitly set appear in black text, and can be cleared by clicking **X** next to them
* Actual execution dates and times in a running release appear in regular black text

You can adjust the dates or duration of a phase or task in its detail view.

#### Controlling when phases and tasks start

If you explicitly set a start date on a phase or task, you can use the **Wait for start date** option in the details view to either:

* Wait to start the phase or task until the configured date and time, even if the release reaches the phase or task before then (this is the default)
* Allow the phase or task to start when the release reaches it, even if this is earlier than the configured date and time

#### Dates and times in exported release data

When you [export a template or release to Excel](/xl-release/how-to/using-the-release-flow-editor.html), the exported `.xlsx` file only contains the start dates and due dates that you set explicitly; this is because other dates and times are calculated in the browser. When you export a running release, the `.xlsx` file will also contain the actual start and end dates and times of completed phases and tasks.

## Using the planner

The XL Release planner allows you to use an interactive Gantt chart to view and edit the timing of the phases and tasks in a release or template. The Gantt chart is a combined timeline of the template or release, its phases, and the tasks within.

To access in the planner in XL Release 5.0.0 or later, click **Planner** at the top of the release flow page. In earlier versions of XL Release, select **Planner** from the **Show** menu.

![Planner: phases overview](../images/planner-phases.png)

Tasks are executed in sequential order within a phase. Therefore, in the example below, Task 2 will not start until Task 1 is complete, as indicated by the line that connects them.

![Planner: default sequence](../images/planner-default-sequence.png)

### Editing tasks and phases in the planner

When editing a task or a phase in the planner, you can:

* Move it by dragging it to a new position
* Set its duration by dragging its right edge
* Set its scheduled start date by dragging its left edge
* Alternatively, set the dates and duration by clicking **Show dates** and then setting them explicitly

![Planner: sequence with start and dates](../images/planner-date-picker.png)

When you set the scheduled start date or duration of a task, the planner will automatically adjust subsequent tasks in the same phase. For more information about dates and durations, refer to [Scheduling releases](/xl-release/how-to/scheduling-releases.html)

**Tip:** Double-click a task in the planner to open its detail view.
