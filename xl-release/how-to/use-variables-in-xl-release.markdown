---
title: Use variables in XL Release
categories:
- xl-release
subject:
- Variables
tags:
- release
- template
- task
- variable
---

When creating your release templates, you will probably be creating tasks that have information that can vary based on the release. Also, you may have one generic release template that can be used for the release process of several of your applications. This reusability is an important feature of of XL Release, since it ensures that you create a template once, and then reuse that template for each application release.

For example, let's say you've completed your release template, capturing all of the tasks necessary for completing the release of your application. And in the template, the application name is the only unique value that needs to be defined for each release. This is where variables come into play. A variable allows you to put a placeholder in your tasks, so that the actual application name is set once the release starts.

You can use variables in many areas of XL Release, including titles, descriptions and fields. You use the variables as placeholders when you are creating the template:

![Variables in a release template](../images/variables-in-xl-release-task.png)

Once the release is started, you define the variable values as part of the starting process:

![Define variable values](../images/define-xl-release-variables.png)

Now that you understand more about variables, you can see that a best practice for creating a template in XL Release is to look for areas of overlap in your application release process. If you can consolidate different application release templates into one template using variables, you'll help minimize the overhead your team needs to manage many release templates.
