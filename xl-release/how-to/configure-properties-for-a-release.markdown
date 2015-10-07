---
title: Configure properties for a release
categories:
- xl-release
subject:
- Releases
tags:
- release
- properties
---

In the release details, select **Properties** from the **Show** menu to go to the release properties page, which provides the metadata of the release.

![Release properties](../images/release-properties-part-1.png)
![Release properties](../images/release-properties-part-2.png)

The following information is available:

* **Release Name**: Change the name of the release (also allowed on a running release).

* **Flag status**: Optionally set a warning message on the release. You can set the alert to amber ("attention needed") or red ("release at risk"). A releases with such a status is highlighted in overviews so it is easy to see.

* **Start date** and **Due date**: Set the _planned_ start and end dates of the release. These dates are used to display the release on the calendar. Note that these are not the dates that the release _actually_ started or ended.

* **Release Owner**: The person who is responsible for the release and who will receive [notifications](#notifications) if tasks fail or are flagged as being in danger. Release owners are automatically added to the [Release Admin team](#admin-teams) when the release is created. This team has all security permissions on a release. Release owners can also use the **My Releases** filter option in the [Release overview](#filtering-releases).

* **Run Release As User** and **Password for user that runs the release**: The username and password of the XL Release user account that will be used to execute scripts.

* **Abort on failure**: If ticked, the release will be aborted when a task fails.

* **Description**: Provides detailed information about the release. To edit the text, click it. The text editor supports [Markdown syntax](markdownsyntax.html), so you can put styled text and hyperlinks here.

* **Attachments**: Lists file attachments on the release. To add an attachment, click **Choose File**. To delete an attachment, click the black cross on the right.

* **Tags**: Set tags on the release to make it easier to find in the release overview.

* **Share calendar**: View the release in a calendar application such as Microsoft Outlook or Apple iCal. The options are:
	* **Publish link**: Publish a link that you can add to a calendar application. When the release dates change, the event is automatically updated in your calendar. Note that everyone who can reach the XL Release server can access this link, which may be a security concern.
	* **Download calendar event**: Download an ICS file that you can open with a calendar application. 

* **Variables**: Lists the [Variables](#variables) that are defined in the release. Variables are placeholders that are filled in when the release starts. You can enter variables in most task fields by typing `${..}` around a word or phrase. You specify the values of variables on this page.

	**Note:** You must provide values for all variables, or any task that contains a variable without a value will fail.

Smart auto-completing is available for deployment packages and environments of a XL Deploy task. Start typing and XL Release will retrieve the list of deployment packages or environments that are available in XL Deploy.

Click **Save** to save your changes to the release properties. Changes are not saved automatically.

To discard changes that you've made without saving, click **Reset**.
