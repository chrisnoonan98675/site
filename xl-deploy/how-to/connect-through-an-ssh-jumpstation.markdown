---
title: Connect through an SSH jumpstation
categories:
- xl-deploy
subject:
- Remoting plugin
tags:
- remoting
- connectivity
- ssh
---

When XL Deploy cannot reach a remote host directly, but that host can be reached by setting up one (or more) SSH tunnels, configure one (or more) `overthere.SshJumpstation` CIs as follows:

1. Create an `overthere.SshJumpStation` CI that represents a host to which XL Deploy can connect directly.
1. For each remote host that cannot be reached directly by XL Deploy, create an `overthere.Host` as usual, but set the jumpstation property to refer to the `overthere.SshJumpStation` CI created in step 1.

When XL Deploy creates a connection to the remote host and determines that it needs to connect through a jumpstation, and will first open a connection to that jumpstation and then setup a SSH tunnel ("local port forward") to the remote host.

**Note:** Jumpstations can also refer to other jumpstations for even more complex network setups, but cycles are not allowed.

## Exposing additional Overthere properties in XL Deploy

Most of the Overthere connection properties defined in the [Overthere documentation](https://github.com/xebialabs/overthere/blob/master/README.md) are available as regular properties or as hidden properties on the `overthere.SshHost` and `overthere.CifsHost` types. If you need access to any additional properties, you can create a type modification in the `ext/synthetic.xml` file like this:

    <type-modification type="overthere.SshHost">
        <property name="listFilesCommand" hidden="true" default="/bin/ls -a1 {0}" />
        <property name="getFileInfoCommand" hidden="true" default="/bin/ls -ld {0}" />
    </type-modification>
