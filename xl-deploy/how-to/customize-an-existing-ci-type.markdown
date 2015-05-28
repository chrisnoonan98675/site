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

Types that exist in XL Deploy can be modified to contain additional synthetic properties. These properties become a part of the CI type and can be specified in the deployment package and shown in the XL Deploy GUI.

There are several reasons to modify a CI:

* A CI property is always given the same value in your environment. Using synthetic properties, the property can be given a default value and hidden from the user in the GUI.
* There are additional properties of an existing CI that you want to specify. For example, suppose there is a CI representing a deployed datasource for a specific middleware platform. The middleware platform allows the user to specify a connection pool size and connection timeout and XL Deploy supports the connection pool size out of the box. In this case, modifying the CI to add a synthetic property allows the user to specify the connection timeout.

**Note**: To use the newly defined property in a deployment, XL Deploy's behavior must be modified. To learn how to add new behavior to the product, refer to [Understanding XL Deploy rules](/xl-deploy/concept/understanding-xl-deploy-rules.html).

Additionally, any property that is modified is listed as a nested `property` element. For each property, the following information can be specified:

{:.table .table-striped}
| Property | Required | Description |
| -------- | -------- | ----------- |
| `name` | Yes | The name of the property to modify. |
| `kind` | No | The type of the property to modify. Possible values are: `enum`, `boolean`, `integer`, `string`, `ci`, `set_of_ci`, `set_of_string`, `map_string_string`, `list_of_ci`, `list_of_string`, and `date` (internal use only). |
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

    <type-modification type="base.Host">
        <property name="connectionTimeoutMillis" kind="integer" default="1200000" hidden="true" />
    </type-modification>

## Extend a CI

The following example adds a "notes" field to a CI to record notes:

    <type-modification type="overthere.Host">
        <property name="notes" kind="string"/>
    </type-modification>
