---
title: Overview of security in XL Deploy
categories:
- xl-deploy
subject:
- Security
tags:
- security
- principals
- permissions
- roles
- ldap
- system administration
---

XL Deploy supports a fine-grained access control scheme to ensure the security of your middleware and deployments. XL Deploy's security mechanism is based on the concepts of _principals_, _roles_ and _permissions_.

## Principals

A (security) principal is an entity that can be authenticated in XL Deploy. Out of the box, XL Deploy supports only users as principals; users are authenticated by means of a username and password. When using an LDAP repository, users and groups in LDAP are also treated as principals.

For more information about LDAP, refer to [How to connect to your LDAP or Active Directory](/xl-deploy/how-to/connect-ldap-or-active-directory.html).

## Roles

Roles are groups of principals that have certain permissions in XL Deploy. Roles are identified by a name that often indicates the role the principals have within the organization (such as 'deployers'). Permissions in the XL Deploy security system can **only** be assigned to roles.

## Permissions

Permissions are rights in XL Deploy. Permissions control what actions a user can execute in XL Deploy, but also which parts of the repository the user can see and change.

Permissions come in two flavors, **global** and **local**. Global permissions apply in all of the XL Deploy product and repository. Once a global permission is granted to a role, XL Deploy allows principals with this role to execute the associated action on all CIs in the repository.

Local permissions are granted **only** on `core.Directory` CIs and root nodes and apply to the subset of the repository contained in the directory. Within this directory, the user can execute the actions that are allowed by the permission. Outside of the directory, this permission does not apply. 

### Global permissions

The following table shows the global permissions that XL Deploy supports.

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| `admin` | This permission grants all rights within XL Deploy. |
| `discovery` | The right to perform discovery of middleware. |
| `login` | The right to log into the XL Deploy application. This permission does not automatically allow the user access to nodes in the repository. |
| `security#edit` | The right to administer security permissions. |
| `task#assign` | The right to reassign any task to someone else. |
| `task#takeover` | The right to assign any task to yourself. |
| `task#preview_step` | The right to inspect scripts that will be executed with steps in the deployment plan. |
| `report#view` | The right to see all the reports. When granted, the UI will show the Reports tab. To be able to view the full details of an archived task, a user needs read permissions on both the environment and application. |
| `controltask#execute` | The right to execute control tasks on configuration items. |

### Local permissions

The following table shows the local permissions that XL Deploy supports.

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| `deploy#initial` | The right to perform an initial deployment of a package to an environment. Applies only for the Environment CIs within the containing directory. |
| `deploy#undeploy` | The right to undeploy an application. Applies only for the environment CIs within the containing directory. |
| `deploy#upgrade` | The right to perform an upgrade of a deployment on an environment. Note that this does not allow deploying items from the package to new targets. Applies only for the environment CIs within the containing directory. |
| `import#initial` | The right to import a package for which the application does not yet exist in the repository and for which a new application will be created. |
| `import#remove` | The right to remove an application or package. Applies only for application and deployment package CIs within the containing directory. |
| `import#upgrade` | The right to import a package for which the application already exists in the repository. Applies only for application CIs within the containing directory. |
| `read` | The right to see CIs in the repository. |
| `repo#edit` | The right to edit (create and modify) CIs in the repository. The user must also have read access to CIs to be able to edit them. Applies only for the CIs within the containing directory. |
| `task#assign` | The right to transfer a task to another user. |
| `task#skip_step` | The right to skip steps in the generated step list before starting a deployment. Applies only for deployments executed on environment CIs in the containing directory. |

## Granting and revoking

Access rights in XL Deploy can be _granted_ to a role or _revoked_ from a role. When rights are granted, all principals that have this role are allowed to perform some action or access repository entities. Granted rights can be revoked again to prevent the action in the future.

## Inherited permissions

In general, permissions on a directory CI are _inherited_ from its parent _unless_ the directory CI specifies its own permissions. 

For example, if you have read permission on the `Environments` root node, you will have read permissions on the  directories and environments that it contains. If the `Environments/production` directory has its own permissions set, your access to the `Environments/production/PROD-1` environment depends on the permissions set on the `Environments/production` directory CI itself.
