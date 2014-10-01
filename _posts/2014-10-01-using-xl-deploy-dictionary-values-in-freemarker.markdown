---
title: Using XL Deploy dictionary values in a FreeMarker template
categories:
- xl-deploy
tags:
- FreeMarker
- dictionary
---

You may want to use XL Deploy dictionary entries in a [FreeMarker](http://freemarker.org/) template; for example, as part of an email that is sent after a successful deployment and contains all dictionaries and their values. To do so, you need to use the following [UDM](http://docs.xebialabs.com/releases/latest/xl-deploy/referencemanual.html#unified-deployment-model-udm) values:

* `deployedApplication.environment.dictionary.name` *[String]*
* `deployedApplication.environment.dictionary.type` *[String]*
* `deployedApplication.environment.dictionary.entries` *[Map(String, String)]*

Using `deployedApplication.environment.dictionary.name` and `deployedApplication.environment.dictionary.type` when iterating through a list is straightforward. `deployedApplication.environment.dictionary.entries`, which is a map of string values, is slightly harder.

This example iterates through every dictionary associated with a deployed application and outputs its name, type (`dictionary` or `encryptedDictionary`), and the entries in it. Try it for yourself!

    <#list deployedApplication.environment.dictionaries as dict> 
        Dictionary: ${dict.name} (${dict.type}) 
        Values: 
            <#list dict.entries?keys as key> 
                ${key} = ${dict.entries[key]}
            </#list>
    </#list>
