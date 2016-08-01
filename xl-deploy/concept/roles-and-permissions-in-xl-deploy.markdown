---
title: Roles and permissions in XL Deploy
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
weight: 260
---

XL Deploy includes fine-grained access control that ensures the security of your middleware and deployments. The security mechanism is based on the concepts of _principals_, _roles_, and _permissions_.

## Principals

A security principal is an entity that can be authenticated in XL Deploy. Out of the box, XL Deploy supports only users as principals; users are authenticated by means of a user name and password. When using an LDAP repository, users and groups in LDAP are also treated as principals.

For more information about LDAP, refer to [How to connect to your LDAP or Active Directory](/xl-deploy/how-to/connect-ldap-or-active-directory.html).

## Roles

Roles are groups of principals that have certain permissions in XL Deploy. Roles are usually identified by a name that indicates the role the principals have within the organization; for example, _deployers_. In XL Deploy, permissions can only be granted to or revoked from a role.

When permissions are granted, all principals that have the role are allowed to perform some action or access repository entities. You can also revoke granted rights to prevent the action in the future.

## Permissions

Permissions are rights in XL Deploy. Permissions control what actions a user can execute in XL Deploy, as well as which parts of the repository the user can see and change. XL Deploy supports *global* and *local* permissions.

### Global permissions

Global permissions apply to XL Deploy and its repository.

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

In XL Deploy, you can set local security permissions on repository nodes (such as Applications or Environments) and on directories in the repository.

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

### How local permissions work in the hierarchy

The security settings on a lower level overwrite all permissions from a higher level. There is no inheritance from higher levels, combining settings from various directories. If there are no permissions set on a directory, the permission settings from the parent are taken (recursively). So if you have a deep hierarchy of nested directories, but you do not set any permissions on them, XL Deploy will take the permissions set on the root node.

All directories higher up in a hierarchy must provide read permission for the roles defined in the lowest directory. Otherwise, the permissions themselves cannot be read. This analogous to file permissions on Unix directories.

For example, if you have read permission on the **Environments** root node, you will have read permissions on the directories and environments that it contains. If the **Environments/production** directory has its own permissions set, your access to the **Environments/production/PROD-1** environment depends on the permissions set on the **Environments/production** directory CI itself.
