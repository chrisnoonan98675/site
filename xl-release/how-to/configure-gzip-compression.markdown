---
title: Configure HTTP GZIP Compression
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- settings
weight: 496
since:
- XL Release 7.0.0
---

As of XL Release 7.0.0, you can configure GZIP compression in XL Release. GZIP compression is used to decrease the size of server responses and save bandwidth usage. There is a small overhead of server CPU usage to compress content. It is enabled by default, but you may want to adjust the settings depending on your use case.

## Configuring the GZIP compression settings

To configure the GZIP settings, first add the `http` property to the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration file. In this property, add an additional `gzip` property. This property identifies the predefined GZIP configuration you wish to use. Supported values are:

{:.table .table-striped}
| Parameter             | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| `enabled`               | Whether to enable GZIP Compression (true by default)             |
| `minSize`               | Content over this size in bytes will be compressed (10240 by default) |
| `compression`           | Compression level from 0-9 (4 by default) |
| `excludedPaths`         | List of string paths that will be excluded from the compression  |

### Sample GZIP compression settings

    http {
        gzip {
            enabled: true
            minSize: 10240
            compression: 4
            excludedPaths: []
        }
    }

## Disabling GZIP compression on the client side

GZIP is supported by most modern browsers. A client can indicate if it supports GZIP-encoded content using the `Accept-Encoding` header.

If no `Accept-Encoding` header is present, or if there is an `Accept-Encoding` header containing the value `gzip`, then GZIP compression will be used.
To disable GZIP, the client should use the `Accept-Encoding` header with the value `gzip;q=0` or `identity`.
