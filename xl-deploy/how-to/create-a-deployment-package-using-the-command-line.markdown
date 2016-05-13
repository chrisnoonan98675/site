---
title: Create a deployment package using the command line
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
---

You can use the command line to create a [deployment package](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html) (DAR file) that you can then import into XL Deploy. This example packages an application called PetClinic that consists of an EAR file and a resource specification.

1. Create a directory to hold the package contents:

        mkdir petclinic-package

1. Collect the EAR file and the configuration directory and store them in the directory:

        cp /some/path/petclinic-1.0.ear petclinic-package
        cp -r /some/path/conf petclinic-package

1. Create a `deployit-manifest.xml` file that describes the contents of the package. Start with:

        <?xml version="1.0" encoding="UTF-8"?>
        <udm.DeploymentPackage version="1.0" application="PetClinic">
          <deployables>
          ...
          </deployables>
        </udm.DeploymentPackage>

    1. Add the EAR file and the configuration folder to the manifest:

            <jee.Ear name="PetClinic-Ear" file="petclinic-1.0.ear" />
            <file.Folder name="PetClinic-Config" file="conf" />

    1. Add the datasource to the manifest:

            <was.OracleDatasourceSpec name="PetClinic-ds">
              <driver>com.mysql.jdbc.Driver</driver>
              <url>jdbc:mysql://localhost/petclinic</url>
              <username>{% raw %}{{DB_USERNAME}}{% endraw %}</username>
              <password>{% raw %}{{DB_PASSWORD}}{% endraw %}</password>
            </was.OracleDatasourceSpec>

        Note that the datasource uses [placeholders](/xl-deploy/how-to/using-placeholders-in-xl-deploy.html) for the user name and password.

        The complete manifest file looks like:

            <?xml version="1.0" encoding="UTF-8"?>
              <udm.DeploymentPackage version="1.0" application="PetClinic">
                <deployables>
                  <jee.Ear name="PetClinic-Ear" file="petclinic-1.0.ear" />
                  <file.Folder name="PetClinic-Config" file="conf" />
                  <was.OracleDatasourceSpec name="PetClinic-ds">
                    <driver>com.mysql.jdbc.Driver</driver>
                    <url>jdbc:mysql://localhost/petclinic</url>
                    <username>{% raw %}{{DB_USERNAME}}{% endraw %}</username>
                    <password>{% raw %}{{DB_PASSWORD}}{% endraw %}</password>
                  </was.OracleDatasourceSpec>
                </deployables>
              </udm.DeploymentPackage>

1. Save the manifest file in the package directory.
1. Create the DAR file:

        jar cf petclinic-1.0.dar petclinic-package/*

1. Log in to XL Deploy and follow the [instructions to import the package](/xl-deploy/how-to/add-a-package-to-xl-deploy.html#import-a-package).
