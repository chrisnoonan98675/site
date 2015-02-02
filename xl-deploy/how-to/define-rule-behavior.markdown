---
title: Define rule behavior
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
- 4.5.0
---

When you define an XML or script rule, you use expressions or scripts to define its behavior. These are written in Jython, a combination of Python and Java.

The data that is available for a planning script to use depends on the scope of the rule. This table shows when each object is available:

<table class="table table-striped">
    <tr>
        <th>Object name</th> <th>Type</th> <th>Scope</th> <th>Description</th>
    </tr>
    <tr>
        <td>context</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/planning/DeploymentPlanningContext.html">DeploymentPlanningContext</a></td> <td>all</td> <td>Use this to add steps and checkpoints to the plan</td>
    </tr>
    <tr>
        <td>deployedApplication</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/DeployedApplication.html">DeployedApplication</a></td> <td>all</td> <td>Specifies which application version will be deployed to which environment</td>
    </tr>
    <tr>
        <td>steps</td> <td> </td> <td>all</td> <td>Allows you to create steps from the <a href="#use-a-predefined-step">step registry</a></td>
    <tr>
        <td>specification</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/DeltaSpecification.html">DeltaSpecification</a></td> <td>pre-plan<br/>post-plan</td> <td>Contains the delta specification for the current deployment</td>
    </tr>
    <tr>
        <td>delta</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html">Delta</a></td> <td>deployed</td> <td>Whether the deployed should be created, modified, destroyed, or left unchanged (noop)</td>
    </tr>
    <tr>
        <td>deployed</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/Deployed.html">Deployed</a></td> <td>deployed</td> <td>In the case of create, modify, or noop, this is the "current" deployed that the <code>delta</code> variable refers to; in the case of destroy, it is not provided</td>
    </tr>
    <tr>
        <td>previousDeployed</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/udm/Deployed.html">Deployed</a></td> <td>deployed</td> <td>In the case of modify, destroy, or noop, this is the "previous" deployed that the <code>delta</code> variable refers to; in the case of create, this is not provided</td>
    </tr>
    <tr>
        <td>deltas</td> <td><a href="/xl-deploy/4.5.x/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Deltas.html">Deltas</a></td> <td>plan</td> <td>Collection of all <code>Delta</code>s in the current <code>InterleavedPlan</code></td>
    </tr>
    <tr>
        <td>controlService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.ControlService">ControlService</a></td> <td>all</td> <td>Gives you access to the ControlService</td>
    </tr>
    <tr>
        <td>deploymentService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.DeploymentService">DeploymentService</a></td> <td>all</td> <td>Gives you access to the DeploymentService</td>
    </tr>
    <tr>
        <td>inspectionService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.InspectionService">InspectionService</a></td> <td>all</td> <td>Gives you access to the InspectionService</td>
    </tr>
    <tr>
        <td>metadataService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.MetadataService">MetadataService</a></td> <td>all</td> <td>Gives you access to the MetadataService</td>
    </tr>
    <tr>
        <td>packageService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.PackageService">PackageService</a></td> <td>all</td> <td>Gives you access to the PackageService</td>
    </tr>
    <tr>
        <td>permissionService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.PermissionService">PermissionService</a></td> <td>all</td> <td>Gives you access to the PermissionService</td>
    </tr>
    <tr>
        <td>repositoryService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.RepositoryService">RepositoryService</a></td> <td>all</td> <td>Gives you access to the RepositoryService</td>
    </tr>
    <tr>
        <td>roleService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.RoleService">RoleService</a></td> <td>all</td> <td>Gives you access to the RoleService</td>
    </tr>
    <tr>
        <td>serverService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.ServerService">ServerService</a></td> <td>all</td> <td>Gives you access to the ServerService</td>
    </tr>
    <tr>
        <td>taskService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.TaskService">TaskService</a></td> <td>all</td> <td>Gives you access to the TaskService</td>
    </tr>
    <tr>
        <td>taskBlockService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.TaskBlockService">TaskBlockService</a></td> <td>all</td> <td>Gives you access to the TaskBlockService</td>
    </tr>
    <tr>
        <td>userService</td> <td><a href="/jython-docs/#!/xl-deploy/4.5.x//service/com.xebialabs.deployit.engine.api.UserService">UserService</a></td> <td>all</td> <td>Gives you access to the UserService</td>
    </tr>
    </tr>
        <tr>
        <td>logger</td> <td><a href="http://www.slf4j.org/api/org/slf4j/Logger.html">Logger</a></td> <td>all</td> <td>Allows you to access the XL Deploy logs. Prints logs to namespace <code>com.xebialabs.platform.script.Logging</code></td>
    </tr>
</table>

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
