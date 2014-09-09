---
title: How to create, read, update and delete configuration items using the XL Deploy REST API
categories:
- xl-deploy
tags:
- API
- CI
- xl-deploy-4.0
- xl-deploy-4.5
---

One of the common maintenance activities when running XL Deploy is adding, modifying and removing items from its [repository](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#repository). This is especially the case for infrastructure and environment [configuration items](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#configuration-items-cis) (CIs) that are not published to XL Deploy automatically in the way [deployment packages](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployment-package) usually are.

Of course, you want to automate these kind of activities as much as possible, too. Many of our users (primarily those not using [XL Scale](http://xebialabs.com/products/xl-scale/) ;-)) have linked XL Deploy to their cloud management and provisioning tools so that, for example, a new virtual machine automatically "registers itself" with XL Deploy by means of a post-create hook.

Here, we'll use `curl` to go through an example of the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html) calls you'll need to make such a scenario work, specifically:

*   [Creating a new CI](#creating-a-new-ci)
*   [Reading an existing CI from the repository](#reading-an-existing-ci-from-the-repository)
*   [Updating the properties of an existing CI](#updating-the-properties-of-an-existing-ci)
*   [Deleting a CI from the repository](#deleting-a-ci-from-the-repository)

## Creating a new CI

Use [`POST /repository/ci/{ID:.*?}`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST) to create a new CI. Here, we'll create a new `overthere.LocalHost` CI with ID `Infrastructure/my-new-ci`:

**INPUT:**

      curl -u<username>:<password> -X POST -H "Content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/repository/ci/Infrastructure/my-new-ci -d@/tmp/read-ci.xml

**Contents of /tmp/read-ci.xml:**

      <overthere.LocalHost id="Infrastructure/my-new-ci">
            <os>UNIX</os>
      </overthere.LocalHost>

**OUTPUT:**

      <overthere.LocalHost id="Infrastructure/my-new-ci" token="61cfc7c5-9ea5-4883-9a98-ae721f678aaa">
            <os>UNIX</os>
      </overthere.LocalHost>

The description of the CI as it was actually saved is returned. This may differ from the input XML, e.g. it may contain properties with default values that were not specified in the input.

If you want to create a CI that is linked to an artifact (although this is usually the case only for CIs in deployment packages), have a look at the version of the API call that takes a multipart [`ArtifactAndData`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.ArtifactAndData.html) parameter.

The general structure for the XML representing the configuration item is described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.plugin.api.udm.ConfigurationItem.html); which specific properties the CI supports can be found in the documentation of the appropriate plugin (e.g. [in the remoting plugin manual](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherelocalhost) for `overthere.LocalHost`). An easy way to get a sample of the XML for a certain type is to create a dummy CI of that type via the [UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html) or [CLI](http://docs.xebialabs.com/releases/latest/deployit/climanual.html) and then to use the REST API to read it.

Which brings us nicely to...

## Reading an existing CI from the repository

Use [`GET /repository/ci/{ID:.*?}`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:GET) to read an existing CI from the repository:

**INPUT:**

      curl -u<username>:<password> http://<xl-deploy-server>:<xl-deploy-port>/deployit/repository/ci/Infrastructure/my-new-ci

**OUTPUT:**

      <overthere.LocalHost id="Infrastructure/my-new-ci" token="eb5cacc9-8beb-447f-8e12-4756151f8308">
      <os>UNIX</os>
      </overthere.LocalHost>

We need to store the output of this call in a temporary location since we want to update this CI in the next call.

The "token" included in the return is a generated value is used to prevent concurrent modifications to the repository: if you try to update an existing CI, the server will compare the value of the token you pass in with the current value on the CI. If they are different, the CI has been updated since you read the value and the server will return a [409 error](https://en.wikipedia.org/wiki/Http_error_codes#4xx_Client_Error).

## Updating the properties of an existing CI

Let's assume we saved the output of the previous call in `/tmp/read-ci.xml`. We can then modify any attributes we want to change in the file and use [`PUT /repository/ci/{ID:.*?}`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:PUT) to update the CI in the repository:

**INPUT:**

      curl -u<username>:<password> -X PUT -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/repository/ci/Infrastructure/my-new-ci -d@/tmp/read-ci.xml

**Contents of /tmp/read-ci.xml:**

      <overthere.LocalHost id="Infrastructure/my-new-ci" token="eb5cacc9-8beb-447f-8e12-4756151f8308">
            <os>WINDOWS</os>
      </overthere.LocalHost>

**OUTPUT:**

      <overthere.LocalHost id="Infrastructure/vm1" token="bab17047-9614-48b6-a741-1081a8541b48">
            <os>WINDOWS</os>
      </overthere.LocalHost>

Again, have a look at the version of the command that takes a multipart `ArtifactAndData` parameter if you're looking to update the artifact associated with the CI.

## Deleting a CI from the repository

Finally, we can use [`DELETE /repository/ci/{ID:.*?}`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:DELETE) to delete our CI from the repository:

      curl -u<username>:<password> -X DELETE http://<xl-deploy-server>:<xl-deploy-port>/deployit/repository/ci/Infrastructure/my-new-ci
