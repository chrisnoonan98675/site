---
title: Define a synthetic method
categories:
- xl-deploy
subject:
- Customization
tags:
- ci
- synthetic
- control task
- plugin
---

In XL Deploy, you can define _methods_ on configuration items (CIs). Each method can be executed on an instance of a CI via the GUI or CLI. Methods are used to implement _control tasks_, actions on CIs to control the middleware. An example is starting or stopping of a server. The CI itself is responsible for implementing the specified method, either in Java or synthetically when extending an existing plugin such as the Generic plugin.

This XML snippet shows how to define a control task:

    <type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
          container-type="tc.Server">
        <generate-deployable type="tc.DataSource" extends="generic.Resource"/>
        ...
        <method name="ping" description="Test whether the datasource is available"/>
    </type>

The _ping_ method defined above can be invoked on an instance of the `tc.DeployedDataSource` CI through the server REST interface, GUI or CLI. The implementation of the _ping_ method is part of the `tc.DeployedDataSource` CI.
