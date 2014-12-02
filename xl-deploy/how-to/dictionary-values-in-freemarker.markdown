---
title: Access dictionary values in Freemarker template
categories: 
- xl-deploy
tags:
- rules
- freemarker
---

It can often be helpful to access dictionary entries from within Freemarker template. One use case for this is sending out an email containing all dictionaries and their values after a successful deployment. You can get access to the dictionary and its properties using following access path in your Freemarker template:

* `deployedApplication` &#8594; `environment` &#8594; `dictionaries` &#8594; `dictionary` &#8594; `name` (_String_)
* `deployedApplication` &#8594; `environment` &#8594; `dictionaries` &#8594; `dictionary` &#8594; `type` (_String_)
* `deployedApplication` &#8594; `environment` &#8594; `dictionaries` &#8594; `dictionary` &#8594; `entries` (_Map[String, String]_)

The `name` and `type` are pretty straightforward to reference while iterating through a list of dictionaries. The `entries` property is a map of string values, so you need a Freemarker directive to print it. The following example allows you to iterate through every dictionary associated with a deployed application, print its name, type (`dictionary` or `encryptedDictionary`), and its entries.

    <#list deployedApplication.environment.dictionaries as dict> 
        Dictionary: ${dict.name} (${dict.type}) 
        Values: 
            <#list dict.entries?keys as key> 
                ${key} = ${dict.entries[key]}
            </#list>
    </#list>

Note that `deployedApplication` object may not be available by default in Freemarker template, but you can add it using your rule step configuration as in the following example.

    <os-script>
        <script>...</script>
        <freemarker-context>
            <deployedApplication expression="true">deployedApplication</deployedApplication>
        </freemarker-context>
    </os-script>
