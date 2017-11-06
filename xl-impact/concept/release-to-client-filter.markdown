---
title: The mechanics of the Release to Client Filter
categories:
- xl-impact
subject:
- Filter
tags:
- filter
---

The **Release to Client Filter** is a core concept that XL Impact is based on. Release to Client is a filter that helps XL Impact discover when a new version was released and when the version becomes visible for real clients.

**Important** You must ensure the **Release to Client Filter** is correctly configured.

For the majority of **Insights**, XL Impacts tries to analyze every event and determine to which **Release to Client** it belongs to.

Examples of Release to Client filters:

* A Jenkins job that uploads a new release version of an application to the **Downloads** section of company website
* A Jenkins job that pushes a new version of a web application to the production infrastructure

At the moment, you can select only one of two options as the Release to Client filter: **Jenkins build** or **XLR Release**.
To use only successful Jenkins builds or XLR Releases as releases to client, it is recommended to add a **Success state** type filter.

Ensure that the events filtered by the Release to client filter are correct. Example: If you perform a release once every two weeks, then the filtered events should also occur once every two weeks.

**Note:** If your release pipeline contains `hotfixes` (small releases with the purpose of bug fixing), you should not consider these as **Release to Client**. The optimal outcome is to obtain correct and relevant statistics and not to mix up long (normal) releases with short (`hotfix`) releases.

XL Impact will then link all the other events to a particular release. The rule behind the process is: a commit is considered to be part of a **Release to Client** only if it was actually released.
For example: If a team is developing a product feature in a repository branch and that branch is not merged into the release branch and is not released, then the feature is not relevant or important enough. The commits to that feature branch will not be considered as part of **Release to Client** and that data will not be used.
If the feature is improved and when the branch is merged, then the commits to this branch will be considered as part of the nearest actual release.
