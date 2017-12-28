---
title: Using the XL Release Relationships plugin
categories:
- xl-release
subject:
tags:
- plugin
- relationships
- subreleases
- gates
since:
- XL Release 7.5.0
---

The XL Release Relationships plugin allows you to visualize relationships between releases, and manage them in bulk.

## Features

* Visualize release and template relationships in their design and runtime states.
* For all releases we can see and filter releases using the following meta information:
** Release status, active phase, active tasks, failed tasks, risk status and score.
* Navigate releases using their relationships
* The following actions can be performed on one or many releases:
** Abort the release
** Retry all failed tasks

## Requirements

The Relationships plugin requires the following:

1. At least version 7.5 of XL Release to be installed.

## What is a relationship?

The plugin understands two types of relationships:

* **Create Release task** The "Create Release" task allows you to create a new release from a template. This is considered to be a relation between a parent (the release containing the Create Release task) and the child (the release that will be created from the template). The relation will be visible both when child and parent are in template state, and when the child release has been created from the parent.
* **Gate task dependencies** The "Gate" task allows you to create a dependency on another release. This is considered to be a relation between a parent (the release containing the Gate task) and the child (the release that is linked from the task dependencies). The relation will also exist when a variable is used that points to a release id that is created at a later point.

* In the Graph view, "Create Release" relationships are shown as solid grey arrows, and the "Gate" relationships are shown as blue dashed arrows. The arrow points in the direction from the parent to the child.
* In the Table view, a tree like representation of the graph is shown. Child releases are nested below their parents. There is no difference between "Gate" or "Create Release" dependencies in the table view.

If multiple relations exist from the same parents to the same child, only one arrow is visible. In the Graph view, the relationships label will list all task names that define the relation, in a comma separated list.

## Navigation

Navigation can be done from both the Graph screen and the Table screen:

* On the Graph screen, you can navigate to a specific release by double clicking the release in the graph. You will end up in the Relationships page for that release.
* On the Table screen, you can navigate to a specific release by double clicking the release title in the table. You will end up on the release flow page for that release.

Navigating back can be done using the browsers "Back" button.

