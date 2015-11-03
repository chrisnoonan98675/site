---
title: Using the release overview
categories:
- xl-release
subject:
- Releases
tags:
- release
- release overview
---

In XL Release, go to **Releases** > **Overview** to see the list of releases that you have permission to view and that are currently active (including releases that are planned, in progress, and failed). Here, you can see the [status](/xl-release/concept/release-life-cycle.html) of each release, as well as any status flags that have been set.

![Release Overview](../images/release-overview.png)

## Release overview actions

Next to each release, click:

* **View** to open the release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html)
* **Start** to start a release that is in the *planned* state
* **Abort** to abort the release

## Dates in the release overview

The **Start date** column shows:

* The planned start date if the release is scheduled in the future
* The actual start date if the release has already started

The **End date** column shows:

* The planned end date if the release is not complete
* The actual end date otherwise

Overdue dates appear in red.

The **Duration** column shows the duration of the release.

## Filtering the release overview

To filter the release overview, click **Filter options** and select:

* **All active releases** to show releases that are busy (in the *in progress*, *failing*, or *failed* state)
* **All planned releases** to show releases that have been created but that have not started yet (in the *planned* state)
* **All completed and aborted releases** to show releases that are completed or aborted (in the *completed* or *aborted* state)
* **Only my releases** to show releases for which you are the release owner
* **Only flagged releases** to show releases that are flagged with a warning message; use this option to see releases that are currently at risk

To filter on the release title, use the **Filter by title or tag** box.

To filter on dates, use the **From** and **To** options.
