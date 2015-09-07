---
title: Detecting duplicate imports
categories:
- xl-testview
subject:
- Test results
tags:
- test tool
- extension
- test result parsers
- api
since:
- 1.3.0
---

For reliable results, you only need to import all test results once. The way to do this depends on the test tool; some tools include a timestamp or other unique ID in the test result files, while others produce identical files for different runs. Some test tools store only one test run, while others keep a complete history.

XL TestView supports two methods for determining if a test run has been imported before: the run key and the file timestamp. You are not required to use either one, but in that case, your build environment should guarantee that duplicate test results will not be present.

## Using the `TestRunHistorian`

The `TestRunHistorian` Java API allows test parsers to determine if a test run has been imported before. It is recommended that this is determined as early as possible, to save time that would be spent on parsing test results.

The API has the following interface:

	public interface TestRunHistorian {
	    long timeOfLatestImport();
	    boolean isKnownKey(String key);
	}

The TestRunHistorian is injected in the test parser scripts in the variable `test_run_historian`.

## Using a run key

The run key is a unique, deterministic string that represents a run. It can be a timestamp read from a file, a hash of a file, a unique test run number provided by the test tool, or any other string. See [Identifying test runs](/xl-testview/concept/identifying-test-runs.html).

To use the run key:

1. Calculate a `runKey`.
2. Call `test_run_historian.isKnownKey(<key>)`.
3. If the result is `true`, then there is a previous import with the same key, and the test parser should stop importing.
4. If the result is `false`, then this is a new import, and importing should continue.
5. Set the `runKey` on the property `@runKey` on each event. This allows XL TestView to identify the run in the future.

## Using a file timestamp

If there is no run key available, you can look at the timestamp of the test results file.

However, note that this is not the preferred method, because files are often transferred and copied to different servers and operating systems, which may affect the timestamp. If the timestamp is in the file itself (instead of the timestamp from the file system), it is better to use the timestamp as a run key.

To use the file timestamp:

1. Calculate a timestamp.
2. Call `TestRunHistorian.timeOfLatestTest()`.
3. If the latest import is before the current timestamp, then this is a new import, and importing should continue.
4. Otherwise, stop importing.
