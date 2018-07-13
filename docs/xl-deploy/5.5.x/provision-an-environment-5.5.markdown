---
title: Provision an environment (XL Deploy 5.5.x)
since:
- XL Deploy 5.5.0
removed:
- XL Deploy 6.0.0
---

You can use XL Deploy's [provisioning feature](/xl-deploy/concept/provisioning-through-xl-deploy.html) to create cloud-based environments in a single action. In XL Deploy terminology, you provision a [*provisioning package*](/xl-deploy/how-to/create-a-provisioning-package.html) to a [*provisioning environment*](/xl-deploy/how-to/create-a-provisioning-environment.html). The environment contains information about *providers* (such as [Amazon EC2](https://aws.amazon.com/ec2/)) and the package describes what the provisioned environment should look like.

The result of provisioning a provisioning package to a provisioning environment is a *provisioned blueprint* that contains *provisioneds*.

**Note:** A version of this topic is available for [XL Deploy 6.0.0 and later](/xl-deploy/how-to/provision-an-environment.html).

## Provision an environment using the GUI

To provision an environment using the XL Deploy GUI:

1. Click **Provision** in the top bar.
1. Under **Provisioning Packages**, locate the blueprint and expand it to see its versions (provisioning packages).
1. Drag the desired provisioning package to the left side of the Provisioning Workspace.
1. Under **Provisioning Environments**, locate the desired environment and drag it to the right side of the Provisioning Workspace.

    XL Deploy automatically maps the provisionables in the package to the providers in the environment.

    ![Provision an environment](/xl-deploy/how-to/images/provisioning-mapping.png)

1. Click **Execute** to immediately start the provisioning.

You can also optionally:

* View or edit the properties of a mapped provisioned by double-clicking it.
* Click **Provisioning Properties** to select orchestrators or enter placeholder values.
* Click **Advanced** if you want to adjust the provisioning plan by skipping steps or inserting pauses.

If the server does not have the capacity to immediately start executing the plan, the plan will be in a QUEUED state until the server has sufficient capacity.

If a step in the provisioning fails, XL Deploy stops executing the provisioning and marks the step as FAILED. Click the step to see information about the failure in the output log.

### The unique provisioning ID

To prevent name collisions, a unique provisioning ID is added to most items that are created during provisioning. This ID is a random string of characters such as `AOAFbrIEq`. You can see it by clicking **Provisioning Properties** on the provisioning plan.

On the [provisioning package](/xl-deploy/how-to/create-a-provisioning-package.html#create-a-provisioning-package), you can specify an XL Deploy environment where the CIs that are created based on bound templates will be added. The unique ID will be appended to this environment name; for example, if you specify the environment name *TEST*, XL Deploy will create an environment called *TEST-AOAFbrIEq*.

The unique ID is also added to the CIs that are created. For example, if you have a [provisionable](/xl-deploy/how-to/create-a-provisioning-package.html#add-a-provisionable-to-a-package) `aws.ec2.InstanceSpec` called *apache-spec*, XL Deploy will created a provisioned called *AOAFbrIEq-apache-spec*.

If the cardinality set on the provisionable is greater than 1, then XL Deploy will append a number to the provisioned name. For example, if *apache-spec* has a cardinality of 3, XL Deploy will create provisioneds called *AOAFbrIEq-apache-spec*, *AOAFbrIEq-apache-spec-2*, and *AOAFbrIEq-apache-spec-3*.

## Provision an environment using the CLI

For information about provisioning an environment using the XL Deploy command-line interface (CLI), refer to [Using the XL Deploy CLI provisioning extension](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html).

## Next steps

After you provision an environment using XL Deploy, you can [deploy an application to it](/xl-deploy/how-to/deploy-to-a-provisioned-environment.html). You can also use XL Deploy to [deprovision an environment](/xl-deploy/how-to/deprovision-an-environment.html).
