---
title: Configure release properties
categories:
- xl-release
subject:
- Releases
tags:
- release
- properties
---

In a release or template, select **Properties** from the **Show** menu to go to the release properties page, where you can specify metadata for the release.

![Release properties](../images/release-properties-part-1.png)
![Release properties](../images/release-properties-part-2.png)

To configure release properties:

1. In the **Release Name** box, enter the name of the release. If the release is running, you can change its name.
2. Next to **Flag status**, optionally select a yellow (attention needed) or red (release at risk) icon and enter a status message. Flagged releases are highlighted in overviews.
3. In **Start date** and **Due date**, set the *planned* start and end dates of the release. XL Release uses these dates to show the release on the calendar.

    **Note:** These are not the dates that the release actually started or ended.

4. Select the person who is responsible for the release from the **Release Owner** list. This person will receive additional [notifications](/xl-release/concept/notifications-in-xl-release.html) if tasks fail or are flagged.

    Release owners are automatically added to the Release Admin team when the release is created. This team has all [permissions](/xl-release/how-to/configure-permissions-for-a-release.html) on the release.

5. In **Run release as user** and **Password for user that runs the release**, enter the user name and password of the XL Release user account that should be used to execute scripts.
6. If you want the release to be aborted when a task fails, select **Abort on failure**.
7. In the **Description** box, optionally enter detailed information about the release. This field allows [Markdown](/xl-release/how-to/use-markdown-in-xl-release.html).
8. Optionally add attachments by clicking **Choose File** next to **Attachments**. To delete an attachment, click the **X** next to it.
9. Optionally add tags to the release to make it easier to find in the Release Overview.
10. To publish a link that you can add to a calendar application such as Microsoft Outlook or Apple iCal, select **Publish link**. If the release dates change, the event is automatically updated in your calendar.

    **Important:** Everyone who can reach the XL Deploy server can access this link, which may be a security concern.

11. To download an ICS file that you can open with a calendar application, click **Download calendar event**.
12. Under **Variables**, enter values for the variables that have been defined in the release. You must provide values for all variables, or any task that contains a variable without a value will fail.

    Auto-completion is available for deployment packages and environments used in XL Deploy tasks.

13. Click **Save** to save your changes to the release properties. Changes are not saved automatically.

To discard your changes without saving, click **Reset**.
