---
title: Troubleshoot a WAS connection
subject:
- WebSphere Application Server plugin
categories:
- xl-deploy
tags:
- websphere
- middleware
---

XL Deploy uses the `wsadmin` tool to perform tasks in IBM WebSphere Application Server (WAS).

When XL Deploy starts `wsadmin` on a server for the first time, you must interactively accept the `dmgr` certificate. XL Deploy cannot do that, so it will hang.

This is the output that appears in the step log:

    =================================================================

    SSL SIGNER EXCHANGE PROMPT ***
    SSL signer from target host null is not found in trust store /opt/ws/6.1/appserver/profiles/AppSrv01/etc/trust.p12.
    Here is the signer information (verify the digest value matches what is displayed at the server):

    Subject DN: CN=was-61-sa, O=IBM, C=US
    Issuer DN: CN=was-61-sa, O=IBM, C=US
    Serial number: 1306835778
    Expires: Wed May 30 11:56:18 CEST 2012
    SHA-1 Digest: C9:A3:48:43:BD:20:96:67:AF:51:E5:9A:EE:46:60:EC:6F:0E:F6:51
    MD5 Digest: 15:43:57:AD:03:74:A0:DB:158:BE:4A:68:A4:57:6C

    Add signer to the trust store now? (y/n) 
    =================================================================
