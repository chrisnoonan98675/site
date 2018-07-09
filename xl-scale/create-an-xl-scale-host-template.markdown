---
title: Create an XL Scale host template
no_index: true
---

[XL Scale](/xl-deploy/concept/introduction-to-xl-scale.html) host templates are created in the Repository under the **Configuration** root node. Because host templates are virtualization platform specific, there will be different configuration options depending on the exact vendor. The following configuration options are common to all vendors:

{:.table .table-striped}
| Property | Category | Type | Description |
| -------- | -------- | ---- | ----------- |
| Instance descriptor | Common | String | FreeMarker and XML descriptor of the middleware that is present once the host template is instantiated |
| OS Family | Connection | Enumeration | The operating system family used on the template |
| Connection type | Connection | Enumeration | The connection method to use when connecting to an instantiated host template |
| Username | Connection | String | The user name to use when connecting to an instantiated host template |
| Password | Connection | String | The password to use when connecting to an instantiated host template |
| Private Key File | Connection | String | The private key file to use when connecting to an instantiated host template |
| Marker file path | Connection | String | The marker file to scan to determine when the instantiated host is ready for use; if no marker file path is specified, the host template is assumed to be ready for use when a network connection to the host is available |
| Boot timeout | Connection | Integer | The amount of seconds to allow for the host to boot before reporting failure |
| Retry delay | Connection | Integer | The amount of seconds to wait between attempts to check for existence of the marker file |

## The marker file

When instantiating a host template, the resulting host may already have the desired middleware installed on it. If this is the case, the host will be ready for use as soon as it has finished booting. It is also possible to provision a host using Puppet, Chef or a shell command after launching it. This is supported via the notion of _marker file_. If the host template specifies a marker file, XL Deploy will poll the launched instance for its presence. When the file is found on the instance filesystem, XL Deploy will conclude the host is up and ready for deployment.

The polling is done based on the connection settings of the Host template, not based on the connection settings that may appear in the host template descriptor itself. As a result some of the connection types will not work out of the box when using the marker file. If you extend the `cloud.BaseHostTemplate` (or a more specific template type) with additional properties, these will be copied over to the overthere connection used for polling if the property name coincides.

It may be a good approach to start with a connection type that works well out of the box like SFTP or SCP, and then change to a connection type that requires customization.

## The host template descriptor

The host template descriptor is a FreeMarker template which is transformed into an XML-based definition of all CIs that are registered in XL Deploy after the host is launched on the virtualization platform.

The FreeMarker template can make use of the variables described in the following table:

{:.table .table-striped}
| Variable | Type | Meaning |
| -------- | ---- | ------- |
| `hostsPath` | String | Folder which will contain the created middleware; this parameter is entered during creation of a host template / environment template instance |
| `hostTemplate` | `ec2.HostTemplate` | Reference to this template; allows reuse of some of its properties |
| `cloudId` | String | Unique ID of the created instance within the virtualization platform |
| `hostAddress` | String | Public IP address of the created host |

### Sample host descriptor

Here is an example of a host descriptor from the [EC2 plugin](/xl-deploy/concept/xl-scale-ec2-plugin.html):

{% highlight xml %}
<#escape x as x?xml>
  <list>
    <cloud.SshHost id="${hostsPath}/${hostTemplate.name}_${hostAddress}">
      <template ref="${hostTemplate.id}"/>
      <cloudId>${cloudId}</cloudId>
      <address>${hostAddress}</address>
      <#if hostTemplate.privateKeyFile??><privateKeyFile>${hostTemplate.privateKeyFile}</privateKeyFile></#if>
      <username>${hostTemplate.username}</username>
      <#if hostTemplate.password??><password>${hostTemplate.password}</password></#if>
      <os>${hostTemplate.os}</os>
      <connectionType>${hostTemplate.connectionType}</connectionType>
    </cloud.SshHost>
    <www.ApacheHttpdServer id="${hostsPath}/${hostTemplate.name}_${hostAddress}/httpd">
      <host ref="${hostsPath}/${hostTemplate.name}_${hostAddress}"/>
      <startCommand>sudo apachectl stop</startCommand>
      <startWaitTime>3</startWaitTime>
      <stopCommand>sudo apachectl stop</stopCommand>
      <stopWaitTime>3</stopWaitTime>
      <restartCommand>sudo apachectl restart</restartCommand>
      <restartWaitTime>10</restartWaitTime>
      <defaultDocumentRoot>/var/www</defaultDocumentRoot>
      <configurationFragmentDirectory>/etc/apache2/conf.d</configurationFragmentDirectory>
    </www.ApacheHttpdServer>
  </list>
</#escape>
{% endhighlight %}

Please note that:

* You should always use `<list>` as a parent XML element, even if you define only one CI.
* When you use references to `${hostTemplate.*}` properties, ensure that they are defined on host template when required by the appropriate CI types; for example, `cloud.SshHost` requires `connectionType`, when `ec2.HostTemplate` does not.
* Because XML is being generated, you must ensure that values are properly encoded. You can achieve this by enclosing the template in `<#escape x as x?xml>...</#escape>`, or alternatively use `${exampleKey?xml}`. See the FreeMarker documentation for details.

## Validating the host template descriptor

Every host template CI provides a `validateDescriptor` control task which processes the FreeMarker template, parses the resulting XML and reports errors if something is wrong. No actual changes are made to the repository during execution of this control task.
