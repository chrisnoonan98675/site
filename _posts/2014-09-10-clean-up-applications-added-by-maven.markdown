---
title: Clean up applications added to XL Deploy by Maven
categories:
- xl-deploy
tags:
- maven
- application
---

When working with a continuous delivery setup in which an application is deployed every night, the number of snapshot versions that Maven adds to the XL Deploy repository can grow large. This Python script cleans up unused snapshot versions from the XL Deploy repository. Applications that are still in use are skipped (XL Deploy raises an exception if an application is in use).

    import re
 
    application = 'YourAppName' 
 
    deployed_apps = repository.search('udm.DeploymentPackage', 'Applications/' + application)
 
    for app in deployed_apps:
        if not re.match('.*-20[0-9]{2}[01][0-9]{3}-[0-9]{6}', app):
            print "Not a snapshot: " + app
            continue
 
    try:
        print "Deleting " + app
        repository.delete(app) 
        print "Ok."
    except:
        print "Fail."
        pass
 
    deployit.runGarbageCollector()
