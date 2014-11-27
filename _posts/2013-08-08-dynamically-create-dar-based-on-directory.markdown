---
title: Dynamically create a deployment package based on files in a directory
categories:
- xl-deploy
tags:
- package
- application
- jenkins
---

This solution will automatically:

* Generate [a script](/sample-scripts/samplePackageImport.py) for each file delivered in a folder
* Create an application deployment package (DAR file) for the files that we want to import and deploy

Also, it will only deploy new or changed files that are delivered.

## Process

The process is:

1. A developer copies a file(s) into a shared folder for deployment.
2. Jenkins monitors the shared folder. If a file exists in the folder, Jenkins generates a DAR file.
3. Jenkins uploads the DAR file to XL Deploy (only new and modified files deployed by XL Deploy).

## Setup

1. Install the following Jenkins plugins:
    * workspace cleanup
    * file system trigger

1. Create a Jenkins job with the options:
    * Build environment - Delete workspace before builds starts
    * FS Monitor plugin watches directory for files
    * XL Deploy plugin to import the DAR that was created, and is in Jenkins workspace
    * In the Jenkins build section – Execute Shell step:

            mkdir $WORKSPACE/workdir
            cat << EOF > package.py
            execfile("ext/file-importer.py")
            buildPackage("/Users/johndoe/tmp/somedir", "wls-test", "2.1-$BUILD_NUMBER", "$WORKSPACE/workdir" )
                
            EOF
            /Users/tjrandall/training/deployit-3.9.1-cli/bin/cli.sh -username admin -password admin -f $WORKSPACE/package.py
     
    Note: In the example above, `/Users/johndoe/tmp/somedir` represents the folder where the `PDX` files will reside. Also, in the `buildPackage` step above, look at the method in the `file-importer.py` script to see how the input values are used.

1. Add `file-importer.py` to `<XLDEPLOY_CLI>/ext`.

1. Add entries to `<XLDEPLOY_SERVER>/ext/synthetic.xml`:

        <!-- For deploying files from a shared folder into XL Deploy -->
        <type type="dev.WlsContainer" extends="generic.Container">
                <property name="suPassword" kind="string" password="true" required="true" hidden="false" />
        </type>

        <type type="dev.pdxFileModule" extends="generic.ExecutedScriptWithDerivedArtifact" deployable-type="dev.pdxFile" container-type="dev.WlsContainer">
                <generate-deployable type="dev.pdxFile" extends="generic.File" />
                <property name="createScript" default="dev/implement-file-scripts.sh" hidden="true" />
                <property name="destroyScript" default="dev/rollback-file-scripts.sh" hidden="true"  />
        </type>

1. Add the `implement-file-scripts.sh` and `rollback-file-scripts.sh` scripts to `<XLDEPLOY_SERVER>/ext/dev`.

1. Add a `dev.WlsContainer` to the appropriate host(s) and add the `dev.WlsContainer(s)` to the environment.

1. Run the Jenkins job. You should see:
    * DAR file generated in Jenkins (`$WORKSPACE/workingdir`)
    * Jenkins-generated DAR file in XL Deploy
    * DAR file deployed to environment
