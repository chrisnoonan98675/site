---
title: Using XL Scale
subject:
- XL Scale
categories:
- xl-deploy
tags:
- xl scale
- virtualization
---

After you create the XL Scale configuration items (CIs) that you need, you can instantiate an _environment template_ or a _host template_. There are two ways to do this in the XL Deploy GUI, either using the wizards on the XL Scale tab or via control tasks in the Repository tab.

## Required permissions

In order to perform cloud operations, the following security permissions are important.

### Create a cloud environment

In order to create a cloud environment:

* You need read permission on the environment template CI
* You need `controltask#execute` permissions on the environment template CI
* You do **not** need to have read or edit permissions on the target environment CI or host path for the cloud operation to be able to create it

XL Scale operates as an administrator user concerning to repository access and therefore the user that initiates the cloud operation does not need permissions on the target environment or host path. The security for cloud is solely managed by the permissions to execute a control task.

XL Scale will not automatically grant permissions on newly created environments and hosts. Instead, make sure that the target location for the new environment and hosts has the intended permission setup.

### Destroy a cloud environment

In order to destroy a cloud environment:

* You need read permission on the environment CI
* You need `controltask#execute` on the environment CI
* You do **not** need to have read or edit permissions on the environment template CI or the host path

## Instantiating and destroying environment templates using the wizard

The XL Scale tab has of two wizards, one for instantiating cloud environments, and one for destroying cloud environments. The wizard helps you to gather the required information to perform the create or destroy task.

### Creating an environment

This wizard can help you to create a cloud environment. This wizard consists of four steps:

* **Select template**: First you need to select an Environment Template. The selection component shows all available instances of ```cloud.EnvironmentTemplate``` and ```udm.Directory``` available under the ```Configuration``` root node of the repository. When you select a template, it will display the description on the right side of the screen.

    ![Create environment](images/cloud-create-1.png "Select template")

* **Fill in parameters**: The second step allows you to fill in additional parameters. The available parameters depends on the selected template. Common parameters that could be found here are environment id, which lets you specify the name of the environment that will be created (prefixed by Environments/), and a second hosts parameters where you can specify where you want to store the created host CIs (prefixed by Infrastructure/).

    ![Create environment](images/cloud-create-2.png "Fill in parameters")

* **Run create task**: This screen shows the create task. You can review the steps. The cloud create task works exactly the same as the deployment task screen. If you want to continue with creating the environment, click execute. If you do not want to continue, click cancel. You can click previous to change the parameters for this task. This only works if the task has not been started yet. When the task is finished, you can click next to continue.

    ![Create environment](images/cloud-create-3.png "Run create task")

* **View result**: The view result page shows the created infrastructure, if any. Typically it will search for all CIs that are part of the created environment. Nodes can be expanded to see contained CIs.

    ![Create environment](images/cloud-create-4.png "View result")

To finish the create wizard you have two options:

* **Close**: This will close the wizard. The resulting infrastructure CIs have already been added to the repository.
* **Deploy**: This will start an initial deployment with the created environment selected. From this point you can continue the initial deployment as usual.

### Destroying an environment

This wizard can help you destroying a cloud environment. This wizard consist of two steps:

* **Select environment**: Select the environment you want to destroy. The selection component will only show instances of the ```cloud.Environment```, and ```udm.Directory```. You can expand directories. When you have selected the Environment you want to destroy, click next.

    ![Destroy environment](images/cloud-destroy-1.png "Select environment")

* **Run destroy task**: This screen shows the destroy task. You can review the steps. If you want to continue with destroying the environment, click execute. If you do not want to continue, click cancel. You can also go back by clicking previous to select a different environment. When the destroy task is complete, you can close the task, and the wizard will restart.

    ![Destroy environment](images/cloud-destroy-2.png "Run destroy task")

## Instantiating and destroying host and environment templates via control tasks

Both host and environment templates have ```instantiate``` and ```destroy``` control tasks that can be invoked directly from the Repository tab. The control tasks create and destroy either hosts or environments. The instantiation of host templates is intended for testing, as such these actions will not appear in the cloud reports.

### Creating hosts and environments

The ```instantiate``` control task provides the following functionality:

{:.table .table-striped}
| CI type | Control task | Purpose |
| ------- | ------------ | ------- |
| `cloud.BaseHostTemplate` | `instantiate` | Launches an instance on a virtualization platform using the parameters provided in the template. Virtualization vendor specific. Creates a host CI and children representing the middleware present in the repository. |
| `cloud.EnvironmentTemplate` | `instantiate` | Creates instances for each of the host templates specified in the `HostTemplates` property. Creates an environment CI with all instantiated cloud hosts attached to it in the repository. Members of this environment are customizable via the XML descriptor. |

### Destroying hosts and environments

The ```destroy``` control task provides the following functionality:

{:.table .table-striped}
| CI type | Control task | Purpose |
| ------- | ------------ | ------- |
| Subtypes of `overthere.Host` that were created via XL Scale | `destroy` | Destroys the virtual host associated with this CI. Removes this CI and all its children from the repository. |
| `cloud.Environment` | `destroy` | Destroys all hosts that were created as part of the environment. Removes all host CIs, all associated middleware CIs and the `cloud.Environment` CI itself from the repository. |

Please note that cloud environments manage all launched hosts that were part of the environment template on which they are based. If other containers are added to a cloud environment, these must be removed from the environment prior to destroying it. Similarly, cloud environment members must not be members of other environments when the cloud environment is destroyed.
