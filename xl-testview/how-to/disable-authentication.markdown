---
title: Disable authentication in XL TestView
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- installation
- ldap
- authentication
since:
- 1.3.0
---

If you do not need XL TestView to authenticate users because it is running in a trusted, you can disable authentication. To do so, set `xlt.authentication.method` to `none` in the `xl-testview.conf` file. For more information, refer to [boot properties](/xl-testview/concept/boot-properties.html).
