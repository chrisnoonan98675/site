---
title: Install XL Impact
categories:
- xl-impact
subject:
- Installation
tags:
- installation
- setup
---

Before installing XL Impact, [ensure that you meet the system requirements](/xl-impact/concept/xl-impact-requirements.html).

## Connecting XL Release to XL Impact to display the UI

1. Download the `xlr-xli-ui-plugin` from the distribution site.
1. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.
1. Add following snippet to configuration file.

          xl {
            plugin {
              xliUI {
                scheme = "https"
                host = "%you will receive value when you sign up%"
                port = 443
                contextPath = "/xli/"
                assetsPath = "assets/"
                apiPath = "api/"
                username = "admin"
                password = "%you will receive value when you sign up%"
              }
            }
          }

1. Restart XL Release server.

**Note:** The `host` and `password` values are provided when you sign up for XL Impact.

## Connecting XL Release to XL Impact to retrieve XL Release data

1. Download the `xlr-xli-data-plugin` from the distribution site.
1. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.
1. Add following snippet to the configuration file.

        xl {
          plugin {
            xliData {
              chunkLimit = 10
              throttlingMinutes = 2
              maxNormalRetry = 5
              xliBaseUrl = "%you will receive value when you sign up%"
              archiveJobCron = "0 59 23 * * ?"
            }
          }
        }

1. Restart XL Release server.        

You can change the `archiveJobCron` pattern, depending on how often you want the data to be synchronized.

You can retrieve data from multiple XL Release deployments and display the information for only one of them in the XL Impact UI. This is done using separate plugins for the UI and for the collected data.

## Connecting XL Impact to your data sources that are exposed to the internet

To configure the XL Impact crawlers to retrieve data from your data sources, you must provide the URLs and credentials to our support team.

## Connecting XL Impact to your intranet data sources that are not exposed to the internet

In addition to providing URLs and credentials, you must launch the XL Impact crawler inside your network to access your data source. This may be required for Jenkins deployments, which are often installed in the intranet and are not exposed to internet directly. The crawlers can be launched as Docker containers or as Java applications. You will receive the details from the XL Impact support team.
