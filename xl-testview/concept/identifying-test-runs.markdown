---
title: Identifying test runs
categories:
- xl-testview
subject:
- Test results
tags:
- test result parsers
since:
- XL TestView 1.3.0
---

XL TestView uses a special key, called a *run-key* to identify individual test runs; this is required to prevent [importing](/xl-testview/how-to/detect-duplicate-imports.html) the same test data multiple times. The run-key is a case-sensitive string. A good run-key has the following properties:

* Deterministic: The result files of a test run will always return the same string.

* Unique: If a second test run has the same key, it will not be imported. Keys should not repeat over runs. A good run-key is unique in the context of a test specification.

* Quick to calculate: The earlier the key is known, the earlier a test run can be dismissed if it is already imported.

* Short: Although there is no maximum length defined, a short string will be faster and save space. Using the whole file is discouraged.

Good candidates for a run-key are a timestamp that is in the file or a unique build number. If that is not possible, the hash value of all files is usable; however, it requires reading all files completely. Note that if a test run consists of multiple files, the order of the files is important when calculating the hash value.
