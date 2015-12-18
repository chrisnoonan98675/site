---
title: Tutorial - Using variables to revise a JIRA issue list
categories:
- xl-release
subject:
- Variables
tags:
- variable
- jira
- tutorial
- user input
since:
- XL Release 4.8.0
---

This tutorial will show you how to use the extended variable mechanism (available in XL Release 4.8 and higher) to create a release that will retrieve a list a of JIRA tickets, have a user revise them and then update the list in JIRA.

In order to do this tutorial, you need access to a JIRA server. In XL Release you will need the following global rights:

* **Admin** in order to configure the JIRA server. 
* **Create Template** for the rest of the tutorial.

For more information on global permissions, see [Configure Permissions](/xl-release/how-to/configure-permissions.html)

## Setup

First we have to configure the JIRA server in XL Release, so our release will be able to connect to it.

Go to **Settings > Configuration** and add a JIRA server.

Configure it to point to the JIRA server you have access to. This server will be available to all releases. You can choose to set the credentials here, or to leave them blank and fill them in later on the release level.

![JIRA server configuration](../images/jira-variables/configure-jira-server.png)

## Creating the template

We want our release to be reusable, so let's start by creating a template first.

Go to **Templates**, create a new template and call it "Check JIRA issues". Name the first and only phase "Check JIRA".

![Empty template](../images/jira-variables/empty-template.png)

For now, we will not bother with setting up teams and permissions; see [Configure release teams](/xl-release/how-to/configure-teams-for-a-release.html) for more information.

## Adding the tasks

Our procedure will be as follows:

1. Query JIRA to get a list of issues
2. Have a user revise the list. The user may add or remove issues.
3. Update the the issues with a comment in JIRA

For each of the steps we will add a task and configure it. We will use variables to connect the information flow between tasks.

## Query JIRA

Click the **Add task** link at the bottom of the phase and select **Jira: Query** from the dropdown. Name the task "Query JIRA" and press the **Add** button.

Now click on it to configure it. 

![Empty template](../images/jira-variables/query-jira-task.png)

The JIRA server you have configured before should be automatically selected in the **Server** field. If you have configured a username and password on the JIRA Server configuration under settings, you don't need to fill them in here.

The **Query** field takes any query in JIRA's JQL format. In the example above the query is

    project = "Sandbox" and status = Open and text ~ "Dummy"
	
which basically says: "Give me all open issues of the 'Sandbox' project that have text 'Dummy' in them". 

Now use JIRA itself to develop a query that works for your project and paste it in here.

## Testing the template

Before we go on, let's test the first step to see if it works.

When testing releases, it's good practice to put a simple Gate task at the end that prevents the release from finishing automatically.

Add a Gate task labeled "OK?" to the template.

![Gate task at the end](../images/jira-variables/test-first-task.png)

Now create a new release and name it "Test 1". Start the release and wait for the "Query JIRA" task to finish. 

![Query result](../images/jira-variables/query-result.png)

On the completed task, you can see the result in two places: First, after the label 'Issues' in the Output properties section. We will use this later on the put the result of the query in a variable that can be used in another task. Second, the issues are displayed in the Log Output in the Comments section. Clicking on the hyperlinks will open the issues in JIRA.

## Revise the issue list

Now let's capture the output of the query and have the user revise it before continuing. To do this, go back to the **Check JIRA issues** template.

Open the **Query JIRA** task and go to the **Output properties** section. This is where you can tell where to store the result of an (automated) task. Next to the **Issues** field, type `queryResultVar` and press enter. 

![Connect query result to queryResultVar variable](../images/jira-variables/queryResultVar.png)

A new variable `${queryResultVar}` has been created and the result of the JIRA query will be stored in it. We can now use it in another task.

The **User Input task** is used during the release process to ask a human for some information. We do this by adding variables to this task. During a release, the user that this task is assigned to, needs to enter a value for these variables or check if they are correct. In this case, we will configure it to ask the user to confirm the list of issues before continuing with the release process. 

Add a task of type **User Input** named "Check JIRA issues" and move it below the **Query JIRA** task. Your release should look like this now:

 ![Template with user input task](../images/jira-variables/added-user-input-task.png)

We are now on the template, and we will configure the **Check JIRA issues** to display the contents of the `queryResultVar` variable, which containers the issue list.

Open the task and press the button **Edit variable list**. This will open the 'edit mode' of the User Input task.

![Configuring the user input task](../images/jira-variables/configure-user-input-task.png)

From the dropdown, select `${queryResultVar}` and press **Save**.

The result is a not-so-impressive form. First of all, since we are in a template there is no query result, so no JIRA issues are displayed. Second, the description and variable feel rather generic and technical. 

![Default form](../images/jira-variables/user-input-form-template.png)

That is is something we can fix. Edit the description "Please enter the required information below" by pressing the pencil icon and enter "Please revise the issue list below. Remove any tickets that should not be updated." This is the message the user will see when the release is in progress.

Now close the task and navigate to the Variables section. Click on the drop-down button next to **Show** in the grey release bar and select **Variables**. This will take you to an overview of all variables that are being used in the release or template.

![Variable overview](../images/jira-variables/variable-overview-on-template.png)

Click on the `${queryResultVar}` line and the edit dialog for variables will open. Here we can enter a human-readable label and description:

![Edit variable](../images/jira-variables/edit-variable.png)

Close the dialog, go back to the release flow and open the **Check JIRA issues** task. You will see it now has a more user-friendly label. 

While the task is open, also click the 'Assign to me' button on the lower right corner. This will make sure the task is assigned to you while testing the release.

## Another test run

Let's see how this all works out in practice. Create a new release ("Test 2") and start it. It will stop on the **Check JIRA issues** task. Open it and it should look something like this:

![User Input task in running release](../images/jira-variables/user-input-in-action.png)

Try removing a couple of issues and Completing the task.

You can check the result of your actions by opening the Variables page again and inspecting the `${queryResultVar}` variable.

## Updating the issues in JIRA

One thing left to do: adding a comment to the selected issues in JIRA. Let's go back to the template. Now add a task of type **Update issues**, call it "Update issues in JIRA" and move it under **Check JIRA issues**. 

Your template should now look like this:

![Full template](../images/jira-variables/full-template.png)

Open the task to configure it.

We use the same JIRA server as before.

The list of issues to be updated can be entered manually, but of course we want to use the variable `${queryResultVar}` we have been using before.

To do so, press the 'Switch to variable' button next to the **Issues** field. 

![Select variable](../images/jira-variables/update-issues-with-variable.png)

Now select `${queryResultVar}` from the drop-down list.

With this technique, the value of the variable will be filled in when the task starts.

The **Update issues** task supports issue transitions in JIRA, updating the summary and adding comments. For this example we will simply add a comment. Do so by entering a comment like "Updated from XL Release!" in the **Comment** field. 

![Update Issues task configuration](../images/jira-variables/update-comment.png)

## Run again

We're done with our template, let's test again. Start a release and see that it runs until the end. 

In the Log output comments of the **Update issues in JIRA** task you will see which issues have been updated. Click on the links to see the results in JIRA.

![Output of update task](../images/jira-variables/log-output-update-issues.png)

You will be able to see the result in JIRA:

![Comment in JIRA](../images/jira-variables/comment-in-jira.png)

## Conclusion

By using variables, XL Release allows you to connect steps in your release procedure together. It's a powerful mechanism that works for automated tasks, but can also be used to interact with people in a user-friendly way.


   
 