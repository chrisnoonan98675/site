---
title: Exposing additional Overthere properties in XL Deploy
categories:
- xl-deploy
subject:
- Remoting
tags:
- remoting
- connectivity
- ssh
- cifs
- smb
weight: 350
---

Most of the Overthere connection properties defined in the [Overthere documentation](https://github.com/xebialabs/overthere/blob/master/README.md) are available as regular properties or as hidden properties on the `overthere.SshHost`, `overthere.CifsHost`, and `overthere.SmbHost` types.

**Note:** SMB is supported in XL Deploy 5.5.6, 6.0.0, and later.

If you need access to any additional properties, you can create a type modification in the `ext/synthetic.xml` file like this:

    <type-modification type="overthere.SshHost">
        <property name="listFilesCommand" hidden="true" default="/bin/ls -a1 {0}" />
        <property name="getFileInfoCommand" hidden="true" default="/bin/ls -ld {0}" />
    </type-modification>
