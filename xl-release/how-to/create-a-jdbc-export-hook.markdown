---
title: Create a JDBC export hook in XL Release
categories:
- xl-release
subject:
- Archiving and data export
tags:
- export
- reporting
- system administration
- archiving
- purging
since:
- 4.7.0
---

[XL Release export hooks](/xl-release/how-to/create-an-export-hook.html) are a powerful mechanism that allows you to define Jython scrips that can do something with completed releases. You can also create JDBC export hooks. These allow you to insert information about releases into a SQL database; for example, for reporting purposes. JDBC export hooks give you access to the `java.sql.Connection` object.

## Step 1 Define a new export hook

Add a new synthetic type to `conf/synthetic.xml`. For example:

    <type type="acme.MysqlReportingExportHook" extends="xlrelease.JdbcExportHook" />

Add `url` as a property of this synthetic type. This allows you to change the value in the user interface and use it in the Jython script. You can also add more properties, such as a username or password. For more information about synthetic types, refer to [Create custom configuration types in XL Release](/xl-release/how-to/create-custom-configuration-types-in-xl-release.html).

## Step 2 Create a Jython script

The Jython script defines actions that you want to perform with release. The scope contains following variables:

* `exportHook`: An object of type [ExportHook](/jython-docs/#!/xl-release/4.7.x/service/com.xebialabs.xlrelease.domain.ExportHook)
* `release`: An object of type [Release](/jython-docs/#!/xl-release/4.7.x/service/com.xebialabs.xlrelease.domain.Release)
* `releaseJson`: A string with serialized release
* `logger`: An object of type `org.slf4j.Logger`; see [Javadoc](http://www.slf4j.org/apidocs/org/slf4j/Logger.html)

Store scripts in the `ext` directory, which is a part of the classpath. By default, the export hook tries to locate `prefix/TypeName.py` on the classpath. In the case of this example, it will be `acme/MysqlReportingExportHook.py`.

Alternatively, you can set a customized script path in the `scriptLocation` property of each export hook:

    <type type="acme.MysqlReportingExportHook" extends="xlrelease.JdbcExportHook">
      <property name="scriptLocation" hidden="true" default="mypath/MyScript.py" />
    </type>

However, it is recommended that you use the default location of `ext`.

## Step 3 Create an instance of the export hook

In XL Release, go to **Settings** > **Configuration** and configure an instance of your export hook.

## Order of execution

All export hooks are executed for all completed releases just before they are archived. The order of execution is not guaranteed.

## Failure handling

Each export hook has only one attempt to perform actions on the release. If it fails, it is not retried.

## Sample export hook

To learn more about export hooks, download the sample export hook from [GitHub](https://github.com/xebialabs/xl-release-samples/tree/master/mysql-jdbc-export-hook) and customize it as desired.
