---
title: File selection patterns used to find test results
categories:
- xl-testview
subject:
- Test results
tags:
- import
---

XL TestView uses file selection patterns to select test results files that should be imported as part of a [test run](/xl-testview/concept/key-concepts.html#test-runs). For example, the configuration of a test tool contains a `defaultSearchPattern` that is used as the initial value of the `searchPattern` property in *active* and *executable* [test specifications](/xl-testview/concept/key-concepts.html#test-specifications).

To select files from a file system, XL TestView uses the same selection pattern as in the [Apache Ant](https://ant.apache.org/manual/dirtasks.html) build system. Patterns are using Unix style path separators: `/`. The following special characters are used:

{:.table .table-striped}
| Special character | Meaning |
| ----------------- | ------- |
| `/` | Directory separator |
| `?` | One character |
| `*` | One or more characters |
| `**` | Zero or more directories |

The patterns are:

* Relative to the working directory that you specify in the test specification
* Case-sensitive if the operating system is case-sensitive

You can provide multiple patterns, separated by a comma (`,`).

## File selection pattern examples

For example, if your working directory is `/var/tests`:

{:.table .table-striped}
| Pattern | Selects | Does not select |
| ------- | ------- | --------------- |
| `**/cucumber-report.json` | Selects all files named `cucumber-report.json` in all subdirectories; for example, `/var/tests/a/cucumber-report.json` and `/var/tests/a/b/c/cucumber-report.json` | |
| `TEST-*.xml` | Selects `/var/tests/TEST-A.xml` and `/var/tests/TEST-myTestSuite.xml` | Does not select `/var/tests/a/TEST-A.xml` |
| `Test?.json` | Selects `/var/tests/Test1.json` and `/var/tests/TestA.json` | Does not select `/var/tests/TestAA.json` |
| `test-result/*` | Selects all files in `/var/tests/test-result` | |
| `/tmp/tests/*` | Selects all files in `/tmp/tests/` | Does not select any files in `/var/tests/` |
| `test-result/*,other-results/*` | Selects all files in `/var/tests/test-result` and `/var/tests/other-results` | |

For more information and additional examples, refer to the Apache Ant [documentation](https://ant.apache.org/manual/dirtasks.html).
