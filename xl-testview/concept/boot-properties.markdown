---
layout: beta
title: XL TestView boot properties
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- properties
- installation
---

During startup, you can set several options on XL TestView. These properties are system properties. 

{:.table .table-striped}
| Property | Default | Description |
| ----- | -------------- | ------------------ |
| **xltest.load.demo.data** | `true` | XL TestView includes a rich set of demonstration data to show all features. Set this property to false to start with a clean database. This feature only works in the first startup, to prevent overwriting existing data. |
| **xltest.license.file** | `conf/xl-test-license.lic` | Relative path to the license file |
| **xltest.server.host** | 0.0.0.0 |  Host where XL TestView runs|
| **xltest.server.port** | `6516` | Port to run XL TestView on |
| **xltest.server.root** |`""` | Context root of the XL TestView service |
| **xltest.server.session.timeout** | 30 | Time to allow browser sessions to live, in minutes |
| **xltest.elasticsearch.data** | `data` | Relative path where Elasticsearch saves its data |
| **xltest.elasticsearch.http** | false | Enable the Elasticsearch HTTP server. This allows access to the Elasticsearch database over the network. No access control is set up on Elasticsearch. **Do not enable this option on production instances.** |



