---
title: Create a deployment package using the XL Deploy GUI
subject:
Packaging
categories:
xl-deploy
tags:
package
application
gui
since:
XL Deploy 3.8.x
deprecated:
XL Deploy 5.0.0
weight: 211
---

**Tip:** For information about creating a deployment package in XL Deploy 5.0.0 and later, refer to [Add a package to XL Deploy](/xl-deploy/how-to/add-a-package-to-xl-deploy.html).

Deployment packages are usually created outside of XL Deploy. For example, packages are built by tools like Maven or Jenkins and then imported using the a XL Deploy plugin. You can manually write a Manifest.MF file for the XL Deploy Archive format (DAR format) and import the package using the XL Deploy GUI.

But, while designing a deployment package this may be a cumbersome process. To quickly assemble a package, it is more convenient to edit it in the XL Deploy UI.

## Creating an application

In XL Deploy, all deployable content is stored in a deployment package. The deployment package will contain the EAR files, HTML files, SQL scripts, DataSource definitions, etc.

Deployment packages are versions of an application. An application will contain one or more deployment packages. Before you can create a deployment package, you must create an application.

To create an application, login to the XL Deploy GUI and go to the **Library** pane.
Hover over **Applications**, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **Application**

Enter the name 'MyApp' and click **Save**.

## Creating a deployment package

To create a deployment package that contains version 1.0 of MyApp:

Click **Applications** to expand the list of applications. Hover over **MyApp** applications, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **DeploymentPackage**.

Enter the name '1.0' and click **Save**.

This action creates a new empty MyApp 1.0 package.

## Adding Deployable content

You can now add actual deployable content to the package. In XL Deploy, all configuration items (nodes in the repository tree) are _typed_. You must specify to XL Deploy beforehand the type of the configuration item, so XL Deploy will know what to do with it.

You can add a simple deployable without file content.

To create a deployable DataSource in the package:

Hover over the **MyApp** application, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **jee** > **DataSourceSpec**.

Enter the name 'MyDataSource' and JNDI-name 'jdbc/my-data-source' and click **Save**.

This creates a functional deployment package that will create a DataSource when deployed to a JEE Application Server like JBoss or WebSphere.

## Adding artifacts

Artifacts are configuration items that contain files. Examples are EAR files, WAR files, but also plain files or folders.

You can add an EAR file to your MyApp/1.0 deployment package using the type `jee.Ear`.

**Note** If you are using specific middleware like WebSphere or WebLogic, you can also add EAR files with the type `was.Ear` or `wls.Ear`. You can use this if you need the WebSphere or WebLogic-specific features. In other situations it is recommended to deploy using the `jee.Ear` type.

Hover over the **MyApp** application, click ![Explorer action menu](/images/menu_three_dots.png), and select **New** > **jee** > **Ear**.

Enter the name 'PetClinic.ear' and upload the EAR file. Click 'Browse file' and select an EAR file from your local workstation. If you are running the XL Deploy Server locally, you can find an example EAR file in `xldeploy-server/importablePackages/PetClinic-ear/1.0/PetClinic-1.0.ear`.

When creating artifacts (configuration items with file content), there are some things to take into account. You can only upload files when creating the configuration item. It is not possible to change the content afterwards. The reason for this is that deployment packages should effectively be read-only. If you change the contents, you may create inconsistencies between what has deployed onto the middleware and what is in the XL Deploy repository. This may lead to surprising errors.

Placeholder scanning of files is only done when they are uploaded. Use the 'Scan Placeholder' checkbox to enable or disable placeholder scanning of files.

When uploading entire directories for the `file.Folder` type, you must zip the directory first, since you can only select a single file for browser upload.

## Specifying property placeholders

It is easy to specify property placeholders. For any deployable configuration item, you can enter a value surrounded by double curly brackets. For example: {% raw %}{{PLACEHOLDER}}{% endraw %}. The actual value used in a deployment will be looked up from a dictionary when a deployment mapping is made.

For example, open 'MyDataSource' and enter 'JNDI_VALUE' as placeholder:

![image](images/package-placeholder-new-ui.png)

The value for Jndi Name will be looked up form the dictionary associated with environment you deploy to.

## Export as DAR

You can export an application as a DAR file. After you download it, you can unzip it and inspect the contents. For example, the generated manifest file can serve as a basis for automatic generation of the DAR.

To export as DAR, hover over the desired application, click ![Explorer action menu](/images/menu_three_dots.png), and select **Export**.
