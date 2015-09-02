---
layout: beta
title: Disable authentication for XL TestView
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- installation
- ldap
- authentication
---

If authentication is not required, because the installation runs in a trusted environment, it can be disabled. This can be done by setting `xlt.authentication.method` to `none` in the `xl-testview.conf` file. See [boot properties](/xl-testview/concept/boot-properties.html).