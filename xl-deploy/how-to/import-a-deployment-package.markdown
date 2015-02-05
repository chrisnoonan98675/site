---
title: Import a deployment package
subject:
- Packaging
categories:
- xl-deploy
tags:
- package
- application
- gui
- repository
---

**Tip:** For information about creating a deployment package in XL Deploy 5.0.0 and later, refer to [Add a package to XL Deploy](/xl-deploy/how-to/add-a-package-to-xl-deploy.html).

The XL Deploy Repository is the database that contains all packages, environments and other CIs. The repository keeps track of this data and all its revisions.

The first step to performing a deployment with XL Deploy is to import your package into the repository. These are the steps you need to follow:

**Start the Import Wizard**. To start the Import Wizard, click the Import button on the Package Browser. 

![Import package](images/import-package-button.png)

**Import the Package**. Run through the steps of the import wizard. _Import deployment package from server_ scans for packages in the configured dropbox, which by default is the `importablePackages` directory from the XL Deploy installation directory. _Import deployment package from URL_ allows the user to specify a URL to download the package from. Note that you cannot re-import packages.

**Close the Import Wizard**. Click on the Close button to complete the import procedure.
