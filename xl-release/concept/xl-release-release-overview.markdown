---
title: XL Release release overview
categories:
- xl-release
subject:
- Releases
tags:
- release
- user interface
---

In XL Release, go to **Releases** > **Overview** to see the list of currently active releases.

![Release Overview](../images/release-overview.png)

The releases that you have permission to view and that are currently active appear. This includes releases that are planned, in progress and failed. 

Each release appears in a block. The first column contains the release title and the current phase (if the release is active).

The actions column contains shortcuts to actions that you can perform on a release:

* **View**: Open the release details screen on the release flow editor page.
* **Start**: Start the release; this is only available if the release is in the 'planned' state.
* **Abort**: Aborts the current release.

The status column shows where the release is in its [lifecycle](/xl-release/concept/release-life-cycle.html).

The **Start date** column shows the planned start date if the release is scheduled in the future or the actual start date if it has already started. The **End date** column shows the planned end date if the release is not complete or the actual end date otherwise. In both columns, an overdue date appears in red. **Duration** shows the duration of the release.

If a status flag is set on the release or on one of its tasks, it appears at the bottom of the release box.

## Filtering releases

![Release Overview Filtering](../images/release-overview-filter.png)

Click **Filter options** to toggle the options:

* **All active releases**: Show releases that are currently busy (release state 'in progress', 'failing', or 'failed').
* **All planned releases**: Show releases that have been created but that have not started yet (release state 'planned')
* **All completed and aborted releases**: Show releases that are completed or aborted (release states 'completed' and 'aborted').
* **Only my releases**: Show releases for which you are the release owner.
* **Only flagged releases**: Only show releases that are flagged with a warning message. Use this option to show releases that are currently at risk.


To filter on release title, use the **Filter by title or tag** box. Enter part of a release title or tag.

To filter on dates, use the **From** and **To** options.
