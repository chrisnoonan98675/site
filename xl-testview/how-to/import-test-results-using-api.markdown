---
title: Import test results using the API
categories:
- xl-testview
subject:
- Jenkins
tags:
- jenkins
- import
- api
---

You can use the [XL TestView API](/xl-testview/latest/rest-api/index.html) to import test results into XL TestView. This is done through a POST request in `multipart/mixed` format, meaning that the POST body contains two parts: metadata in JSON format and test results in zipped format.

## Metadata

Metadata are properties that will be attached to each test run (or all test runs) that XL TestView will extract from the test results.

If you would like to use metadata on a *per-build* basis, ensure that your test results only contain data for one test run. This means that one build equals one test run. 

If you provide a history of test runs that XL TestView does not know about, then XL TestView will import all data into multiple runs and attach the metadata to all of those runs; this will render any *per-build* data unusable.

### Restrictions

You can include any metadata properties that you want, with a few restrictions:

* Properties cannot start with an at sign (`@`).
* Properties can start with the `ci` prefix, but note that XL TestView uses this prefix and may use it more extensively in the future.

Note: the `ci` properties where derived, but not limited to, a Jenkins paradigm.

XL TestView uses the following metadata properties:

{:.table .table-striped}
| Key | Value type | Description |
| --- | ---------- | ----------- |
| `ciBuildNumber` | String | The build number |
| `ciBuildResult` | String | A value representing the result of the build |
| `ciBuildUrl ` | String | The absolute URL to the specific build |
| `ciExecutedOn ` | String | The build agent that produced the test results |
| `ciJobName ` | String | The name of the job in your CI tool |
| `ciJobUrl ` | String | The absolute URL to the build job (assuming a job has *builds*) |
| `ciServerUrl ` | String | The absolute URL to the CI server |
| `source ` | String | Any string value that indicates where the test results came from; for example, the XL TestView Jenkins plugin uses `jenkins`  |
