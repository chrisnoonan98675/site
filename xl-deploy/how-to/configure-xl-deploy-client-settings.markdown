---
title: Configure XL Deploy client settings
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- configuration
- deployment
since:
- XL Deploy 6.0.0
---

You can configure the following advanced XL Deploy client settings in `<XLDEPLOY_SERVER_HOME>/conf/deployit.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `client.automatically.map.all.deployables` | When set to "true", all deployables will be auto-mapped to containers when you set up an initial or update deployment in the GUI, and XL Deploy will ignore the `map.all.deployables.on.initial` and `map.all.deployables.on.update` settings | true |
| `client.automatically.map.all.deployables.on.initial` | When set to "true", all deployables will be auto-mapped to containers only when you set up an initial deployment in the GUI (supported in XL Deploy 5.1.4, 5.5.0, and later) | false |
| `client.automatically.map.all.deployables.on.update` | When set to "true", all deployables will be auto-mapped to containers only when you set up an update deployment (supported in XL Deploy 5.1.4, 5.5.0, and later) | false |
| `client.session.timeout.minutes` | Number of minutes before a user's session is locked when the GUI is idle | 0 (no timeout) |
| `client.session.remember.enabled` | Show or hide the **Keep me logged in** option on the log-in screen | true (option is shown) |
