---
title: Impact of using satellite on your deployment plan
categories:
- xl-deploy
subject:
- Satellite
tags:
- deployment
- planning
- remoting
- satellite
---

When a deployment needs at least one satellite to achieve its goal, you'll see new steps added into the deployment plan. Each block at the root level of a plan is called a **phase**. Without any satellite, a deployment is composed most of the time of one phase.

Adding a satellite in a deployment will create *two new phases**. A setup phase is inserted at the first position. This phase contains steps that will check that all related satellites are up and running, that all the plugins are in sync with XL Deploy and will then transfer all the artifacts only once per satellite. Each satellite preparation is done in parallel.

![image](images/a-plan-with-three-phases.png) 

At the beginning of a second phase, some new steps are added to ping all satellites just before the deployment.  An upcoming feature will let you schedulde phases on different time. So you want to prepare the task on satellites at 3pm and only deploy at 11pm. 

![image](images/prepare-satellite-phase.png) 

Then the regular deployment is executed. If a block of steps is executed from a satellite, you can see it from the step description in your plan. In the following example, we can notice that the deployment on environment **env** will be executed with satellite **satellite.xebialabs.uk**. After checking the connection, it will proceed to the deployment of file **test.war** from **satellite.xebialabs.uk** to **localhost**.

![image](images/step-executed-on-satellite.png) 

In the end, the last phase will ensure that all the working directories on satellite are cleaned up. Because cleaning up temporary files is not related to a successful or failed deployment, XL Deploy will always try to execute those step when you cancel a task, unless you skipped them or if the task has not been executed once. This is the same behavior as cleaning up temporary staging files.

![image](images/cleaning-satellite-phase.png) 