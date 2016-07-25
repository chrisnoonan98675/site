---
title: Core concepts of XL Release
categories:
- xl-release
subject:
- Architecture
tags:
- release
- phase
- task
- template
- variable
- team
weight: 405
---

[*Releases*](/xl-release/concept/release-life-cycle.html) are at the heart of XL Release. A release represents a number of activities in a certain time period, with people working on them. XL Release allows you to plan, track, and execute releases automatically. It acts as a single source of truth for everyone who is involved in making the release a success.

A release is divided into [*phases*](/xl-release/how-to/using-the-release-flow-editor.html), which represent logical stages in the process that must happen in succession. For example, a release could include Development, QA, and Deployment phases. In XL Release, a phase is a grouping of tasks, which are the activities that must be done to fulfill the release.

[*Tasks*](/xl-release/concept/task-life-cycle.html) are activities in a release. In XL Release, everything that must be done is defined as a task. There are manual tasks, in which a human must do something, and automated tasks that the XL Release flow engine performs, unattended.

When a release is started, XL Release executes the [*release flow*](/xl-release/how-to/using-the-release-flow-editor.html). This is the workflow of the release. XL Release determines the current task that needs to be picked up and executes it (if it is an automated task) or sends a message to the person responsible for it (if it is a manual task).

Each release has a [*release owner*](/xl-release/how-to/configure-release-properties.html), the person that is responsible for correct performance of the release. If something goes wrong, the release owner will be notified. For example if an automated task produces an error, or one of the people working on a task indicates that he is in trouble.

A [*template*](/xl-release/how-to/create-a-release-template.html) is a blueprint for a release. You can use a template to start different releases that have the same flow. A template is very similar to a release; however, some functionality is different because a template is never executed directly. For example, there are no start or end dates in a template; most tasks are assigned to teams rather than actual people; and [*variables*](/xl-release/concept/variables-in-xl-release.html) are used as placeholders for information that differs from release to release, such as an application's version number.

Each release or release template defines a set of [*teams*](/xl-release/how-to/configure-release-teams-and-permissions.html). A team is a logical grouping of people who perform a certain role. For example, on a release you can define a Development team, a QA team, an OPS team, and a Release Management team.
