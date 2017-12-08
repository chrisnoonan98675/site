---
title: Configure OpenID Connect(OIDC) Authentication for XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- security
- sso
- 2fa
- oidc
- system administration
- user management
since: XL Deploy 7.5.0
---

This topic describes how to configure XL Deploy authentication using the OpenID Connect(OIDC) protocol.
OpenID Connect (OIDC) is an identity layer built on top of the OAuth 2.0 protocol and supported by various OAuth 2.0 providers.

OIDC defines a sign-in flow that enables a client application to authenticate a user. User identity information is encoded in a secure JSON Web Token (JWT), called ID token.
You can log into XL Deploy using various Identity providers that support the OIDC authentication protocol,
such as OKTA, [Keycloak](http://www.keycloak.org/downloads.html), and Azure Active Directory (Office 365).

You can easily add an additional layer of security for XL Deploy by enabling multi-factor authentication (MFA).
Using MFA and depending on your identity provider settings, users are required to acknowledge a verification phone call, text message, or app notification on their smart phones after correctly entering their passwords.

## Requirements

### Server requirements

* [XL Deploy 7.5](/xl-deploy/concept/requirements-for-installing-xl-deploy.html) or later
* [XL Deploy OIDC Authentication plugin](https://dist.xebialabs.com/customer/xl-deploy/plugins/xld-auth-oidc-plugin) installed

## Setup

### Server setup

On `xl-deploy.example.com`:

1. Download the XL Deploy OIDC Authentication plugin ZIP from the [distribution site](https://dist.xebialabs.com/customer/xl-deploy/plugins/xld-auth-oidc-plugin).
1. Copy the plugin inside the `XL_Deploy_SERVER_HOME/lib/` directory.
1. Remove the Default Authentication plugin `security-plugin-default-*.jar` from the `XL_DEPLOY_SERVER_HOME/lib/` directory.
1. To configure the OIDC Authentication plugin, create the `XL_DEPLOY_SERVER_HOME/conf/auth-oidc.conf` file with this content:

        xl {
          security {
            auth {
              providers {
                oidc {
                  clientId="<your client id here>"
                  clientSecret="<your client secret here>"        

                  keyRetrievalUri="https://oidc.example.com/endpoint/keys"

                  keyRetrievalSchedule = "<cron_schedule>"

                  issuer="<OpenID Provider Issuer here>"

                  accessTokenUri="<The redirect URI to use for returning the access token>"                  
                  userAuthorizationUri="<The authorize endpoint to request tokens or authorization codes via the browser>"
                  logoutUri="<The logout endpoint to revoke token via the browser>"
                  redirectUri="https://xl-deploy.example.com/login/external-login"

                  rolesClaimName="<your roles claim here>"
                  userNameClaimName="<your username claim here>"
                }
              }
            }
          }
        }

**Notes:**
1. To determine the actual values for the variables in the `auth-oidc.conf` file, see *Discovery endpoint*.
1. Only one authentication plugin at a time is supported. Make sure that your plugins directory contains only one `security-plugin-*.jar` plugin.
1. To obtain the signing keys from the Identity provider, XL Deploy checks the `keyRetrievalUri` endpoint periodically. To set a specific time interval, set the value of `keyRetrievalSchedule` to a cron expression.   

### Discovery endpoint

The discovery endpoint is a static page that you use to retrieve metadata about your OIDC Identity Provider.

In most of the cases the discovery endpoint is available through `/.well-known/openid-configuration` relative to the base address of your Identity Provider.
For example: `https://login.microsoftonline.com/xebialabs.com/.well-known/openid-configuration`

The field names and values are defined in the [OpenID Connect Discovery Specification](https://openid.net/specs/openid-connect-discovery-1_0.html).

The `issuer`, `accessTokenUri`, `userAuthorizationUri`, `jwks_uri`, and `logoutUri` options are also usually presented in the JSON metadata that the Identity Provider server publishes at the discovery endpoint. The path obtained in the `jwks_uri` is used as a value for `keyRetrievalUri` in the `XL_DEPLOY_SERVER_HOME/conf/auth-oidc.conf` file.

**Note:** The `redirectUri` endpoint must always point to the `/login/external-login` XL Deploy endpoint. The `redirectUri` is an endpoint where authentication responses can be sent and received by XL Deploy. It must exactly match one of the `redirect_uris` you registered in OKTA and Azure AD portal and it must be URL encoded. For Keycloak you can register a pattern for `redirect_uri` from the Keycloak Admin Panel (For example, you can provide a mask such as: `http://example.com/mask**` that matches `http://example.com/mask/` or `http://example.com/mask`).

### Select *claims* for a specific user

In OIDC there are notions called **claims** that define the settings to obtain information about a specific user, such as the username and roles. When a user logs in, the XL Deploy server receives a token with a number of claims (a number of key-value pairs). From these claims, you must select two keys that represent the username and the user roles.

You can provide the required claims from the following configuration properties:
* `rolesClaimName` - In XL Deploy, the OIDC roles become principals that you can assign to roles inside XL Deploy.
* `userNameClaimName` - Unique username for both internal and external users. You cannot sign in with a user if a local account with the same username exists.

## OpenID Connect Logout
The logout works by directing the user’s browser to the end-session endpoint of the OpenID Connect provider, with the logout request parameters encoded in the URL query string.
If you need to redirect to the login page after logout, you can use your `redirectUri` as the `post_logout_redirect_uri` query parameter.
Example: https://xl-deploy.example.com/auth/realms/XLDeploy/protocol/openid-connect/logout?post_logout_redirect_uri=https://xl-deploy.example.com/login/external-login

## Current setup limitations
1. The supported tokens are JWT tokens with RS256 signatures
1. Unsigned tokens and HS256 signed tokens are not supported.
1. XL Deploy does not support `nonce` in the OIDC handshake (protection against replay attacks).
1. OpenID Connect provider and XL Deploy instances should be time synchronized (for example on NTP).

## Login as an Internal User
The plugin offers a seamless user experience by automatically redirecting an unauthenticated user to the Identity Provider's login page.
This does not allow you to sign in directly as an Internal User. If you want to sign in as an Internal User, you can browse directly to `xl-deploy.example.com/login`.

## Integration with Keycloak Identity provider

### Installing Keycloak

1. Download the latest version of [Keycloak](http://www.keycloak.org/downloads.html)
1. Unpack the file, open a terminal window and go to the directory where you extracted the file.
1. Go to the `bin` directory and run `standalone.sh`.
1. By default Keycloak runs on port `8080`. Navigate with your browser to that location and create an admin user (example: admin/admin).
1. When the admin user is created, go to the Administration Console.

### Setting up configuration for XL Deploy in Keycloak

1. On the top left, go to **Master**, open the drop down menu, and click **Add realm**.
1. Set the name to **XLDeploy**, and save it.
1. Set the **Display name** to **XL Deploy**.
1. On the sidebar, select **Clients**
1. Click **create**, set the **client ID** to **XL Deploy Server**, and click **Save**.
1. Set **Access Type** to **Confidential**, and add the URL to your XL Deploy server (for example: 'http://localhost:4516/') as Valid Redirect URI.
1. Click **Save**. A new **Credentials** tab appears.

* To add roles to Keycloak, select **Roles** on the sidebar. These will be mapped to the XL Deploy principals later.
For example: If you create a user `JohnDoe` and you assign the role `admins` to the user, and in XL Deploy you assign `admin permission` to `admins`, then the `JohnDoe` user can log in as an `admin` in XL Deploy.

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
1. Configure one or more **Redirect URIs** for your XL Deploy application.
1. Click **Finish**

* Using the **People** or **Groups** tabs, assign people to your newly created XL Deploy application.

**Note:** Users cannot authenticate to your XL Deploy application if they are not assigned.

* Find your **Client ID** in the **Client Credentials** section of the **Groups** tab. You will use the Client ID that you just obtained to configure the XL Deploy server as it was described above.

For more details on how to configure OKTA, refer to [OKTA documentation](https://developer.okta.com/docs/api/resources/oidc.html#openid-connect-api).

## Integration with Azure Active Directory (Office 365)

Register your application as described [here](https://docs.microsoft.com/en-us/azure/active-directory/develop/active-directory-protocols-openid-connect-code#register-your-application-with-your-ad-tenant) and use `https://login.microsoftonline.com/{your tenant id}/.well-known/openid-configuration` to collect configurations.
