---
title: Types used in the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- ci
weight: 119
---

This is an overview of the types that are available in the XL Deploy command-line interface (CLI).

## Artifact

A configuration item (CI) with an associated file; extends `ConfigurationItem`.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| file | OverthereFile | An abstract representation of a file that can be access through an `OverthereConnection` |

## ArtifactAndData

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| artifact | Artifact | The artifact |
| data | byte[] | The file contents |
| filename | String | The name of the file |

## ConfigurationItem

A new CI or a CI from the repository.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| id | String | The ID of the CI |
| type | String | The UDM type of the CI |
| ci_attributes | CiAttributes | Creation and modification date and user of this CI |
| values | Map | The properties of the CI |
| validations | List<ValidationMessage> | The validation errors on the CI |

## ConfigurationItemId

A CI reference with type information.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| id | String | The ID of the CI |
| type | String | The UDM type of the CI |

## Deployment

Settings for doing a deployment.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| deployedApplication | ConfigurationItem | The application being deployed |
| deployeds | List<ConfigurationItem> | The configured deployeds |
| deployables | List<ConfigurationItemId> | The available deployables in the package |
| containers | List<ConfigurationItemId> | The containers being deployed to |
| deploymentType | String | The type of deployment. Possible values: `INITIAL`, `UPDATE`, `UNDEPLOYMENT` |

## StepState

Information about a task step.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| description | String | The human-readable description of the step |
| state | String | The current state of the step. Possible values: `PENDING`, `SKIP`, `EXECUTING`, `PAUSED`, `FAILED`, `DONE`, `SKIPPED` |
| startDate | Calendar | The date the step was started |
| completionDate | Calendar | The date the step was completed |
| log | String | The log output of the step |
| failureCount | String | The times the step has failed |
| metadata | map | Step metadata, containing the `order` and `deployed` |

## TaskState

XL Deploy task.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| id | String | The ID of the task |
| state | String | The state the task was in when queried. Possible values: `PENDING`, `QUEUED`, `EXECUTING`, `STOPPED`, `EXECUTED`, `DONE`, `CANCELLED` |
| startDate | Calendar | The date the task was started |
| completionDate | Calendar | The date the task was completed |
| nrSteps | int | The number of steps in the task |
| currentStepNr | int | The number of the step the task is currently at |
| metadata | map | Task metadata, including the `application`, `environment`, `version` |
| failureCount | int | The number of times the task has stopped because of a failed step |
| owner | String | The current owner of the task |

## TaskWithSteps

XL Deploy task including its steps.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| id | String | The ID of the task |
| state | String | The state the task was in when queried. Possible values: `PENDING`, `QUEUED`, `EXECUTING`, `STOPPED`, `EXECUTED`, `DONE`, `CANCELLED` |
| startDate | Calendar | The date the task was started |
| completionDate | Calendar | The date the task was completed |
| nrSteps | int | The number of steps in the task |
| currentStepNr | int | The number of the step the task is currently at |
| metadata | map | Task metadata, containing the `application`, `environment`, `version` |
| failureCount | int | The number of times the task has stopped because of a failed step |
| owner | String | The current owner of the task |
| steps | List | All steps in the task represented as StepState objects |

## FullTaskInfo

Task that includes step information. Extends `TaskInfo`.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| steps | List<StepInfo> | The steps in the task |

## FullTaskInfos

List of tasks with step information.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| tasks | List<FullTaskInfo> | All retrieved tasks as a list |

## ValidationMessage

Indicates a validation error and provides a message.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| ciId | String | The ID of the CI this validation message refers to |
| propertyName | String | The name of the property in the CI this validation message refers to |
| message | String | The message itself |

## StepInfo

Information about a task step. **Deprecated**; use StepState instead.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| description | String | The human-readable description of the step |
| state | String | The current state of the step. Possible values: `PENDING`, `SKIP`, `EXECUTING`, `PAUSED`, `FAILED`, `DONE`, `SKIPPED` |
| startDate | Calendar | The date the step was started |
| completionDate | Calendar | The date the step was completed |
| log | String | The log output of the step |
| failureCount | String | The times the step has failed |
| nr | int | The position of the step in the task |

## TaskInfo

XL Deploy task. **Deprecated**; use TaskState instead.

{:.table .table-striped}
| Name | Type | Description |
| ---- | ---- | ----------- |
| id | String | The ID of the task |
| label | String | The label describing the task |
| state | String | The state the task was in when queried. Possible values: `PENDING`, `QUEUED`, `EXECUTING`, `STOPPED`, `EXECUTED`, `DONE`, `CANCELLED` |
| startDate | Calendar | The date the task was started |
| completionDate | Calendar | The date the task was completed |
| nrOfSteps | int | The number of steps in the task |
| currentStepNr | int | The number of the step the task is currently at |
| application | String | For deployment tasks, the `udm.Application` the task is for |
| version | String | For deployment tasks, the version of the `udm.Application` the task is for. This refers to the actual package being deployed, viz. the deployment package or composite package |
| environment | String | For deployment tasks, the environment the package is being deployed to |
| failureCount | int | The number of times the task has stopped because of a failed step |
