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

Using single sign-on (SSO) authentication, you can log in to XL Release automatically with the username and password used to log in to your Windows machine.

You can log in to XL Release from a non-Windows machine using Windows SSO authentication. To log in as an external user, make sure you [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html).

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

## Requirements

- XL Release 7.0 or later

## Browser support
 
- XLR supported browsers (Internet Explorer, Chrome, and Firefox) are all supported. For Firefox on Windows, the about:config file needs to be updated. Navigate to about:config in the url and then type 'sspi' into the 'Filter' field and set the field to be:
 
   `network.auth.use-sspi false`
     

    