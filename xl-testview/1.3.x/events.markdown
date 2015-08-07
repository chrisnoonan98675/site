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

XL TestView stores all test results in the data base in events. These events are key-value maps and are a uniform representation of a test result, independent of the tool the result originates from. Custom test tools will need to create a list of events, and the events have a number of requirements. If these requirements are not met, the events will not be accepted by the system. Is is required to protect the integrity of the data.

An event is a key-value map. The keys are strings, the values are objects. These objects are usually simple: Strings, Integers, Longs.

TestTools are allowed to insert their own keys and values. The keys cannot override any of the mandatory keys here.

There are 4 types of events:

* ImportStarted
* ImportFinished
* FunctionalResult
* PerformanceResult

A test tool will always produce a list of events, called a run. A run has the following properties:

* Exactly one ImportStarted event
* Exactly one ImportFinished event
* A number of FunctionalResult events **or** a number of PerformanceResult events.

##Event properties
All events have the following required properties:


| Key        | Value type       |Event Type<sup>1</sup>    | M<sup>6</sup>  |XL TV<sup>7</sup>| Restrictions | Description | 
| ------------- |-------------|-------- |-----|-------| ------| ---|
| `_id`|String|All|✔|✔||Identifier of Elastic Search. Not used by Test Tools|
|  `runId`|String|All|✔|✔|||
|  `timestamp`	|	DateTime<sup>2</sup> |All	|✔	|✔	|1980-Now<sup>8</sup>	|	Time of import|
|  `type`	|String	|All	|✔	| |	Valid type<sup>5</sup>|	|
|  `testSpecification`	|String	|All	|✔|✔||
|  `lastModified`	|DateTime<sup>2</sup>	|	IS|	✔|	|	1980-Now<sup>8</sup>|	Time this test was executed|
|  `failureReason`	|String	|FR	|	|	| | Textual information about the test result|
|  `duration`	|	Integer|FR	|	|	|	> 0| |
|  `name`	|String	|FR	|	|	|	|	|
|  `result`	|String	|FR	|✔	|	|	|	No restrictions, but one of PASSED, FAILED, SKIPPED is encouraged.|
|  `firstError`	|	String|FR	|	|	|	| |
|  `hierarchy`	|[String]<sup>3</sup>	|FR	|✔	|	|	|Structure of test results. Used for drill down in reports.|
| `runKey` |String|IS||||Unique identifier of this run<sup>4</sup>

1. Event Type is IS for `ImportStarted`, IF for `ImportFinished`, PR for `PerformanceResult` and FR for `FunctionalResult`
2. DateTime is actually a Long representing the number of milliseconds since 1970-01-01 00:00:00 UTC.
3. [] is a collection. [String] means a list or set of Strings.
4. See TestParsers for usage of this property
5. One of `importStarted`, `importFinished`, `functionalResult`, `performanceResult`
6. This field is mandatory in the respective event type
7. This field will be set by XL Test View. Any value it already contains will be overwritten.
8. Not before 1980-01-01 and not in the future

##Performance results
| Key        | Value type       |Event Type<sup>1</sup>    | M<sup>6</sup>  |XL TV<sup>7</sup>| Restrictions | Description | 
| ------------- |-------------|-------- |-----|-------| ------| ---|
|`numberOfRequests.total`|Long|PR|✔||> 0| Number of tests in a performance run
|`numberOfRequests.ok`|Long|PR|✔||> 0| Number of passed tests in a performance run
| `numberOfRequests.ko` | Long| PR |✔ | | > 0|Number of failed tests in a performance run.