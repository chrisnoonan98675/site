---
title: Configure OpenID Connect(OIDC) Authentication for XL Release
categories:
- xl-release
subject:
- System administration
tags:
- security
- sso
- 2fa
- oidc
- system administration
- user management
since: XL Release 7.2.0
---

This topic describes how to configure XL Release authentication using the OpenID Connect(OIDC) protocol.
OpenID Connect (OIDC) is an identity layer built on top of the OAuth 2.0 protocol and supported by various OAuth 2.0 providers.

OIDC defines a sign-in flow that enables a client application to authenticate a user. User identity information is encoded in a secure JSON Web Token (JWT), called ID token.
You can log into XL Release using various Identity providers that support the OIDC authentication protocol,
such as OKTA, [Keycloak](http://www.keycloak.org/downloads.html), and Azure Active Directory (Office 365).

## Requirements

### Server requirements

* [XL Release 7.2](/xl-release/concept/requirements-for-installing-xl-release.html) or later
* [XL Release OIDC Authentication plugin](https://dist.xebialabs.com/customer/xl-release/plugins/xlr-auth-oidc-plugin) installed

### Environment requirements

### Client requirements

* Chrome
* Internet Explorer 11 or later
* Firefox

## Setup

### Server setup

On `xl-release.example.com`:

1. Download the XL Release OIDC Authentication plugin ZIP from the [distribution site](https://dist.xebialabs.com/customer/xl-release/plugins/xlr-auth-oidc-plugin).
2. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.
3. Remove the Default Authentication plugin `xlr-auth-default-plugin-*.jar` from the `XL_RELEASE_SERVER_HOME/plugins/` directory.
4. To configure the OIDC Authentication plugin, modify the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file by adding a `xl.security.auth.providers` section:

        xl {
          security {
            auth {
              providers {
                oidc {
                  clientId="<your client id here>"
                  clientSecret="<your client secret here>"        

                  publicKey="<your public key here>"

                  issuer="<OpenID Provider Issuer here>"

                  accessTokenUri="<The redirect URI to use for returning the access token>"                  
                  userAuthorizationUri="<The authorize endpoint to request tokens or authorization codes via the browser>"
                  logoutUri="<The logout endpoint to revoke token via the browser>"
                  redirectUri="https://xl-release.example.com/oidc-login"

                  scopes=["<your>", "<scopes>", "<here>"]

                  rolesClaim="<your roles claim here>"
                  userNameClaim="<your username claim here>"
                  emailClaim="<your email claim here>"
                  fullNameClaim="<your fullName claim here>"
                }
              }
            }
          }
        }

**Note**: Only one authentication plugin at a time is supported. Make sure that your plugins directory contains only one `xlr-auth-*.jar` plugin.

### Discovery endpoint

The discovery endpoint is a static page that you use to retrieve metadata about your OIDC Identity Provider.

In most of the cases the discovery endpoint is available through `/.well-known/openid-configuration` relative to the base address of your Identity Provider.
For example: `https://login.microsoftonline.com/xebialabs.com/.well-known/openid-configuration`

The field names and values are defined in the [OpenID Connect Discovery Specification](https://openid.net/specs/openid-connect-discovery-1_0.html).

In OIDC there are notions called **scopes** and **claims** that define the settings to obtain information about a specific user, such as the username, name, email, and group.

You can provide the required claims from the following configuration properties:
* `rolesClaim` - In XL Release, the OIDC roles become principals that you can assign to roles inside XL Release.
* `userNameClaim` - Unique username for both internal and external users. You cannot sign in with a user if a local account with the same username exists.
* `emailClaim` - The email address is required to send notifications, for example: when a task that is assigned to you starts.             
* `fullNameClaim` - The full name of the user profile.

The fields described above must be present in the scopes that you can provide from **scopes**.

## Current setup limitations
1. The supported tokens are JWT tokens with RS256 signatures
1. Unsigned tokens and HS256 signed tokens are not supported.
1. Only static configuration is supported. If an OIDC provider changes the encryption keys, the XL Release instance must be reconfigured and rebooted.
5. XL Release does not support `nonce` in the OIDC handshake (protection against replay attacks).
6. OpenID Connect provider and XL Release instances should be time synchronized (for example on NTP).

## Login as an Internal User
The plugin offers a seamless user experience by automatically redirecting an unathenticated user to the Identity Provider's login page.
This does not allow you to sign in directly as an Internal User. If you want to sign in as an Internal User, you can browse directly to `xl-release.example.com/login`.

**Note**: The `xl-release.example.com/login` page is also an entry point when a local account has an identical username with another user from your Identity Provider. The user is automatically redirected to the page with a corresponding message.

## Integration with Keycloak Identity provider

### Installing Keycloak

1. Download the latest version of [Keycloak](http://www.keycloak.org/downloads.html)
1. Unpack the file, open a terminal window and go to the directory where you extracted the file.
1. Go to the `bin` directory and run `standalone.sh`.
1. By default Keycloak runs on port `8080`. Navigate with your browser to that location and create an admin user (example: admin/admin).
1. When the admin user is created, go to the Administration Console.

### Setting up configuration for XL Release in Keycloak

1. On the top left, go to **Master**, open the drop down menu, and click **Add realm**.
1. Set the name to **XLRelease**, and save it.
1. Set the **Display name** to **XL Release**.
1. On the sidebar, select **Clients**
1. Click **create**, set the **client ID** to **XL Release Server**, and click **Save**.
1. Set **Access Type** to **Confidential**, and add the URL to your XL Release server (for example: 'http://localhost:4516/') as Valid Redirect URI.
1. Click **Save**. A new **Credentials** tab appears.

* To add roles to Keycloak, select **Roles** on the sidebar. These will be mapped to the XL Release principals later.
For example: If you create a user `JohnDoe` and you assign the role `admins` to the user, and in XL Release you assign `admin permission` to `admins`, then the `JohnDoe` user can log in as an `admin` in XL Release.

1. On the sidebar, under **Users**, click **Add user**. Set the username to `JohnDoe` and click **Save**.
1. Select the **Credentials** tab and specify a password.

* If you have created roles, go to the **Role Mappings** tab and add the desired roles.
1. Click **Clients** in the sidebar, go to the **Mappers** tab, and click **Create**.
1. Specify a name (for example: group memberships), set the **Mapper Type** to **Group Membership**, set the **Token Claim Name** to `groups`, and set the **Add to ID Token** to **ON**.

## Integration with OKTA Identity provider

You can register an OAuth client by creating a new application integration from the OKTA Admin interface:

1. Log in to your OKTA organization as an administrator.
1. Click **Admin**.
1. From **Shortcuts** on the right side of the screen, click **Add Applications**.
1. Click **Create New App** on the left side of the screen.
1. Select **OpenID Connect** as the **Sign on method**.
1. Configure one or more **Redirect URIs** for your XL Release application.
1. Click **Finish**

* Using the **People** or **Groups** tabs, assign people to your newly created XL Release application.

**Note:** Users cannot authenticate to your XL Release application if they are not assigned.

* Find your **Client ID** in the **Client Credentials** section of the **Groups** tab. You will use the Client ID that you just obtained to configure the XL Release server as it was described above.

For more details on how to configure OKTA, refer to [OKTA documentation](https://developer.okta.com/docs/api/resources/oidc.html#openid-connect-api).

## Integration with Azure Active Directory (Office 365)

Register your application as described [here](https://docs.microsoft.com/en-us/azure/active-directory/develop/active-directory-protocols-openid-connect-code#register-your-application-with-your-ad-tenant) and use `https://login.microsoftonline.com/{your tenant id}/.well-known/openid-configuration` to collect configurations.
