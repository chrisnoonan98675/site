---
title: Detecting duplicate imports
categories:
- xl-testview
subject:
- Extensibility
tags:
- import
- extension
- test result parsers
- api
since:
- XL TestView 1.3.0
---

For reliable results, you only need to import the test results once. The way to do this depends on the test tool; some tools include a timestamp or other unique ID in the test result files, while others produce identical files for different runs. Some test tools store only one test run, while others keep a complete history.

XL TestView supports two methods for determining if a test run has been imported before: the run key and the file timestamp. You are not required to use either one, but in that case, your build environment should guarantee that duplicate test results will not be present.

## Using the `TestRunHistorian`

The [`TestRunHistorian`](/xl-testview/latest/javadoc/TestRunHistorian.html) Java API allows test parsers to determine if a test run has been imported before. It is recommended that this is determined as early as possible, to save time that would be spent on parsing test results.

The API has the following interface:

	public interface TestRunHistorian {
	    long timeOfLatestImport();
	    boolean isKnownKey(String key);
	}

The TestRunHistorian is injected in the test parser scripts in the variable `test_run_historian`.

## Using a *run key*

XL TestView uses a special key called a *run key* to identify individual test runs; this is required to prevent importing the same test data multiple times. The run key is a case-sensitive string. A good run-key has the following properties:

* Deterministic: The result files of a test run will always return the same string.
* Unique: If a second test run has the same key, it will not be imported. Keys should not repeat over runs. A good run-key is unique in the context of a test specification.
* Quick to calculate: The earlier the key is known, the earlier a test run can be dismissed if it is already imported.
* Short: Although there is no maximum length defined, a short string will be faster and save space. Using the whole file is discouraged.

Good candidates for a run key are a timestamp that is in the file or a unique build number. If that is not possible, the hash value of all files is usable; however, it requires reading all files completely. Note that if a test run consists of multiple files, the order of the files is important when calculating the hash value.

To use the run key:

1. Calculate a *run key*.
2. Call `test_run_historian.isKnownKey(<key>)`.
3. If the result is `true`, then there is a previous import with the same key, and the test parser should stop importing.
4. If the result is `false`, then this is a new import, and importing should continue.
5. Set the *run key* on the property `@runKey` on the [`importStarted`](/xl-testview/concept/events.html#importstarted-event-properties) event. This allows XL TestView to identify the run in the future.

## Using a file timestamp

If there is no run key available, you can look at the timestamp of the test results file. However, this is not the preferred method, because files are often transferred and copied to different servers and operating systems, which may affect the timestamp. If the timestamp is in the file itself (instead of the timestamp from the file system), it is better to use the timestamp as a run key.

To use the file timestamp:

1. Calculate a timestamp.
2. Call `TestRunHistorian.timeOfLatestTest()`.
3. If the latest import is before the current timestamp, then this is a new import, and importing should continue.
4. Otherwise, stop importing.
