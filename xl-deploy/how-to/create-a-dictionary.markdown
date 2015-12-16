---
title: Create a dictionary
categories:
- xl-deploy
subject:
- Dictionaries
tags:
- placeholder
- dictionary
---

[Placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable. At deployment time, you can provide values for placeholders manually or they can be resolved from [dictionaries](/xl-deploy/how-to/create-a-dictionary.html) that are assigned to the target environment.

Dictionaries are sets of key-value pairs that you assign to environments. You can use dictionaries to store environment-specific information such as file paths and user names, as well as sensitive data such as passwords.

Starting in XL Deploy 5.0.0, a dictionary can contain both plain-text and encrypted entries. Prior to XL Deploy 5.0.0, you use dictionaries for plain-text entries and *encrypted dictionaries* for sensitive information.

## Create a dictionary

To create a dictionary:

1. Click **Repository** in the top menu.
1. Right-click **Environments** and select **Dictionary**.
1. In the **Name** box, enter a name for the dictionary.
1. Click ![Add button](/images/button_add_placeholder.png) under **Entries** to add a plain-text entry.
    1. Under **Key**, enter the placeholder without delimiters ({% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} by default). Under **Value**, enter the corresponding value.
    1. Repeat this process for each plain-text entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click ![Add button](/images/button_add_placeholder.png) under **Encrypted Entries** to add an encrypted entry (supported in XL Deploy 5.0.0 and later).
    1. Under **Key**, enter the placeholder without delimiters. Under **Value**, enter the corresponding value. Note that in encrypted entries, the value is always masked with asterisks (`*`).
    1. Repeat this process for each encrypted entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click **Save** to save the dictionary.

**Tip:** In XL Deploy 5.0.0 and later, you can create a dictionary [while creating a new environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).

## Create an encrypted dictionary

To create an encrypted dictionary:

1. Click **Repository** in the top menu.
2. Right-click **Environments** and select **Encrypted Dictionary**.
3. In the **Name** box, enter a name for the encrypted dictionary.
4. Click ![Add button](/images/button_add_placeholder.png) under **Entries** to add an entry.
1. Under **Key**, enter the placeholder without delimiters ({% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} by default). Under **Value**, enter the corresponding value. Note that in encrypted dictionary entries, the value is always masked with asterisks (`*`).
1. Repeat this process for each entry that you want to add. If you want to remove an entry, select it and click ![Remove button](/images/button_remove_placeholder.png).
1. Click **Save** to save the dictionary.

## Assign a dictionary to an environment

To assign a dictionary to an environment:

1. Click **Repository** in the top menu.
2. Expand **Environments** and double-click the desired environment.
3. Under **Dictionaries**, select the dictionary and click ![Add button](/images/button_add_container.png) to move it to the **Members** list.
4. Click **Save** to save the environment.

You can assign multiple dictionaries to an environment. Dictionaries are evaluated in order; XL Deploy resolves each [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) using the first value that it finds.
