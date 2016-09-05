---
title: Create and start releases
---

## Manually create a release from a template

To create a release from a template, either:

* Click **New Release from Template** on the template overview
* Click **New Release** on the template when it is open in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html)

When you create a release from a template, it is first in a *planned* state. This is important because, before the release starts and users are notified by email that tasks are assigned to them, the release owner must:

1. Assign values to [variables](/xl-release/concept/variables-in-xl-release.html)
2. Populate the [release teams](/xl-release/how-to/configure-teams-for-a-release.html) or revise the members of the teams
3. Optionally set scheduled start and due dates on tasks
4. Revise dependencies on other releases (dependencies can only be set on active releases, so they are not specified in the template)

## Create a release from another release

The [Create Release task type](/xl-release/how-to/create-a-create-release-task.html) allows you to automatically create and start a release based on a configured template. You can use the Create Release task for several different release orchestration scenarios.

## Create a release from an archived release

To start a release from an archived release:

1. In XL Release, select **Releases** > **Overview** from the top bar.
1. Find the archived release. Note that you may need to adjust the [filter options](/xl-release/how-to/using-the-release-overview.html#filtering-the-release-overview) if completed releases aren't shown.

    ![The release overview screen](/xl-release/images/start-a-release-from-an-archived-release-1.png)

1. Click the release title to open it.
1. Select **Variables** from the **Show** list and take note of the values of the variables that were used when the release was run.

    ![The Properties page of the archived release](/xl-release/images/start-a-release-from-an-archived-release-3.png)

1. Select **Properties** from the **Show** list.

    ![The Properties page of the archived release](/xl-release/images/start-a-release-from-an-archived-release-4.png)

1. Click the **Created from template** link to open the [template](/xl-release/how-to/create-a-release-template.html) from which the release was originally created.

    ![The template that was used to start this release](/xl-release/images/start-a-release-from-an-archived-release-5.png)

1. On the template, click [**New Release**](/xl-release/how-to/start-a-release-from-a-template.html).

    ![The new release](/xl-release/images/start-a-release-from-an-archived-release-6.png)

1. Enter the values of the variables from the archived release.

    ![The recreated release](/xl-release/images/start-a-release-from-an-archived-release-7.png)

1. Click **Create** to create the release.

## Create a release from a trigger

Triggers are an automated way to create and run a release. A trigger is a kind of XL Release plugin that is executed periodically and can create and run a release from a template.

**Important:** A trigger will not trigger a release during its first run; it will wait until the next iteration.

To see the triggers on a template, select **Triggers** from the **Show** menu in the release flow editor. To configure a trigger:

1. Under **Settings**, select **Allow concurrent triggered releases** to allow releases that are started by the trigger's template to run concurrently instead of sequentially.

    For example, if this option is not selected and a trigger's template detects that many releases need to be started in a short time, XL Release will only run one triggered release at a time.

    ![Trigger settings](/xl-release/images/triggers-settings.png)

1. Select a trigger type from the list under **Triggers list**.
1. You can now configure the trigger settings. For example, for a *Git: Poll* trigger:

    ![Trigger form](/xl-release/images/git-plugin-fields.png)

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

    ![Trigger list](/xl-release/images/triggers-list.png)

**Note:** All releases created by a trigger are tagged with the ID of the trigger. You can find all releases created by a trigger by clicking **Releases linked to this trigger**.

### Other trigger options

You can create a [custom trigger](custom-trigger.html) or use one of the trigger plugins:

* [Git trigger](git-trigger.html)
* [Nexus trigger](nexus-trigger.html)
* [Subversion (SVN) trigger](svn-trigger.html)
* [Time trigger](time-trigger.html)

## Create a release from the REST API

If you want to automate XL Release—for example, to trigger a release or pipeline run from an upstream system—the [XL Release REST API](/xl-release/latest/rest-api/) is good way to do so. This topic describes how to start a new release based on a release template using [cURL](http://curl.haxx.se/docs/manpage.html).

### Get the template ID

First, you need to know the release template ID. You can easily find this in the URL for the template itself.

![URL for template](/xl-release/images/template-release-id.png)

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

![Planner: phases overview](/xl-release/images/planner-phases.png)

Tasks are executed in sequential order within a phase. Therefore, in the example below, Task 2 will not start until Task 1 is complete, as indicated by the line that connects them.

![Planner: default sequence](/xl-release/images/planner-default-sequence.png)

### Editing tasks and phases in the planner

When editing a task or a phase in the planner, you can:

* Move it by dragging it to a new position
* Set its duration by dragging its right edge
* Set its scheduled start date by dragging its left edge
* Alternatively, set the dates and duration by clicking **Show dates** and then setting them explicitly

![Planner: sequence with start and dates](/xl-release/images/planner-date-picker.png)

When you set the scheduled start date or duration of a task, the planner will automatically adjust subsequent tasks in the same phase. For more information about dates and durations, refer to [Scheduling releases](/xl-release/how-to/scheduling-releases.html)

**Tip:** Double-click a task in the planner to open its detail view.
