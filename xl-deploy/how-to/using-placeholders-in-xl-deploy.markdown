---
title: Using placeholders in XL Deploy
categories:
- xl-deploy
subject:
- Dictionaries
tags:
- placeholder
- dictionary
- package
- environment
---

Placeholders are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable. At deployment time, you can provide values for placeholders manually or they can be resolved from [dictionaries](/xl-deploy/how-to/create-a-dictionary.html) that are assigned to the target environment.

**Note:** When you *update* an application, XL Deploy will resolve the values for placeholders again from the dictionary. For more information, refer to [Resolving properties during application updates](/xl-deploy/concept/resolving-properties-during-application-updates.html).

## Placeholder format

XL Deploy recognizes placeholders using the following format:

	{% raw %}{{ PLACEHOLDER_KEY }}{% endraw %}

If you want to use delimiters other than {% raw %}`{{`{% endraw %} and {% raw %}`}}`{% endraw %} in artifacts of a specific configuration item (CI) type, [modify the CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html) and change the hidden property `delimiters`. This property is a five-character string that consists of two characters identifying the leading delimiter, a space, and two characters identifying the closing delimiter; for example, `%% %%`.

## File placeholders

_File_ placeholders are used in artifacts in a deployment package. XL Deploy scans packages that it imports for text files and searches these text files for file placeholders. The following items are scanned:

* File-type CIs
* Folder-type CIs
* Archive-type CIs

Before a deployment can be performed, a value must be specified for **all** file placeholders in the deployment.

### Special file placeholder values

There are two special placeholder values for file placeholders:

* `<empty>` replaces the placeholder key with an empty string
* `<ignore>` ignores the placeholder key, leaving it as-is

The angle brackets (`<` and `>`) are required for these special values.

**Note:** A file placeholder that contains other placeholders does not support the special `<empty>` value.

## Property placeholders

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, property placeholders do not necessarily need to get a value from a dictionary. If the placeholder can not be resolved from a dictionary, the placeholder is left as-is.
