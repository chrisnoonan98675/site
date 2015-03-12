---
title: Create a new release via the XL Release REST API using cURL (XL Release 4.5+)
categories:
- xl-release
subject: Release
tags:
- api
---

_This article uses the public XL Release REST API, available from XL Release 4.5. For earlier versions of XL Release, please refer to [the older version of this article](create-a-new-release-via-rest-api-using-curl-4.0.html)._

If you're looking to automate XL Release, e.g. to trigger a release/pipeline run from an upstream system, the [XL Release REST API](https://docs.xebialabs.com/xl-release/latest/rest-api/) is good way to do this. Here we'll describe how to kick off a new release based on a release template using [cURL](http://curl.haxx.se/docs/manpage.html).

First, we need to know the release template ID. One easy way to find this is in the URL for the template itself.

![URL for template](images/template-release-id.png)

There is a single API call to start a release from a template, which is documented [here](/xl-release/4.5.x/rest-api/#!/templates/start).

For the simple template that we're using in this example, the cURL command to create the release would be:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release2994650/start -i -X POST -d '{"title": "My Automated Release"}'

The ID of the release we have created is contained in the response. For example:

    {"id":"Release1624834", ...}

A more complex example, which includes the setting of variables, would look something like this:

    curl -u 'admin:admin'  -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release612654/start -i -X POST -d '{"releaseTitle": "My Automated Release", "releaseVariables": {"${version}": "1.0", "${name}": "John"}}'

To explicitly allow cURL to perform "insecure" SSL connections and transfers, add this to the cURL command:

    -sslv3 -k

To use a certificate, add this to the cURL command:

    -sslv3  --cacert /path/to/certificate

If you created a keystore during the setup of XL Release, use this command to extract the certificate: 

    keytool -exportcert -rfc -alias jetty -keystore conf/keystore.jks -file conf/cert.crt

