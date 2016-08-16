---
no_index: true
title: Discover deployed applications
---

XebiaLabs allows you to automatically, or in a guided fashion, discover the configuration and applications that are installed in an existing environment and use them to create a deployment package, so you can quickly replicate an installation to new environments. You can select specific configuration items (CIs), edit their properties, and use [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) and [dictionaries](/xl-deploy/how-to/create-a-dictionary.html) to tokenize environment-specific settings. In the case of CIs that contain binary data (such as EAR, WAR, and ZIP files), you can select the required artifact from a repository and associate it with the configuration.

![Discover application](images/reverse-engineer/reverse-engineer-02.png)

## Step 1 View deployed CIs

To discover deployed applications, start by viewing the CIs that are deployed to a container in your middleware topology. You can search for containers by type (for example, `was.ManagedServer` for a WebSphere Managed Server) or by name.

![Discover application](images/reverse-engineer/reverse-engineer-03.png)

## Step 2 Select deployed CIs

After you select a container, you can select one or more CIs to add to the deployment package.

![Discover application](images/reverse-engineer/reverse-engineer-04a.png)

To remove a CI from the package, click **X** next to the CI.

![Discover application](images/reverse-engineer/reverse-engineer-04b.png)

## Step 3 Review and edit CI properties

To see the properties of a deployed CI, click it.

![Discover application](images/reverse-engineer/reverse-engineer-04c.png)

To edit the value of a CI property, click the value. To clear the value, click **X** next to the property.

You can enter a new value or tokenize the property by entering a [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) in `{% raw %}{{ placeholder }}{% endraw %}` format. You can then provide the value for the placeholder in a [dictionary](/xl-deploy/how-to/create-a-dictionary.html) that you associate to one or more environments.

![Discover application](images/reverse-engineer/reverse-engineer-05.png)

Alternatively, you can click the arrow next to a property to quickly tokenize it by having the system generate a placeholder based on the deployable name and the selected property. For example, this is the `isGrowable` property before quick tokenization:

![Discover application](images/reverse-engineer/reverse-engineer-06a.png)

And after quick tokenization:

![Discover application](images/reverse-engineer/reverse-engineer-06b.png)

Whether you manually replace a property or use the quick tokenization feature, the original value of the property is stored in a dictionary, so you always have a history of the CI's properties.

![Discover application](images/reverse-engineer/reverse-engineer-07.png)

## Step 4 Save the package

After you have selected CIs and configured their properties as desired, you can save the deployment package for use in other environments.

![Discover application](images/reverse-engineer/reverse-engineer-08.png)
