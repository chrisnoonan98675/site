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
- XL Release 6.0.1
---

As of XL Release 6.0.1, you can configure GZIP compression in XL Release. GZIP compression is used to decrease the size of server responses and save bandwidth usage. There is a small overhead of server CPU usage to compress content. It is enabled by default, but you may want to adjust the settings depending on your use case.

  If you have a fast network, or if there is an HTTP server in front of XL Release which already performs GZIP compression, you can disable it by setting `enabled: false` in the configuration (See table below)

## Configuring the GZIP compression settings

To configure the GZIP settings, first add the `xl`(if it does not already exist), `server` and `http` properties to the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration file. In the `http` property, add an additional `gzip` property. This property identifies the predefined GZIP configuration you wish to use. Supported values are:

{:.table .table-striped}
| Parameter             | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| `enabled`               | Whether to enable GZIP Compression (true by default)             |
| `minSize`               | Content over this size will be compressed. In bytes or [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md#hocon-human-optimized-config-object-notation) format (10 kB by default) |
| `compression`           | Compression level from 0-9 (4 by default) |
| `excludedPaths`         | List of string paths that will be excluded from the compression  |

### Sample GZIP compression settings
    xl {
        server {
            http {
                gzip {
                    enabled: true
                    minSize: 10 kB
                    compression: 4
                    excludedPaths: []
                }
            }
        }
    }


## Disabling GZIP compression on the client side

GZIP is supported by default on most modern browsers. GZIP compression will only be used if the HTTP request's `Accept-Encoding` header contains the value `gzip`.
