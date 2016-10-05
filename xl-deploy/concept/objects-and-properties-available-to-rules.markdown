---
title: Objects and properties available to rules
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- deployment
- planning
- step
since:
- XL Deploy 4.5.0
weight: 129
---

When you define an XML or script rule, you use expressions or scripts to define its behavior. These are written in Jython, a combination of Python and Java.

## Objects available to rules

The data that is available for a planning script to use depends on the scope of the rule. This table shows when each object is available:

{:.table .table-striped}
| Object name | Type | Scope | Description |
| ----------- | ---- | ----- | ----------- |
| context | [DeploymentPlanningContext](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/planning/DeploymentPlanningContext.html) | all | Use this to add steps and checkpoints to the plan. |
| deployedApplication | [DeployedApplication](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/DeployedApplication.html) | all | Specifies which application version will be deployed to which environment.<br/><br/>In XL Deploy 5.1.0 and later, `deployedApplication` is not available in the case of `DESTROY`. |
| previousDeployedApplication | [DeployedApplication](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/DeployedApplication.html) | all | In XL Deploy 5.1.0 and later, this is the previous application version that was deployed.<br/><br/>In XL Deploy 5.0.x and earlier, `previousDeployedApplication` is not available. |
| steps | | all |  Allows you to create steps from the [step registry](/xl-deploy/how-to/use-a-predefined-step-in-a-rule.html). |
| specification | [DeltaSpecification](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/DeltaSpecification.html) | pre-plan<br/>post-plan | Contains the delta specification for the current deployment. |
| delta | [Delta](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html) | deployed | Whether the deployed should be created, modified, destroyed, or left unchanged (`NOOP`). |
| deployed | [Deployed](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/Deployed.html) | deployed | In XL Deploy 4.5.3, 5.0.0, and later, and in the case of `CREATE`, `MODIFY`, or `NOOP`, this is the "current" deployed that the `delta` variable refers to; in the case of `DESTROY`, it is not provided.<br /><br />In XL Deploy 4.5.2 and earlier, and in the case of `CREATE`, `MODIFY`, or `NOOP`, this is the "current" deployed that the `delta` variable refers to; in the case of `DESTROY`, this is the "old" deployed. |
| previousDeployed | [previousDeployed](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/Deployed.html) | deployed | In XL Deploy 4.5.3, XL Deploy 5.0.0, and later, and in the case of `MODIFY`, `DESTROY`, or `NOOP`, this is the "previous" deployed that the `delta` variable refers to; in the case of `CREATE`, this is not provided.<br /><br />In XL Deploy 4.5.2 and earlier, `previousDeployed` is not available. |
| deltas | [Deltas](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Deltas.html) | plan | Collection of all `Delta`s in the current `InterleavedPlan`. |
| controlService | [ControlService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.ControlService) | all | Gives you access to the `ControlService`. |
| deploymentService | [DeploymentService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.DeploymentService) | all | Gives you access to the `DeploymentService`. |
| inspectionService | [InspectionService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.InspectionService) | all | Gives you access to the `InspectionService`. |
| metadataService | [MetadataService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.MetadataService) | all | Gives you access to the `MetadataService`. |
| packageService | [PackageService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.PackageService) | all | Gives you access to the `PackageService`. |
| permissionService | [PermissionService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.PermissionService) | all | Gives you access to the `PermissionService`. |
| repositoryService | [RepositoryService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.RepositoryService) | all | Gives you access to the `RepositoryService`. |
| roleService | [RoleService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.RoleService) | all | Gives you access to the `RoleService`. |
| serverService | [ServerService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.ServerService) | all | Gives you access to the `ServerService`. |
| taskService | [TaskService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.TaskService) | all | Gives you access to the `TaskService`. |
| taskBlockService | [TaskBlockService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.TaskBlockService) | all | Gives you access to the `TaskBlockService`. |
| userService | [UserService](/jython-docs/#!/xl-deploy/5.5.x//service/com.xebialabs.deployit.engine.api.UserService) | all | Gives you access to the `UserService`. |
| logger | [Logger](http://www.slf4j.org/api/org/slf4j/Logger.html) | all | Allows you to access the XL Deploy logs. Prints logs to namespace `com.xebialabs.platform.script.Logging`. |

**Note:** These objects are not automatically available for *execution* scripts, such as in the `jython` or `os-script` step. If you need an object in such a step, the planning script must make the object available explicitly; for example, by adding it to the `jython-context` map parameter in the case of a `jython` step.

## Accessing CI properties

To access configuration item (CI) properties, including synthetic properties, use the property notation. For example:

    name = deployed.container.myProperty

You can also refer to a property in the dictionary style, which is useful for dynamic access to properties. For example:

    propertyName = "myProperty"
    name = deployed.container[propertyName]

For full, dynamic read-write access to properties, you can access properties through the `values` object. For example:

    deployed.container.values["myProperty"] = "test"

## Accessing deployeds

In the case of rules with the [`plan`](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html#plan-scope) scope, the `deltas` object will return a list of [`delta`](/xl-deploy/5.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Deltas.html) objects. You can get the `deployed` object from each `delta`.

The delta and delta specification expose the previous and current deployed. To access the deployed that is going to be updated, use the `deployedOrPrevious` property:

    depl = delta.deployedOrPrevious
    app = specification.deployedOrPreviousApplication

## Comparing delta operations and types

You can compare a delta operation to the constants `"CREATE"`, `"DESTROY"`, `"MODIFY"` or `"NOOP"` as follows:

    if delta.operation == "CREATE":
        pass

You can compare the CI `type` property to the string representation of the fully qualified type:

    if deployed.type == "udm.Environment":
        pass
