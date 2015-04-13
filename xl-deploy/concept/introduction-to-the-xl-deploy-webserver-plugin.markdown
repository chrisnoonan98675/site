---
title: Introduction to the XL Deploy Web Server plugin
categories: 
- xl-deploy
subject:
- Web Server plugin
tags:
- plugin
- web server
- apache
---

The XL Deploy Web Server plugin supports the deployment of web content and web server configuration to a web server.

## Features

* Supports deployment to Apache and IHS web servers.
* Deploys and undeploys web server artifacts:
    * Web content (HTML pages, images, etc.)
    * Virtual host configuration
    * Any configuration fragment
* Supports starting, stopping, and restarting of web servers as control tasks.

## Using the `www.ApacheVirtualHost` configuration item

The following is a manifest snippet that shows how web content and a virtual host can be included in a deployment package. The web content CI refers to a folder, `html`, included in the deployment package.

    <udm.DeploymentPackage version="2.0" application="PetClinic-ear" >
        <jee.Ear name="PetClinic" file="PetClinic-2.0.ear" />
        <www.WebContent name="PetClnic-html" file="html" />
        <www.ApacheVirtualHostSpec name="PetClinic-vh">
            <host>*</host>
            <port>8080</port>
        </www.ApacheVirtualHostSpec>
    </udm.DeploymentPackage>

## Using the `www.ApacheConfFragment` configuration item

Defining a new fragment to deploy to the Apache configuration takes two steps:

1. Defining the type of configuration fragment and its properties
1. Supplying a template for the configuration fragment implementation, per default the script searches for `DEPLOYIT_HOME/ext/www/apache/${deployed.type}.conf.ftl`.

Example:

1. Define an `ApacheProxyPassSetting` type in `DEPLOYIT_HOME/ext/synthetic.xml`:

        <type type="www.ApacheProxyPassSetting" extends="www.ApacheConfFragment" deployable-type="www.ApacheProxyPassSpec">
            <generate-deployable type="www.ApacheProxyPassSpec" extends="generic.Resource" />
            <property name="from" />
            <property name="to" />
            <property name="options" required="false" default="" />
            <property name="reverse" kind="boolean" required="false" default="false" />
         </type>

1. Create `www.ApacheProxyPassSetting.conf.ftl` in `DEPLOYIT_HOME/ext/www/apache`:

        --- start www.ApacheProxyPassSetting.conf.ftl ---
        ProxyPass ${deployed.from} ${deployed.to} <#if (deployed.options?has_content)>${deployed.options}</#if>
        <#if (deployed.reverse)>
        ProxyPassReverse ${deployed.from} ${deployed.to}
        </#if>
        --- end www.ApacheProxyPassSetting.conf.ftl ---
