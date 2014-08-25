---
title: Understanding Deployables and Deployeds
categories: knowledge-base xl-deploy
tags: deployable deployed package
---

In the [_XL Deploy: Understanding Packages_](http://vimeo.com/99837504) video, we described _Deployables_ - files and settings, delivered in a [deployment package](http://docs.xebialabs.com/releases/4.0/deployit/referencemanual.html#deployment-package), that your application needs to run - and _Deployeds_ - the things that are actually created in your target servers as part of a deployment.

Since these two concepts are central to understanding XL Deploy, and the difference between the two can be subtle, I would like to spend a bit of time talking about them.

### What is the relationship between Deployables and Deployeds?

Every item that is deployed to a target system by XL Deploy - whether that's a file that is copied to a server, an SQL script that is executed against a database, or a virtual host created in a web server - comes from an item in the deployment package that is currently being deployed. In other words, each Deployed has a Deployable as its source. Put a different way, during a deployment each Deployed is "created from" a Deployable.

In that sense, a Deployable can almost be considered as a "request", "template" or "specification" for the Deployeds that will actually be created. The names of many types of Deployables, e.g. [<tt>www.ApacheVirtualHostSpec</tt>](docs.xebialabs.com/releases/latest/deployit/webserverPluginManual.html#wwwapachevirtualhostspec) (note the "spec" at the end) reflect this. Deployables may have a payload, such as a file or folder to be copied to the target server (XL Deploy calls these deployables [_artifacts_](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#artifacts)), or may be "pure" pieces of configuration (these are called [_resources_](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#resource-specifications)).

Note that the relationship between Deployables and Deployeds is one-to-many, i.e. one Deployable in a deployment package can be the source for many Deployeds in the target environment. For example, we can copy a file in the deployment package to many target servers, creating one Deployed per server.

### What are the differences between Deployables and Deployeds?

If we consider a Deployable to be a "template" or "specification" for a Deployed, it is easier to understand a key difference between the two: Deployables may be "incomplete" or "less fully-specified" than the Deployeds that are created from them.

For example, a deployable artifact may consist only of a file or folder payload, which contains a placeholder. When the artifact is deployed, properties such as the target path, and values for the placeholders, must be specified - but these are only required on the Deployed, **not** on the Deployable.

In addition, further properties will become relevant depending on which type of system the file is deployed to. For example, a file copied to a Unix server becomes a _Unix_ file, with Unix-specific attributes such as <tt>owner</tt> and <tt>group</tt>. The same file (i.e. the same Deployable) copied to a Windows server becomes a _Windows_ file, with Windows-specific attributes.

Also, if the file is deployed to multiple Unix servers, each deployed file may have _different_ values for a particular attribute, e.g. a different target path on each server.

In general:

1. the type of a Deployed is different from the type of the Deployable
2. a Deployable type can give rise to different Deployed types, depending on the target system to which it is deployed
3. a Deployed can have more properties than its "source" Deployable
4. multiple Deployeds created from the same Deployable can have different property values, even if they are of the same type

![one-deployable-different-deployed-types.png](/attachments/token/LnSEMXOUi9MzghY5MNbuKibBu/?name=one-deployable-different-deployed-types.png)
**<span style="font-size: 0.9em;">The same <tt>jee.DataSourceSpec</tt> can become a <tt>jbossas.NonTransactionalDatasource</tt> on JBoss and a <tt>tomcat.DataSource</tt> on Tomcat.</span>**

![one-deployable-many-deployeds.png](/attachments/token/1uMQu7MpBVFrJCCRJsFkVVThH/?name=one-deployable-many-deployeds.png)
**<span style="font-size: 0.9em;">The value of the <tt>targetPath</tt> attribute can be different for different Deployeds from the same Deployable</span>**.

Back to our example file: even though we have said that properties such as the target path are required only on the **deployed** file, there may well be cases where we know _when we are packaging up our Deployable_ where it needs to go. That is why the deployable file also contains a <tt>targetPath</tt> property (optional, not mandatory!): if set, its value will be used for all deployed files created from the Deployable.

In other words:

5. properties of Deployables are copied over to corresponding properties of the Deployeds that are created from them
6. properties of Deployeds that have no corresponding property on their source Deployable (you can easily [add these properties](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#extending-a-ci) if you need them), or for which no value is set on the source Deployable, are given default values that depend on the Deployed type

![deployable-deployed-props.png](/attachments/token/uZmdQyI2nChaidees06WONfzv/?name=deployable-deployed-props.png)
**<span style="font-size: 0.9em;">Values of properties of the Deployable are copied to the Deployed if the property name matches. Some properties of Deployeds have no corresponding properties on a Deployable.</span>**

Speaking of specifying the target path for a file to be copied up front: in a realistic scenario, it will often be the case that we don't know the entire path when we package up the Deployable. For instance, we may know that the file needs to be copied to <tt>&lt;install-dir&gt;/bin</tt> - we know the <tt>/bin</tt> part, but <tt>&lt;install-dir&gt;</tt> may be different for each environment. We can accomplish this in XL Deploy by using a placeholder for the environment-specific part of the property, e.g. <tt>{{INSTALL_DIR}}/bin</tt>.

This means:

7. Deployables should be independent of the target environment. Where properties of a Deployable need to vary per target environment, they can be specified using placeholders
8. when a Deployed is created from a Deployable whose properties contain placeholders, these placeholders are automatically resolved to actual values defined on the target environment or container

We’re almost there! Just three further points we should discuss in relation to Deployables and Deployeds:

9. Deployed properties are subject to [validation rules](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#defining-validation-rules), Deployable properties generally are not. Because a Deployable by its very nature can be incomplete, it usually does not make sense to try to validate it. After all, you only need to be sure that you have all required information at the moment that you want to _create_ something from the Deployable, i.e. at the moment we're creating a Deployed based on that Deployable.

You will notice that, in XL Deploy, most properties that are required on Deployeds are not required on the corresponding Deployable. They can either be supplied by [defaults](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#modifying-existing-cis), or you can specify them "just in time", i.e. when [putting together the deployment specification](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#initial-deployment). This does mean, however, that the deployment requires manual intervention, so cannot be carried out via e.g. the [Jenkins](http://docs.xebialabs.com/releases/latest/xldeploy-plugin-plugin/jenkinsPluginManual.html) or [Maven](http://tech.xebialabs.com/deployit-maven-plugin/4.0.0/) plugins).

10. Deployed properties can have [various kinds](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#modifying-existing-cis) (strings, numbers etc.), but the corresponding properties on the Deployables, where present, are all strings. This is because e.g. the value of a numeric property of the Deployed may be environment-specific, so we will want to use a placeholder in the Deployable. Since placeholders are specified as strings in XL Deploy, the property on the Deployable has to be a string property for this to work.
![deployable-vs-deployed-property-attributes.png](/attachments/token/mrQP1cqBpXjYa5Tc56iMufd6P/?name=deployable-vs-deployed-property-attributes.png)
**<span style="font-size: 0.9em;">Properties are required on the Deployed, but usually optional on the Deployable. Even if a property on the Deployed is a number or, as here, a boolean, the corresponding property on the Deployable is a string, so placeholders can be used. Placeholders are replaced with the appropriate values for the environment on the Deployed.</span>**

### How does XL Deploy work with Deployables and Deployeds?

Now that we have discussed how Deployables and Deployeds are related, and what the differences between the two are, let's talk briefly about how XL Deploy actually _uses_ them (more details in the [_Extending XL Deploy_ guide](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#preparing-and-performing-deployments-in-xl-deploy)).

XL Deploy uses Deployeds - or, more specifically, the _changes_ you ask to be made to Deployeds - to figure out which steps need to be added to the deployment plan. These steps will be different depending on the type of change and the type of Deployed being created/modified/removed: _creating_ a new Deployed usually requires different actions from changing a property on an existing Deployed (a _MODIFY_ action, in XL Deploy terminology).

Note that the steps we are talking about here depend on changes to the **Deployeds**, not the Deployables: after all, _these_ are the things we are trying to create, modify or remove during a deployment. Deployables can have behaviour too, but this is not what is happening during a deployment plan. This is why the vast majority the out-of-the-box content in XL Deploy's plugins relates to Deployeds.