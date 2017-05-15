---
title: Configure Integrated Windows Authentication for XL Release
categories:
- xl-release
subject:
- System administration
tags:
- security
- sso
- iwa
- windows
- active directory
- system administration
- user management
since: XL Release 7.0.0
---

This topic describes how to configure XL Release to use Integrated Windows Authentication to authenticate users and retrieve role (group) membership without prompting the users for a user name and password (Single Sign-On). In XL Release, Active Directory users and groups become principals that you can assign to roles.

While role memberships and permissions assigned to roles are stored in the XL Release JCR repository, XL Release treats the Active Directory repository as read-only. This means that XL Release will use information from the Active Directory repository, but it cannot make changes to that information.

**Note:** Currently XL Release supports only the SPNEGO/Kerberos based cryptographic exchange. NTLMSSP authentication is not supported.

## Requirements

### Server requirements

* Oracle JDK 1.8
* [XL Release 7.0](/xl-release/concept/requirements-for-installing-xl-release.html) or later
* XL Release SPNEGO Authentication plugin installed

### Environment requirements

* Microsoft Windows Server 2012 R2 (or later) Windows domain controller, with configured DNS Server and Active Directory

### Client requirements

* Chrome
* Internet Explorer 11 or later
* Firefox (requires additional configuration)

## Setup

Example setup:

* Windows domain: EXAMPLE.COM
* Windows workgroup: EXAMPLE
* Windows Domain Controller machine: `dc.example.com`
* Windows Workstation machine: `client.example.com`
* Windows Domain administrator user: `Administrator@EXAMPLE.COM`
* Some Windows Domain (normal) users: (i.e. Bob@EXAMPLE.COM)
* XL Release server machine: `xl-release.example.com`

Please adapt the values to your actual environment.

### Server setup

#### Configure the Windows Domain Controller

On `dc.example.com`:

1. Create an HTTP server account user for XL Release server in Active Directory:
  * Sam account name: `xl-release`
  * User principal name: `xl-release@example.com`
  * Service principal names: `HTTP/xl-release.example.com`
  * Password: `Passw0rd`
1. Export the Kerberos Keytab file to `C:\example.com-xl-release_keytab`:

    ktpass `
        /out C:\example.com_xl-release_keytab `
        /mapuser xl-release@EXAMPLE.COM `
        /princ HTTP/xl-release.example.com@EXAMPLE.COM `
        /pass Passw0rd `
        /ptype KRB5_NT_PRINCIPAL `
        /crypto All    

1. Copy the Kerberos Keytab file to `xl-release.example.com` machine.

#### Configure the XL Release Server

It is expected that XL Release was installed under `XL_RELEASE_SERVER_HOME` directory and that the exported Kerberos Keytab file was copied under `/tmp`.

On `xl-release.example.com`:

1. Download the XL Release SPNEGO Authentication plugin from https://xebialabs.com/products/xl-release/plugins/.
1. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.
1. To configure the SPNEGO Authentication plugin, modify the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file by adding a `xl.security.auth.providers` section:

        xl {
          security {
            auth {
              providers {
                kerberos {
                  servicePrincipal = "HTTP/xl-release.example.com@EXAMPLE.COM"
                  keyTabLocation = "file:///tmp/example.com_xl-release_keytab"

                  ldap {
                    url = "ldap://dc.example.com"
                    userDn = "xl-release@example.com"
                    password = "Passw0rd"

                    userSearch {
                      base = "cn=users,dc=example,dc=com"
                      filter = "(&(objectClass=user)(userPrincipalName={0}))"
                    }

                    groupSearch {
                      base = "cn=users,dc=example,dc=com"
                      filter = "(&(objectClass=group)(member={0}))"
                      rolePrefix = ""
                    }
                  }
                }
              }
            }
          }
        }

#### Optional: form-based authentication using domain credentials

With the upper configuration in place, you can automatically access any XL Release page directly (SSO) without entering credentials on the login page. If you still want to provide the classic form-based authentication against Active Directory (e.g. login using domain credentials from non Microsoft Windows client computers), you must modify the `XL_RELEASE_SERVER_HOME/conf/xl-release-security.xml` security configuration file:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:security="http://www.springframework.org/schema/security"
           xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
        ">

        <bean id="rememberMeAuthenticationProvider"
            class="com.xebialabs.deployit.security.authentication.RememberMeAuthenticationProvider"
        />

        <security:authentication-manager alias="authenticationManager">
            <security:authentication-provider ref="rememberMeAuthenticationProvider"/>
            <security:authentication-provider ref="activeDirectoryProvider"/>
            <security:authentication-provider ref="xlAuthenticationProvider"/>
        </security:authentication-manager>
    </beans>

The `activeDirectoryProvider` authentication provider uses the values specified in the `xl.security.auth.providers.kerberos.ldap` section of the `xl-release.conf` configuration file.

### Configure the Windows Client

* Make sure you can log in into your Windows workstation using a domain user.
* Add the network interface that will be used to contact `xl-release.example.com` to the list of trusted networks.

#### Browsers

Chrome and Internet Explorer do not require any further configuration.
For Firefox, the following configuration settings must be modified:

Navigate to `about:config` in the URL, type 'negotiate' into the **Filter** field, and set the following fields to:

    network.negotiate-auth.delegation-uris  xl-release.example.com
    network.negotiate-auth.trusted-uris     xl-release.example.com


### Test the authentication plugin

1. Restart XL Release.
1. Log in as a domain user on `client.example.com`
1. Open a browser and navigate to `http://xl-release.example.com/` (or `https://xl-release.example.com/` if you use HTTPS).
1. Click **Log in with Windows**.

If you are using a Windows machine, the authentication starts immediately. A confirmation message is displayed if you have been successfully authenticated. If you are using a non-Windows machine, you must manually enter your Active Directory credentials into the login form.
