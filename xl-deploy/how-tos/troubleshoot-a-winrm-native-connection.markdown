---
title: Troubleshoot a WINRM_NATIVE connection
tags:
- connectivity
- remoting
- winrm
- overthere
---

These are configuration errors that can occur when using XL Deploy with WINRM_NATIVE.

## The `winrm` configuration command fails with the message `There are no more endpoints available from the endpoint mapper`

The Windows Firewall has not been started. See [Microsoft Knowledge Base article #2004640](http://support.microsoft.com/kb/2004640) for more information.

## The `winrm` configuration command fails with the message `The WinRM client cannot process the request`

This can occur if you have disabled the `Negotiate` authentication method in the WinRM configuration. To fix this situation, edit the configuration in the Windows registry under the key `HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\WSMAN\` and restart the Windows Remote Management service.

Courtesy of [this blog post by Chris Knight](http://blog.chrisara.com.au/2012/06/recovering-from-winrm-authentication.html).

## WinRM command fails with the message `java.net.ConnectException: Connection refused`

The Windows Remote Management service is not running or is not running on the port that has been configured. Start the service or configure XL Deploy to use a different *port*.

## WinRM command fails with a 401 response code

Multiple causes can lead to this error message:

1. The Kerberos ticket is not accepted by the remote host:

    * Did you set up the correct service principal names (SPNs) as described in [the section on Kerberos setup for remote hosts](#cifs_host_setup_spn)? The hostname is case insensitive, but it has to be the same as the one used in the *address* property, i.e. a simple hostname or a fully qualified domain name. Domain policies may prevent the Windows Management Service from creating the required SPNs. See [this blog by LazyJeff](http://fix.lazyjeff.com/2011/02/how-to-fix-winrm-service-failed-to.html) for more information.

    * Has the reverse DNS of the remote host been set up correctly? See [Principal names and DNS](http://web.mit.edu/Kerberos/krb5-devel/doc/admin/princ_dns.html) for more information. Please note that the `rdns` option is not available in Java's Kerberos implementation.

1. The WinRM service is not set up to accept unencrypted traffic. Did you execute step #8 of the [host setup for WinRM](#cifs_host_setup_winrm)?

1. The user is not allowed to log in. Did you uncheck the "User must change password at next logon" checkbox when you created the user in Windows?

1. The user is not allowed to perform a WinRM command. Did you grant the user (local) administrative privileges?

1. Multiple domains are in use and they are not mapped in the `[domain_realm]` section of the Kerberos `krb5.conf` file. For example:

        [realms] 
        EXAMPLE.COM = { 
        kdc = HILVERSUM.EXAMPLE.COM 
        kdc = AMSTERDAM.EXAMPLE.COM 
        kdc = ROTTERDAM.EXAMPLE.COM 
        default_domain = EXAMPLE.COM 
        }

        EXAMPLEDMZ.COM = { 
        kdc = localhost:2088 
        default_domain = EXAMPLEDMZ.COM 
        }

        [domain_realm] 
        example.com = example.COM 
        .example.com = example.COM 
        exampledmz.com = EXAMPLEDMZ.COM 
        .exampledmz.com = EXAMPLEDMZ.COM

        [libdefaults] 
        default_realm = EXAMPLE.COM 
        rdns = false 
        udp_preference_limit = 1

Refer to the [Kerberos documentation](http://web.mit.edu/kerberos/krb5-current/doc/admin/conf_files/krb5_conf.html) for more information about `krb5.conf`.

## WinRM command fails with a 500 response code

Multiple causes can lead to this error message:

1. If the command was executing for a long time, this might have been caused by a timeout. You can increase the WinRM timeout specified by the `winrmTimeout` property to increase the request timeout. Don't forget to increase the `MaxTimeoutms` setting on the remote host as well. For example, to set the maximum timeout on the server to five minutes, enter the following command:

        winrm set winrm/config @{MaxTimeoutms="300000"}

1. If a lot of commands are being executed concurrently, increase the `MaxConcurrentOperationsPerUser` setting on the server. For example, to set the maximum number of concurrent operations per user to 100, enter the following command:

        winrm set winrm/config/service @{MaxConcurrentOperationsPerUser="100"}

Other configuration options that may be of use are `Service/MaxConcurrentOperations` and `MaxProviderRequests` (WinRM 1.0 only).

## WinRM command fails with an unknown error code

If you see an unknown WinRM error code in the logging, you can use the `winrm helpmsg` command to get more information, e.g.

    winrm helpmsg 0x80338104
    The WS-Management service cannot process the request. The WMI service returned an 'access denied' error.

Courtesy of [this PowerShell Magazine blog post by Shay Levy](http://www.powershellmagazine.com/2013/03/06/pstip-decoding-winrm-error-messages/).

## WinRS command fails with `out of memory` error

After customizing the value of `MaxMemoryPerShellMB`, you may receive an "Out of memory" error when executing a WinRS command. This is caused by an error in WinRM. A hot fix is available from [Microsoft](http://support.microsoft.com/kb/2842230).

## Kerberos authentication fails with the message `Unable to load realm info from SCDynamicStore`

The Kerberos subsystem of Java cannot start up. Did you configure it as described in [the section on Kerberos setup for the source host](#cifs_host_setup_krb5)?

## Kerberos authentication fails with the message `Cannot get kdc for realm ...`

The Kerberos subsystem of Java cannot find the information for the realm in the `krb5.conf` file. The realm name specified in [the Kerberos configuration on the source host](#cifs_host_setup_krb5) is case sensitive and must be entered in upper case in the `krb5.conf` file.

Alternatively, you can use the `dns_lookup_kdc` and `dns_lookup_realm` options in the `libdefaults` section to automatically find the right realm and KDC from the DNS server if it has been configured to include the necessary `SRV` and `TXT` records:

    [libdefaults]
        dns_lookup_kdc = true
        dns_lookup_realm = true

## Kerberos authentication fails with the message `Server not found in Kerberos database (7)`

The service principal name for the remote host has not been added to Active Directory. Did you add the SPN as described in [the section on Kerberos setup for remote hosts](#cifs_host_setup_spn)?

## Kerberos authentication fails with the message `Pre-authentication information was invalid (24)` or `Identifier doesn't match expected value (906)`

The username or the password supplied was invalid. Did you supply the correct credentials?

## Kerberos authentication fails with the message `Integrity check on decrypted field failed (31)`

Is the target host part of a Windows 2000 domain? In that case, you'll have to add `rc4-hmac` to the supported encryption types:

    [libdefaults]
        default_tgs_enctypes = aes256-cts-hmac-sha1-96 des3-cbc-sha1 arcfour-hmac-md5 des-cbc-crc des-cbc-md5 des-cbc-md4 rc4-hmac
        default_tkt_enctypes = aes256-cts-hmac-sha1-96 des3-cbc-sha1 arcfour-hmac-md5 des-cbc-crc des-cbc-md5 des-cbc-md4 rc4-hmac

## Kerberos authentication fails with the message `Message stream modified (41)`

The realm name specified in [the Kerberos configuration on the source host](#cifs_host_setup_krb5) does not match the case of the Windows domain name. The realm name is case sensitive and must be entered in upper case in the `krb5.conf` file.

## I am not using Kerberos authentication and I still see messages saying `Unable to load realm info from SCDynamicStore`

The Kerberos subsystem of Java cannot start up and the remote WinRM server is sending a Kerberos authentication challenge. If you are using local accounts, the authentication will proceed successfully despite this message. To remove these messages either configure Kerberos as described in [the section on Kerberos setup for the source host](#cifs_host_setup_krb5) or disallow Kerberos on the WinRM server as described in step 4 of [the section on WinRM setup](#cifs_host_setup_winrm).
