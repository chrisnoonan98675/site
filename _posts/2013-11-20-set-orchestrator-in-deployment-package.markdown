---
title: Set the orchestrator in an XL Deploy deployment package
categories:
- xl-deploy
tags:
- package
- application
- orchestrator
---

In XL Deploy, [orchestrators](http://docs.xebialabs.com/releases/latest/xl-deploy/referencemanual.html#orchestrators) combine the steps for individual component changes into an overall deployment plan and determine how the steps will be executed (for example, sequentially or in parallel).

You can configure the orchestrators for an application directly in the deployment package ([DAR file](http://docs.xebialabs.com/releases/4.5/xl-deploy/referencemanual.html#deployment-archive-dar-format)), so you don't have to select them when you set up the deployment of the application.

1. In the `ext` directory of your XL Deploy server, open `synthetic.xml` if it exists, or create a new `synthetic.xml` file.
1. Add the following code to the file:

        <type-modification type="udm.Version">
            <property name="orchestrator" description="Indicates the orchestrator to be used" required="false" />
        </type-modification>

1. Save the file and restart the XL Deploy server.
1. Log in to the XL Deploy user interface and go to the **Repository**.
1. Under **Applications**, create a new application called SampleApplication.
2. Create a new SampleApplication deployment package, called 1.0.
3. Add a `cmd.Command` resource deployable to the package and give it the command `ls`.
4. Now, version 1.0 of SampleApplication is ready. Right-click the package and select **Export**. The package will be saved in the location of your choice with the name `SampleApplication-1.0.dar`.
5. Use a command such as `unzip` or tool like WinRAR to extract the contents of the DAR file. One of the files it extracts is `deployit-manifest.xml`.
6. Open `deployit-manifest.xml` and add an `orchestrator` tag as follows:

        <udm.DeploymentPackage version="1.0" application="SampleApplication">
            <satisfiesReleaseNotes>false</satisfiesReleaseNotes>
            <satisfiesPerformanceTested>false</satisfiesPerformanceTested>
            <orchestrator>sequential-by-container</orchestrator>
            <deployables>

      **Tip:** Refer to the [XL Deploy Reference Manual](http://docs.xebialabs.com/releases/4.5/xl-deploy/referencemanual.html#types-of-orchestrators) for a complete list of the types of orchestrators that are available.

1. Change the `version` to 2.0.
2. Save `deployit-manifest.xml` and update the DAR with this updated file.
3. Rename the DAR file as `SampleApplication-2.0.dar`.
4. Copy `SampleApplication-2.0.dar` to the `importablePackages` directory of your XL Deploy server.
5. Return to the XL Deploy interface and go to **Deployment**.
6. Click ![Import package button](/images/button_import_package.png) and select **Import deployment package from server**.
7. Select `SampleApplication-2.0.dar` and click **Import**.
8. Drag the 2.0 version of SampleApplication to the left side of the Deployment Workspace.
9. Drag any existing environment to the right side of the Deployment Workspace.
10. Click **Deployment Properties** and verify that the orchestrator you set in `deployit-manifest.xml` is selected. 


## Using placeholders for environment independence

You can take advantage of placeholders and dictionaries to make your orchestrator settings environment-independent.

1. When you set the orchestrator in `deployit-manifest.xml`, use a placeholder instead of an orchestrator name:

        <udm.DeploymentPackage version="3.0" application="SampleApplication">
            <satisfiesReleaseNotes>false</satisfiesReleaseNotes>
            <satisfiesPerformanceTested>false</satisfiesPerformanceTested>
            <orchestrator>{{ORCHESTRATOR_TYPE}}</orchestrator>
            <deployables>

1. Update the DAR file, increase the application version to 3.0, and import it in XL Deploy, as described above.
1. In the XL Deploy interface, go to **Repository** and create a dictionary under **Environment** (or open an existing one).
2. Add a dictionary item called ORCHESTRATOR_TYPE and set it to an orchestrator type such as `sequential-by-container`.
3. Add this dictionary to an environment.
3. Go to **Deployment** and set up a deployment of SampleApplication 3.0. Be sure to select the environment that you added the dictionary to.
4. Click **Deployment Properties** and verify that the orchestrator you set in the dictionary is selected.
