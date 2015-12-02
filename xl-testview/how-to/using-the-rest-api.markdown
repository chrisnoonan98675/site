---
title: Using the REST API
categories:
- xl-testview
subject:
- Extensibility
tags:
- api
---

You can use the [XL TestView API](/xl-testview/latest/rest-api/index.html) to create, edit and delete test specifications in XL TestView. This can be done through the documented REST API. In this document, we describe a typical flow of creating a test specification, and importing test results to it.

We use curl for demonstrating the REST calls, but any HTTP-based tool can be used as well. We assume XL TestView is installed on localhost, configured to the default port 6516, and with default authentication. The following commands should be executed from a shell using curl.

## Creating a project

In XL TestView, a test specification resides inside a project. In this example, we will first create a project with the title "Demo Project". 

```
curl -u admin:password -X POST \
     --header "Content-Type:application/json" \
     -d '{"title":"Demo Project"}' localhost:6516/api/v1/projects
```

The server will respond with the created project, and a generated unique id:

```
{"id":"113e3e96-de0c-426a-b383-49716ed59c36","title":"Demo Project"}
```

## Creating a test specification

Now that we have created a project, we can create a test specification inside it. In the request URL, we will need to specify the id of the project we created in the previous example. In this example, we will create a JUnit test specification called "Demo Test Specification".

```
curl -u admin:password -X POST \
     --header "Content-Type:application/json" \
     -d '{"title":"Demo Test Specification", "testToolName": "xlt.JUnit"}' \
     localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications
``` 

The server will respond with the created test specification:

```
{"id":"28d4b667-a32d-4734-acc3-65dc6a3449bf","title":"Demo Test Specificaiton","testToolName":"xlt.JUnit"}
```

## Modifying an existing test specification

In the previous example, we didn't specify a qualification type for the test specification. In this example, we will modify the test specification we created, and add a qualification type to it. In the request URL, we need to specify the ids of the test specification and the project.

**Note:** All existing fields of the test specification must also be specified, as per the HTTP guidelines detailed in RFC 2616.

```
curl -u admin:password -X PUT \
     --header "Content-Type:application/json" \
     -d '{"title":"Demo Test Specificaiton","testToolName":"xlt.FitNesse", "qualificationType":"xlt.DefaultFunctionalTestsQualifier"}' \
     localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications/28d4b667-a32d-4734-acc3-65dc6a3449bf
```

The server will respond with the modified test specification:

```
{"id":"28d4b667-a32d-4734-acc3-65dc6a3449bf","qualificationType":"xlt.DefaultFunctionalTestsQualifier","title":"Demo Test Specificaiton","testToolName":"xlt.FitNesse"}
```

## Importing test results into a test specification
Now that our test specification is configured correctly, and with a qualification type, we can import tests results from a build. Test results are stored in a test specification as test runs. The test results should be contained in a single zip file, which should contain only the results themselves. The import API allows adding optional metadata which can be used for tracking purposes or custom reports. In this example, we add a metadata propertly called `importedFrom`, which is not used by XL TestView in any way.

For this example, you can download an [example test results file](samples/test-results.zip).

```
curl -u admin:password -H "Content-Type: multipart/mixed" \
     -F upload=@'test-results.zip;type=application/zip' \
     -F json='{"importedFrom":"curl"};type=application/json' \
     localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications/28d4b667-a32d-4734-acc3-65dc6a3449bf/testruns 
```

The server will respond with the created test run id:

```
{"testRunId":["3ee6614c-addd-4126-92cb-2274c8af201b"]}
```