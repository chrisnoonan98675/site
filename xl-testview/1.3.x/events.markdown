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
## Events
XL TestView stores all test results in the database as "events". These events are key-value maps and are a uniform representation of a test result, independent of the tool the result originated from. Custom test tools will need to create a list of events, and the events have a number of requirements. If these requirements are not met, the events will not be accepted by the system. This is required to protect the integrity of the data.

An event is a key-value map. The keys are strings, the values are either strings, integers, booleans or floating point values, or lists of one of those. Another often used data type is DateTime, which represents a date. In the events this is represented as the number of milliseconds since 1970-01-01 00:00:00 UTC, stored in a 64-bit integer. 

During the processing of test results the parsers are allowed to insert their own keys and values. All the keys used by XL-TestView start with an `@` or an `_`. Test tools cannot use any keys starting with `@` or `_` except for the ones described here.

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

These properties are set on all events by XL-TestView. Reports can read them, but Test Parsers do not use them.

| Key        | Value type       | Mandatory  |  Description | 
|------------|------------------|------------|--------------|
| `_id`|String|✔|Identifier of Elastic Search. Not used by Test Tools|
|  `@runId`|String|✔||
|  `@createdAt`	|	DateTime	|✔	| 		Time of import. Always larger than 1980 and not in the future|
|  `@testSpecification`	|String	|✔||

All events have the following required properties:

| Key        | Value type       | Mandatory  |  Description | 
|------------|------------------|------------|--------------|
|  `@type`	|String	| ✔|One of `importStarted`, `importFinished`, `functionalResult`, `performanceResult`	|

### `importStarted`:

| Key        | Value type       | Mandatory  |  Description | 
|------------|------------------|------------|--------------|
|  `@testedAt`	|DateTime	|	|		Time this test was executed. Not before 1980-01-01 and not in the future
| `@runKey` |String||Test specification specific identifier of this run. This key can be used to determine if a test run has already been imported. See [Detecting duplicate imports](/xl-testview/how-to/detect-duplicate-imports.html).

### `importFinished`:

Has no properties apart from the required ones.

### `functionalResult`:

| Key        | Value type       | Mandatory  |  Description | 
|------------|------------------|------------|--------------|
|  `@result`	|String	|✔		|	No restrictions, but one of PASSED, FAILED, SKIPPED is encouraged.|
|  `@hierarchy`	|list of String	|✔		|Structure of test results. Used for drill down in reports. The whole hierarchy should be a unique textual representation of a test and its position in the suite. For example, if a unit test in JUnit was in the class in `com.example.PersonTest`, and the test was called `test1`, the hierarchy would be `['com','example','PersonTest','test1']`|
|  `@message`	|String	|	|  Textual information about the test result|
|  `@duration`	|	Integer|	|	Duration of this test in milliseconds. Should be positive| 

### `performanceResult`:

Note: `performanceResult` will be subject to change in the next release.
