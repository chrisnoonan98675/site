---
title: XL Release single sign-on (SSO) authentication
categories:
- xl-release
subject:
- Authentication
tags:
- sso
- single sign-on
- authentication
since: XL Release 7.0.0
---

Using single sign-on (SSO) authentication, you can log in to XL Release automatically without having to type your username or password.

You can log in to XL Release from a Windows machine using Windows SSO authentication.

// move to "how to/setup" section:
To log in as an external user, make sure you [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html).

// with the username and password used to log in to your Windows machine.
// say it is a plugin, where to find it, where to put it

## Requirements

### XL Release

- JDK 1.8
- [XL Release 7.0](/xl-release/concept/requirements-for-installing-xl-release.html) or later

### Environment

- Windows 2012r2 or later
- Active Directory Domain Services Tools
- Group Policy Management Console (GPMC)
- DNS Server

### Browsers

- Chrome 6.0.472 or later
- Internet Explorer 11 or later
- Firefox 1.7 beta or later

## Setup

Example setup:
- windows domain: EXAMPLE.COM
- windows workgroup: EXAMPLE
- Windows Domain Controller machine: dc.example.com
- Windows Workstation machine: client.example.com
- XL Release server machine: xl-release.example.com
- Windows Domain administrator user: Administrator@EXAMPLE.COM

### Server setup

#### Windows Domain Controller

On `dc.example.com`:
- create an HTTP server account user for XL Release server in Active Directory (i.e.: HTTP/xl-release@EXAMPLE.COM), with a password (i.e. "Passw0rd");
- export the keytab to `C:\example.com-xl-release_keytab`:
```ktpass `
    /out C:\example.com_xl-release_keytab `
    /mapuser xl-release@EXAMPLE.COM `
    /princ HTTP/xl-release.example.com@EXAMPLE.COM `
    /pass Passw0rd `
    /ptype KRB5_NT_PRINCIPAL `
    /crypt All```
- copy the keytab to `xl-release.example.com` machine

#### XL Release Server:

On 'xl-release.example.com':
- // configure...

### Client setup

// TODO: spend a few words on client setup (i.e. windows workstation).

#### Browser

Chrome and Internet Explorer will work without any further configuration.
For Firefox on Windows the about:config file needs to be updated.

Navigate to about:config in the url and then type 'negotiate' into the 'Filter' field and set the following fields to:

    ```
    network.negotiate-auth.delegation-uris  https://xl-release.full.url.or.something
    network.negotiate-auth.trusted-uris     https://xl-release.full.url.or.something```



'sspi' into the 'Filter' field and set the field to be:


   `network.auth.use-sspi false`

To use SSO authentication, you must download the `xlr-auth-spnego-plugin` zip file and extract it to the `XL_RELEASE_SERVER_HOME/plugins/` folder. In the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file, you must add the following security settings:

    xl {
      security {
        auth {
          providers {
            kerberos {
              servicePrincipal = "HTTP/server.lab.local@LAB.LOCAL"
              keyTabLocation = "file:///home/vagrant/server.keytab"

              ldap {
                url = "LDAP_SERVER_URL"
                userDn = "_user@domain_"
                password = "_password_"

                userSearch {
                  base = "USER_SEARCH_BASE"
                  filter = "USER_SEARCH_FILTER"
                }

                groupSearch {
                  base = "GROUP_SEARCH_BASE"
                  filter = "GROUP_SEARCH_FILTER"
                  rolePrefix = ""
                }
              }
            }
          }
        }
      }
    }

## Log in XL Release with Windows SSO

Open a new instance of XL Release in your browser and click **Sign in with Windows**.

**Note** Make sure your browser is configured for SSO authentication.

If you are using a Windows machine, the authentication starts immediately. A confirmation message is displayed if you have been successfully authenticated. If you are using a non-Windows machine, you must provide Windows Active Directory credentials to sign in.

