---
layout: beta
title: Troubleshoot custom reports in XL Test
categories:
- xl-test
subject:
- Reports
tags:
- report
- script
- python
- json
- freemarker
---

## My report does not appear and I see errors in the browser console

This can be caused by invalid JSON for Highcharts. To troubleshoot it:

* Check for keys that are not surrounded by single quotation marks.
* If you see an error about `fontSize`, ensure that you do not have a `subtitle` key/value pair in your JSON.
* Ensure that comments are marked with `#` and not with `//`.

## I see a "Failed to retrieve report..." error

This error indicates a syntax error in Python. Ensure that all keys in the `Dictionary` are surrounded with single quotation marks.

To see the exact line that caused this error, review the XL Test server log at `<XLTEST_HOME>/log/xltest-server.log`.
