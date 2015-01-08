---
title: Specify file encoding for artifacts
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- encoding
---

Different files in artifacts or deployment packages might have different encodings or character sets. To ensure that these files are kept in their correct encoding while running them through the placeholder replacement, you can specify the encoding in the artifact property called `fileEncodings`. This property is a map that will map regular expressions matching the full path of the file in the artifact to a target encoding.

For example, if you have the following files in a `file.Folder` artifact:

* `en-US/index.html`
* `zh-CN/index.html`

You can set the following values in the `fileEncodings` property to ensure the Chinese files are interpreted as UTF-16:

{:.table}
| key | value |
| --- | ----- |
| `h-CN.*` | `UTF-16BE` |
