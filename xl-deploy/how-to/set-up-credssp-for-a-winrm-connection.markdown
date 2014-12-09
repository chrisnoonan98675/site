---
title: Enable multi-hop support (CredSSP) for WINRM_NATIVE
subject:
- Remoting plugin
categories:
- xl-deploy
tags:
- connectivity
- remoting
- winrm
- credssp
- overthere
---

To enable multi-hop support (a.k.a. CredSSP) for WinRM when using the `WINRM_NATIVE` connection type, follow the steps below. CredSSP is not supported by the `WINRM_INTERNAL` connection type.

### On the XL Deploy server machine:

1. Execute the following command:
    `winrm set winrm/config/client/auth '@{CredSSP="true"}'`
1. Open the Group Policy Editor (`gpedit.msc`)
1. Go to _Computer Configuration/Administrative Templates/System/Credentials Delegation_.
1. Edit _Allow Delegating Fresh Credentials_.
1. Select _Enabled_.
1. Add the SPNs for target servers to the list, e.g. `WSMAN/hostname.domain`
    * Wildcards are allowed, e.g.: `WSMAN/*`
1. Click _Apply_.

If the XL Deploy server is not in the same Windows domain as at least one of the target machines, also:

1. Edit _Allow Delegating Fresh Credentials with NTLM-only Server Authentication_.
1. Select _Enabled_.
1. Add the SPNs for target servers to the list, e.g. `WSMAN/hostname.domain`
1. Click _Apply_.

### On each target machine:

1. Execute the following command:
```
winrm set winrm/config/service/auth '@{CredSSP="true"}'
```

### In XL Deploy:

1. For each target machine, edit the corresponding `overthere.Host` CI.
1. Select the category _WINRM_.
1. Enable _Allow credential delegation_.
    * From the CLI, set the property `winrsAlowDelegate` to `true`.

### More information

For more information, see the following links:

* [Multi-Hop Support in WinRM](http://msdn.microsoft.com/en-us/library/ee309365(v=vs.85).aspx)
* [Enable-WSManCredSSP](http://technet.microsoft.com/en-us/library/hh849872.aspx)
