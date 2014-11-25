---
title: How password properties and encrypted dictionaries combine to secure sensitive data in XL Deploy
subject:
- Dictionaries
categories:
- xl-deploy
tags:
- dictionary
- security
---

In XL Deploy's [type system](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#type-system), any property defined as [`password="true"`](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#synthetic-properties) is stored in the [repository](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#repository) in encrypted form (AES-256 at the time of writing) and appears as `******` in the user interface. The [`password="true"`](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#synthetic-properties) setting usually applies to properties called "password", but you can define _any_ property as secure in this way.

In the case of secure properties of [deployable items](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployables)—such as the password for a [datasource spec](http://docs.xebialabs.com/releases/latest/tomcat-plugin/tomcatPluginManual.html#tomcatdatasourcespec) or similar piece of configuration—the value is usually _not_ set directly on the deployable, because it varies across target environments.

You can handle a secure property by setting the property on the deployable to a [placeholder](http://docs.xebialabs.com/releases/4.0/deployit/referencemanual.html#placeholders) such as `{% raw %}{{my.datasource.password}}{% endraw %}`. When the deployable is mapped to a specific target environment, XL Deploy resolves the placeholder using [dictionaries](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#dictionaries) that are linked to the environment. XL Deploy selects the first value that it finds for the `my.datasource.password` key.

There are two types of dictionary in XL Deploy. "Regular" dictionaries are straightforward sets of string key/value pairs, and are intended to store non-sensitive values. There is no notion of a "secret" value in a regular dictionary; its values are stored in plain text and are visible anyone with [read access](http://docs.xebialabs.com/releases/latest/deployit/securitymanual.html#permissions) to the dictionary [configuration item](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#configuration-items-cis).

Not the right place to store our value for `my.datasource.password`, therefore! Instead, we should use an [encrypted dictionary](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#encrypted-dictionaries) to store such secret values. The keys in encrypted dictionaries are treated as plain text (the dictionaries would be pretty hard to use otherwise!), but the values are treated in the same way as `password="true"` properties: they are hidden in the UI and stored in encrypted form in the repository.

## How do properties and dictionaries work together?

There are four possible combinations of properties and dictionaries. Let's examine what happens in each case. Assuming we have:

* A deployable with two properties:
    * A normal property called _username_
    * A password property called _password_
* Two dictionaries:
    * A regular dictionary called NormalDict that contains the entry `my.username = scott`
    * An encrypted dictionary called SecureDict that contains the entry `my.password = tiger`

### Normal property, regular dictionary

The deployable contains `username = {% raw %}{{my.username}}{% endraw %}`. XL Deploy resolves this property, resulting in `username = scott` on the corresponding [item to be deployed](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployeds).

### Secret property, encrypted dictionary

The deployable contains `password = {% raw %}{{my.password}}{% endraw %}`. XL Deploy resolves this property, resulting in `password = tiger`. In the UI, the value only appears in encrypted form, so we only see `password = *****`.

This screenshot shows the resolved properties with `username = {% raw %}{{my.username}}{% endraw %}` and `password = {% raw %}{{my.password}}{% endraw %}`:

![Resolved properties](/images/password-properties-encrypted-dictionaries-01.png)

### Secret property, regular dictionary

The deployable contains `password = {% raw %}{{my.username}}{% endraw %}`. XL Deploy resolve this property to `password = scott`, which is not a security problem (`tiger` was _already_ a visible value because it is stored in a regular dictionary). However, it will probably fail, because the password is incorrect.

### Normal property, encrypted dictionary

The deployable contains `username = {% raw %}{{my.password}}{% endraw %}`. XL Deploy will **not** resolve this property, because that would leak the password. Instead, it will leave the `username` value blank. If `username` is a required property, this will cause an immediate validation error when XL Deploy attempts to generate the deployment plan.

This screenshot shows the resolved properties with `password = {% raw %}{{my.username}}{% endraw %}` (the value is not visible because passwords are obscured) and `username = {% raw %}{{my.password}}{% endraw %}` (blank because it is a normal property attempting to use a value stored in an encrypted dictionary):

![Resolved properties](/images/password-properties-encrypted-dictionaries-02.png)

## Why don't the values that I entered in an encrypted dictionary appear in normal properties?

As described above, XL Deploy will not resolve a _normal_ property that is set to a placeholder stored in an _encrypted_ dictionary. This protects the value of the placeholder, so it is not visible if someone attempts to reference them in a normal property of a deployable (accidentally or otherwise).

## How can I prevent users from storing sensitive data where they shouldn't?

XL Deploy cannot determine which key in a dictionary "should" have a secret value. Therefore, there is no default validation that attempts to prevent users from entering a value that should be secure into a regular dictionary (for example, putting `my.password` in _NormalDict_ instead of _SecureDict_).

If there is a convention that you can follow in your organization—for example, all keys that contain "password" should have secret values and should _not_ be stored in regular dictionaries—then you can [define a validation rule](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#validation-rules) on regular dictionaries to enforce this convention.

Of course, we highly recommend that you set up [security in XL Deploy](http://docs.xebialabs.com/releases/latest/deployit/securitymanual.html#security-in-xl-deploy) so that all dictionaries—regular and encrypted—can only be viewed by the relevant people and teams. This means that, even if you accidentally store a value that should be secret in a regular dictionary, the number of users who could see the value is limited.
