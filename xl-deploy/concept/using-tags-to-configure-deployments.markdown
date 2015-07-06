---
title: Using tags to configure deployments
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- tag
- application
- package
---

XL Deploy's tagging feature allows you to configure deployments by marking which deployables should be mapped to which containers.

To perform a deployment using tags, specify the tags on the deployables and containers. You can specify them in an imported deployment package or in the XL Deploy Repository.

When deploying an application to an environment, XL Deploy will match the deployables and containers based on the following rules:

1. Both of them have **no** tags
1. Either of them has a tag called `*`
1. Either of them has a tag called `+` and the other has at least one tag
1. Both have at least one tag in common

If none of these rules apply, XL Deploy will not generate a deployed for the deployable-container combination.

This table shows which tags match when:

{:.table .table-striped}
| Deployable/container | No tags | Tag `*` | Tag `+` | Tag `X` | Tag `Y` |
| -------------------- | ------- | ------- | ------- | ------- | ------- |
| No tags | &#9989; | &#9989; | &#10060; | &#10060; | &#10060; |
| Tag `*` | &#9989; | &#9989; | &#9989; | &#9989; | &#9989; |
| Tag `+` | &#10060; | &#9989; | &#9989; | &#9989; | &#9989; |
| Tag `X` | &#10060; | &#9989; | &#9989; | &#9989; | &#10060; |
| Tag `Y` | &#10060; | &#9989; | &#9989; | &#10060; | &#9989; |

For example, assume you have front-end and back-end applications that you want to deploy to two application servers. If you tag the front-end EAR file and the front-end application server with `FE`, and tag the back-end EAR file and back-end application server with `BE`, XL Deploy will automatically create the correct deployeds.
