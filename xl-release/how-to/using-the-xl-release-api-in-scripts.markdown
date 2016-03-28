---
title: Using the XL Release API in scripts
categories:
- xl-release
subject: XL Release API
tags:
- api
- script
since:
- XL Release 4.5.0
---

XL Release exposes an API that developers can use to manipulate releases and tasks. This topic describes how to use the XL Release public API in Jython scripts. The API can be accessed from both Script tasks and plugin scripts. 

## Creating and running a simple example

We will write a simple script to add a comment to a task. Because it is just a "Hello World" example, we will just create a blank release in stead of going through a template.

From the Release Overview screen, select **New Release**. Set **Script example** as the release name and create the release.

In the newly created release, call the first phase **Test**, add a script task called **Add a comment** and a gate task called **Check result**.

![Script test tasks](../images/script-test/phase.png)

Now open the script task and add the following snippet:

    task = getCurrentTask()
    comment = Comment()
    comment.setComment("Hello World!")
    taskApi.commentTask(task.id, comment)

Close the task and start the release. 
The script task will fail! Open the task and the reason for failure is displayed as log output in the comment section:

![Script test tasks](../images/script-test/no-run-as-user.png)

There is message there: "A Release User account has to be set in order to use XL Release's API". This tells us that we have to explicitly say under which user name the script will be run. Because the API gives access to releases and tasks, XL Release needs to check someone's credentials in order to check if certain operations are allowed. To set such a user, go to the **Release Properties** page and find the properties **Run scripts as user** and **Password**

Enter your own credentials for now. (Don't forget to hit **Save**!) These user credentials will be used to run all scripts that are defined in this release. 

**Tip:** for a more serious set up than testing we recommend to set up a global "Script Runner" user that can be used in each release and has tailor-made security settings.

Go back to the **Release Flow** and retry the failed script task.

![Script test tasks](../images/script-test/retry.png)

The task should succeed now, and the release will stop at the gate in order to give us some time to inspect the results.

![Script test tasks](../images/script-test/check-result.png)

Click on the **Add a comment task** and scroll down to the comments section. We can see that the "Hello World!" comment has been added:

![Script test tasks](../images/script-test/hello-world.png)

## Looking at the code

Let's take a look at the code step by step.

    task = getCurrentTask()

In the first line, we get a reference to the currently executing task (in other words, our "Add a comment" Script task) and stores it in a variable `task`. The method to do this is `getCurrentTask()`, which is one of a number of methods that are directly exposed to Jython scripts.

To set a comment on a task, we use the Public API endpoint `taskApi.commentTask`, which is documented [here](/jython-docs/#!/xl-release/4.6.x//service/com.xebialabs.xlrelease.api.v1.TaskApi).

The commentTask method takes the task ID and a `Comment` object as a parameter. The [Comment](/jython-docs/#!/xl-release/4.6.x//service/com.xebialabs.xlrelease.api.v1.forms.Comment) object is a simple wrapper around a string. It is instantiated as follows:

    comment = Comment()
    comment.setComment("Hello World!")

With the comment object in place we can call the `commentTask()` method:
  
    taskApi.commentTask(task.id, comment)

This will add the comment to the task. Note that for any call to a API endpoint like `taskApi` or `releaseApi`, you need to set the "Run scripts as user" property on the release. The scripts will be executed with the rights that user has on that particular release.

## More documentation

This is just a small example about that you can do with the XL Release API. Check out the documentation to see what methods are available.

* [Jython API for XL Release 4.6.x](/jython-docs/#!/xl-release/4.6.x/)
* [Script task reference](/xl-release/how-to/create-a-script-task.html)
* [Create custom task types in XL Release](/xl-release/how-to/create-custom-task-types-in-xl-release.html)
* [Create custom configuration types in XL Release](/xl-release/how-to/create-custom-configuration-types-in-xl-release.html)
* [Declare custom REST endpoints in XL Release](/xl-release/how-to/declare-custom-rest-endpoints-in-xl-release.html)
