---
title: XL Release pipeline view
categories:
- xl-release
subject:
- Pipeline
tags:
- pipeline
- user interface
---

The **Pipeline** view shows an overview of all active releases, the phases they are in, and which task is currently active.

![Pipeline](../images/pipeline.png)

A flag appears next to the release title if a status message is set to indicate that the release is at risk.

All phases appear in a linear fashion. Completed phases have a gray tick mark inside them. An active phase shows the currently active task. Gate tasks are red, all other tasks are light blue. If a phase starts or ends with a gate that has not passed yet, it appears in the pipeline view with the gate icon ![Gate icon](../images/gate-icon.png).
