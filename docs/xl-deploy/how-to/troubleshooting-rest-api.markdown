---
title: Troubleshooting the REST API
categories:
- xl-deploy
subject:
- API
tags:
- REST
- API
- troubleshooting
---

## Changing the maximum number of tasks per page

As of XL Deploy version 7.6.0, the `/export` and `/query` calls for the [`TaskBlockService`](/xl-deploy/7.6.x/rest-api/com.xebialabs.deployit.engine.api.TaskBlockService.html) API were modified with a new `Paging` parameter that displays a specific number of tasks per page.

You can change the maximum number of results per page by updating the `xl.rest.api.maxPageSize` parameter.
To change the maximum page size:

* If `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` was added as a configuration file, append the file with this:

          xl.rest.api.maxPageSize = custom_positive_integer

* If the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` configuration file is present in your XL Deploy installation and the `xl { }` section is defined, append this inside:

          rest {
            api {
              maxPageSize = custom_positive_integer
            }
          }  

**Note:** You must restart your XL Deploy server after modifying the `xl-deploy.conf` file for the changes to be picked up.

**Important:** If none of the settings above are applied, the `xl.rest.api.maxPageSize` defaults to 1000 as it is pre-configured inside the XL Deploy server.
