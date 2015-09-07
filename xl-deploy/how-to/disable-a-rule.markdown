---
title: Disable a rule
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- planning
- step
since:
- 4.5.0
---

You can disable any rule that is registered in XL Deploy's rule registry, including rules that are:

* Predefined in XL Deploy
* Defined in the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file
* Defined in `xl-rules.xml` files in plugin JARs

To disable a rule, add the `disable-rule` tag under the `rules` tag in `xl-rules.xml`. You identify the rule that you want to disable by its name (this is why rule names must be unique).

For example, to disable a rule with the name `deployArtifact`, use:

{% highlight xml %}
<?xml version="1.0"?>
<rules xmlns="http://www.xebialabs.com/xl-deploy/xl-rules">
    <disable-rule name="deployArtifact" />
</rules>
{% endhighlight %}

## Predefined rule naming

You can disable the rules that are predefined by Java classes in XL Deploy. Each of the methods that used to define steps is translated into a corresponding rule. This section describes the naming convention for each type of predefined rule.

### Deployed system rules

All methods of deployed classes that are annotated with `@Create`, `@Modify`, `@Destroy`, `@Noop` annotations. The name of the rule is given by concatenation of the UDM type of the deployed class, the method name, and annotation name. For example:

{% highlight java %}
file.DeployedArtifactOnHost.executeCreate_CREATE
{% endhighlight %}

### Contributor system rules

All methods that are annotated with `@Contributor` annotations. The rule name is defined by concatenation of the full class name and method name. For example:

{% highlight java %}
com.xebialabs.deployit.plugin.generic.container.LifeCycleContributor.restartContainers
{% endhighlight %}

### Pre-plan and post-plan system rules

All methods that are annotated with `@PrePlanProcessor` or `@PostPlanProcessor` annotations. The rule name is defined by concatenation of the full class name and method name. For example:

{% highlight java %}
com.xebialabs.deployit.plugins.releaseauth.planning.CheckReleaseConditionsAreMet.validate
{% endhighlight %}
