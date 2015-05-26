---
title: Deploy an application using the sequential-by-loadbalancer-group orchestrator
categories:
- xl-deploy
subject:
- F5 BIG-IP plugin
tags:
- f5
- big-ip
- middleware
- plugin
- load balancer
- tutorial
since:
- 5.0.1
---

This scenario shows how you can use the `sequential-by-loadbalancer-group` orchestrator to deploy to web servers and application servers.

Assume that you have a web site that consists of two groups of physical servers. Each group represents one side of the infrastructure in the load balancer: side A and side B.

To simplify this scenario, assume that each side consists of one physical machine on which you have installed one Apache web server and one WebLogic application server. In reality, each side might consist of more than one machine with more than one web server and there can be a cluster of application servers.

The goal is to deploy a new version of an application to the web site. The application consists of web content (static HTML, pictures, and so on) and an EAR file. Web content is deployed to the Apache web server and the EAR file is deployed to the WebLogic application server.

To deploy a new version of the application, you must:

* For side A:
	* Disable access to Side A in the load balancer
	* Upgrade the application 
	* Enable access to Side A
* For side B:
	* Disable access to Side B in the load balancer
	* Upgrade the application 
	* Enable access to Side B

The end-user experience depends on the static web content and enterprise application being deployed in sync. This means that you cannot deploy only static web content and enable access to it before the enterprise application is fully deployed on the same side of the web site. You want to avoid having static content that points to an application that has not been deployed yet. Therefore, you want to execute the deployment in parallel as much as possible.

## Define application and infrastructure building blocks

This is the application that will be deployed:

1. Application that has some static web content: `web-content`
2. Application that consists of an EAR file: `PetClinic-ear`
3. Composite application that consists of static web content and an EAR file: `LB-Application`

And this is the infrastructure where it will be deployed:

1. Define Apache web server `sideA-apache`
2. Define Apache web server `sideB-apache`
3. Define WebLogic server `sideA-wls`
4. Define WebLogic server `sideB-wls`

This is how it looks in the Repository:

![Basic infrastructure](images/deploy-using-sequential-by-loadbalancer-orchestrator/basic_infrastructure.png)

To define the load balancer, create a F5 BIG-IP load balancer and add `sideA-apache`, `sideB-apache`, `sideA-wls`, and `sideB-wls` as load balancer members.

![Load balancer members](images/deploy-using-sequential-by-loadbalancer-orchestrator/load_balancer_members.png)

## Create a default deployment plan

If you create a deployment without any customization:

![Basic deployment](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_1.png)

The resulting deployment plan will be:

![Basic deployment plan](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_basic.png)

## Orchestrate the deployment

To split the deployment into two groups, edit the servers in the Repository:

1. Associate servers `sideA-apache` and `sideA-wls` to group `1`
2. Associate servers `sideB-apache` and `sideB-wls` to group `2`

This is the configuration for the server `sideA-apache`:

![Associate sideA-apache to deployment group 1](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_group_1.png)

### Enable the `sequential-by-deployment-group` orchestrator

Now you can use the **Deployment Properties** to enable the `sequential-by-deployment-group` orchestrator:

![Enable sequential-by-deployment-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_1.png)

**Note:** Plan optimization has been disabled to help you track orchestrations.

XL Deploy generates the deployment plan:

![Deployment plan with sequential-by-deployment-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_sequential_by_group.png)

### Parallelize deployment to the servers in each group

Now you can parallelize deployment to the servers in each group by defining additional sub-groups. Put the Apache servers into sub-group `1` and wls servers into sub-group `2`.

This is the configuration for the `sideA-wls` server:

![Associate sideA-wls to deployment sub-group 2](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_sub_group_2.png)

### Enable the `parallel-by-deployment-sub-group` orchestrator

Use the **Deployment Properties** to enable the `parallel-by-deployment-sub-group` orchestrator.

![Enable parallel-by-deplyoment-sub-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_2.png)

XL Deploy generates the deployment plan:

![Deployment plan with parallel-by-deployment-sub-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_parallel_by_sub_group.png)

### Enable the `sequential-by-loadbalancer-group` orchestrator

However, the plan above does not exactly meet the goal. With this plan, XL Deploy would do the following in parallel:

1. Deploy to Apache web server
	1. Disable access to Apache web server in load balancer
	1. Deploy to Apache web server
	1. Enable access to Apache web server in load balancer
1. Deploy to WebLogic application server
	1. Disable access to WebLogic application server in load balancer
	1. Deploy to WebLogic application server
	1. Enable access to Apache web server in load balancer

This means that access to the web site would be allowed, even if it is not fully deployed. Typically, deployment of web content will be faster than deployment of enterprise applications, and users will probably be able to access static web content while the back-end applications are still being deployed.

Instead, XL Deploy should do the following, sequentially:

1. Disable access to Apache web server and WebLogic application server
	1. Disable access to Apache web server in load balancer
	1. Disable access to WebLogic application server in load balancer
1. In parallel:
	1. Deploy to Apache web server
	1. Deploy to WebLogic application server
1. Enable access to the Apache web server and WebLogic application server
	1. Enable access to WebLogic application server in load balancer
	1. Enable access to Apache web server in load balancer

To achieve this, use the **Deployment Properties** to enable the `sequential-by-loadbalancer-group` orchestrator.

![Enable sequential-by-loadbalancer-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_3.png)

XL Deploy generates the deployment plan:

![Deployment plan with sequential-by-loadbalancer-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_nonfinal.png)

The `sequential-by-loadbalancer-group` orchestrator transforms the sub-plans into a new sequential plan that:

1. Disables access to all containers in original sub-plan
2. Deploys as with original sub-plan
3. Enables access to all containers in original sub-plan

Unfortunately, the `sequential-by-loadbalancer-group` orchestrator is applied too late and access to the web site might be enabled too soon. To fix this, move the orchestrator up one level:

![Move up sequential-by-loadbalancer-group orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_4.png)

The deployment plan now looks like:

![Deployment plan with sequential-by-loadbalancer-group orchestrator in right place](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_final.png)

Then, enable plan optimization:

![Enable `Optimize Plan`](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_5.png)

XL Deploy generates the deployment plan:

![Optimized deployment plan](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_final_optimized.png)

Which satisfies the goal.
