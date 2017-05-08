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

`xl-auth-spnego-plugin` is an authentication plugin for XL Release allowing Kerberos Single Sign-On authentication.

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
- Windows domain: EXAMPLE.COM
- Windows workgroup: EXAMPLE
- Windows Domain Controller machine: `dc.example.com`
- Windows Workstation machine: `client.example.com`
- Windows Domain administrator user: `Administrator@EXAMPLE.COM`
- Some Windows Domain (normal) users: (i.e. Bob@EXAMPLE.COM)
- XL Release server machine: `xl-release.example.com`

Please adapt the values to your actual environment.

### Server setup

#### Example Windows Domain Controller

On `dc.example.com`:
- create an HTTP server account user for XL Release server in Active Directory:
  - Sam account name: `xl-release`
  - User principal name: `xl-release@example.com`
  - Service principal names: `HTTP/xl-release.example.com`
  - Password: `Passw0rd`
- export the keytab to `C:\example.com-xl-release_keytab`:

```
ktpass `
    /out C:\example.com_xl-release_keytab `
    /mapuser xl-release@EXAMPLE.COM `
    /princ HTTP/xl-release.example.com@EXAMPLE.COM `
    /pass Passw0rd `
    /ptype KRB5_NT_PRINCIPAL `
    /crypt All    
```
- copy the keytab to `xl-release.example.com` machine


#### Example XL Release Server:

We assume XL Release was installed under `XL_RELEASE_SERVER_HOME` directory and that you copied the keytab file under `/tmp`.

On 'xl-release.example.com':
- download and unzip the `xl-auth-spnego-plugin` zip file inside `XL_RELEASE_SERVER_HOME/plugins/` directory.
- edit `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file and add a `xl.security.auth.providers` section:
```
xl {
  security {
    auth {
      providers {
        kerberos {
          servicePrincipal = "HTTP/xl-release.example.com@EXAMPLE.COM"
          keyTabLocation = "file:///tmp/example.com_xl-release_keytab"

          ldap {
            url = "ldap://dc.example.com"
            userDn = "xl-release@example.com" // check if it actually works with != Administrator
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
```


### Example Windows Client setup

- Make sure you can login into your Windows workstation using a domain user.
- Add the network interface that will be used to contact `xl-release.example.com` to the list of trusted networks.

#### Browsers

Chrome and Internet Explorer will work without any further configuration.
For Firefox, some configuration is needed.

Navigate to about:config in the url and then type 'negotiate' into the 'Filter' field and set the following fields to:

   
    network.negotiate-auth.delegation-uris  http://xl-release.example.com
    network.negotiate-auth.trusted-uris     http://xl-release.example.com

Note: if your XL Release instance runs on HTTPS, please use https:// instead of http://.

Then type 'sspi' into the 'Filter' field and set the following field to:

    network.auth.use-sspi false

### Test it out

- restart XL Release
- login as a normal domain user on `client.example.com`
- open a browser and point it to `http://xl-release.example.com/` (or `https://xl-release.example.com/` if you use HTTPS).
- click **Sign in with Single Sign-On**.

If you are using a Windows machine, the authentication starts immediately. A confirmation message is displayed if you have been successfully authenticated. If you are using a non-Windows machine, you must provide Windows Active Directory credentials to sign in.

