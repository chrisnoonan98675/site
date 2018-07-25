---
title: Create a custom validation rule
categories:
- xl-deploy
subject:
- Configuration items
tags:
- validation rule
- java
- ci
weight: 256
---

You can add validation rules to properties and configuration items (CIs) in the `synthetic.xml`. Out of the box, XL Deploy comes with the `regex` validation rule, which can be used to define naming conventions using regular expressions.

This XML snippet shows how to add a validation rule:

{% highlight xml %}
<type type="tc.WarModule" extends="ud.BaseDeployedArtifact" deployable-type="jee.War"
       container-type="tc.Server">
    <property name="changeTicketNumber" required="true">
        <rule type="regex" pattern="^JIRA-[0-9]+$"
           message="Ticket number should be of the form JIRA-[number]"/>
    </property>
</type>
{% endhighlight %}

The validation will throw an error, when the `tc.WarModule` is being saved in XL Deploy with a value that is not of the form `JIRA-[number]`.

## Define a validation rule in Java

You can define XL Deploy validation rules in Java. These can then be used to annotate CIs or their properties so that XL Deploy can perform validations.

This is an example of a property validation rule called `static-content` that validates that a string kind field has a specific fixed value:

{% highlight java %}
import com.xebialabs.deployit.plugin.api.validation.Rule;
import com.xebialabs.deployit.plugin.api.validation.ValidationContext;
import com.xebialabs.deployit.plugin.api.validation.ApplicableTo;
import com.xebialabs.deployit.plugin.api.reflect.PropertyKind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApplicableTo(PropertyKind.STRING)
@Retention(RetentionPolicy.RUNTIME)
@Rule(clazz = StaticContent.Validator.class, type = "static-content")
@Target(ElementType.FIELD)
public @interface StaticContent {
    String content();

    public static class Validator
           implements com.xebialabs.deployit.plugin.api.validation.Validator<String> {
        private String content;

        @Override
        public void validate(String value, ValidationContext context) {
            if (value != null && !value.equals(content)) {
                context.error("Value should be %s but was %s", content, value);
            }
        }
    }
}
{% endhighlight %}

A validation rule consists of an annotation, in this case `@StaticContent`, which is associated with an implementation of `com.xebialabs.deployit.plugin.api.validation.Validator<T>`. They are associated using the `@com.xebialabs.deployit.plugin.api.validation.Rule` annotation. Each method of the annotation needs to be present in the validator as a property with the same name, see the `content` field and property above. It is possible to limit the kinds of properties that a validation rule can be applied to by annotating it with the `@ApplicableTo` annotation and providing that with the allowed property kinds.

When you've defined this validation rule, you can use it to annotate a CI like such:

{% highlight java %}
public class MyLinuxHost extends BaseContainer {
    @Property
    @StaticContent(content = "/tmp")
    private String temporaryDirectory;
}
{% endhighlight %}

Or you can use it in synthetic XML in the following way:

{% highlight xml %}
<type name="ext.MyLinuxHost" extends="udm.BaseContainer">
    <property name="temporaryDirectory">
        <rule type="static-content" content="/tmp"/>
    </property>
</type>
{% endhighlight %}
