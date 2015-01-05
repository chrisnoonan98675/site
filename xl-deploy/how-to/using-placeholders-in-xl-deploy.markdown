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

Placeholders are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable.

## Placeholder format

XL Deploy recognizes placeholders using the following format:

	{{ PLACEHOLDER_KEY }}

Values for placeholders can be provided manually or filled in from a _dictionary_.

**Note:** When you upgrade an application, XL Deploy will resolve the values for the placeholders again from the dictionary. If a previously known key is removed from the dictionary, XL Deploy will use the last known value.

## File placeholders

_File_ placeholders occur inside of artifacts in the deployment package. XL Deploy scans packages that it imports for text files and searches these text files for file placeholders. The following items are scanned:

* File-type CIs
* Folder-type CIs
* Archive-type CIs

Before a deployment can be performed, a value must be specified for **all** file placeholders in the deployment.

### Special file placeholder values

There are two special placeholder values for _file_ placeholders:

* `<empty>` replaces the placeholder key with an empty string
* `<ignore>` ignores the placeholder key, leaving it as-is

The angle brackets (`<` and `>`) are required for these special values.

**Note:** A file placeholder that contains other placeholders does not support the special `<empty>` value.

## Property placeholders

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, _property_ placeholders do not necessarily need to have a value from a dictionary. If the placeholder can not be resolved from a dictionary, the placeholder is left as-is.
