---
title: Customize an existing CI type
categories:
- xl-deploy
subject:
- Customization
tags:
- ci
- synthetic
- type system
---

You can customize XL Deploy configuration item (CI) types to contain additional synthetic properties. These properties become a part of the CI type and can be specified in the deployment package (DAR file) and shown in the XL Deploy GUI.

There are several reasons to modify a CI:

* A CI property is always given the same value in your environment. Using synthetic properties, you can give the property a default value and hide it in the GUI.
* There are additional properties of an existing CI that you want to specify.

    For example, suppose there is a CI representing a deployed datasource for a specific middleware platform. The middleware platform allows you to specify a connection pool size and connection timeout, but XL Deploy only supports the connection pool size by default. In this case, modifying the CI to add a synthetic property allows you to specify the connection timeout.

**Note:** To use a newly defined property in a deployment, you must modify XL Deploy's behavior. To learn how to do so, refer to [Understanding XL Deploy rules](/xl-deploy/concept/understanding-xl-deploy-rules.html).

## Specify CI properties

For each CI, you must specify a `type`. Any property that is modified is listed as a nested `property` element. For each property, the following information can be specified:

{:.table .table-striped}
| Property | Required | Description |
| -------- | -------- | ----------- |
| `name` | Yes | The name of the property to modify. |
| `kind` | No | The type of the property to modify. Possible values are: `enum`, `boolean`, `integer`, `string`, `ci`, `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, `list_of_string`, and `date` (internal use only). You must always specify the `kind` of the parent CI. You can find the `kind` next to the property name in the plugin reference documentation. |
| `description` | No | Describes the property. |
| `category` | No | Categorizes the property. Each category is shown in a separate tab in the XL Deploy GUI. |
| `label` | No | Sets the property's label. If set, the label is shown in the XL Deploy GUI instead of the name. |
| `required` | No | Indicates whether the property is required or not. Note that you cannot modify the required attribute of existing CIs. |
| `size` | No | Specifies the property size. Possible values are: `default`, `small`, `medium`, and `large`. Large text fields will be shown as a text area in the XL Deploy GUI. *Only relevant for properties of kind `string`.* |
| `default` | No | Specifies the default value of the property. |
| `enum-class` | No | The enumeration class that contains the possible values for this property. *Only relevant for properties of kind `enum`.* |
| `referenced-type` | No | The type of the referenced CI. *Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`.* |
| `as-containment` | No | Indicates whether the property is modeled as containment in the repository. If true, the referenced CI or CIs are stored under the parent CI. *Only relevant for properties of kind `ci`, `set_of_ci`, or `list_of_ci`.* |
| `hidden` | No | Indicates whether the property is hidden. Hidden properties do not appear in the XL Deploy GUI. Note that a hidden property must have a default value. |
| `transient` | No | Indicates whether the property is persisted in the repository or not. |
| `inspectionProperty` | No | Indicates that this property is used for inspection (discovery). |

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
