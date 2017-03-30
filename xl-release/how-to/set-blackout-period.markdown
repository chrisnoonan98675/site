---
title: Set blackout period
categories:
- xl-release
subject:
- Calendar
tags:
- blackout
- period
- calendar
since:
- XL Release 6.2.0
---

XL Release allows you to set a start date and an end date to ensure that specific tasks cannot be performed during the set blackout period.
To create, edit, or delete a blackout period, you must have the *Admin* or *Edit blackout period* [global permissions](/xl-release/how-to/configure-permissions.html).

To create a new blackout period:

1. Select **Releases** > **Calendar** from the top bar (in XL Release 5.0.x and earlier, click **Calendar** in the top bar).
1. Click ![Edit calendar day](/images/icon_edit_calendar_day.png) in any day on the calendar.
1. Select **Set blackout period**.
1. In the **Label** box, specify a name for the blackout period.
1. Select a start date and time for the blackout period.
1. Select an end date and time for the blackout period.
1. Click **Save**.

You can see all the blackout periods in the [calendar view](/xl-release/how-to/using-the-calendar-view.html). On the calendar, the blackout period appears as a red hashed area.

![image](/images/blackout.png)

## Tasks in blackout period

All the tasks can be prevented from starting during a blackout period. You can do this by postponing each task until after the blackout period.

**Note** If a task is already running, it cannot be postponed.

To postpone a task:

1. Go to the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) and click the task.
1. Under **Scheduled start date**, click the **Postpone during blackout period** checkbox.

![image](/images/postpone-task.png)

The task scheduled start date is reset to 1 minute after the set end date of the blackout period.

If a task is postponed during blackout period, you can manually override the setting and start the task immediately.

## Edit or delete blackout period

To edit or delete a blackout period, select it in the [calendar view](/xl-release/how-to/using-the-calendar-view.html), and then click **Edit** or **Delete**.

If a task is set to **Postpone during blackout period** and you delete de blackout period, the task retains the current scheduled start date.
