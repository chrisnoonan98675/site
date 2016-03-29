---
title: Create a new release via the REST API using cURL (XL Release 4.0.x and earlier)
categories:
- xl-release
subject:
- Releases
tags:
- api
deprecated:
- XL Release 4.0.x
---

_This article uses the internal XL Release REST API. When using XL Release 4.5.0 or later, please use the public API and refer to [the updated version of this article](/xl-release/how-to/create-a-new-release-via-rest-api-using-curl.html)._

If you're looking to automate XL Release, e.g. to trigger a release/pipeline run from an upstream system, the XL Release REST API is good way to do this. Here we'll describe how to kick off a new release based on a release template using [cURL](http://curl.haxx.se/docs/manpage.html).

First, we need to know the release template ID. One easy way to find this is in the URL for the template itself.

![URL for template](../images/template-release-id.png)

In order to achieve what we're trying to do, we first need to create a new release, then start it. For the simple template that we're using in this example, the cURL command to create the release would be:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/releases -i -X POST -d '{"templateId":"Release2994650", "scheduledStartDate": "2014-07-30","dueDate": "2014-08-30", "title": "My Automated Release", "flag": {"status": "OK"}, "calendarPublished": true,"description": "Created this release via curl call", "owner": "{XL Release Administrator}"}'

The ID of the release we have created is contained in the response. For example:

    {"id":"Release1624834"}

We need to extract the ID value from the response ([choose your favorite approach](https://unix.stackexchange.com/questions/121718/how-to-parse-json-with-shell-scripting-in-linux)) and can then start the release:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/releases/Release1624834/start -i -X POST

A more complex example, which includes the setting of variables and tags, would look something like this:

    curl -u 'admin:secret' -v -H "Content-Type: application/json" http://elton:5516/xlrelease/releases/ -i -X POST -d '{"templateId":"Release2994623" , "scheduledStartDate":"2014-08-20", "dueDate": "2014-08-27", "title": "Complex Automated Release", "flag": {"status": "OK"}, "calendarPublished": false,"description": "Created this release via curl call", "owner": "{XL Release Administrator}","variables": [{"key": "${packageId}", "value": "admin"},{"key": "${QA environment}", "value": "admin2"}, {"key": "${ACC environment}", "value": "admin3"}], "tags":["dest","dest2"]}'

To explicitly allow cURL to perform "insecure" SSL connections and transfers, add this to the cURL command:

    -sslv3 -k

To use a certificate, add this to the cURL command:

    -sslv3  --cacert /path/to/certificate

If you created a keystore during the setup of XL Release, use this command to extract the certificate: 

    keytool -exportcert -rfc -alias jetty -keystore conf/keystore.jks -file conf/cert.crt

