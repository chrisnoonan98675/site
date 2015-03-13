---
title: Create an XL Scale EC2 host template
categories:
- xl-deploy
subject:
- XL Scale
tags:
- xl scale
- host
- ec2
- plugin
- template
- virtualization
---

A host template describes a single host that can be launched on EC2. The host template is a  ```ec2.HostTemplate``` configuration item (CI). In addition to the generic host template properties, it allows some EC2-specific properties.

This is an example of an EC2 host descriptor:

    <#escape x as x?xml>
      <list>
        <cloud.SshHost id="${hostsPath}/${hostTemplate.name}_${hostAddress}">
          <template ref="${hostTemplate.id}"/>
          <cloudId>${cloudId}</cloudId>
          <address>${hostAddress}</address>
          <#if hostTemplate.privateKeyFile??><privateKeyFile>${hostTemplate.privateKeyFile}</privateKeyFile></#if>
          <#if hostTemplate.username??><username>${hostTemplate.username}</username></#if>
          <#if hostTemplate.password??><password>${hostTemplate.password}</password></#if>
          <#if hostTemplate.os??><os>${hostTemplate.os}</os></#if>
          <#if hostTemplate.connectionType??><connectionType>${hostTemplate.connectionType}</connectionType></#if>
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

Every ```ec2.HostTemplate``` CI provides a ```validateDescriptor``` control task which processes the FreeMarker template, parses the resulting XML and reports errors if something is wrong. No actual changes are made to the repository during execution of this control task.

Please note that:

* EC2 hosts which you define here should be either ```cloud.SshHost``` or ```cloud.CifsHost```.
* The ```cloud.SshHost``` or ```cloud.CifsHost``` in the template must contain the XML fragments for ```address```, ```cloudId``` and ```template```. These are needed for the proper functioning of the plugin.
* You should always use ```<list>``` as a parent XML element, even if you define only one CI.
* When you use references to ```${hostTemplate.*}``` properties, make sure they are defined on host template when required by the appropriate CI types. That is, ```cloud.SshHost``` requires ```connectionType```, when ```ec2.HostTemplate``` does not.
* Since XML is being generated you have to make sure that values are properly encoded. You can achieve this by enclosing the template in ```<#escape x as x?xml>...</#escape>```, or alternatively use ```${exampleKey?xml}```. See the FreeMarker documentation for details.
