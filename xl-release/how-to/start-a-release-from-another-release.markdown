---
title: Start a release from another release
categories:
- xl-release
subject:
- Releases
tags:
- create release
- subrelease
since:
- XL Release 5.0.0
---

The [Create Release task type](/xl-release/how-to/create-a-create-release-task.html) allows you to automatically create and start a release based on a configured template. You can use the Create Release task for several different release orchestration scenarios.

## Master release with subreleases

For example, you can create a "master release" that will start several "subreleases" and then wait for them to finish. The Create Release task can be confgued to return the newly created release's unique ID in an output variable; you can then use this variable in other tasks, such as a [Gate task](/xl-release/how-to/create-a-gate-task.html) as shown below.

This is a master release that will start three subreleases:

![Sample "master release"](../images/create-release-examples/create-release-task-example-master-with-subreleases-01.png)

This is one of the Create Release tasks, showing the output variable that will be populated with the new release's ID:

![Sample Create Release task](../images/create-release-examples/create-release-task-example-master-with-subreleases-02.png)

And this is a Gate task that will wait for the subreleases to finish:

![Sample Gate task](../images/create-release-examples/create-release-task-example-master-with-subreleases-03.png)

## Kickstart release

Another scenario is a "kickstart" release that only serves to start other releases, and does not wait on them to finish. This is useful if you have many small releases that you want to run in parallel. For example:

![Sample "kickstart release"](../images/create-release-examples/create-release-task-example-kickstart-release.png)

The Create Release tasks would be configured just as as in the master/subrelease scenario, though the use of output variables would be optional in this case.

## Release chain

You can also use the Create Release task to "chain" releases together by starting a new release near or at the end of a release. This is useful if you have several releases that you want to run in sequence. 

For example, just before this release ends, it will start another release:

![Sample "release chain"](../images/create-release-examples/create-release-task-example-chain-releases-01.png)

And the subsequent release will do the same:

![Sample "release chain"](../images/create-release-examples/create-release-task-example-chain-releases-02.png)
