---
title: Create a deployment package using the CLI
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- cli
---

To create the package using the XL Deploy command-line interface (CLI), start by creating a directory `petclinic-package` to hold the package content:

    mkdir petclinic-package

Now, collect the EAR file and configuration directory and store them in the newly created directory:

    cp /some/path/petclinic-1.0.ear petclinic-package
    cp -r /some/path/conf petclinic-package

The datasource is a resource specification, not an artifact, so there is no file to include.

Now, let's create the DAR manifest for these entries. 

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="PetClinic">
      <deployables>
        ...
      </deployables>
    </udm.DeploymentPackage>

Add the EAR and configuration folder:

    <jee.Ear name="PetClinic-Ear" file="petclinic-1.0.ear" />

    <file.Folder name="PetClinic-Config" file="conf" /> 

Add the datasource to the manifest as follows:

    <was.OracleDatasourceSpec name="PetClinic-ds">
      <driver>com.mysql.jdbc.Driver</driver>
      <url>jdbc:mysql://localhost/petclinic</url>
      <username>{% raw %}{{DB_USERNAME}}{% endraw %}</username>
      <password>{% raw %}{{DB_PASSWORD}}{% endraw %}</password>
    </was.OracleDatasourceSpec>

Note how the datasource uses placeholders for username and password.

The complete manifest looks like this:

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

Save the manifest in the package directory. Finally, create the DAR archive with the command:

        jar cf petclinic-1.0.dar petclinic-package/*

The resulting archive can be imported into XL Deploy.
