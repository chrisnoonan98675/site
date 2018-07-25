---
title: Perform hot deployments
categories:
xl-deploy
subject:
Deployment
tags:
deployment pattern
orchestration
---

This guide explains how to perform "hot" deployments with XL Deploy. Hot deployment is the practice of updating an application without restarting infrastructure or middleware components.

This approach normally relies on the technology being able to accommodate updates without restarting; for example, Red Hat JBoss Application Server (AS) implements this functionality by [scanning a directory](https://docs.jboss.org/author/display/AS7/Application+deployment) for changes and automatically deploying any changes that it detects.

By default, the [JBoss AS plugin for XL Deploy](/xl-deploy/concept/jboss-application-server-plugin.html) restarts the target server when a deployment takes place. You can change this behavior by preventing the restart and specifying the hot deploy directory as a target.

This sample section of a [`synthetic.xml`](/xl-deploy/how-to/customize-an-existing-ci-type.html) file makes the `restartRequired` property available and assigns the `/home/deployer/install-files` directory to the `targetDirectory` property for the `jbossas.EarModule` configuration item (CI) type:

{% highlight xml %}
<type-modification type="jbossas.EarModule">
  <!-make it visible so that I can control whether to restart a Server or not from UI-->
  <property name="restartRequired" kind="boolean" default="true" hidden="false"/>
  
  <!-custom deploy directory for my jboss applications -->
  <property name="targetDirectory" default="/home/deployer/install-files" hidden="true"/>
</type-modification>
{% endhighlight %}

For more information, refer to [Extending the JBoss Application Server plugin](/xl-deploy/how-to/extend-the-xl-deploy-jboss-application-server-plugin.html).
