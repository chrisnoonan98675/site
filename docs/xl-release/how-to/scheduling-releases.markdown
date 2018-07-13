---
title: Scheduling releases
categories:
- xl-release
subject:
- Releases
tags:
- release
- planner
- schedule
weight: 421
---

In XL Release, you can schedule your releases by setting start dates and times, end dates and times, and durations on templates, releases, phases, and tasks. When you set dates and durations on phases and tasks, XL Release automatically adjusts other phases and calculates the release duration and end date.

## Controlling when releases start

Since XL Release 7.0.0 you can schedule any planned release to start automatically at a given date and time in the future.

By enabling the **Start automatically on selected date** checkbox on the [release properties page](/xl-release/how-to/configure-release-properties.html) the release will get scheduled to start on the specified **Start date**.

If the checkbox is not enabled, the release will require a manual start; this is the default behaviour.

**Note:** To set a release to start automatically when being created from the API or the XLR DSL, adjust the **autoStart** and **scheduledStartDate** properties to the desired values.

## Controlling when phases and tasks start

If you explicitly set a start date on a phase or task, you can use the **Wait for start date** option in the details view to either:

* Wait to start the phase or task until the configured date and time, even if the release reaches the phase or task before then (this is the default)
* Allow the phase or task to start when the release reaches it, even if this is earlier than the configured date and time

## Viewing and setting dates and durations in the planner

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

## Default durations

By default, XL Release assigns these durations to tasks:

* One hour for manual tasks
* One minute for automated tasks

## Dates and times in exported release data

When you [export a template or release to Excel](/xl-release/how-to/using-the-release-flow-editor.html), the exported `.xlsx` file only contains the start dates and due dates that you set explicitly; this is because other dates and times are calculated in the browser. When you export a running release, the `.xlsx` file will also contain the actual start and end dates and times of completed phases and tasks.
