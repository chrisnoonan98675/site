---
title: JIRA integration with XL Deploy
categories:
- xl-deploy
tags:
- jira
- integration
- api
---

To integrate JIRA with XL Deploy, we will use the [change management community plugin](https://github.com/xebialabs/community-plugins/tree/master/deployit-udm-plugins/utility-plugins/change-mgmt-plugin).

First, create a `<DEPLOYIT_SERVER>/ext/chg` directory and place the Python scripts that interact with JIRA there.

The plugin has two main features:

* Allowing you to include a `chg.ChangeRequest` in a package and map it to a `chg.ChangeManager` in the target environment. This will always generate a "validate ticket" step and, if the `updateChangeTicketAfterDeployment` of the `ChangeManager` is set, will add an additional step to carry out some update after the deployment.

* Allowing you to require that a `ChangeRequest` is deployed as part of the deployment. You do this by defining the release condition `requiresChangeTicket` (you can also change the `ChangeTicket` suffix via the `changeTicketChecklistItemSuffix` property of the `ChangeManager`) and enabling that for an environment. Note that you cannot set this property via the Release Dashboard; it is automatically set based on whether you are creating or modifying a `ChangeRequest` as part of the mapping.

After downloading the change management plugin to the `<DEPLOYIT_SERVER>/plugins` directory:

1. Verify that your JIRA installation has the REST API and has it enabled
1. Find a host that meets the JIRA prerequisites from the [README](https://github.com/xebialabs/community-plugins/blob/master/deployit-udm-plugins/utility-plugins/change-mgmt-plugin/README.md) (basically, a Unix box with Python and the Python HTTP libs that can call the JIRA REST API installed)
1. Test that you can make requests from that host to the REST API via Python; here, you can see which commands will actually be executed
1. Edit the `synthetic.xml` file to define your customizations

For example:

    <!-- will be set automatically during deployment planning, not by users -->
    <type-modification type="udm.Version">
        <property name="satisfiesChangeTicket" kind="boolean" hidden="true" required="false" default="false" category="Deployment Checklist" />
    </type-modification>

    <!-- The complementary property that determines whether an environment requires change tickets for deployments is configured as follows --> 
    <type-modification type="udm.Environment">
        <property name="requiresChangeTicket" kind="boolean" required="false" default="false" category="Deployment Checklist" />
    </type-modification>

    <!-- JIRA -->
    <type type="chg.JiraChangeManager" extends="chg.ChangeManager" description="A JIRA instance">
        <property name="url" kind="string" required="true"/>
        <property name="username" kind="string" required="true"/>
        <property name="password" kind="string" password="true" required="true"/>
        <property name="transitionName" kind="string" required="true" description="The transition to use when updating a change ticket with this manager"/>
        <property name="transitionMessage" kind="string" description="An optional comment to include when performing the transition"/>
    </type>

    <type type="chg.JiraChangeTicket" extends="chg.ChangeTicket" container-type="chg.JiraChangeManager">
        <property name="createScript" hidden="true" default="chg/jira-check-for-request.py.ftl" />
        <property name="updateScript" hidden="true" default="chg/jira-update-request.py.ftl" />
    </type>

Now you can create the JIRA server and define the scripts that execute for various actions:

1. Create an [Overthere](https://github.com/xebialabs/overthere) host for this machine in XL Deploy and define an `chg.JiraChangeManager` on it
1. Add the `JiraChangeManager` to the target environment
1. Add a `ChangeRequest` to the deployment package, map it to the `JiraChangeManager` and see which steps it generates
1. Run the deployment plan
