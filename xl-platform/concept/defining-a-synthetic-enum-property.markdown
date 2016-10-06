---
title: Defining a synthetic enum property
categories:
- xl-deploy
- xl-release
- xl-testview
subject:
- Customization
tags:
- synthetic
- enum
- typesystem
since:
- XL Deploy 5.2.0
- XL Release 4.8.0
- XL TestView 1.4.0
---

This topic will explain how to create an `enum` kind property fully in `synthetic.xml`. In previous versions of the product(s) you needed to have a compiled Java Enum class on the classpath to define the values of the enumeration. Now you can add the following properties to the `synthetic.xml` file of your plugins / extensions:

    <type ...>
      <property name="myEnum" kind="enum">
        <enum-values>
          <value>value-1</value>
          <value>value-2</value>
        </enum-values>
      </property>
    </type>

Now the UI will show a drop-down box with a choice of `value-1` and `value-2`. And it will automatically be validated that the value entered is one of the choices.
