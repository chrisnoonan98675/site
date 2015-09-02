---
title: XL TestView file selection patterns
categories:
- xl-testview
subject:
- Test specifications
tags:
- test specification
- test results
- import
---

To select files from a file system, XL TestView uses the selection pattern that is also used by the [Apache Ant](https://ant.apache.org/manual/dirtasks.html) build system. The following special characters are used:
 
* `?`: one character
* `*`: one or more characters
* `**`: zero or more directories

The patterns are:

* Relative to the working directory that you specify in the test specification
* Case-sensitive if the operating system is case-sensitive

For example, if your working directory is `/var/tests`:

* `**/cucumber-report.json` selects all files named `cucumber-report.json` in all subdirectories; for example, `/var/tests/a/cucumber-report.json` and `/var/tests/a/b/c/cucumber-report.json`

* `TEST-*.xml` selects `/var/tests/TEST-A.xml` and `/var/tests/TEST-myTestSuite.xml`, but not `/var/tests/a/TEST-A.xml`

* `Test?.json` selects `/var/tests/Test1.json` and `/var/tests/TestA.json`, but not `/var/tests/TestAA.json`

* `test-result/*` selects all files in `/var/tests/test-result`

* `/tmp/tests/*` selects all files in `/tmp/tests/`, but no files in `/var/tests/`

For more information and additional examples, refer to the Apache Ant [documentation](https://ant.apache.org/manual/dirtasks.html).
