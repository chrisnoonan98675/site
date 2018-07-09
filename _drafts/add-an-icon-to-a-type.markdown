---
title: Add an icon to a type
categories:
- xl-deploy
subject:
- Customization
tags:
- ci
- synthetic
- type system
- icon
since:
- XL Deploy 5.5.0
---

In the XL Deploy user interface, a small icon usually appears next to configuration items (CIs), indicating their types. The icon that is used is defined in the type system. Every type can optionally define one icon, which all instances of that type will use.

If a type does not specify an icon, XL Deploy uses the icon of the parent type. At the root of the type system, there is a default icon defined for the `udm.BaseConfigurationItem` type.

## Define an icon

To define the icon for a CI type, you provide a relative path to the icon file.

The icon can be defined by providing a relative path to the icon. For more information about the relative path please continue to read 'How to bundle icons with a plugin'

### Define an icon in `synthetic.xml`

This example shows how to define an icon in the `synthetic.xml` file:

    <type type="example.MyConfigurationItem" extends="udm.BaseConfigurationItem">
        <icon>icons/types/example.MyConfigurationItem.svg</icon>
        ...
    </type>

The icon is specified in a `<icon>` node in the type definition.

### Define an icon using a Java annotation

This example shows how to define an icon using a Java annotation:

    @Metadata(description = "This is an example of a type that defines an icon.")
    @TypeIcon(value = "icons/types/example.MyConfigurationItem.svg")
    public class MyConfigurationItem extends BaseConfigurationItem {
        ...
    }

The `value` specifies the relative path. The annotation can be combined with the MetaData `annotation`.

## Bundle icons with a plugin

Icons must be stored in the `web` folder at the root of the plugin. All files in that folder will be used as resources for the user interface. To refer to the icon, use its path, excluding the `web` folder.

For example, the following icon:

    <plugin root>/web/icons/types/example.MyConfigurationItem.svg

Can be referenced in this way:

    <icon>icons/types/example.MyConfigurationItem.svg</icon>

The XL Deploy server will access the icon at:

    http://[deployit-server]/[context]/icons/types/example.MyConfigurationItem.svg

## Icon image type and dimensions

XL Deploy does not restrict the image format, size, or dimensions of icons. It will attempt to scale the image to 24x24 pixels. Whether the icon appears as expected depends on the image format that you choose, and on the browser that you use. It is recommended that you use SVG format, because it scales easily. XL Deploy's default icons use SVG format.

## Limitations

You cannot redefine icons, and they cannot be overridden through a type modification. If you want to redefine the icon of a specific type, you must define a subtype.

It is possible that XL Deploy will not show or respect icons in certain user interface components.
