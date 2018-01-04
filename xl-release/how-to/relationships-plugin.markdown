---
title: Using the XL Release Relationships feature
categories:
- xl-release
subject:
- Releases
tags:
- plugin
- relationships
- sub-releases
- gates
since:
- XL Release 7.5.0
---

The XL Release Relationships feature allows you to visualize relationships between releases and manage multiple releases at the same time.

## Features

* Visualize release and template relationships in their design and runtime states.
* For all releases, you can view and filter releases using the following meta information: release status, active phase, active tasks, failed tasks, risk status, and risk score.
* Navigate through releases using their relationships.
* The following actions can be performed on one or multiple releases: **Abort the release** and **Retry all failed tasks**.

## Requirements

The Relationships feature requires the following:

1. XL Release version 7.5 or later to be installed.
1. The XL Release `relationship-plugin` to be installed.

## Types of relationships

The plugin understands two types of relationships:

* **Create release task**: The **Create Release** task allows you to create a new release from a template. This is considered to be a relation between a parent (the release containing the **Create Release** task) and the child (the release that will be created from the template). The relation is visible both when the child and parent are in the template state, and when the child release has been created from the parent.
* **Gate task dependencies**: The **Gate** task allows you to create a dependency on another release. This is considered to be a relation between a parent (the release containing the **Gate** task) and the child (the release that is linked from the task dependencies). The relation will also exist when a variable is used that points to a release ID that is created at a later point.

* In the graph view, **Create Release** relationships are shown as solid grey arrows, and the **Gate** relationships are shown as blue dashed arrows. The arrow points in the direction from the parent to the child.
* In the table view, the graph is shown as tree structure representation. Child releases are nested below their parents. There is no difference between **Gate** or **Create Release** dependencies in the table view.

If multiple relations from the same parents to the same child exist, only one arrow is visible. In the graph view, the relationships label will list all the task names that define the relation in a comma separated list.

## Navigation between releases

The navigation can be performed from both the graph view and the table view:

* In the graph view, you can navigate to a specific release by double clicking the release in the graph. This opens the relationships page for that release.
* In the table view, you can navigate to a specific release by double clicking the release title in the table. This opens the release flow page for that release.

To navigate back to a previous page, use the **Back** button of your browser.
