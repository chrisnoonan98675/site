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

A security principal is an entity that can be authenticated in XL Deploy. Out of the box, XL Deploy only supports users as principals; users are authenticated by means of a user name and password. When using an LDAP repository, users and groups in LDAP are also treated as principals. For more information about LDAP, refer to [How to connect to your LDAP or Active Directory](/xl-deploy/how-to/connect-ldap-or-active-directory.html).

XL Deploy includes a built-in user called `admin`. When you first install XL Deploy, this user is granted all global and local permissions. Prior to XL Deploy 6.0.0, `admin` was a special user account and you could not change its permissions. In XL Deploy 6.0.0 and later, `admin` is a normal user account with permissions that can be changed. If you assign `admin` to a role, then its default permissions are overridden by the permissions of that role.

## Roles

Roles are groups of principals that have certain permissions in XL Deploy. Roles are usually identified by a name that indicates the role the principals have within the organization; for example, _deployers_. In XL Deploy, permissions can only be granted to or revoked from a role.

When permissions are granted, all principals that have the role are allowed to perform some action or access repository entities. You can also revoke granted rights to prevent the action in the future.

## Permissions

Permissions are rights in XL Deploy. Permissions control what actions a user can execute in XL Deploy, as well as which parts of the repository the user can see and change. XL Deploy supports *global* and *local* permissions.

### Global permissions

Global permissions apply to XL Deploy and all of its repository. In cases where there is a local version and a global version of a permission, the global permission takes precedence over the local permission.

XL Deploy supports the following global permissions:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| `admin` | This permission grants all rights within XL Deploy. |
| `controltask#execute` | The right to execute control tasks on configuration items. |
| `discovery` | The right to perform discovery of middleware. |
| `login` | The right to log into the XL Deploy application. This permission does not automatically allow the user access to nodes in the repository. |
| `report#view` | The right to see all the reports. When granted, the UI will show the Reports tab. To be able to view the full details of an archived task, a user needs read permissions on both the environment and application. |
| `security#edit` | The right to administer principals, roles, and permissions. |
| `task#assign` | The right to assign a task to another user. |
| `task#move_step` | This permission has no effect. |
| `task#preview_step` | The right to inspect scripts that will be executed for steps in an execution plan. |
| `task#skip_step`| The right to skip a step in an execution plan. |
| `task#takeover` | The right to assign a task to yourself. |

### Local permissions

In XL Deploy, you can set local security permissions on repository nodes (such as **Applications** or **Environments**) and on directories in the repository. In cases where there is a local version and a global version of a permission, the global permission takes precedence over the local permission.

XL Deploy supports the following local permissions:

{:.table .table-striped}
| Permission | Description | Applies to... |
| ---------- | ----------- | ------------- |
| `controltask#execute` | The right to execute control tasks on configuration items. | Applications, Environments, Infrastructure, and Configuration  |
| `generate#dsl` | The right to generate the contents of a directory as a Groovy file. | Applications, Environments, Infrastructure, and Configuration |
| `deploy#initial` | The right to perform an initial deployment of an application to an environment. | Environments |
| `deploy#undeploy` | The right to undeploy an application from an environment. | Environments |
| `deploy#upgrade` | The right to perform an update deployment of an application to an environment. Note that this permission does not allow the user to deploy deployables in the package to new targets. | Environments |
| `import#initial` | The right to import a package for an application that does not exist in the repository. | Applications |
| `import#remove` | The right to remove an application or package. | Applications |
| `import#upgrade` | The right to import a package for an application that already exists in the repository. | Applications |
| `read` | The right to see CIs in the repository. | Applications, Environments, Infrastructure, and Configuration |
| `repo#edit` | The right to create and modify CIs in the repository. The user must also have read access to CIs to be able to edit them. | Applications, Environments, Infrastructure, and Configuration |
| `task#move_step` | This permission has no effect. | Environments |
| `task#skip_step` | The right to skip a step in an execution plan. | Environments |
| `task#takeover` | The right to assign a task to yourself. | Environments |

### How local permissions work in the hierarchy

In the hierarchy of the XL Deploy repository, the permissions that are configured on a lower level of the hierarchy overwrite all permissions from a higher level. There is no inheritance from higher levels; that is, XL Deploy does not combine settings from various directories. If there are no permissions set on a directory, the permission settings from the parent are taken (recursively). This means that, if you have a deep hierarchy of nested directories and you do not set any permissions on them, XL Deploy will take the permissions set on the root node.

All directories higher up in a hierarchy must provide `read` permission for the roles defined in the lowest directory. Otherwise, the permissions themselves cannot be read. This analogous to file permissions on Unix directories.

For example, if you have read permission on the **Environments** root node, you will have read permissions on the directories and environments that it contains. If the **Environments/production** directory has its own permissions set, then your access to the **Environments/production/PROD-1** environment depends on the permissions set on the **Environments/production** directory CI itself.

 In cases where there is a local version and a global version of a permission, the global permission takes precedence over the local permission at all levels of the hierarchy.
