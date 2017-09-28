---
title: Persistence Configuration Manual
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

// Disclaimer: BETA feature ...

XL Release 7.2 can store active releases in an SQL database, instead of jackrabbit repository.
The SQL support not being yet complete, XL Release still uses jackrabbit to store users, user profiles, shared configurations and settings.
As per previous releases, XL Release uses an SQL database to store archived releases.

# Overview

There are now three entries in the configuration that needs to be defined:

* `xl.repository`: The Jackrabbit repository (unchanged)
* `xl.reporting`: The SQL database for archived releases (unchanged)
* `xl.database`: The new SQL database for active releases, folders and permissions.

To enable this new feature, you need to

1. Set the `xl.repository.sql` setting to `true`
2. Configure `xl.database` to point to an existing database
3. Download the correct JDBC driver for your database and copy it under `XL_RELEASE_SERVER_HOME/lib`


# Requirements

# Supported databases

Currently we support these databases:

* H2
* Derby
* PostgreSQL
* MySQL
* Oracle

// TODO: add specific versions/version ranges?

# Configuration

## H2

## Derby

## PostgreSQL

## MySQL

## Oracle