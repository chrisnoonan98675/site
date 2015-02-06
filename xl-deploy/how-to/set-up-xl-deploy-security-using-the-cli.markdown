---
title: Set up XL Deploy security using the CLI
categories:
- xl-deploy
subject:
- Security
tags:
- security
- system administration
- cli
---

After a fresh installation of XL Deploy, no permissions are granted to any user. The only users that have any permissions granted to them, are the `administrator` users and they will, by default, have **all** permissions granted to them. XL Deploy ships with one predefined administrator user called `admin`, with default password `admin`.

So the first task an `admin` user should do is change the default password to something hard to guess and keep this password private. 

The next tasks should consist of adding users to XL Deploy's repository (if no other credentials store is in use, or
possibly in addition to another credentials store that will be used) and granting permissions to users that will work with XL Deploy, starting with the (global) permission to login.

## Change the `admin` user's password

Changing the `admin` user's password consists of the following steps:

1. Change the `admin` user's password using the command-line interface (CLI):

        adminUser = security.readUser('admin')
        adminUser.password = 'newpassword_1'
        security.modifyUser(adminUser)

2. Change the `admin` user's password in the `deployit.conf` configuration file:

        admin.password=newpassword_1

3. Restart the XL Deploy server.

**Note**: The password in the `deployit.conf` file is encrypted when the server starts for the first time.

## Add users to the repository

Creating and deleting users can be done using the following commands:

    deployit> security.createUser('John', 'Doe')
    deployit> security.createUser('Alice', 'secret')
    deployit> security.deleteUser('John')

XL Deploy can **only** create users in its own repository, even if it is configured to use an LDAP repository for authentication and authorization. To create users in, for example, an LDAP credentials store, use your favorite LDAP administration tool.

The concept of groups is also supported by XL Deploy when using LDAP as the credentials store. All groups defined in LDAP can be assigned to roles in XL Deploy and users belonging to these groups will be assigned the role permissions when they use the system.

## Change a user's password

If for some reason a user's password gets compromised or the user has forgotten it, it is possible to set a new password for the user. Note that it will not be possible to retrieve the user's password because XL Deploy does not store it as plain text. When requesting and viewing a user CI, passwords will always be shown as eight asterisks (`*`).

To change the password for user `Alice`, issue the following command:

    deployit> user = security.readUser('Alice')
    deployit> user.password = 'newpassword_1'
    deployit> security.modifyUser(user)

Please note the difference between the name of the user in the first command, `John`, and the object representing this user that was returned after executing the first command; this `user` object need also be used in the next two commands following the first one.

## Create roles and assigning principals to roles

Roles are managed with the following command:

	security.assignRole("developers", ["john"])

This assigns principal _john_ to the _developers_ role. If the role does not exist yet, it is created.

## Log in as a different user

It is possible to login as a different user once the CLI has started up. In order to log in as another user, you must first logout the user that is currently logged in. This will show the `deployit>` prompt from which it is then possible to login again as a different user.

In following example an administrator is logged in as user `alice` and needs to login as `admin` to delete user
`John`.

    deployit> security.logout()
    deployit> security.login('admin', 'admin')

    # Delete the user Alice
    deployit> security.deleteUser('Alice')

    #switch back to the account with less privileges
    deployit> security.logout()

## Grant permissions

To grant a particular permission to a role, use a statement such as the following:

	security.grant("import#initial", "developers", ['Applications'])

To grant a particular permission to a role within a directory, use a statement such as the following:

	security.grant("deploy#initial", "developers", ['Environments/DevelopmentDirectory'])

See the [Graphical User Interface Manual](guimanual.html) for more information about configuring security via the GUI.

## Revoke permissions

To revoke a particular permission from a role, use a statement such as the following:

	security.revoke("read", "developers")

Or, to revoke a permission from a particular directory:

	security.revoke("read", "developers", ['Environments/DevelopmentDirectory'])

## Retrieve permissions

The `security` object offers methods to find the permissions for a certain role and the permissions for the currently logged in user.

* To show one's own permissions, issue `print security.getPermissions()`.
* To show the permissions for a particular role, issue `print security.getPermissions('rolename')`

Both methods return a `AssignedPermissions` object, that shows the relevant permissions when printed. The `AssignedPermissions` object makes it possible to retrieve and inspect user permissions, to find out if a given role has any permissions at all or has a specific permission and on what CIs. This will, for example, allow an administrator to automate granting and/or revoking of permissions by use of a script, depending on the retrieved permissions and their state or CIs.

Take note that only a user with `security#edit` permission is allowed to retrieve permissions for a role and to grant or revoke permissions. Normally it is only possible to retrieve and list one's own permissions. Trying to grant permissions using the second form of the command without this permission will lead to an exception.

A somewhat more extensive example of using this object, to be executed as an administrator user, is listed in following snippet:

    deployit> security.logout()
    deployit> security.login('admin', 'admin')
    deployit> security.createUser('alice', 'al1ce')
    deployit> security.assignRole('deployer', ['alice'])

    deployit> if security.isGranted('deployer','login'):
        security.grant('login', 'deployer')

    deployit> print security.getPermissions('deployer')
    deployit> security.removeRole('deployer')
    deployit> security.deleteUser('alice')

## Example of initial security setup

This example shows how to implement the security policy described above. To recap, here are the roles and permissions they should have:

* **Administrators**: `repo#edit` permission on the Infrastructure and Environment trees.
* **Front-end deployers**: access to the `front-end` application directory, `deploy#initial` and `deploy#upgrade` on environments DEV and TEST, `read` rights on environment PROD.
* **Back-end deployers**: access to the `back-end` application directory, `deploy#initial` and `deploy#upgrade` on environments DEV and TEST, `read` rights on environment PROD.
* **Senior deployers**: `import#initial` permission for the applications, `deploy#initial` and `deploy#upgrade` permission on all environments.
* **Developers**: `import#upgrade` permission for the applications, `deploy#upgrade` on the DEV and TEST environments.

The first task for a user with administrator privileges will be to create the users and roles in XL Deploy and allowing them to log in. Executing the following code will do this (the passwords in the examples are chosen for clarity. When creating users, it is recommended choose strong passwords that are difficult to guess):
       
    # Sample security setup.
   
    deployit> security.createUser('alice', 'al1ce')
    deployit> security.assignRole('administrators', ['alice'])
    deployit> security.createUser('bob', 'b0b')
    deployit> security.assignRole('senior-deployers', ['bob'])
    deployit> security.createUser('carol', 'car0l')
    deployit> security.assignRole('frontend-deployers', ['carol'])
    deployit> security.createUser('dave', 'd@ve')
    deployit> security.assignRole('backend-deployers', ['dave'])
    deployit> security.createUser('mallory', 'mall0ry')
    deployit> security.assignRole('developers', ['mallory'])

    deployit> security.grant('login', 'administrators')
    deployit> security.grant('login', 'senior-deployers')
    deployit> security.grant('login', 'frontend-deployers')
    deployit> security.grant('login', 'backend-deployers')
    deployit> security.grant('login', 'developers')

    # Create some environments
    deployit> infraGroup = repository.create(factory.configurationItem('Infrastructure/Dev','core.Directory',{}))
    deployit> host = factory.configurationItem(infraGroup.id + '/myHost', 'overthere.SshHost', {
                'os':'UNIX',
                'address':'localhost',
                'username':'deployit',
                'password':'deployit'})
    deployit> repository.create(host)
    deployit> repository.create(factory.configurationItem('Environments/Dev', 'core.Directory'))
    deployit> repository.create(factory.configurationItem('Environments/Test', 'core.Directory'))
    deployit> repository.create(factory.configurationItem('Environments/Acc', 'core.Directory'))
    deployit> repository.create(factory.configurationItem('Environments/Prod', 'core.Directory'))
    deployit> repository.create(factory.configurationItem('Environments/Dev/env', 'udm.Environment', 
                { 'members': ['Infrastructure/Dev/myHost'] }))
    deployit> repository.create(factory.configurationItem('Environments/Test/env', 'udm.Environment'))
    deployit> repository.create(factory.configurationItem('Environments/Acc/env', 'udm.Environment'))
    deployit> repository.create(factory.configurationItem('Environments/Prod/env', 'udm.Environment'))
    # Import a application
    deployit> repository.create(factory.configurationItem('Applications/team1','core.Directory',{}))
    deployit> repository.create(factory.configurationItem('Applications/team1/PetClinic-ear','udm.Application',{}))
    deployit> deployit.importPackage('PetClinic-ear/1.0')

Because the `login` permission is a global permission, there's no need to specify a list of CIs it will be in effect upon.

Next is to grant the individual roles their needed aggregated permissions in order for them to perform their tasks. The following snippet of code is a continuation of the above snippet; together with the snippets to come, it will constitute the complete script for this example.

The first role we will grant permissions to, is the `administrators` role. At minimum it needs `read` and `repo#edit` permission on the `Infrastructure` and `Environments` root nodes in order to perform any creation, deletion and updating of CIs under these root nodes.

    deployit> security.grant('read', 'administrators',['Infrastructure', 'Environments'])
    deployit> security.grant('repo#edit', 'administrators', ['Infrastructure', 'Environments', 'Applications'])

Notice the use of a list of target CIs in the first command. The permissions will only be set on these two specified CIs, which happen to be the root nodes for `Infrastructure` and `Environments` in this case. If, for instance, a user exists who has limited administrator privileges, it would be possible to just grant the role of this user rights to specific environments by using the command:

    deployit> security.grant('repo#edit', 'administrators', ['Environments/Prod'])

Going back to the example, the senior deployer needs the permissions to import and deploy applications to the DEV, TEST, ACC and PROD environments:

    deployit> security.grant("import#initial", 'senior-deployers', ['Applications'])
    deployit> security.grant("import#upgrade", 'senior-deployers', ['Applications'])
    deployit> security.grant("deploy#initial", 'senior-deployers', ['Environments'])
    deployit> security.grant("deploy#upgrade", 'senior-deployers', ['Environments'])

Setting up permissions for the deployers will be a bit more complicated because of the restrictions imposed. Let's first create a separation of applications in front end and back end. We do this by creating two directories:

    deployit> repository.create(factory.configurationItem('Applications/frontend', 'core.Directory'))
    deployit> repository.create(factory.configurationItem('Applications/backend', 'core.Directory'))

Next, allow the front-end deployer to access the front-end applications and the same for the backend deployer. Both roles can deploy to the DEV, TEST and ACC environments.

    deployit> security.grant("import#initial", 'frontend-deployers', ['Applications'])
    deployit> security.grant("import#upgrade", 'frontend-deployers', ['Applications/frontend'])
    deployit> security.grant("deploy#initial", 'frontend-deployers', ['Environments/Dev', 'Environments/Test', 
        'Environments/Acc'])
    deployit> security.grant("deploy#upgrade", 'frontend-deployers', ['Environments/Dev', 'Environments/Test', 
        'Environments/Acc'])

    deployit> security.grant("import#initial", 'backend-deployers', ['Applications'])
    deployit> security.grant("import#upgrade", 'backend-deployers', ['Applications/backend'])
    deployit> security.grant("deploy#initial", 'backend-deployers', ['Environments/Dev', 'Environments/Test', 
        'Environments/Acc'])
    deployit> security.grant("deploy#upgrade", 'backend-deployers', ['Environments/Dev', 'Environments/Test', 
        'Environments/Acc'])

As can be seen from the commands in the snippet above, the deployers do not have permission to perform a deployment to the `Environments/Prod` environment. In order for them to see deployments in the production environment, `read` permission should be granted.

    deployit> security.grant('read', 'frontend-deployers', ['Environments/Prod'])
    deployit> security.grant('read', 'backend-deployers', ['Environments/Prod'])

Finally permissions need to be granted to the `developer` role. This role is permitted to import new versions of applications into XL Deploy and to perform the deployment upgrade to the Dev and Test environments that goes hand in hand with this new version.

    deployit> security.grant("import#upgrade", 'developers', ['Applications/frontend', 'Applications/backend'])
    deployit> security.grant("deploy#upgrade", 'developers', ['Environments/Dev', 'Environments/Test'])

In this scenario the the `developer` role has the ability to import new versions of both front-end and back-end applications. If there were no specific directories below the Applications root node that override the root permissions, the following command would give permissions for all applications in XL Deploy:

    deployit> security.grant('import#upgrade', 'developers', ['Applications'])

After the commands in the above snippets have been executed, the initial security setup of XL Deploy will match the intended security setup as described in the beginning of this section.
