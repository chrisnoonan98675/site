---
title: Using the CLI to discover WebLogic (WLS) middleware
categories:
- xl-deploy
tags:
- cli
- weblogic
- middleware
---

XL Deploy can discover existing middleware and configure it in its repository. Depending on the middleware, some configuration items (CIs) must be provided as a starting point.

For example, this is how you perform discovery for [WebLogic (WLS)](http://docs.xebialabs.com/releases/latest/wls-plugin/wlsPluginManual.html):

    wlsHost = repository.create("Infrastructure/weblogic.vm", 
        factory.configurationItem("Host",{"address":"weblogic.vm","username":"ubuntu",
            "password":"ubuntu", "operatingSystemFamily":"UNIX", "accessMethod":"SSH_SFTP"}))
    aWlsDomain = factory.configurationItem("WlsDomain",
        {'activeHost':'Infrastructure/weblogic.vm','wlHome':'/home/ubuntu/oracle/wls1033/wlserver',
            'username':'weblogic','password':'weblogic10',
            'port':'7001'})
    aWlsDomain.id='AdminServer'
    discovered = deployit.discover(aWlsDomain)
    # create the discovered CI into the repository
    createdIds = repository.create(discovered
