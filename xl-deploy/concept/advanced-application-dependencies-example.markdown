---
title: Advanced application dependencies example
categories:
- xl-deploy
subject:
- Dependencies
tags:
- package
- deployment
- dependency
- microservices
since:
- XL Deploy 5.1.0
---

This is an example of an advanced scenario with multiple applications that depend on one another.

Assume that you have five applications called CustomerProfile, Inventory, PaymentOptions, ShoppingCart, and WebsiteFront-End. Their versions and dependencies are as follows:

{:.table .table-striped}
| Application name | Version | Depends on... |
| ---------------- | ------- | ------------- |
| CustomerProfile | `1.0` | Inventory `[1.0,2.0)` |
| | | PaymentOptions `[1.0,3.0]` |
| Inventory | `1.0` | No dependencies |
| | `1.5` | ShoppingCart `[1.0,2.0]` |
| PaymentOptions | `1.1` | No dependencies |
| | `2.1` | ShoppingCart `[1.5,2.0]` |
| | `3.5` | ShoppingCart `[1.5,2.0]` |
| ShoppingCart | `2.0` | WebsiteFront-End `2.0` |
| | `2.5` | WebsiteFront-End `2.0` |
| WebsiteFront-End | `1.0` | No dependencies |
| | `2.0` | No dependencies |

You can set up a deployment of the latest version of CustomerProfile by dragging it to the Deployment Workspace. XL Deploy automatically adds the deployables from the dependent deployment packages.

![Mapped application with dependencies](images/mapping-with-dependent-apps.png)

**Tip:** Hover the mouse pointer over a deployable to see the deployment package it belongs to.

## How dependent application versions are selected

This is how XL Deploy selected the right application versions:

1. CustomerProfile 1.0: This is the latest version of CustomerProfile, so XL Deploy selected it when you dragged the application to the Deployment Workspace.

2. Inventory 1.5: CustomerProfile 1.0 depends on Inventory [1.0,2.0), so XL Deploy selects the highest version between 1.0 and 2.0, which is Inventory 1.5.

3. PaymentOptions 2.1: CustomerProfile 1.0 depends on PaymentOptions [1.0,3.0], so XL Deploy selects the highest version between 1.0 and 3.0, which is PaymentOptions 2.1.

4. ShoppingCart 2.0: Inventory 1.5 and PaymentOptions 2.1 both depend on ShoppingCart. XL Deploy selects the highest version that satisfies both of their requirements, which is ShoppingCart 2.0.

5. WebsiteFront-End 2.0: ShoppingCart 2.0 requires WebsiteFront-End 2.0, so XL Deploy selects that version.

## Updating a deployed application

You can easily update a deployed application to a new version. For example, to update the Inventory application to version 1.9:

1. Under **Environments** in the Deployment Workspace, right-click Inventory 1.5 and select **Update**.
2. Under **Packages**, locate Inventory 1.9 and drag it to the left side of the deployment workspace.
3. Click **Execute** to execute the deployment.

This deployment is possible because Inventory 1.9 satisfies the CustomerProfile dependency on Inventory `[1.0,2.0)`. Updating Inventory to a version such as 2.1 is not possible, because 2.1 does not satisfy the dependency.
