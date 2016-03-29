---
title: Create a new release via the REST API using cURL (XL Release 4.5.0 and later)
categories:
- xl-release
subject:
- Releases
tags:
- api
since:
- XL Release 4.5.0
---

_This article uses the public XL Release REST API, which is available from XL Release 4.5.0. For earlier versions of XL Release, please refer to [the older version of this article](/xl-release/how-to/create-a-new-release-via-rest-api-using-curl-4.0.html)._

If you want to automate XL Release—for example, to trigger a release or pipeline run from an upstream system—the [XL Release REST API](/xl-release/latest/rest-api/) is good way to do so. This topic describes how to start a new release based on a release template using [cURL](http://curl.haxx.se/docs/manpage.html).

## Get the template ID

First, you need to know the release template ID. You can easily find this in the URL for the template itself.

![URL for template](../images/template-release-id.png)

## Start a release from the template

The [`/api/v1/templates/{templateId:.*?}/start`](/xl-release/4.6.x/rest-api/#!/templates/start) API call creates a release from a template and immediately starts it. For the simple template in this example, the cURL command to create the release would be:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release2994650/start -i -X POST -d '{"releaseTitle": "My Automated Release"}'

**Note:** In API calls, the release template ID is always prepended with `Applications/` for technical reasons. For more information about template IDs, refer to [How to find IDs](/xl-release/how-to/how-to-find-ids.html).

The response to the API contains the ID of the release that was created. For example:

    {"id":"Release1624834", ...}

**Tip:** In XL Release 4.8.0 and later, you can use the [`/api/v1/templates/{templateId:.*Release[^/]*}/create`](/xl-release/4.8.x/rest-api/#!/templates/create) API call to create a release without immediately starting it. You can then use the [`/api/v1/releases/{releaseId:.*Release[^/]*}/start`](/xl-release/4.8.x/rest-api/#!/releases/start) call to start the planned release.

### Start a release with variables

A more complex example that includes the setting of variables would look like this:

    curl -u 'admin:admin' -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release612654/start -i -X POST -d '{"releaseTitle": "My Automated Release", "releaseVariables": {"${version}": "1.0", "${name}": "John"}}'

## Security when using cURL

To explicitly allow cURL to perform "unsecure" SSL connections and transfers, add this to the cURL command:

    -sslv3 -k

To use a certificate, add this to the cURL command:

    -sslv3  --cacert /path/to/certificate

If you created a keystore during the setup of XL Release, use this command to extract the certificate: 

    keytool -exportcert -rfc -alias jetty -keystore conf/keystore.jks -file conf/cert.crt
