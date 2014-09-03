---
title: How to create, read, update and delete configuration items using the XL Deploy REST API
categories:
 
- xl-deploy
tags:
- API
- configuration-item
- CI
---

One of the common maintenance activities when running XL Deploy is adding, modifying and removing items from its [repository](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#repository). This is especially the case for infrastructure and environment [configuration items](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#configuration-items-cis) (CIs) that are not published to XL Deploy automatically in the way [deployment packages](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployment-package) usually are.

Of course, you want to automate these kind of activities as much as possible, too. Many of our users (primarily those not using [XL Scale](http://xebialabs.com/products/xl-scale/) ;-)) have linked XL Deploy to their cloud management and provisioning tools so that, for example, a new virtual machine automatically "registers itself" with XL Deploy by means of a post-create hook.

Here, we'll use <tt>curl</tt> to go through an example of the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html) calls you'll need to make such a scenario work, specifically:

*   [Creating a new CI](#create-a-new-ci)
*   [Reading an existing CI from the repository](#read-existing-ci)
*   [Updating the properties of an existing CI](#update-ci-properties)
*   [Deleting a CI from the repository](#delete-a-ci)

### <a name="create-a-new-ci"></a>Creating a new CI

Use&nbsp;[<tt>POST /repository/ci/{ID:.*?}</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST)&nbsp;to create a new CI. Here, we'll create a new <tt>overthere.LocalHost</tt> CI with ID <tt>Infrastructure/my-new-ci</tt>:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; -X POST -H "Content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/repository/ci/Infrastructure/my-new-ci -d@/tmp/read-ci.xml
> 
> **Contents of /tmp/read-ci.xml:**
> 
> &lt;overthere.LocalHost id="Infrastructure/my-new-ci"&gt;
> &nbsp; &lt;os&gt;UNIX&lt;/os&gt;
> &lt;/overthere.LocalHost&gt;
> 
> **OUTPUT:**
> 
> &lt;overthere.LocalHost id="Infrastructure/my-new-ci" token="61cfc7c5-9ea5-4883-9a98-ae721f678aaa"&gt;
> &nbsp; &lt;os&gt;UNIX&lt;/os&gt;
> &lt;/overthere.LocalHost&gt;

The description of the CI as it was actually saved is returned. This may differ from the input XML, e.g. it may contain properties with default values that were not specified in the input.

If you want to create a CI that is linked to an artifact (although this is usually the case only for CIs in deployment packages), have a look at the version of the API call that takes a multipart [<tt>ArtifactAndData</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.ArtifactAndData.html) parameter.

The general structure for the XML representing the configuration item is described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.plugin.api.udm.ConfigurationItem.html); which specific properties the CI supports can be found in the documentation of the appropriate plugin (e.g. [in the remoting plugin manual](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherelocalhost) for <tt>overthere.LocalHost</tt>). An easy way to get a sample of the XML for a certain type is to create a dummy CI of that type via the [UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html) or [CLI](http://docs.xebialabs.com/releases/latest/deployit/climanual.html) and then to use the REST API to read it.

Which brings us nicely to...

### <a name="read-existing-ci"></a>Reading an existing CI from the repository

Use&nbsp;[<tt>GET /repository/ci/{ID:.*?}</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:GET)&nbsp;to read an existing CI from the repository:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/repository/ci/Infrastructure/my-new-ci
> 
> **OUTPUT:**
> 
> &lt;overthere.LocalHost id="Infrastructure/my-new-ci" token="eb5cacc9-8beb-447f-8e12-4756151f8308"&gt;
> &nbsp; &lt;os&gt;UNIX&lt;/os&gt;
> &lt;/overthere.LocalHost&gt;

We need to store the output of this call in a temporary location since we want to update this CI in the next call.

The "token" included in the return is a generated value is used to prevent concurrent modifications to the repository: if you try to update an existing CI, the server will compare the value of the token you pass in with the current value on the CI. If they are different, the CI has been updated since you read the value and the server will return a [409 error](https://en.wikipedia.org/wiki/Http_error_codes#4xx_Client_Error).

### <a name="update-ci-properties"></a>Updating the properties of an existing CI

Let's assume we saved the output of the previous call in <tt>/tmp/read-ci.xml</tt>. We can then modify any attributes we want to change in the file and use&nbsp;[<tt>PUT /repository/ci/{ID:.*?}</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:PUT)&nbsp;to update the CI in the repository:

> **INPUT:**
> 
> curl&nbsp;-u&lt;username&gt;:&lt;password&gt; -X PUT -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/repository/ci/Infrastructure/my-new-ci -d@/tmp/read-ci.xml
> 
> **Contents of /tmp/read-ci.xml:**
> 
> &lt;overthere.LocalHost id="Infrastructure/my-new-ci" token="eb5cacc9-8beb-447f-8e12-4756151f8308"&gt;
> &nbsp; &lt;os&gt;WINDOWS&lt;/os&gt;
> &lt;/overthere.LocalHost&gt;
> 
> &nbsp;
> 
> **OUTPUT:**
> 
> &lt;overthere.LocalHost id="Infrastructure/vm1" token="bab17047-9614-48b6-a741-1081a8541b48"&gt;
> &nbsp; &lt;os&gt;WINDOWS&lt;/os&gt;
> &lt;/overthere.LocalHost&gt;

Again, have a look at the version of the command that takes a multipart <tt>ArtifactAndData</tt> parameter if you're looking to update the artifact associated with the CI.

### <a name="delete-a-ci"></a>Deleting a CI from the repository

Finally, we can use&nbsp;[<tt>DELETE /repository/ci/{ID:.*?}</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:DELETE)&nbsp;to delete our CI from the repository:

> curl&nbsp;-u&lt;username&gt;:&lt;password&gt; -X DELETE http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/repository/ci/Infrastructure/my-new-ci
