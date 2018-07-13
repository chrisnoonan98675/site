---
title: Set up roles and permissions using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- security
- system administration
- cli
- roles
- permissions
- principals
weight: 246
---

After a fresh installation of XL Deploy, no [permissions](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html) are granted to any user. The only users that have any permissions granted to them, are the `administrator` users and they will, by default, have **all** permissions granted to them. XL Deploy ships with one predefined administrator user called `admin`, with default password `admin`.

So the first task an `admin` user should do is change the default password to something hard to guess and keep this password private.

The next tasks should consist of adding users to XL Deploy's repository (if no other credentials store is in use, or possibly in addition to another credentials store that will be used) and granting permissions to users that will work with XL Deploy, starting with the (global) permission to login.

## Change the `admin` user's password

To change the `admin` user's password:

1. Use the XL Deploy [command-line interface (CLI)](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html) to change the password:

        adminUser = security.readUser('admin')
        adminUser.password = 'newpassword_1'
        security.modifyUser(adminUser)

2. Change the password in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` configuration file:

        admin.password=newpassword_1

3. Restart the XL Deploy server.

**Note**: The password in the `deployit.conf` file is encrypted when the server starts for the first time.

## Create new users

To create new XL Deploy users, execute the following commands in the CLI:

    security.createUser('john', 'secret01')
    security.createUser('alice', 'secret02')

To delete a user, execute:

    security.deleteUser('john')

XL Deploy can *only* create users in its own repository, even if it is configured to use an LDAP repository for authentication and authorization. You must use an LDAP administration tool to create users in, for example, an LDAP credentials store.

XL Deploy supports the concept of _groups_ when using LDAP as the credentials store. All groups defined in LDAP can be assigned to roles in XL Deploy, and users belonging to these groups will be assigned the role permissions when they use the system.

## Change a user's password

If a user's password is compromised or the user has forgotten it, you can set a new password for the user. Note that you cannot retrieve the user's password because XL Deploy does not store it as plain text. When requesting and viewing a user CI, passwords will always be shown as eight asterisks (`*`).

To change the password for a user, execute the following commands in the CLI:

    user = security.readUser('alice')
    user.password = 'newpassword_1'
    security.modifyUser(user)

Note the difference between the name of the user in the first command and the `user` object, which is returned after you execute the command. You must use the `user` object in the second and third commands.

## Create roles and assigning principals to roles

To assign a role to a user, execute the following command in the CLI:

	security.assignRole("developers", ["john"])

This assigns principal _john_ to the _developers_ role. If the role does not exist, XL Deploy creates it.

## Log in as a different user

After the CLI is started, you can log in as a different user. You must first log out of the current user; you will then see the `deployit` prompt and you can log in as a different user.

In this example, an administrator is logged in as user `alice`, and needs to switch to user `admin` to delete user `john`.

    security.logout()
    security.login('admin', 'admin')

    # Delete the user john
    security.deleteUser('john')

    # Switch back to the account with less privileges
    security.logout()

## Grant permissions

To grant a particular permission to a role, execute:

    security.grant("import#initial", "developers", ['Applications'])

To grant a particular permission to a role within a directory, execute:

	security.grant("deploy#initial", "developers", ['Environments/DevelopmentDirectory'])

## Revoke permissions

To revoke a particular permission from a role, execute:

	security.revoke("read", "developers")

Or, to revoke a permission from a particular directory:

	security.revoke("read", "developers", ['Environments/DevelopmentDirectory'])

## Retrieve permissions

The `security` object offers methods to find the permissions for a certain role and the permissions for the currently logged in user.

* To show your own permissions, execute `print security.getPermissions()`.
* To show the permissions for a particular role, issue `print security.getPermissions('rolename')`

Both methods return a `AssignedPermissions` object that shows the relevant permissions when printed. The `AssignedPermissions` object allows you to retrieve and inspect user permissions, including specific permissions on configuration items (CIs), You can use this to automate the granting and/or revoking of permissions through a script.

Note that only a user with the `security#edit` permission can retrieve permissions for a role and grant or revoke permissions.

This is an extended example of working with permissions:

    security.logout()
    security.login('admin', 'admin')
    security.createUser('alice', 'al1ce')
    security.assignRole('deployer', ['alice'])

    if security.isGranted('deployer','login'):
        security.grant('login', 'deployer')

    print security.getPermissions('deployer')
    security.removeRole('deployer')
    security.deleteUser('alice')

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
