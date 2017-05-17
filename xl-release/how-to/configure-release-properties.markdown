---
title: Configure release properties
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- properties
weight: 413
---

To specify metadata for a release or template, select **Properties** from the **Show** menu to go to the release properties page.

![Release properties](../images/release-properties.png)

To configure release properties:

1. In the **Release Name** box, enter the name of the release. If the release is running, you can change its name.
1. In the **Description** box, optionally enter detailed information about the release. This field allows [Markdown](/xl-release/how-to/use-markdown-in-xl-release.html).

    Under the description, you can see links to the template that the release is based on and the release that [started it](/xl-release/how-to/create-a-create-release-task.html) (if applicable).

1. Next to **Flag status**, optionally select a yellow (attention needed) or red (release at risk) icon and enter a status message. Flagged releases are highlighted in overviews.
1. In **Start date** and **Due date**, set the *planned* start and end dates of the release. XL Release uses these dates to show the release on the calendar. These are not the dates that the release actually started or ended.

    **Note:** You can set a release to start automatically at the specified start date by enabling the **Start automatically on selected date** checkbox. You can override this setting by manually starting the release. The default state of the **Start automatically on selected date** checkbox is disabled.

1. Select the person who is responsible for the release from the **Release Owner** list. This person will receive additional [notifications](/xl-release/concept/notifications-in-xl-release.html) if tasks fail or are flagged.

    Release owners are automatically added to the Release Admin team when the release is created. This team has all [permissions](/xl-release/how-to/configure-permissions-for-a-release.html) on the release.

1. In **Run automated tasks as user** and **Password**, enter the user name and password of the XL Release user account that should be used to execute scripts in this release.
1. If you want the release to be aborted when a task fails, select **Abort on failure**.
1. Next to **Tags**, optionally add tags to the release to make it easier to find in the release overview.
1. Optionally add attachments by clicking **Choose File** next to **Attachments**. To delete an attachment, click the **X** next to it.
1. To publish a link that you can add to a calendar application such as Microsoft Outlook or Apple iCal, select **Publish link**. If the release dates change, the event is automatically updated in your calendar.

    **Important:** Everyone who can reach the XL Deploy server can access this link, which may be a security concern.

1. To download an ICS file that you can open with a calendar application, click **Download calendar event**.
1. Under **Release Variables**:
    * If you are using XL Release 4.8.0 or later, click [**Variables Screen**](/xl-release/how-to/create-release-variables.html) to enter values for the variables that have been defined in the release.
    * If you are using XL Release 4.7.x or earlier, enter values for the variables that have been defined in the release. You must provide values for all variables, or any task that contains a variable without a value will fail.
1. Click **Save** to save your changes to the release properties. Changes are not saved automatically.

To discard your changes without saving, click **Reset**.
