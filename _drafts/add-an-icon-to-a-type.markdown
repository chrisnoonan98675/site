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
---

In the user interface, configuration items are sometimes accompanied with a small icon that resembles its type. 
The icon used is defined in the type system. Every type can define one icon. All instances of that type will use that icon.
If a type does not specify an icon, the icon of the parent type will be used. At the root of the type hierarchy the default icon is configured for `udm.BaseConfigurationItem`.

## Defining an icon
The icon can be defined by providing a relative path to the icon. For more information about the relative path please continue to read 'How to bundle icons with a plugin'

* Example using synthetic.xml:


    <type type="example.MyConfigurationItem" extends="udm.BaseConfigurationItem">
        <icon>icons/types/example.MyConfigurationItem.svg</icon>
        ...
    </type>

The icon is specified in a `<icon>` node inside the type definition.

* Example using a java annotation:


    @Metadata(description = "This is an example of a type that defines an icon.")
    @TypeIcon(value = "icons/types/example.MyConfigurationItem.svg")
    public class MyConfigurationItem extends BaseConfigurationItem {
        ...
    }

The `value` specifies the relative path. The annotation can be combined with the MetaData `annotation`.

## How to bundle icons with a plugin
Icons need to be put in the `web` folder at the root of your plugin. All files in that folder will be server as resources for the user interface.
To reference the icon, use the path of the icon, excluding the `web` folder. 

Example:

`<plugin root>/web/icons/types/example.MyConfigurationItem.svg`

Can be referenced in this way:

`<icon>icons/types/example.MyConfigurationItem.svg</icon>`

Will be accessible by the XL Deploy server at (you do not need to remember this, its just to show the complete picture):

`http://[deployit-server]/[context]/icons/types/example.MyConfigurationItem.svg`

## Icon image type
The system does not restrict you in which image format, size or dimensions to use. Just remember that the icon will be shown by the browser.
The application will try to scale the image to a dimension of 24 by 24 pixels.

Whether the icon will be displayed as expected will depend on the image format chosen, and the browser used. We suggest to use SVG format, 
because this format is easy to scale. This format is also used for the icons of XL Deploy.

## Limitations 
Icons can not be re-defined. Icons can not be overridden when doing a type-modification. If you want to redefine the icon of a specific type,
you can define a subtype. Icons might also not always be shown or respected. This will depend on the user interface component.
