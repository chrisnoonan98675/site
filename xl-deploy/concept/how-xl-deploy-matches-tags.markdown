---
title: How XL Deploy matches tags
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

When deploying an application to an environment, XL Deploy will match the deployables and containers based on the following rules:

1. Both have **no** tags
1. Either is tagged with an asterisk (`*`)
1. Either is tagged with a plus sign (`+`) and the other has at least one tag
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
