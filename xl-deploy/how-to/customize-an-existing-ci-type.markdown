---
title: Customize an existing CI type
categories:
- xl-deploy
subject:
- Configuration items
tags:
- ci
- synthetic
- type system
weight: 252
---

XL Deploy's type system allows you to customize any configuration item (CI) type by adding, hiding, or changing its properties. These properties become a part of the CI type and can be specified in the deployment package (DAR file) and shown in the XL Deploy GUI.

New CI type properties are called _synthetic properties_ because they are not defined in a Java class. You define properties and make changes in an XML file called `synthetic.xml` which is added to the XL Deploy classpath. Changes to the CI types are loaded when the XL Deploy server starts.

There are several reasons to modify a CI type:

* A CI property is always given the same value in your environment. Using synthetic properties, you can give the property a default value and hide it in the GUI.
* There are additional properties of an existing CI that you want to specify.

    For example, suppose there is a CI representing a deployed datasource for a specific middleware platform. The middleware platform allows you to specify a connection pool size and connection timeout, but XL Deploy only supports the connection pool size by default. In this case, modifying the CI to add a synthetic property allows you to specify the connection timeout.

**Note:** To use a newly defined property in a deployment, you must modify XL Deploy's behavior. To learn how to do so, refer to [Understanding XL Deploy rules](/xl-deploy/concept/understanding-xl-deploy-rules.html).

## Specify CI properties

For each CI, you must specify a `type`. Any property that is modified is listed as a nested `property` element. For each property, the following information can be specified:

{:.table .table-striped}
| Property | Required | Description | Notes |
| -------- | -------- | ----------- | ----- |
| `name` | Yes | The name of the property to modify. | |
| `kind` | No | The type of the property to modify. Possible values are: `enum`, `boolean`, `integer`, `string`, `ci`, `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, `list_of_string`, and `date` (internal use only). | You must always specify the `kind` of the parent CI. You can find the `kind` next to the property name in the plugin reference documentation. |
| `description` | No | Describes the property. | |
| `category` | No | Categorizes the property. Each category is shown in a separate tab in the XL Deploy GUI. | |
| `label` | No | Sets the property's label. If set, the label is shown in the XL Deploy GUI instead of the name. | |
| `required` | No | Indicates whether the property is required or not. | You cannot change the `required` attribute of an existing CI; that is, if a CI's `required` property is set to "true", you cannot later change it to "false". |
| `size` | No | Specifies the property size. Possible values are: `default`, `small`, `medium`, and `large`. Large text fields will be shown as a text area in the XL Deploy GUI. | Only relevant for properties of kind `string`. |
| `default` | No | Specifies the default value of the property. | |
| `enum-class` | No | The enumeration class that contains the possible values for this property. | Only relevant for properties of kind `enum`. |
| `referenced-type` | No | The type of the referenced CI. | Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`. |
| `as-containment` | No | Indicates whether the property is modeled as containment in the repository. If true, the referenced CI or CIs are stored under the parent CI. | Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`. |
| `hidden` | No | Indicates whether the property is hidden, which means that it does not appear in the  XL Deploy GUI and cannot be set by the manifest or by the Jenkins, Maven, or Bamboo plugin. | A hidden property must have a default value. |
| `transient` | No | Indicates whether the property is persisted in the repository or not. | |
| `inspectionProperty` | No | Indicates that this property is used for inspection (discovery). | |

**Note:** For security reasons, the `password` property of a CI cannot be modified.

## Hide a CI property

The following example hides the `connectionTimeoutMillis` property for `Hosts` from the UI and gives it a default value:

{% highlight xml %}
<type-modification type="base.Host">
    <property name="connectionTimeoutMillis" kind="integer" default="1200000" hidden="true" />
</type-modification>
{% endhighlight %}

## Extend a CI

The following example adds a "notes" field to a CI to record notes:

{% highlight xml %}
<type-modification type="overthere.Host">
    <property name="notes" kind="string"/>
</type-modification>
{% endhighlight %}

## Change a default value

If you add a type modification to a CI with a default value and then change that value, CIs that were created before the modification will not pick up the new default value. For example:

1. Define an `overthere.SshHost` CI called _HostA_.
1. Add the following type modification:

        <type-modification type="overthere.SshHost">
            <property name="important" kind="string" default="no" hidden="false" />
        </type-modification>

1. Restart XL Deploy.

    HostA now has a property called `important`, which contains the value "no".

1. Add a new `overthere.SshHost` CI called _HostB_. It also has the `important` property with value "no".
1. Change the default value of the `important` property:

        <type-modification type="overthere.SshHost">
            <property name="important" kind="string" default="probably" hidden="false" />
        </type-modification>

1. Restart XL Deploy.
1. The value of the `important` property in HostA is now "probably", while the value of the `important` property in HostB is still "no".

This is because HostA was created before the `important` property was added, while HostB was created afterwards. HostA does not actually know about the `important` property, although it appears in the repository (with its default value) for display purposes. However, HostB is aware of the `important` property, so its value will be persisted.

To ensure that the `important` value in HostA is persisted, you must open HostA in the repository and then save it.
