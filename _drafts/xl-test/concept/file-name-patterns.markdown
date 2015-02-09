---
title: File selection patterns
categories:
- xl-test
subject:
- Test specification
tags:
- test
- specification
---

For selecting files on the file system xl-test uses the selection pattern also used by the [Apache Ant](https://ant.apache.org/manual/dirtasks.html) build system. The following special characters are used:
 
  * ? means one character
  * \* means one or more characters
  * \*\* means one or more directories.

The patterns are relative on the working directory specified in the test specification. The patterns are case sensitive if the operating system is case sensitive.   

Examples:
If your working directory is `/var/tests`

  * **`**/cucumber-report.json`** selects all files named `cucumber-report.json`, in all subdirectories. `/var/tests/a/cucumber-report.json` is selected, as well as `/var/tests/a/b/c/cucumber-report.json`.
  * **`TEST-*.xml`** selects `/var/tests/TEST-A.xml`, `/var/tests/TEST-myTestSuite.xml`, but not `/var/tests/a/TEST-A.xml`
  * **`Test?.json`** selects `/var/tests/Test1.json`, `/var/tests/TestA.json` but not `/var/tests/TestAA.json`
  * **`test-result/*`** selects all files in the directory `/var/tests/test-result`
  * **`/tmp/tests/*`** selects all files in `/tmp/tests/` but not those in `/var/tests/`
  
More information and additional examples can be found in the [documentation](https://ant.apache.org/manual/dirtasks.html) of Apache Ant
 
