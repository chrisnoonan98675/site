---
title: Events
categories:
- xl-testview
subject:
- Events
tags:
- test parsers
- events
---

XL TestView stores all test results in the database as "events". These events are key-value maps and are a uniform representation of a test result, independent of the tool the result originated from. Custom test tools will need to create a list of events, and the events have a number of requirements. If these requirements are not met, the events will not be accepted by the system. This is required to protect the integrity of the data.

An event is a key-value map. The keys are strings, the values are either strings, integers, booleans or floating point values.

During the processing of test results the parsers are allowed to insert their own keys and values. The keys cannot override any of the mandatory keys here.

There are 4 types of events:

* `importStarted`
* `importFinished`
* `functionalResult`
* `performanceResult` (incubating)

A test result parser will always produce a list of events, called a run. A run has the following properties:

* Exactly one `importStarted` event
* Exactly one `importFinished` event
* Any number of FunctionalResult events, for "functional" test tools **or** PerformanceResult events, for "performance" test tools. Those events may not be mixed.

##Event properties
All events have the following required properties:


| Key        | Value type       | Mandatory  | Restrictions | Description | 
|---|---|---|---|---|
| `_id`|String|✔||Identifier of Elastic Search. Not used by Test Tools|
|  `runId`|String|✔|||
|  `timestamp`	|	DateTime<sup>1</sup>	|✔	| 1980-Now<sup>8</sup>	|	Time of import|
|  `type`	|String	| |One of `importStarted`, `importFinished`, `functionalResult`, `performanceResult`|	|
|  `testSpecification`	|String	|✔|✔||

1. DateTime is actually a Long representing the number of milliseconds since 1970-01-01 00:00:00 UTC.

### `importStarted`:

| Key        | Value type       | Mandatory  | Restrictions | Description | 
| ------------- |-------------|-----|-------| ------| ---|
|  `lastModified`	|DateTime	|	|	1980-Now|	Time this test was executed. Not before 1980-01-01 and not in the future
| `runKey` |String||||Test specifiation specific identifier of this run. This key can be used to determine if a test run has already been imported.

### `importFinished`:

Has no properties apart from the required ones.

### `functionalResult`:

| Key        | Value type       | Mandatory  |Restrictions | Description | 
|---|---|---|---|---|
|  `result`	|String	|✔	|	|	No restrictions, but one of PASSED, FAILED, SKIPPED is encouraged.|
|  `hierarchy`	|list of String	|✔	|	|Structure of test results. Used for drill down in reports.|
|  `firstError`	|	String|	|	| |
|  `failureReason`	|String	|	| | Textual information about the test result|
|  `duration`	|	Integer|	|	> 0| |

### `performanceResult`:

Note: `performanceResult` will be subject to change in the next release.
