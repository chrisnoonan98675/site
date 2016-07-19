---
title: Using step macros
categories:
- xl-deploy
subject:
- Rules
tags:
- step
- step macro
- rules
since:
- XL Deploy 5.5.0
weight: 131
---

Since XL Deploy 5.5.0, you can define new step primitives by using [predefined step primitives](/xl-deploy/how-to/use-a-predefined-step-in-a-rule.html) such as `jython` and `os-script`. These are called _step macros_. After you define a step macro, you can refer to it by name, the same way you would refer to a predefined step. This allows you to reuse built-in steps and customize them for your system. Step macros can include one or more parameters of any valid XL Deploy type.

You define step macros in the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file. Step macros are registered with the XL Deploy step registry at startup.

**Important:** You can only configure one step in a step macro.

## Define a step macro

This is an example of a simple step macro definition. This XML defines a step macro with the name `wait-for-ssh-connection` that wraps a [`wait`](/xl-deploy/5.5.x/referencesteps.html#wait) step.

{% highlight xml %}
<step-macro name="wait-for-ssh-connection">
    <steps>
        <wait>
            <order>60</order>
            <description>Wait for 25 seconds to make sure SSH connection can be established</description>
            <seconds>25</seconds>
        </wait>
    </steps>
</step-macro>
{% endhighlight %}

Wrapping a `wait` step in a step macro allows you to refer to the step with a name that is relevant to your system.

## Use the step macro

To use the `wait-for-ssh-connection` step, refer to it in the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file:

{% highlight xml %}
<rule name="ec2-wait" scope="deployed">
    <conditions>
        <type>ec2.InstanceSpec</type>
        <operation>CREATE</operation>
    </conditions>
    <steps>
        <wait-for-ssh-connection/>
    </steps>
</rule>
{% endhighlight %}

Now, for each deployed of type `ec2.InstanceSpec`, XL Deploy will add a wait step to the plan.

## Define a step macro with parameters

The `wait-for-ssh-connection` step macro defined above is static; that is, for each instance, it will add a 25-second wait time. You can make it dynamic by defining parameters in the step macro definition. For example, suppose you want to use the SSH wait time defined on the deployed instead of a hard-coded value. Change the step macro definition as follows:

{% highlight xml %}
<step-macro name="wait-for-ssh-connection">
    <parameters>
        <parameter name="sshWaitTime" type="integer" description="Time to wait"/>
    </parameters>
    <steps>
        <wait>
            <order>60</order>
            <description expression="true">"Wait for %d seconds to make sure SSH connection can be established" % (macro['sshWaitTime'])</description>
            <seconds expression="true">macro['sshWaitTime']</seconds>
        </wait>
    </steps>
</step-macro>
{% endhighlight %}

In this example:

* An `sshWaitTime` parameter of type _integer_ been added. The valid types for a step macro parameter are `boolean`, `integer`,`string`, `ci`, `list_of_string`,`set_of_string`, and `map_string_string`.
* The `description` and `seconds` both refer to the `sshWaitTime`. XL Deploy will put the value of `sshWaitTime` in a dictionary with the name `macro`.
* Both `description` and `seconds` are marked as expressions so that they are evaluated by the Jython engine.

You can now refer `wait-for-ssh-connection` step as follows:

{% highlight xml %}
<rule name="ec2-wait" scope="deployed">
    <conditions>
        <type>ec2.InstanceSpec</type>
        <operation>CREATE</operation>
    </conditions>
    <steps>
        <wait-for-ssh-connection>
            <sshWaitTime>25</sshWaitTime>
        </wait-for-ssh-connection>
    </steps>
</rule>
{% endhighlight %}

The value of `sshWaitTime` will be determined from the deployed. The Jython engine will evaluate the `deployed.sshWaitTime` and set the `sshWaitTime` parameter. Now, every deployed can have its own `sshWaitTime` value that will be used as the wait time.

## Using step macros in script rules

You can also use step macros in [script rules](/xl-deploy/how-to/writing-script-rules.html). For example:

{% highlight python %}
step = steps.wait_for_ssh_connection(sshWaitTime=25)
context.addStep(step)
{% endhighlight %}
