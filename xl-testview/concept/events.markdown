---
title: Events
categories:
- xl-testview
subject:
- Test results
tags:
- test result parsers
- events
- extension
- api
since:
- 1.3.0
---

XL TestView stores all test results in the database as "events". These are key-value maps that are a uniform representation of a test result, independent of the tool the result originated from.

Custom test tools must create a list of events, which have a number of requirements. If these requirements are not met, XL TestView will not accept the events. This is required to protect the integrity of the data.

An event is a key-value map. The keys are strings, while the values are strings, integers, Booleans, floating point values, or lists of one of those. Another often used data type is `DateTime`, which represents a date. In the events this is represented as the number of milliseconds since 1970-01-01 00:00:00 UTC, stored in a 64-bit integer.

During test results processing, parsers are allowed to insert their own keys and values. All keys used by XL TestView start with an at-sign (`@`) or an underscore (`_`). Test tools cannot use any keys starting with `@` or `_` except for the ones described here.

## Types of events

There are four types of events:

* `importStarted`
* `importFinished`
* `functionalResult`
* `performanceResult`

A test result parser will always produce a list of events, called a run. A run has the following properties:

* Exactly one `importStarted` event
* Exactly one `importFinished` event
* For functional test tools, any number of `functionalResult` events
* For performance test tools, any number of `performanceResult` events

**Note:** `functionalResult` and `performanceResult` events cannot be mixed.

## Event properties

XL TestView sets these properties on all events. Reports can read them, but test results parsers do not use them.

For each event, XL TestView makes sure the following properties are set:

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `_id` | String | &#x2714; | Identifier of Elasticsearch; not used by test tools. |
| `@runId` | String | &#x2714; | |
| `@createdAt` | DateTime | &#x2714;	| Time of import; always larger than 1980 and not in the future. |
| `@testSpecification` | String | &#x2714; | |

### Required properties

All events should have the following required properties:

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `@type` | String | &#x2714; | One of `importStarted`, `importFinished`, `functionalResult`, or `performanceResult`. |

### `importStarted` event properties

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `@testedAt` | DateTime | | Time this test was executed; not before 1980-01-01 and not in the future. |
| `@runKey` | String | | Test specification-specific identifier of this run. This key can be used to determine if a test run has already been imported; see [Detecting duplicate imports](/xl-testview/how-to/detect-duplicate-imports.html). |

### `importFinished` event properties

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `@duration` | Integer | &#x2714; | The total duration of a run. For `functionalResult`'s, the duration is calculated as the summation of the duration of the individual test results. |

### `functionalResult` event properties

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `@result` | String | &#x2714; | No restrictions, but `PASSED`, `FAILED`, or `SKIPPED` is recommended. |
| `@hierarchy` | list of String | &#x2714; | Structure of test results, used for drilling down in reports. The whole hierarchy should be a unique textual representation of a test and its position in the suite. For example, if a unit test in JUnit was in the class `com.example.PersonTest` and the test was called `test1`, the hierarchy would be `['com','example','PersonTest','test1']` |
| `@firstError` | String | | Textual information about the test result. |
| `@duration` | Integer | | Duration of this test in milliseconds; should be positive. |

### `performanceResult` event properties

`performanceResult` is subject to change in future releases

{:.table .table-striped}
| Key | Value type | Mandatory | Description |
| --- | ---------- | --------- | ----------- |
| `simulationName` | String | &#x2714; | The name of the performance test; this field is provided on the `importStarted` event. |
| `numberOfRequests.ok` | Integer | &#x2714; | Number of requests that went okay. |
| `numberOfRequests.ko` | Integer | &#x2714; | Number of errored requests. |
| `meanResponseTime.total` | Integer | &#x2714; | The mean response time over the whole test run. |
