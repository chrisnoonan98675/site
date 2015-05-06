---
layout: beta
title: XL Test file selection patterns
categories:
- xl-test
subject:
- Test specification
tags:
- test specification
- test results
- import
---

To select files from a file system, XL Test uses the selection pattern that is also used by the [Apache Ant](https://ant.apache.org/manual/dirtasks.html) build system. The following special characters are used:
 
* `?`: one character
* `*`: one or more characters
* `**`: zero or more directories

The patterns are:

* Relative to the working directory that you specify in the test specification
* Case-sensitive if the operating system is case-sensitive

For example, if your working directory is `/var/tests`:

{:.table .table-striped}
| Pattern | Selects | Does not select |
| ------- | ------- | --------------- |
| `**/cucumber-report.json` | All files named `cucumber-report.json` in all subdirectories; for example, `/var/tests/a/cucumber-report.json` and `/var/tests/a/b/c/cucumber-report.json` | |
| `TEST-*.xml` | `/var/tests/TEST-A.xml` and `/var/tests/TEST-myTestSuite.xml` | `/var/tests/a/TEST-A.xml` |
| `Test?.json` | `/var/tests/Test1.json` and `/var/tests/TestA.json` | `/var/tests/TestAA.json` |
| `test-result/*` | All files in `/var/tests/test-result` | |
| `/tmp/tests/*` | All files in `/tmp/tests/` | Files in `/var/tests/` | |	
For more information and additional examples, refer to the Apache Ant [documentation](https://ant.apache.org/manual/dirtasks.html).
