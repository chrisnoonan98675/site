---
title: Migrate to SQL
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
- database
- repository
weight: 493
---



## Overview

Assumptions:

* `XL_RELEASE_SERVER_HOME_CURRENT` refers to the location of your current installation of XL Release, i.e. `/var/lib/xl/xl-release-6.1-server/`
* `XL_RELEASE_SERVER_HOME_72` refers to the location of the upgraded installation of XL Release 7.2, i.e. `/var/lib/xl/xl-release-7.2-server/`
* `XL_RELEASE_SQL_MIGRATOR_HOME` refers to the location of the XL Release SQL Migrator toop, i.e. `/home/user/xl-release-sql-migrator`

Steps:

1. [Shut down XL Release](https://docs.xebialabs.com/xl-release/how-to/shut-down-xl-release.html)
2. [Back up XL Release](https://docs.xebialabs.com/xl-release/how-to/back-up-xl-release.html)
3. [Download XL Release 7.2](https://dist.xebialabs.com/customer/xl-release/product/7.2.0) ([Release notes](https://support.xebialabs.com/hc/en-us/articles/115004985086-XL-Release-7-2-0-released))
3. [Upgrade XL Release](https://docs.xebialabs.com/xl-release/how-to/upgrade-xl-release.html)
4. Obtain `xl-release-sql-migrator` // TODO: add download link
5. Configure `xl-release-sql-migrator`
6. Run the migrator
7. Reconfigure XL Release 7.2 to use SQL
8. [Start XL Release 7.2](https://docs.xebialabs.com/xl-release/how-to/start-xl-release.html)


## Migrate to PostgreSQL

## Migrate to MySQL

## Migrate to Oracle