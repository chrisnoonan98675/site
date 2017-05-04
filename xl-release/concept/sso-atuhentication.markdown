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
                url = "ldap://dc01.lab.local"
                userDn = "_user@domain_"
                password = "_password_"

                userSearch {
                  base = "cn=users,dc=lab,dc=local"
                  filter = "(&(objectClass=user)(userPrincipalName={0}))"
                }

                groupSearch {
                  base = "cn=users,dc=lab,dc=local"
                  filter = "(&(objectClass=group)(member={0}))"
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
