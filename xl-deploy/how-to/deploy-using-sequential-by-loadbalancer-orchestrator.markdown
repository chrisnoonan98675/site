---
title: Deploy application using `sequential-by-loadbalancer-group` orchestrator
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
---

## Define task

Let us presume we have a web site that consists of two groups of physical servers.
Each group represents one side of our infrastructure in the load balancer.
We will call them Side A and Side B.

In order to make this scenario simple we will presume that each side consists of one physical machine on which we have installed one apache web server and one weblogic application server. 
In reality each side might consist of more than one machine with more than one web server and there can be a cluster of application servers.

Our goal is to deploy new version of an application to this web site.
An application consists of web content (static html, pictures, etc) and .ear file. 
Web content is deployed to apache web server while .ear file gets deployed to the weblogic application server(s).

In order to deploy new version of an application we have to:

* for side A:
	* disable access to the Side A in our loadbalancer
	* upgrade application 
	* enable access to the Side A
* for side B:
	* disable access to the Side B in our loadbalancer
	* upgrade application 
	* enable access to the Side B

Additionally we would like to execute deployment in parallel as much as possible.

Please note that end user experience depends on both static web content and enterprise application being deployed in sync.
This means that we cannot deploy only static web content and enable access to it before enterprise application is fully deployed on the same side of the web site.
If we're not careful we might end up with static content pointing to the not yet deployed application.

Let's see how can we accomplish this goal.

## Define application and infrastructure building blocks

Let's define application to deploy:

1. application that has some static web content: `web-content`
2. application that consists of an ear: `PetClinic-ear`
3. composite application that consists of static web content and an .ear: `LB-Application`

Let's define infrastructure:

1. define apache web server `sideA-apache`
2. define apache web server `sideB-apache`
3. define weblogic server `sideA-wls`
4. define weblogic server `sideB-wls`

It should look like this:

![Basic infrastructure](images/deploy-using-sequential-by-loadbalancer-orchestrator/basic_infrastructure.png)


Let's define loadbalancer:

1. create F5 BIG IP load balancer and add `sideA-apache`, `sideB-apache`, `sideA-wls`, `sideB-wls` as load balancer members

![Load balancer members](images/deploy-using-sequential-by-loadbalancer-orchestrator/load_balancer_members.png)

If we just create deployment without any tweaking:

![Basic deployment](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_1.png)

we will end up with a deployment plan similar to the following one:

![Basic deployment plan](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_basic.png)

## Orchestrate deployment

We would like to split deployment into 2 groups. In order to accomplish this edit servers in `Repository` tree:

1. associate servers `sideA-apache` and `sideA-wls` with group `1`
2. associate servers `sideB-apache` and `sideB-wls` with group `2`

Here is the screenshot of the configuration for the server `sideA-apache`:

![Associate `sideA-apache` with deployment group `1`](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_group_1.png)

Now we can tweak deployment plan under `Deployment options...` and enable `sequential-by-deployment-group` orchestrator:

![Enable `sequential-by-deployment-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_1.png)

*Please notice that we have disabled plan optimization. This helps us track orchestrations.*

XL Deploy generates deployment plan similar to the following one:

![Deployment plan with `sequential-by-deployment-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_sequential_by_group.png)

This is fine, but we would like to parallelize deployment to the servers in each group.
In order to accomplish this we can define additional sub-groups.
We should put apache servers into sub-group `1` and wls servers into sub-group `2`.

Here's the screenshot of the configuration for the `sideA-wls` server:

![Associate `sideA-wls` with deployment sub-group `2`](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_sub_group_2.png)

Now we can tweak deployment plan under `Deployment options...` and enable `parallel-by-deployment-sub-group` orchestrator.

![Enable `parallel-by-deplyoment-sub-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_2.png)

XL Deploy generates deployment plan similar to the following one:

![Deployment plan with `parallel-by-deployment-sub-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_parallel_by_sub_group.png)

This is not exactly what we want. With the generated plan for each deployment group we would:

* in parallel:
	* deploy to apache web server
		* disable access to apache web server in load balancer
		* deploy to apache web server
		* enable access to apache web server in load balancer
	* deploy to wls application server
		* disable access to wls application server in load balancer
		* deploy to wls application server
		* enable access to apache web server in load balancer

Did you notice the problem? With a deployment plan such as this we allow
access to our website even if it's not fully deployed.
Typically, deployment of web content will be faster than deployment of
enterprise applications and we will most probably allow users to access static
web content while our backend applications are still deploying.

We don't want customers to access our static content while there is a
deployment still in progress, so we have to do something about it and turn
deployment plan into something more similar to the following scenario:

* sequentially:
	* disable access to the apache web server and wls application server
		* disable access to apache web server in load balancer
		* disable access to wls application server in load balancer
	* in parallel:
		* deploy to apache web server
		* deploy to wls application server
	* enable access to the apache web server and wls application server
		* enable access to wls application server in load balancer
		* enable access to apache web server in load balancer

Let us tweak deployment plan under `Deployment options...` and enable `sequential-by-loadbalancer-group` orchestrator.

![Enable `sequential-by-loadbalancer-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_3.png)

XL Deploy generates deployment plan similar to the following one:

![Deployment plan with `sequential-by-loadbalancer-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_nonfinal.png)


`sequential-by-loadbalancer-group` orchestrator takes existing sub-plans and transforms them into a new sequential plan that consists of following groups of tasks:

1. disable access to all containers in original sub-plan
2. deploy as with original sub-plan
3. enable access to all containers in original sub-plan

Unfortunately, orchestrator `sequential-by-loadbalancer-group` is applied too late and we might still enable access to our web site too soon.
In order to fix this deployment plan we need to move `sequential-by-loadbalancer-group` orchestrator one level up. So, let's do that:

![Move up `sequential-by-loadbalancer-group` orchestrator](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_4.png)

XL Deploy generated deployment plan now looks like this:

![Deployment plan with `sequential-by-loadbalancer-group` orchestrator in right place](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_final.png)

This looks like the plan we want.

If we enable plan optimization:

![Enable `Optimize Plan`](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_options_5.png)

XL Deploy generates following deployment plan:

![Optimized deployment plan](images/deploy-using-sequential-by-loadbalancer-orchestrator/deployment_plan_final_optimized.png)


... and this is what we want.

