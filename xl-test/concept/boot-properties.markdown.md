---
layout: beta
title: Boot properties
categories:
- xl-test
subject:
- Boot properties
tags:
- properties

---

During startup several options can be set on XL-Test. All these properties are system properties. 

{:.table .table-striped}
| Property | Default | Remarks |
| ----- | -------------- | ------------------ |
| **xltest.load.demo.data** | `true` | XL-Test comes with a rich set of demonstration data to show all features. Set this property to false to start with a clean database. This feature only works in the first startup, to prevent overwriting existing data. |
| **xltest.license.file** | `conf/xl-test-license.lic` | Relative path to the license file |
| **xltest.server.host** | 0.0.0.0 |  Host where XL-Test runs|
| **xltest.server.port** | `6516` | Port to run XL-Test on |
| **xltest.server.root** |`""` | Context root of the XL-Test service |
| **xltest.server.session.timeout** | 30 | Time to live of browser sessions in minutes.|
| **xltest.elasticsearch.data** | `data` | Relative path where Elastic Search saves its data. |
| **xltest.elasticsearch.http** | false | Enable the http server of elastic search. This allows access to the elastic search database over the network. No access control is setup on Elastic Search. **Do not enable on production instances**|



