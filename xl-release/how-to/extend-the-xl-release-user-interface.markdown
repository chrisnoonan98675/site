---
title: Extend the XL Release user interface
categories:
- xl-release
subject:
- Extending XL Release
tags:
- jython
- gui
since:
- XL Release 6.0.0
---

You can extend XL Release by creating new endpoints backed by Jython scripts and new UI screens that use them. Each extension must be packaged in a `jar` archive and saved in the `plugins` folder of the XL Release server.

**Tip:** It is recommended that you create a folder under `web` with a unique name for each UI extension plugin, to avoid file name collisions.

The following XML files tell XL Release where to find and how to interpret the content of an extension:

* `xl-rest-endpoints.xml` for adding new REST endpoints
* `xl-ui-plugin.xml` for adding new top-menu items

These files are both optional.

## Adding menu items to XL Release

The `xl-ui-plugin.xml` file contains information about the menu items to add to XL Release. You can order them using the `weight` attribute.

For example, `xl-ui-plugin.xml` could contain:

{% highlight xml %}
<plugin xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.xebialabs.com/deployit/ui-plugin"
        xsi:schemaLocation="http://www.xebialabs.com/deployit/ui-plugin xl-ui-plugin.xsd">

    <menu-ref ref="xlrelease.menu.Main">
        <menu id="xlrelease.menu.Custom" label="Custom" weight="45">
            <menu-item label="Releases list" uri="include/ReleasesListPage/index.html" weight="10" path-suffix="custom/releases">
                <property name="permissions" value="security#edit" />
            </menu-item>
        </menu>
    </menu-ref>

    <library name="xlrelease.releasesListPage"/>

</plugin>
{% endhighlight %}

Menus are enclosed in the `<plugin>` tag. The `xl-ui-plugin.xsd` schema verifies the way that menus are defined.

## Referring to predefined menu items

`<menu-ref>` allows you to refer to the predefined XL Release menus. Predefined menus have structure similar to:

{% highlight xml %}
<menu id="xlrelease.menu.Main" label="" weight="-1">
    <menu-item label="Tasks" path-suffix="tasks" weight="10" uri=""/>
    <menu id="xlrelease.menu.Releases" label="Releases" weight="20">
        <menu-item label="Overview" path-suffix="releases" weight="10" uri=""/>
        <menu-item label="Pipeline" path-suffix="pipeline" weight="20" uri=""/>
        <menu-item label="Templates" path-suffix="templates" weight="30" uri=""/>
    </menu>
    <menu id="xlrelease.menu.Reports" label="Reports" weight="40">
        <menu-item label="Dashboard" path-suffix="dashboard" weight="10" uri="" />
        <menu-item label="Release automation" path-suffix="release-automation" weight="20" uri="" />
        <menu-item label="Release Value Stream" path-suffix="release-value-stream" weight="30" uri="" />
    </menu>
    <menu id="xlrelease.menu.Settings" label="Settings" weight="50">
        <menu-item label="Profile" path-suffix="profile" weight="10" uri=""/>
        <menu-item label="Global variables" path-suffix="global-variables" weight="20" uri=""/>
        <!-- ... other menu items ... -->
        <menu-item label="Configuration" path-suffix="configuration" weight="100" uri="">
            <property name="permissions" value="admin"/>
        </menu-item>
    </menu>
</menu>
{% endhighlight %}

In this example, `<menu-ref ref="xlrelease.menu.Main">` defines an extension for XL Release's main menu, which has the ID `xlrelease.menu.Main`.

`<menu id="xlrelease.menu.Custom" label="Custom" weight="45">` adds a new menu with ID `xlrelease.menu.Custom`. This menu will have the label `Custom` and will be placed between the menus with IDs `xlrelease.menu.Reports` and `xlrelease.menu.Settings`, because weight 45 is between their respective weights.

## Nesting menu references

You can nest `<menu-ref>` items. This example shows how to add an item to a nested menu (in this case, `xlrelease.menu.Reports`):

{% highlight xml %}
<plugin xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="http://www.xebialabs.com/deployit/ui-plugin"
        xsi:schemaLocation="http://www.xebialabs.com/deployit/ui-plugin xl-ui-plugin.xsd">

    <menu-ref ref="xlrelease.menu.Main">
        <menu-ref ref="xlrelease.menu.Reports">
            <menu-item label="Pending releases by team" ... />
        </menu-ref>
    </menu-ref>
    ...
{% endhighlight %}

## Menu elements

### `<menu>` attributes

The attributes that are available for `<menu>` are:

{:.table .table-bordered}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `id`      | Yes | Menu item ID, which must be unique among all menu items in XL Release; if there are duplicate IDs, XL Release will return a RuntimeException. |
| `label`   | Yes | Text to show on the menu button. |
| `uri`     | Yes | Link that will be used to fetch the content of the extension. The link must point exactly to the file that the browser should load. Default pages such as `index.html` are not guaranteed to load automatically. |
| `weight`  | Yes | Menu item order; the higher the weight, the further to the right the menu item will be placed. |

`<menu>` can contain `<menu>`, [`<menu-item>`](#menu-item-attributes), and [`<menu-separator>`](#menu-separator-attributes) elements.

### `<menu-separator>` attributes

The attributes that are available for `<menu-separator>` are:

{:.table .table-bordered}
| Attribute     | Required | Description |
| ------------- | -------- | ----------- |
| `weight`      | Yes      | Menu separator order. |

**Note:** Menu separators are not rendered in the XL Release user interface.

### `<menu-ref>` attributes

The attributes that are available for `<menu-ref>` are:

{:.table .table-bordered}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `ref`     | Yes      | ID of the menu item that will be extended. This must refer to an existing menu in the XL Release. |

`<menu-ref>` can contain [`<menu>`](#menu-attributes), [`<menu-item>`](#menu-item-attributes), or [`<menu-separator>`](#menu-separator-attributes) elements, as well as other `<menu-ref>` elements.

### `<menu-item>` attributes

The attributes that are available for `<menu-item>` are:

{:.table .table-bordered}
| Attribute     | Required | Description |
| ------------- | -------- | ----------- |
| `label`       | Yes      | Text to show on the menu button. |
| `uri`         | Yes      | Link that will be used to fetch the content of the extension. The link must point exactly to the file that the browser should load. Default pages such as `index.html` are not guaranteed to load automatically. |
| `weight`      | Yes      | Menu item order; the higher the weight, the further in the menu item will be placed. |
| `path-suffix` | Yes      | Suffix that the menu item in the URL will use. |

`<menu-item>` elements can contain [`<property>`](#property-attributes) elements.

### `<property>` attributes

Each menu item can contain multiple `<property>` elements that you can use to enhance functionality. The attributes that are available for `<property>` are:

{:.table .table-bordered}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `name`    | Yes      | Name of the property exposed on a menu item. |
| `value`   | Yes      | Value of the property. |

Currently, the only supported property is named `permissions`. It allows you to provide a comma-separated list of [global](/xl-release/how-to/configure-permissions.html) or [release](/xl-release/how-to/configure-permissions-for-a-release.html) permissions; if a user has any of the permissions in the list, then he or she can access the menu item. Global permissions are checked for the top-level menu, while release permissions are checked for the Release page submenu.

`permissions` can have the following values:

{:.table .table-bordered}
| Permission              | Scope    | Description |
| ----------              | -------- | ----------- |
| admin                   | Global   | All permissions |
| login                   | Global   | Permission to log in to XL Release |
| security#edit           | Global   | Access to the Roles and Permissions screens and permission to edit security on releases and templates |
| template#create         | Global   | Permission to create a new template |
| release#create          | Global   | Permission to create a release from any template; also see the Create Release permission on a single release |
| reports#view            | Global   | Permission to view reports |
| global_variables#edit   | Global   | Permission to edit global variables (available in XL Release 4.8.0 and later) |
| template#create_release | Template | Permission to create a release from the template |
| template#view           | Template | Permission to view the template |
| template#edit           | Template | Permission to edit the template |
| template#edit_security  | Template | Permission to edit the template security settings |
| release#view            | Release  | Permission to view the release |
| release#edit            | Release  | Permission to edit the release |
| release#edit_security   | Release  | Permission to edit the release security settings |
| release#start           | Release  | Permission to start the release |
| release#abort           | Release  | Permission to abort the release |
| release#edit_task       | Release  | Permission to edit task in the release |
| release#reassign_task   | Release  | Permission to reassign task in the release |

### `<library>` attributes

The attributes that are available for `<library>` are:

{:.table .table-bordered}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `name` | Yes | Name of the Angular module (extension library) that the extension should load. |
