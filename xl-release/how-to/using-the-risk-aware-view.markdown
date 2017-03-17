---
title: Using risk awareness in XL Release  
categories:
- xl-release
subject:
- Risk Awareness
tags:
- release
- risk aware
- flag
since:
- XL Release 6.2.0
---

XL Release calculates a risk level for each release based on different factors such as flags, failed or failing states, or due dates. This allows you to see which releases have a high risk level so you can take the appropriate actions.

You can see if a release has a high risk level in the [release overview](/xl-release/how-to/using-the-release-overview.html). An icon next to the release indicates the risk level. Click the icon to see the detailed risk information.

![image](/images/at-risk.png) - Shows **At risk** state, the reason (Example: `Release has tasks flagged as red.`), and a message (Example: `Task name: Flag comment`)

![image](/images/attention-needed.png) - Shows **Attention needed** state, the reason (Example: `Release has tasks flagged as amber.`), and a message (Example: `Task name: Flag comment`)

![image](/images/on-track.png) - Shows **On track** state - The release is on track.

The risk assessors used to calculate the risk level of a release are the following:

* Release flags: Red or Amber
* Release state: Failed or Failing
* Task flags: Red or Amber
* Release past due date
* Current task past due date
* Retries for a failed task

To order the release overview by risk level, click **Order by** and select **Risk**.

The release overview shows the releases with the highest risk level at the top.
