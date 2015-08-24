---
title: Detecting duplicate imports
categories:
- xl-testview
subject:
- Architecture
tags:
- testparsers
- api
---

# Detecting duplicate imports

For reliable results it is required to import all test results only once. The way to do this depends on the test tool, because some tools write a clear timestamp or other unique id in the test result files, but other tools can produce identical files for different runs. And some test tools store only one test run, but other test tools keep a complete history.

XL-TestView provides two solutions for this problem: The runKey, and the lastModified. There is a java API, called the TestRunHistorian, which allows test parsers to determine if a run has been imported before. It is preferred to do this as early as possible, to save the time spend on parsing test results.

The api has the following interface:

	public interface TestRunHistorian {
	    long timeOfLatestImport();
	    boolean isKnownKey(String key);
	}

The TestRunHistorian is injected in the test parser scripts in the variable `test_run_historian`.

It is not required to use any of these solutions, but in that case the build environment will have to guarantee that no duplicate results will be present.


## RunKey
The run key is a unique, deterministic string that represents this run. This can be a timestamp read from the file, a hash of a file, a unique test run number provided by the test tool or any other string. The way to use this is the following:

1. Calculate a runKey
2. Call `test_run_historian.isKnownKey(<key>)`
3. If the result is `true` there is a previous import with the same key, and the test parser should stop importing.
4. If the result is `false` this is a new import and importing should continue.
5. Set the runKey on the property `@runKey` on each event. This allows XL-TestView to identify this run in the future.

## LatestTest
If the there is no runKey available it is also possible to look at the timestamp of the file. This is not preferred, because files are often transferred and copied over different servers and operating systems, which might affect the timestamps.

If the timestamp is in the file itself (instead of the timestamp from the file system), it is better to use the timestamp as a runKey.

To use this, do the following:

1. Calculate a timestamp
2. Call `TestRunHistorian.timeOfLatestTest()`
3. If the latest import is before the current timestamp, continue. Otherwise, stop the import.