---
technical_preview: true
title: Using the CI Explorer
categories:
- xl-deploy
subject:
- CI Explorer
tags:
- ci
- repository
since:
- XL Deploy 6.0.0
---

The CI Explorer gives you a new way to work with XL Deploy. In the CI Explorer, you can manage the configuration items (CIs) in your repository, deploy and undeploy applications, and provision and deprovision environments, all without installing Flash in your browser.

**Important:** The CI Explorer is currently available as a Technical Preview, so its functionality is limited. Future releases will add more features to the CI Explorer.

## Requirements and installation

The CI Explorer is automatically installed when you install or upgrade to XL Deploy 6.0.0 or later.

To use the CI Explorer, you must use the latest version of Firefox, Chrome, or Safari, or Internet Explorer 10 or later. Internet Explorer Compatibility View is not supported.

**Tip:** For information about system requirements for the XL Deploy server, refer to [Requirements for installing XL Deploy](/xl-deploy/concept/requirements-for-installing-xl-deploy.html).

## Access the CI Explorer

To access the CI Explorer, go to `URL:PORT/technical-preview.html`, where `URL:PORT` is the location where you have configured access to the XL Deploy GUI; for example, `http://xl-deploy.company.com:4516/technical-preview.html`.

If you are not already logged in to XL Deploy, you must enter your XL Deploy user name and password when the browser prompts you.

## Work with CIs

In the CI Explorer, you'll see the the contents of your repository in the left pane. When you create or open a CI, you can edit its properties in the right pane.

### Create a CI

To create a new CI, locate the place where you want to create it in the left pane, hover over it and click ![CI Explorer action menu](/images/menu_three_dots.png), and then select **New**. A new tab opens in the right pane.

### Open and edit a CI

To open an existing CI, double-click it in the left pane. A new tab opens in the right pane with the CI properties.

Edit the properties as desired, and then click **Save** to save your changes. To discard your changes without saving, click **Cancel**.

### Rename a CI

To copy an existing CI, locate it in the left pane, hover over it and click ![CI Explorer action menu](/images/menu_three_dots.png), and then select **Rename**. Change the name as desired, and then press ENTER to save your changes.

To cancel without saving your changes, press ESC or click another CI in the left pane.

### Duplicate a CI

To duplicate an existing CI, locate it in the left pane, hover over it and click ![CI Explorer action menu](/images/menu_three_dots.png), and then select **Duplicate**. A new CI appears in the left pane with a number appended to its name; for example, if you duplicate an application called _MyApp_, the CI Explorer creates an application called _MyApp (1)_. You can then rename and/or edit the new CI as desired.

### Delete a CI

To delete an existing CI, locate it in the left pane, hover over it and click ![CI Explorer action menu](/images/menu_three_dots.png), and then select **Delete**. Finally, confirm or cancel the deletion.

## Search for CIs

To search for a CI, type a search term in the **Search** box at the top of the left pane and press ENTER. If there are search results, they appear in the left pane.

To open a CI from the search results, double-click it. To see the full path of a CI in the search results, hover over the CI with your mouse pointer.

To clear the search results, click **X** in the **Search** box.

## Deploy an application or provision an environment

To deploy an application or provision a cloud-based environment using the CI Explorer:

1. Expand **Applications**, and then expand the application that you want to deploy or provision.
2. Hover over the desired deployment package or provisioning package, click ![CI Explorer action menu](/images/menu_three_dots.png), and then select **Deploy**. A new tab appears in the right pane.
3. In the new tab, select the target environment. You can filter the list of environments by typing in the **Search** box at the top.

    *Tip:* To see the full path of an environment in the list, hover over it with your mouse pointer.

4. Click **Execute** to immediately start executing the deployment or provisioning.
