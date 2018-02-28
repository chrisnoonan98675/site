---
title: Lock tasks (XL Release 8.0)

categories:
- xl-release
subject:
- Lock tasks
tags:
- Releases
- Lock tasks
- Locks
since:
- XL Release 8.0
---
## Lock tasks
Lock tasks place mandatory steps into your release process. This feature is useful in highly regulated sectors that require a strict compliance regime.

### Pipeline design and locked tasks

Lock permission is role based and must be added to a users' profile before they can lock or unlock tasks. We recommend that this role is given to people who create release pipelines, or specific compliance people.   

Before adding locked tasks, specify, design, and test your complete pipeline. Adding locks before the pipeline is designed and tested will make the process complex.

-	Locked activities are mandatory. If a locked task occurs during a release, this task needs to be executed before the release can continue.
Locked tasks can be: automated - the release will continue once the task is completed; and manual - a user will need to sign-off in XL Release, or by remote approval, for the release to continue.

### Continuous delivery and compliance
Use lock tasks to enforce continuous compliance. This

### Audits
IT process audits

### Evidence database

### Minimum standards - freedom


Speed and control and scale are the three most important parameters if you want to do Continuous Delivery at enterprise scale. And in particular if you want to do this in government, insurance or banking. All three areas are highly regulated and require following a strict compliance regime. Without the right evidence continuous delivery is a mission impossible.

Besides version control and the evidence database introduced earlier XebiaLabs made another big step forward by providing the ability to lock and unlock tasks and groups. This is essential to ensure steps that are required are always executed and evidence is stored automatically.

Lock tasks are essential to enforce some mandatory steps while allowing freedom.
If you are working with minimum standards in your organisation you understand that a lot of steps are up to the interpretation of the team. You do not exactly describe how code coverage goals can be reached, or how a specific API test will be executed. However sometimes some steps are mandatory, you can think about a change request, an approval or a security test.

By giving autonomy and freedom to the teams you want to be sure some mandatory activities are executed. Locked tasks are in this case a perfect ingredient to do this. You can give a lot of freedom to the team to compose their own pipeline to adjust to their situation.

Lock tasks are crucial to build the right audit trail
If a team decides to skip certain activities for a good reason, limited evidence is stored in XL Release Evidence database. This results sometimes in bad auditing results. Audits on IT processes are happening frequent in the domains with high regulations. Therefore storing evidence should be taken seriously and placed in the process as natural as possible.

By locking tasks you not only enforce it will be executed, you also get a guarantee all the evidence is stored in the evidence database since skipping is no longer an option. If a task or group that was locked will fail, moving to the next one is not possible. You have to do it again until you pass it successfully.

Lock tasks are a vital part of the continuous compliance loop
Time after time XebiaLabs is introducing new components to the continuous compliance story. We believe that tools have and can be a powerful tool to achieve a high degree of continuous compliance. The ultimate goal is that devops team don’t have to bother about the compliance part of their delivery. It’s off the shelf integrated in the pipeline if they make use of the right setup.

At this moment XebiaLabs offers next to lock tasks the following components for continuous compliance
•	An evidence database, to automatically store all the evidence to provide and audit trail when needed.
•	Various off the shelf integrations with for example Fortify, Black Duck, Sonar to ensure the right security levels
•	Tagging of activities to relate your steps in the pipeline to your (IT) control framework
•	Single Sign On based on your company AD, LDAP or OIDC setup
•	Fine grained roles and permissions for composing and executing pipelines
•	Full integration with ticketing, change management and CMDB tooling to connect all parts together.


With a locked task you can guarantee a signoff by a responsible owner is given
The most important and most frequent request we received was to give the ability to lock a sign off task. Sign offs from the responsible persons are a crucial part of an audit trail. Whether this is the manager, an asset owner or the release manager, a sign off needs to be in place before a release is going to production.

With a locked task you can ensure particular steps are executed
Next to sign off tasks there are some other important tasks that are essential to lock. First of all the different security tests, static code scanning, library scanning and dynamic scans are crucial in a highly regulated environment.

If quality is crucial because a failure is not an option, other tests can be locked. Performance, API or integration testing is depending on the situation sometimes critical to execute.

In the last place are some activities that help to build a stronger audit trail crucial to lock. Creating a Change Record in for example ServiceNow is for some organisations very important. This creation can be automated for 100% via XL Release, by locking this task it becomes inevitable.

With a lock task on a group you can guarantee steps are executed in a certain order.
To make locking even easier we’ve added this functionality to groups. With locking a group you ensure a certain order as designed in the group. This is important if for example a signoff can only be given after a static code scan. By specifying this in one group the order is also guaranteed.
