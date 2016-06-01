---
title: Remoting plugin SSH compatibility
categories: 
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- connectivity
- remoting
- ssh
---

The XL Deploy Remoting plugin uses the [sshj](https://github.com/shikhar/sshj) library for SSH and supports all algorithms and formats that are supported by that library:

* Ciphers: `aes{128,192,256}-{cbc,ctr}`, `blowfish-cbc`, `3des-cbc`
* Key Exchange methods: `diffie-hellman-group1-sha1`, `diffie-hellman-group14-sha1`
* Signature formats: `ssh-rsa`, `ssh-dss`
* MAC algorithms: `hmac-md5`, `hmac-md5-96`, `hmac-sha1`, `hmac-sha1-96`
* Compression algorithms: `zlib` and `zlib@openssh.com` (delayed `zlib`)
* Private Key file formats: `pkcs8` encoded (the format used by [OpenSSH](http://www.openssh.com/))
