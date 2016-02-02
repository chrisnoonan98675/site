---
title: Events in XL TestView
categories:
- xl-testview
subject:
- Extensibility
tags:
- test result parsers
- extension
- api
since:
- XL TestView 1.3.0
---

XL TestView stores all test results in the database as "events". These are key-value maps that are a uniform representation of a test result, independent of the tool the result originated from.

Custom test result parsers must create a list of events, which have a number of requirements. If these requirements are not met, XL TestView will not accept the events. This is required to protect the integrity of the data.

An event is a key-value map. The keys are strings, while the values are strings, integers, Booleans, floating point values, or lists of one of those. Another often used data type is `DateTime`, which represents a date. In the events this is represented as the number of milliseconds since 1970-01-01 00:00:00 UTC, stored in a 64-bit integer.

During test results processing, parsers need to set the necessary key-value pairs that XL TestView requires to function. All keys starting with an at-sign (`@`) or an underscore (`_`) are reserved by XL TestView. Test result parsers must not use keys starting with `@` or `_` for their own keys. To provide its own metadata a test result parser may use different keys.

## Types of events

There are four types of events:

* `importStarted`
* `importFinished`
* `functionalResult`
* `performanceResult`

A test result parser will always produce a list of events, called a test run; this is the set of results associated with a single execution of the tests in a test specification.

A run has the following properties:

* Exactly one `importStarted` event
* Exactly one `importFinished` event
* For functional test tools, any number of `functionalResult` events
* For performance test tools, any number of `performanceResult` events

**Note:** `functionalResult` and `performanceResult` events cannot be mixed.

## Event properties


The following properties need to be set by test result parsers, so reports and qualification scripts can use them.

For each event, XL TestView ensures that the following properties are set:

{:.table .table-striped}
| Key | Value type | Required | Description |
| --- | ---------- | --------- | ----------- |
| `_id` | String | &#x2714; | Identifier of Elasticsearch; not used by test tools. |
| `@runId` | String | &#x2714; | |
| `@createdAt` | DateTime | &#x2714; | Time of import; always larger than 1980 and not in the future. |
| `@testSpecification` | String | &#x2714; | |

### `importStarted` event properties

{:.table .table-striped}
| Key | Value type | Required | Description |
| --- | ---------- | --------- | ----------- |
| `@type` | String | &#x2714; | One of `importStarted`, `importFinished`, `functionalResult`, or `performanceResult`. |
| `@testedAt` | DateTime | | Time this test was executed; not before 1980-01-01 and not in the future. |
| `@runKey` | String | | Test specification-specific identifier of this run. This key can be used to determine if a test run has already been imported; see [Detecting duplicate imports](/xl-testview/how-to/detect-duplicate-imports.html). |

### `importFinished` event properties

{:.table .table-striped}
| Key | Value type | Required | Description |
| --- | ---------- | --------- | ----------- |
| `@type` | String | &#x2714; | One of `importStarted`, `importFinished`, `functionalResult`, or `performanceResult`. |
| `@duration` | Integer | &#x2714; | The total duration of a run. For `functionalResult`'s, the duration is calculated as the summation of the duration of the individual test results. |

### `functionalResult` event properties

{:.table .table-striped}
| Key | Value type | Required | Description |
| --- | ---------- | --------- | ----------- |
| `@type` | String | &#x2714; | One of `importStarted`, `importFinished`, `functionalResult`, or `performanceResult`. |
| `@result` | String | &#x2714; | No restrictions, but `PASSED`, `FAILED`, or `OTHER` is recommended. |
| `@hierarchy` | list of String | &#x2714; | Structure of test results, used for drilling down in reports. The whole hierarchy should be a unique textual representation of a test and its position in the suite -- the hierarchy must be unique in the context of a test run. For example, if a unit test in JUnit was in the class `com.example.PersonTest` and the test was called `test1`, the hierarchy would be `['com','example','PersonTest','test1']`. |
| `@firstError` | String | | Textual information about the test result. This field typically contains a message in case of a failed test result. |
| `@duration` | Integer | | Duration of this test in milliseconds; should be positive. |

### `performanceResult` event properties

`performanceResult` is subject to change in future releases

{:.table .table-striped}
| Key | Value type | Required | Description |
| --- | ---------- | --------- | ----------- |
| `@type` | String | &#x2714; | One of `importStarted`, `importFinished`, `functionalResult`, or `performanceResult`. |
| `simulationName` | String | &#x2714; | The name of the performance test; this field is provided on the `importStarted` event. |
| `numberOfRequests.ok` | Integer | &#x2714; | Number of requests that went okay. |
| `numberOfRequests.ko` | Integer | &#x2714; | Number of errored requests. |
| `meanResponseTime.total` | Integer | &#x2714; | The mean response time over the whole test run. |

### Metadata

Test result parsers can add their own metadata to events. Metadata is typically provided by the CI tool or via the REST API, but a test result parser may also derive it on its own.

#### Restrictions

You can include any metadata properties that you want, with a few restrictions:

* Properties cannot start with an at-sign (`@`) or an underscore (`_`).
* Properties can start with the `ci` prefix, but note that XL TestView uses this prefix and may use it more extensively in the future.

Note: the `ci` properties are derived, but not limited to, a Jenkins CI implementation. If you happen to use a different CI tool you can do so as long as the value provided for the `ci` properties below reflect their intended meaning.

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
| `ciSource ` | String | Any string value that indicates where the test results came from; for example, the XL TestView Jenkins plugin uses `jenkins` |
