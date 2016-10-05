---
title: Sample REST API usage
categories:
- xl-testview
subject:
- API
tags:
- api
- project
- test specification
- import
since:
- XL TestView 1.4.x
weight: 760
---

You can use the [XL TestView REST API](/xl-testview/latest/rest-api/index.html) to interact with the objects in XL TestView. This topic describes the typical flow of creating a test specification and importing test results into it.

These examples demonstrate the REST calls with cURL commands that you can execute from a shell, but you can use any HTTP-based tool. The examples assume that XL TestView is installed on `localhost`, configured to use the default port (`6516`), and using default authentication.

## Create a project

In XL TestView, a test specification is stored in a project. This example shows how to create a project with the title "Demo Project".

    curl -u admin:password -X POST \
         --header "Content-Type:application/json" \
         -d '{"title":"Demo Project"}' localhost:6516/api/v1/projects

The XL TestView server responds with the created project and a unique ID that it generated:

    {"id":"113e3e96-de0c-426a-b383-49716ed59c36","title":"Demo Project"}

## Create a test specification

After you create a project, you can create a test specification in it. In the request URL, you need to specify the ID of the project that you created (`113e3e96-de0c-426a-b383-49716ed59c36` in the previous example). This example creates a JUnit test specification called "Demo Test Specification".

    curl -u admin:password -X POST \
         --header "Content-Type:application/json" \
         -d '{"title":"Demo Test Specification", "testToolName": "xlt.JUnit"}' \
         localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications

The XL TestView server responds with the created test specification:

    {"id":"28d4b667-a32d-4734-acc3-65dc6a3449bf","title":"Demo Test Specification","testToolName":"xlt.JUnit"}

## Modify an existing test specification

The previous example does not specify a qualification type for the test specification. This example modifies the test specification that was created by adding a qualification type to it. The request URL must specify the IDs of the test specification and the project.

**Note:** You must specify all existing fields of the test specification, as per the HTTP guidelines detailed in RFC 2616.

    curl -u admin:password -X PUT \
         --header "Content-Type:application/json" \
         -d '{"title":"Demo Test Specification","testToolName":"xlt.JUnit", "qualificationType":"xlt.DefaultFunctionalTestsQualifier"}' \
         localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications/28d4b667-a32d-4734-acc3-65dc6a3449bf

The XL TestView server responds with the modified test specification:

    {"id":"28d4b667-a32d-4734-acc3-65dc6a3449bf","qualificationType":"xlt.DefaultFunctionalTestsQualifier","title":"Demo Test Specification","testToolName":"xlt.JUnit"}

## Import test results into a test specification

Now that the test specification is configured, you can import test results from a build. Test results are stored in a test specification as test runs. Test results should be packaged in a single ZIP file that only contains the results themselves (no other files).

The import API allows you to add [optional metadata](/xl-testview/concepts/events.html#metadata) that you can use for tracking purposes or for custom reports. This example adds a metadata property called `importedFrom` (a property that XL TestView does not use).

For this example, you can download a [sample test results file](samples/test-results.zip).

    curl -u admin:password -H "Content-Type: multipart/mixed" \
         -F upload=@'test-results.zip;type=application/zip' \
         -F json='{"importedFrom":"curl"};type=application/json' \
         localhost:6516/api/v1/projects/113e3e96-de0c-426a-b383-49716ed59c36/testspecifications/28d4b667-a32d-4734-acc3-65dc6a3449bf/testruns

The XL TestView server responds with the test run ID it created:

    {"testRunId":["3ee6614c-addd-4126-92cb-2274c8af201b"]}
