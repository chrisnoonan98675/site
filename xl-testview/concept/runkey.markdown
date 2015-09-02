---
title: Runkey
categories:
- xl-testview
subject:
- Test parsers
tags:
- test parsers
---

A runkey is used to identify separate test runs, which is required to prevent importing the same test data multiple times. It is a case sensitive String. A good runkey has the following properties:

* Deterministic

   The result files of a test run will always return the same string.
* Unique

   If a second test run ever turns up with the same key, it will not be imported. Keys should not repeat over runs.

* Quick to calculate

   The sooner the key is know, the sooner a test run can be thrown out if it is already imported.

* Short

   Although there is no maximum length defined, a short string will be faster and save space. Using the whole file is discouraged.

Good candidates for a runkey are a timestamp that is in the file or a unique build number. If that is not possible, the hash of all files is also usable but it required reading all files completely. Keep in mind that if a test run consists of multiple files, the order of the files is important when calculating the hash.