---
title: Using the XL Deploy CLI to discover WebSphere (WAS) middleware
categories:
- xl-deploy
tags:
- cli
- websphere
- middleware
---

XL Deploy can discover existing middleware and configure it in its repository. Depending on the middleware, some configuration items (CIs) must be provided as a starting point.

For example, this is how you perform discovery for [IBM WebSphere (WAS)](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html):

    wasHost = repository.create("Infrastructure/rs94asob.k94.kvk.nl (DEV99)",
    factory.configurationItem("Host",{"address":"was-61", "username":"root", "password":"centos", 
        "operatingSystemFamily":"UNIX", "accessMethod":"SSH_SFTP"}))
    aDmManager = factory.configurationItem("WasDeploymentManager",
        {'host':'Infrastructure/rs94asob.k94.kvk.nl (DEV99)', "wasHome":"/opt/ws/6.1/profiles/dmgr",
        "username":"wsadmin","password":"*****"})
    aDmManager.id="c-ws-dev99"

    discovered = deployit.discover(aDmManager)

XL Deploy will discover the WAS middleware and return the found CIs. Then, they can be stored in the repository.
