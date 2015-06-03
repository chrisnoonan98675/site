---
title: JDBC export hooks in XL Release
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

[XL Release export hooks](/xl-release/concept/export-hooks-in-xl-release.html) is a powerful mechanism which allows to define Jython scrips that can do something with completed releases. If you want to insert information into releases into a SQL database, for instance, for reporting purposes, you can use JDBC export hooks that provide you access to `java.sql.Connection` object out of the box.


## 1. Define new export hook

For that you have to add a new synthetic type to `conf/synthetic.xml`. For example:

  <type type="acme.MysqlReportingExportHook" extends="xlrelease.JdbcExportHook" />

Here add `url` as a property of this synthetic type. That allows to change this value in the UI and use in the Jython script. Of course, you can add more properties (e.g. username, password...). For more information about synthetic types please check [this article](/xl-release/how-to/create-custom-configuration-types-in-xl-release.html).

## 2. Create a Jython script

That script will define actions which you want to perform with release. The scope will contain following variables:

* `exportHook`: an object of type [JdbcExportHook](/jython-docs/#!/xl-release/4.7.x/service/com.xebialabs.xlrelease.domain.JdbcExportHook)
* `release`: an object of type [Release](/jython-docs/#!/xl-release/4.7.x/service/com.xebialabs.xlrelease.domain.Release).
* `releaseJson`: a string with serialized release.
* `logger`: an object of type `org.slf4j.Logger`. See [javadoc](http://www.slf4j.org/apidocs/org/slf4j/Logger.html).

Scripts can be placed into `ext` directory, which is a part of the classpath. By default export hook looks for `prefix/TypeName.py` on the classpath. In our case it will be `acme/MysqlReportingExportHook.py`.

Each export hook can have customized script path set into `scriptLocation` property.

    <type type="acme.MysqlReportingExportHook" extends="xlrelease.JdbcExportHook">
      <property name="scriptLocation" hidden="true" default="mypath/MyScript.py" />
    </type>

It's easier to stick to conventions though.

## 3. Create an instance of export hook

Go to `Settings -> Configuration` and configure an instance of your JDBC export hook.


## Other considerations

#### Sample export hook

There is a [sample export hook](https://github.com/xebialabs/xl-release-samples/tree/master/mysql-jdbc-export-hook). Play with it to understand how it works.

#### Order of execution

All export hooks are executed for all completed releases just before they are archived. The order of execution is not guaranteed.

#### Failure handling

Each hook has only one attempt to perform actions on the release. Once failed it's not retried.
