---
title: Examples using the XL Deploy REST API
categories:
xl-deploy
subject:
API
tags:
examples
REST
API
---

The [REST API](https://docs.xebialabs.com/xl-deploy/latest/rest-api/) allows you to execute different commands to create, edit, or delete configuration items (CIs) in XL Deploy. You can access the XL Deploy REST API via a URL of the form: `http://[host]:[port]/[context-root]/deployit/[service-resource]`.

This topic provides examples of tasks that you can perform in XL Deploy using the REST API. These examples create a directory and several infrastructure CIs in the directory, add a CI to and remove a CI from an environment, and then delete a CI.

In these examples:

* The credentials being used are user name `amy` and password `secret01`.
* XL Deploy is running at `http://localhost:4516`.
* The [`cURL` tool](https://curl.haxx.se/docs/manpage.html) is used to show the REST calls.
* The specified XML files are stored in the location from which cURL is being run.

## Create a directory

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST) to create a directory (`core.Directory` CI type).

**Input**

If the CI data is stored in an XML file:

    curl -u amy:secret01 -X POST -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory -d@directory.xml

If the CI data is stored in a JSON file:

    curl -u amy:secret01 -X POST -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory -d@directory.json

**Content of the XML file**

{% highlight xml %}
<core.Directory id="Infrastructure/SampleDirectory">
</core.Directory>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
  "type": "core.Directory",
  "id": "Infrastructure/SampleDirectory"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<core.Directory id="Infrastructure/SampleDirectory" token="f3bc20b4-3c67-4e59-aa7b-14f3d8c62ac5" created-by="amy" created-at="2017-03-13T21:00:40.535+0100" last-modified-by="amy" last-modified-at="2017-03-13T21:00:40.535+0100"/>
{% endhighlight %}

## Create an SSH host

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST) to create an SSH host (`overthere.SshHost` CI type). The properties that are available for the CI are described in the [Remoting Plugin Reference](/xl-deploy/6.1.x/remotingPluginManual.html).

**Input**

If the CI data is stored in an XML file:

    curl -u amy:secret01 -X POST -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost -d@ssh-host.xml

If the CI data is stored in a JSON file:

    curl -u amy:secret01 -X POST -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost -d@ssh-host.json

**Content of the XML file**

{% highlight xml %}
<overthere.SshHost id="Infrastructure/SampleDirectory/SampleSSHHost">
  <address>1.1.1.1</address>
  <connectionType>INTERACTIVE_SUDO</connectionType>
  <os>UNIX</os>
  <port>22</port>
  <username>sampleuser</username>
  <password>secret02</password>
  <sudoUsername>root</sudoUsername>
</overthere.SshHost>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
  "type": "overthere.SshHost",
  "address": "1.1.1.1",
  "connectionType": "INTERACTIVE_SUDO",
  "os": "UNIX",
  "port": "22",
  "username": "sampleuser",
  "password": "secret02",
  "sudoUsername": "root",
  "id": "Infrastructure/SampleDirectory/SampleSSHHost"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<overthere.SshHost id="Infrastructure/SampleDirectory/SampleSSHHost" token="f2936b5c-b553-46be-b40a-f7528c27aa65" created-by="amy" created-at="2017-03-13T21:12:38.256+0100" last-modified-by="amy" last-modified-at="2017-03-13T21:12:38.256+0100">
  <tags/>
  <os>UNIX</os>
  <puppetPath>/usr/local/bin</puppetPath>
  <connectionType>INTERACTIVE_SUDO</connectionType>
  <address>1.1.1.1</address>
  <port>22</port>
  <username>sampleuser</username>
  <password>{b64}lINyyCcWc8NK7TTTESBLoA==</password>
  <sudoUsername>root</sudoUsername>
</overthere.SshHost>
{% endhighlight %}

## Create a Tomcat server

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST) to create an [Apache Tomcat](http://tomcat.apache.org/) server (`tomcat.Server` CI type). The properties that are available for the CI are described in the [Tomcat Plugin Reference](/xl-deploy-tomcat-plugin/6.0.x/tomcatPluginManual.html#tomcatserver).

**Input**

If the CI data is stored in an XML file:

	curl -u amy:secret01 -X POST -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer -d@tomcat-server.xml

If the CI data is stored in an JSON file:

    curl -u amy:secret01 -X POST -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer -d@tomcat-server.json

**Content of the XML file**

{% highlight xml %}
<tomcat.Server id="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer">
  <home>/opt/apache-tomcat-8.0.9/</home>
  <startCommand>/opt/apache-tomcat-8.0.9/bin/startup.sh</startCommand>
  <stopCommand>/opt/apache-tomcat-8.0.9/bin/shutdown.sh</stopCommand>
  <startWaitTime>10</startWaitTime>
  <stopWaitTime>10</stopWaitTime>
</tomcat.Server>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
  "type": "tomcat.Server",
  "home": "/opt/apache-tomcat-8.0.9/",
  "startCommand": "/opt/apache-tomcat-8.0.9/bin/startup.sh",
  "stopCommand": "/opt/apache-tomcat-8.0.9/bin/shutdown.sh",
  "startWaitTime": "10",
  "stopWaitTime": "10",
  "id": "Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<tomcat.Server id="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer" token="b3378d43-3620-4f69-a2e1-d0a2ba6178de" created-by="amy" created-at="2017-03-13T21:33:16.558+0100" last-modified-by="amy" last-modified-at="2017-03-13T21:33:16.558+0100">
  <tags/>
  <envVars/>
  <host ref="Infrastructure/SampleDirectory/SampleSSHHost"/>
  <home>/opt/apache-tomcat-8.0.9/</home>
  <startCommand>/opt/apache-tomcat-8.0.9/bin/startup.sh</startCommand>
  <stopCommand>/opt/apache-tomcat-8.0.9/bin/shutdown.sh</stopCommand>
  <startWaitTime>10</startWaitTime>
  <stopWaitTime>10</stopWaitTime>
</tomcat.Server>
{% endhighlight %}

## Create a Tomcat virtual host

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:POST) to create a Apache Tomcat virtual host (`tomcat.VirtualHost` CI type). The properties that are available for the CI are described in the [Tomcat Plugin Reference](/xl-deploy-tomcat-plugin/6.0.x/tomcatPluginManual.html#tomcatvirtualhost).

**Input**

If the CI data is stored in an XML file:

	curl -u amy:secret01 -X POST -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost -d@tomcat-virtual-host.xml

If the CI data is stored in a JSON file:

    curl -u amy:secret01 -X POST -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost -d@tomcat-virtual-host.json

**Content of the XML file**

{% highlight xml %}
<tomcat.VirtualHost id="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost">
</tomcat.VirtualHost>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
  "type": "tomcat.VirtualHost",
  "id": "Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<tomcat.VirtualHost id="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost" token="24143636-fec4-4f1f-a055-c10f8f0bd439" created-by="amy" created-at="2017-03-13T21:37:11.540+0100" last-modified-by="amy" last-modified-at="2017-03-13T21:37:11.540+0100">
  <tags/>
  <envVars/>
  <server ref="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer"/>
  <appBase>webapps</appBase>
  <hostName>localhost</hostName>
</tomcat.VirtualHost>
{% endhighlight %}

## Add the virtual host to an environment

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:PUT) to add the Apache Tomcat virtual host created above to an environment (`udm.Environment` CI type). The properties that are available for the CI are described in the [UDM CI Reference](/xl-deploy/6.1.x/udmcireference.html#udmenvironment).

**Input if the environment does not exist in XL Deploy**

If the CI data is stored in an XML file:

    curl -u amy:secret01 -X POST -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.xml

If the CI data is stored in a JSON file:

    curl -u amy:secret01 -X POST -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.json

**Input if the environment exists and is called _TestEnv_**

If the CI data is stored in an XML file:

    curl -u amy:secret01 -X PUT -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.xml

If the CI data is stored in a JSON file, the environment exists in XL Deploy, and it is named _TestEnv_:

    curl -u amy:secret01 -X PUT -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.json

**Content of the XML file**

{% highlight xml %}
<udm.Environment id="Environments/TestEnv">
  <members>
     <ci ref="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost" />
  </members>
</udm.Environment>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
  "type": "udm.Environment",
  "members": [
    {"ci-ref": "Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost"}
  ],
  "id": "Environments/TestEnv"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<udm.Environment id="Environments/TestEnv" token="95b28b83-0c2c-4229-84a5-e62bd1108bab" created-by="amy" created-at="2017-03-14T08:41:30.175+0100" last-modified-by="amy" last-modified-at="2017-03-14T08:59:14.962+0100">
  <members>
    <ci ref="Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost"/>
  </members>
  <dictionaries/>
  <triggers/>
</udm.Environment>
{% endhighlight %}

## Remove the virtual host from the environment

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:PUT) to remove the Apache Tomcat virtual host created above from the _TestEnv_ environment. You must do this before you can delete the virtual host CI from XL Deploy.

**Input**

If the CI data is stored in an XML file:

    curl -u amy:secret01 -X PUT -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.xml

If the CI data is stored in an JSON file:

    curl -u amy:secret01 -X PUT -H "Content-type:application/json" http://localhost:4516/deployit/repository/ci/Environments/TestEnv -d@environment.json

**Content of the XML file**

{% highlight xml %}
<udm.Environment id="Environments/TestEnv">
</udm.Environment>
{% endhighlight %}

**Content of the JSON file**

{% highlight json %}
{
    "type": "udm.Environment",
    "id": "Environments/TestEnv"
}
{% endhighlight %}

**Response**

{% highlight xml %}
<udm.Environment id="Environments/TestEnv" token="597ac2cb-2f0d-484b-848b-ab027ab8e70f" created-by="amy" created-at="2017-03-14T08:41:30.175+0100" last-modified-by="amy" last-modified-at="2017-03-14T10:18:04.629+0100">
  <members/>
  <dictionaries/>
  <triggers/>
</udm.Environment>
{% endhighlight %}

## Delete the Tomcat virtual host

This REST call uses the [RepositoryService](/xl-deploy/6.1.x/rest-api/com.xebialabs.deployit.engine.api.RepositoryService.html#/repository/ci/{ID:.*?}:DELETE) to delete the Apache Tomcat virtual host created above from XL Deploy.

**Input**

    curl -u amy:secret01 -X DELETE -H "Content-type:application/xml" http://localhost:4516/deployit/repository/ci/Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost

**Response**

If the virtual host was successfully deleted, you will not see a response message.

If you did not remove the virtual host from the environment, you will see:

    Repository entity Infrastructure/SampleDirectory/SampleSSHHost/SampleTomcatServer/SampleVirtualHost is still referenced by Environments/TestEnv
