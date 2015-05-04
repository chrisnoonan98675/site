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
- release owner
- template
- variable
- team
---

*Releases* are at the heart of XL Release. A release represents a number of activities in a certain time period, with people working on them. XL Release allows you to plan, track, and execute releases automatically. It acts as a single source of truth for everyone who is involved in making the release a success.

A release is divided into *phases*, which represent logical stages in the process that must happen in succession. For example, a release could include Development, QA, and Deployment phases. In XL Release, a phase is a grouping of tasks, which are the activities that must be done to fulfill the release.

*Tasks* are activities in a release. In XL Release, everything that must be done is defined as a task. There are manual tasks, in which a human must do something, and automated tasks that the XL Release flow engine performs, unattended.

When a release is started, XL Release executes the *release flow*. This is the workflow of the release. XL Release determines the current task that needs to be picked up and executes it (if it is an automated task) or sends a message to the person responsible for it (if it is a manual task).

Each release has a *release owner*, the person that is responsible for correct performance of the release. If something goes wrong, the release owner will be notified. For example if an automated task produces an error, or one of the people working on a task indicates that he is in trouble.

A *template* is a blueprint for a release. You can use a template to start different releases that have the same flow. A template is very similar to a release; however, some functionality is different because a template is never executed directly. For example, there are no start or end dates in a template; most tasks are assigned to teams rather than actual people; and *variables* are used as placeholders for information that differs from release to release, such as an application's version number.

Each release or release template defines a set of *teams*. A team is a logical grouping of people who perform a certain role. For example, on a release you can define a Development team, a QA team, an OPS team, and a Release Management team.
