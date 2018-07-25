---
title: Shut down XL Release
categories:
xl-release
subject:
System administration
tags:
system administration
weight: 491
---

You can use a REST API call to shut down the XL Release server. This is an example of a command that uses [cURL](http://curl.haxx.se/) to shut down the server (replace `admin:admin` with your credentials):

    curl -X POST --basic -u admin:admin http://admin:admin@localhost:5516/server/shutdown

You can also use a command prompt or Terminal to stop the XL Release server process.
