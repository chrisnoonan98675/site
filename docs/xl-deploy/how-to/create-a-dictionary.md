---
title: Create a dictionary
categories:
xl-deploy
subject:
Dictionaries
tags:
placeholder
dictionary
weight: 161
---

[Placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable. At deployment time, you can provide values for placeholders manually or they can be resolved from dictionaries that are assigned to the target environment.

Dictionaries are sets of key-value pairs that store environment-specific information such as file paths and user names, as well as sensitive data such as passwords. Dictionaries are designed to store small pieces of data, such as a user name or file path. Although XL Deploy does not limit the length of dictionary values, it is recommended that you avoid entries over 100 characters. Longer values will degrade performance.

You assign dictionaries to environments. The order of the dictionaries in an environment matters, because if the same entry exists in multiple dictionaries, then XL Deploy uses the first entry that it finds.

Starting in XL Deploy 5.0.0, a dictionary can contain both plain-text and encrypted entries. Prior to XL Deploy 5.0.0, you use dictionaries for plain-text entries and *encrypted dictionaries* for sensitive information.

## Create a dictionary


To create a dictionary:

1. In the top bar, click **Explorer**.
1. Hover over **Environments**, click ![Menu button](/images/menu_three_dots.png), and select **Dictionary**.
1. In the **Name** field, enter a name for the dictionary.
1. In the **Common** section, in the **Entries** field, click **Add new row**.
1. In the **Key**, enter the placeholder without delimiters `{% raw %}{{{% endraw %}` and `{% raw %}}}{% endraw %}` by default.
1. Under **Value**, enter the corresponding value.
1. Repeat this process for each plain-text entry.
**Note:** To remove an entry, click ![Remove button](../../images/cancel-search.png).
1. In the **Encrypted Entries** section, click **Add new row**.
1. Under **Key**, enter the placeholder without delimiters.
1. Under **Value**, enter the corresponding value.
**Note:** Encrypted entry values are always masked with an asterisks (`*`).
1. Repeat this process for each encrypted entry.
**Note:** To remove an entry, click ![Remove button](../../images/cancel-search.png).
1. Click **Save**.

**Tip:** In XL Deploy 5.0.0 and later, you can create a dictionary [while creating a new environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html).

## Create an encrypted dictionary

To create an encrypted dictionary:

1. In the top bar, click **Explorer**.
1. Hover over **Environments**, click ![Menu button](/images/menu_three_dots.png), and select **Encrypted dictionary**.
1. In the **Name** field, enter a name for the encrypted dictionary.
1. In the **Common** section, in the **Entries** field, click **Add new row**.
1. In the **Key**, enter the placeholder without delimiters `{% raw %}{{{% endraw %}` and `{% raw %}}}{% endraw %}` by default.
1. Under **Value**, enter the corresponding value.
1. Repeat this process for each plain-text entry.
**Note:** To remove an entry, click ![Remove button](../../images/cancel-search.png).
1. Click **Save** to save the dictionary.

## Assign a dictionary to an environment

To assign a dictionary to an environment:

1. In the top bar, click **Explorer**.
2. Expand **Environments** and double-click the desired environment.
3. In the **Dictionaries** field, select a dictionary from the dropdown list.
4. Click **Save** to save the environment.

Multiple dictionaries can be assigned to an environment. Dictionaries are evaluated in order; XL Deploy resolves each [placeholder](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) using the first value that it finds.

## Restrict a dictionary to containers or applications

You can restrict a dictionary so that XL Deploy will only apply it to specific containers, specific applications, or both. To restrict a dictionary:

1. In the top bar, click **Explorer**.
1. Expand **Environments** and double-click the desired dictionary.
1. Under the **Restrictions** section, click in the **Restrict to containers** field and select one or more container from the dropdown list.
1. Click in the **Restrict to applications** field, and from the dropdown list, select one or more applications.
1. Click **Save**.

**Note:** An unrestricted dictionary cannot refer to entries in a restricted dictionary.

### Restricted dictionary example

When you restrict a dictionary, it affects the way XL Deploy resolves placeholders at deployment time. For example, assume you have the following setup:

* A dictionary called DICT1 has an entry with the key `key1`. DICT1 is restricted to a container called CONT1.
* A dictionary called DICT2 has an entry with the key `key2` and value `key1`.
* An environment has CONT1 as a member. DICT1 and DICT2 are both assigned to this environment.
* An application called APP1 has a deployment package that contains a [`file.File`](/xl-deploy/latest/filePluginManual.html) CI. The artifact attached to the CI contains the placeholder `{% raw %}{{key2}}{% endraw %}`.

When you try to deploy the package to the environment, mapping of the CI will fail with the error *Cannot expand placeholder {% raw %}{{key1}}{% endraw %} because it references an unknown key key1*.

This has to do with the fact that, when XL Deploy resolves placeholders from a dictionary, it requires  that *all* keys in the dictionary be resolved. In this scenario, XL Deploy tries to resolve `{% raw %}{{key2}}{% endraw %}` with the value from `key1`, but `key1` is missing because DICT1 is restricted to CONT1. The restriction means that DICT1 is not available to APP1.

There are a few ways you can resolve or work around this scenario:

* Restrict DICT1 to APP1 (in addition to CONT1)
* Add `key1` to DICT2 and give it a "dummy" value (so the mapping will succeed)
* Create another unrestricted dictionary that will provide a default value for `key1`
