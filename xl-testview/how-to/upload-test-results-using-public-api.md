---
title: Upload your test results via the API
categories:
- xl-testview
subject:
- Jenkins
tags:
- jenkins
- import
- api
---

Importing test results can be done via the public import API of XL TestView. 

For technical details please look at our API documentation. (TODO: Add link).

An import via the API is done by doing a POST request in `multipart/mixed` format. This means the POST body contains two parts. One part contains *metadata* (in JSON) and one part contains the *test results* (in zipped format).

## Metadata

Metadata are properties you would like to attach to an import and that will be attached to each (or all) test runs that will be extracted from the test results.

If you would like to use metadata on a *per build* basis, be sure your test results only contain data for one test run. This way *one build equals one test run*. 

If you provide a history of test-runs that XL TestView does not know about, then XL TestView will import all data into multiple runs and attach the metadata to all those runs. Therefor rendering any *per build* data unusable.

You can send over any kind of properties you want, with only a few restrictions:

### Restrictions

- Your properties may not begin with an `@` (at) sign.
- Your properties *may* begin with the `ci` prefix, but be aware of the fact that XL TestView *will* use these in the future. See below which properties are used.

Note: the `ci` properties where derived, but not limited to, a Jenkins paradigm.

The following properties are used by XL TestView:


{:.table .table-striped}
| Key | Value type | Description |
| --- | ---------- | ----------- |
| `ciBuildNumber ` | String | Build number.
| `ciBuildResult ` | String | Value representing the result of the build.
| `ciBuildUrl ` | String | The (absolute) url to the specific build.
| `ciExecutedOn ` | String | The build agent that produced the test results.
| `ciJobName ` | String | The name of the job within your CI tool.
| `ciJobUrl ` | String | The (absolute) url to the build job. (assuming a Job has *builds*)
| `ciServerUrl ` | String | The (absolute) url to the CI server
| `source ` | String | Value indidicating where the test results came from. Could be any string. Ie `jenkins` is used by the XL TestView jenkins plugin.

