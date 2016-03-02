---
title: Create a custom control task
categories:
- xl-deploy
subject:
- Control task
tags:
- control task
- java
- ci
- discovery
---

You can define [control tasks](/xl-deploy/how-to/using-control-tasks-in-xl-deploy.html) on configuration items (CIs) that allow you to execute actions from the XL Deploy GUI or CLI. Control tasks specify a list of steps to be executed in order. You can parameterize control tasks in two ways:

* By specifying _arguments_ to the control task in the control task _configuration_
* By allowing the user to specify _parameters_ to the control task during control task _execution_

Arguments are configured in the control task definition in the `synthetic.xml` file. Arguments are specified as attributes on the synthetic method definition XML and are passed as-is to the control task. 

Parameters are specified by defining a parameters CI type.

## Implement a control task as a method

You can implement a control task in Java as a method annotated with the `@ControlTask` annotation. The method returns a `List<Step>` that the server will execute when it is invoked:

{% highlight java %}
@ControlTask(description = "Start the Apache webserver")
public List<Step> start() {
    // Should return actual steps here
    return newArrayList();
}
{% endhighlight %}

## Implement a control task as a delegate

Another way to implement a control task in Java is to use a delegate that is bound via synthetic XML. A delegate is an object with a default constructor that contains one or more methods annotated with `@Delegate`. Those can be used to generate steps for control tasks.

{% highlight java %}
class MyControlTasks {

    public MyControlTasks() {}

    @Delegate(name="startApache")
    public List<Step> start(ConfigurationItem ci, String method, Map<String, String> arguments) {
        // Should return actual steps here
        return newArrayList();
    }
}
{% endhighlight %}

{% highlight xml %}
<type-modification type="www.ApacheHttpdServer">
    <method name="startApache" label="Start the Apache webserver" delegate="startApache" argument1="value1" argument2="value2"/>
</type-modification>
{% endhighlight %}

When the `start` method above is invoked, the arguments `argument1` and `argument2` will be provided in the `arguments` parameter map.

## Control tasks with parameters

Control tasks can have parameters. Parameters can be passed to the task that is started. In this way the control task can use these values during execution. Parameters are normal CIs, but need to extend the `udm.Parameters` CI. This is an example CI that can be used as control task parameter:

{% highlight xml %}
<type type="www.ApacheParameters" extends="udm.Parameters">
    <property name="force" kind="boolean" />
</type>
{% endhighlight %}

This example Parameters CI contains only one property named *force* of kind **Boolean**. To define a control task with parameters on a CI, use the `parameters-type` attribute to specify the CI type:

{% highlight xml %}
<type-modification type="www.ApacheHttpdServer">
    <method name="start" />
    <method name="stop" parameters-type="www.ApacheParameters" />
    <method name="restart">
        <parameters>
            <parameter name="force" kind="boolean" />
        </parameters>
    </method>
</type-modification>
{% endhighlight %}

The `stop` method uses the `www.ApacheParameters` `Parameters` CI we just defined. The `restart` method has an inline definition for its parameters. This is a short notation for creating a Parameters definition. The inline parameters definition is equal to using `www.ApacheParameters`.

Parameters can also be defined in Java classes. To do this you need to specify the `parameterType` element of the `ControlTask` annotation. The `ApacheParameters` class is a CI and remember that it needs to extend the UDM `Parameters` class.

{% highlight java %}
@ControlTask(parameterType = "www.ApacheParameters")
public List<Step> startApache(final ApacheParameters params) {
    // Should return actual steps here
    return newArrayList();
}
{% endhighlight %}

If you want to use the `Parameters` in a delegate, your delegate method specify an additional 4th parameter of type Parameters:

{% highlight java %}
@SuppressWarnings("unchecked")
@Delegate(name = "methodInvoker")
public static List<Step> invokeMethod(ConfigurationItem ci, final String methodName, Map<String, String> arguments, Parameters parameters) {
    // Should return actual steps here
    return newArrayList();
}
{% endhighlight %}

## Discovery

XL Deploy's discovery mechanism is used to discover existing middleware and create them as CIs in the repository.

To enable discovery in a plugin, first indicate that the CI type is discoverable (that is, it can be used as the starting point of the discovery process) by giving it the annotation `Metadata(inspectable = true)`.

Then, indicate where in the repository tree the discoverable CI should be placed by adding an as-containment reference to the parent CI type. This also means that the context menu for the parent CI type will show the `Discover` menu item for your CI type. For example, to indicate that a CI is stored under a `overthere.Host` CI in the repository, define the following field in your CI:

{% highlight java %}
@Property(asContainment=true)
private Host host;
{% endhighlight %}

Finally, implement an inspection method that inspects the environment for an instance of your CI. This method needs to add an inspection step to the given context. For example:

{% highlight java %}
@Inspect
public void inspect(InspectionContext ctx) {
    CliInspectionStep step = new SomeInspectionStep(...);
    ctx.addStep(step);
}
{% endhighlight %}

`SomeInspectionStep` should do two things: inspect properties of the current CIs and discover new ones. Those should be registered in `InspectionContext` with `inspected(ConfigurationItem item)` and `discovered(ConfigurationItem item)` methods respectively.
