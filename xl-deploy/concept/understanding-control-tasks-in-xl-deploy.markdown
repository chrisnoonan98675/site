---
title: Understanding control tasks in XL Deploy
categories:
- xl-deploy
subject:
- Middleware
tags:
- middleware
- control task
- task
---

Control tasks are actions that can be performed on middleware or middleware resources; for example, checking the connection to a host is a control task.

When a control task is invoked, XL Deploy starts a task that executes the steps associated with the control task.

To trigger a control task on a CI in the repository, do the following:

1. **List the control tasks for a CI**. In the Repository, locate the CI for which you want to trigger a control task. Right-click the item to see the control tasks.
2. **Execute the control task on a CI**. Select the control task you want to trigger. This will invoke the selected action on the CI.
3. **Provide parameters**. If the control task has parameters, you must provide them before you start the control task.
