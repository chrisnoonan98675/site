---
title: How to get a value out of a map variable
categories:
- xl-release
subject:
- Variables
tags:
- variables
- script
- jython
- plugin
- customization
---

## Introduction

Suppose you have a variable `${meals}` of type key-value map:

|Key|Value|
|-|-|
|breakfast|eggs|
|lunch|salad|
|dinner|ratatouille|

We have another variable called `${mealtime}`, which can have any of the values `breakfast`, `lunch` or `dinner`.

Now in a task, how do we get a hold of `salad` if `${mealtime}` is set to `lunch`?

## Script solution

Inside a script it is easy to do, we just use the Python syntax:

	${meals}[$mealtime]
	
We can try to use this style inside a text field...

![Python look-up](../images/map-variable/python-style.png)

But it won't work, because inside a task variables are replaced literally, without any interpretation.

![Task variable replacement](../images/map-variable/interpretation.png)

What we can do is to add a Script Task that will get the value from the map and store it a new variable called `${mymeal}`. 

## Adding a variable and a script

The new variable `${mymeal}` isn't relevant when starting the release, so on the template, go to the **Variables** screen to create it and uncheck 'Required' and 'Show on create release form'.

![Edit my meal](../images/map-variable/edit-mymeal.png)

We can now use `${mymeal}` in tasks without worrying that it will interfere while creating a release.

In order to make this work, we'll add a Script Task called 'Set mymeal variable' just before the task that will use it.

The template will look like this:

![Template with set my meal variable as first task](../images/map-variable/template.png)

Inside a Script Task, variables can be read and set through a Python dictionary called `releaseVariables`.

This is the script insde the task called "Set mymeal variable":

```
releaseVariables['mymeal'] = ${meals}['${mealtime}']
```


## Security

Now the script is setting a release variable by modifying `releaseVariables` and as a consequence we have to make sure the release has the right permissions to do so. To configure this, go to **Properties** page on the template and set **Run script as user** and **Password** to a user that has write permissions on the release.

![Run scripts as user](../images/map-variable/run-as-user.png)

We are now ready to run the template!

![Let's have salad](../images/map-variable/lets-have-salad.png)

## Conclusion

To get a value out of a map to be used in any text field, add a little script task that does that and stores the result in another variable.


