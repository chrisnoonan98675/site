---
title: Define a job schedule
no_index: true
---

To detect configuration drifts on a target system in XL Deploy, you must define and run a job schedule. The job schedule monitors the target system for any changes and creates a drift report when any configuration drift is detected.

To create a new job schedule:

1. Click **Jobs** in the top menu.
1. Click **Job Schedules** the left pane.

    You see a list of existing job schedules
    ![Job Schedules](images/job-schedule.png)

1. Click the **New** button.

    ![Job Schedule Detail](images/job-schedule-detail.png)

1. In the required fields, specify the name, group, description, cron expression, user, and password for the job schedule.
1. Select a defined job type from the **Script Job Type** drop-down list.
1. Click **Save**.

**Note** Select one or more jobs from the list and click **Resume** to run the jobs or **Pause** to pause them. You can also edit an existing job schedule by selecting it and clicking **Edit**.

To delete one or more job schedules from the list, select them and then click **Delete**.
