---
title: Create a new release via the REST API
categories:
xl-release
subject:
Public API
tags:
api
since:
XL Release 4.5.0
weight: 427
---

If you want to automate XL Release—for example, to trigger a release or pipeline run from an upstream system—the [XL Release REST API](/xl-release/latest/rest-api/) is good way to do so. This topic describes how to use [cURL](http://curl.haxx.se/docs/manpage.html) or [PowerShell](https://technet.microsoft.com/en-us/library/bb978526.aspx) to start a new release based on a release template.

## Find the template ID

Before using the API, you need to know the ID of the release template. You can find this in the URL for the template itself. For example:

![URL for template](../images/template-release-id.png)

For more information about template IDs, refer to [How to find IDs](/xl-release/how-to/how-to-find-ids.html).

**Important:** In the API call itself, you must always prepend the template ID with `Applications/` for technical reasons.

## Using cURL

The [`/api/v1/templates/{templateId:.*?}/start`](/xl-release/6.0.x/rest-api/#!/templates/start) API call creates a release from a template and immediately starts it. For the simple template in this example, the cURL command to create the release would be:

    curl -u 'admin:secret'  -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release2994650/start -i -X POST -d '{"releaseTitle": "My Automated Release"}'

The response to the API contains the ID of the release that was created. For example:

    {"id":"Release1624834", ...}

**Tip:** In XL Release 4.8.0 and later, you can use the [`/api/v1/templates/{templateId:.*Release[^/]*}/create`](/xl-release/6.0.x/rest-api/#!/templates/create) API call to create a release without immediately starting it. You can then use the [`/api/v1/releases/{releaseId:.*Release[^/]*}/start`](/xl-release/6.0.x/rest-api/#!/releases/start) call to start the planned release.

### Start a release with variables

A more complex example that includes the setting of variables would look like this:

    curl -u 'admin:admin' -v -H "Content-Type: application/json" http://localhost:5516/api/v1/templates/Applications/Release612654/start -i -X POST -d '{"releaseTitle": "My Automated Release", "releaseVariables": {"${version}": "1.0", "${name}": "John"}}'

### Security when using cURL

To explicitly allow cURL to perform "unsecure" SSL connections and transfers, add this to the cURL command:

    -sslv3 -k

To use a certificate, add this to the cURL command:

    -sslv3  --cacert /path/to/certificate

If you created a keystore when installing XL Release, use this command to extract the certificate:

    keytool -exportcert -rfc -alias jetty -keystore conf/keystore.jks -file conf/cert.crt

## Using PowerShell

This example shows how you can use PowerShell to automatically create and start a release based on a template with the ID `Release293292865`:

{% highlight powershell %}
$body = @{
    "releaseTitle" = "My Automated Release"
}
$json = $body | ConvertTo-Json
$headers = @{
"Authorization" = "Basic YWRtaW46YWRtaW4="
}
Invoke-RestMethod -Headers $headers -ContentType "application/json" -Body $json -Method Post -Uri "http://samplehost:5516/api/v1/templates/Applications/Release293292865/start"
{% endhighlight %}
