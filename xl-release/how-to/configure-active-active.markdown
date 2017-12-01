---
title: Configure active/active mode
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- failover
- database
- active/hot-standby
- active/active
- clustering
since:
- XL Release 7.5.0
---

XL Release version 7.5.0 or later supports active/active configuration. Where 2 or more XL Release nodes can serve requests at the same time.

![Active/active configuration](../images/diagram-active-hot-standby.png)

## Difference between active/hot-standby and active/active

- TODO

## Instructions

The configuration of `active/active` mode is the same as [active/hot-standby mode](/xl-release/how-to/configure-active-hot-standby.html). The only difference is that instead of  `xl.cluster.mode=hot-standby`, you should use `xl.cluster.mode=full`.
