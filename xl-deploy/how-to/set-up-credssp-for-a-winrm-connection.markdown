---
title: Enable multi-hop support (CredSSP) for WINRM_NATIVE
subject:
- Bundled plugins
categories:
- xl-deploy
tags:
- connectivity
- remoting
- winrm
- overthere
---

To enable multi-hop support (also known as CredSSP) for WinRM when using the `WINRM_NATIVE` connection type, follow the steps below. CredSSP is not supported by the `WINRM_INTERNAL` connection type.

### On the XL Deploy server machine and/or XL Satellite instance(s)

1. Execute the following command:

        winrm set winrm/config/client/auth @{CredSSP="true"}

1. Open the Group Policy Editor (`gpedit.msc`).
1. Go to **Computer Configuration** > **Administrative Templates** > **System** > **Credentials Delegation**.
1. Edit **Allow Delegating Fresh Credentials**.
1. Select **Enabled**.
1. Add the SPNs for target servers to the list; for example, `WSMAN/hostname.domain`.

   **Tip:** Wildcards are allowed; for example, `WSMAN/*`.

1. Click **Apply**.

If the XL Deploy server is not in the same Windows domain as at least one of the target machines, also:

1. Edit **Allow Delegating Fresh Credentials with NTLM-only Server Authentication**.
1. Select **Enabled**.
1. Add the SPNs for target servers to the list; for example, `WSMAN/hostname.domain`.
1. Click **Apply**.

### On each target machine

Execute the following command:

    winrm set winrm/config/service/auth @{CredSSP="true"}

### In XL Deploy

1. For each target machine, edit the corresponding `overthere.Host` configuration item (CI).
1. Select the category **WINRM**.
1. Enable **Allow credential delegation**.

From the CLI, set the property `winrsAlowDelegate` to `true`.

### More information

For more information, refer to:

* [Multi-Hop Support in WinRM](http://msdn.microsoft.com/en-us/library/ee309365(v=vs.85).aspx)
* [Enable-WSManCredSSP](http://technet.microsoft.com/en-us/library/hh849872.aspx)
