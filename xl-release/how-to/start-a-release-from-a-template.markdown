---
title: Start a release from a template
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
weight: 423
---

You can create a release from a template by:

* Selecting **Templates** > **Folders** from the top bar, locating the template, and clicking **New release**
* Selecting **Templates** > **All templates** from the top bar, locating the template, and clicking **New release**
* Clicking **New Release** on the template when it is open in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html)
* Clicking **New Release from Template** on the template overview (in XL Release 5.0.x and eariler)

When you create a release from a template, it is first in a *planned* state. This is important because, before the release starts and users are notified by email that tasks are assigned to them, the release owner must:

1. Assign values to [variables](/xl-release/concept/variables-in-xl-release.html)
2. Populate the [release teams](/xl-release/how-to/configure-teams-for-a-release.html) or revise the members of the teams
3. Optionally set scheduled start and due dates on tasks
4. Revise dependencies on other releases (dependencies can only be set on active releases, so they are not specified in the template)
