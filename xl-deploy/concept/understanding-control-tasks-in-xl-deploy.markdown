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

Control tasks are actions that can be performed on middleware or middleware resources. When a control task is invoked, XL Deploy starts a task that executes the steps associated with the control task. To trigger a control task on a CI in the repository, do the following:

1. **List the Control Tasks for a CI**. In the Repository Browser navigate the repository to find the CI for which you want to trigger a control task. Right-click on the item and select **Tasks**. A submenu will show a list of possible control tasks.
2. **Execute the Control Task on a CI**. Select the control task from the list you want to trigger. This will invoke the selected action on the CI.
3. **Fill in parameters**. If the control task has parameters, you can to fill them in before you can start the control task.

![Selecting a control task](images/control-task.png)
