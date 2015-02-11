---
title: Configure the release dashboard
subject:
- Release dashboard
categories:
- xl-deploy
tags:
- package
- application
- gui
- checklist
---

## Configuring deployment checklists

The checks on deployment checklists are specified on each environment that is a member of the deployment pipeline. XL Deploy's XML configuration capabilities are used to extend the type system with the new checklist items. This actually needs to happen in two places: on the _environment_ and on the _deployment package_.

The environment specifies which checklist items must apply before a deployment package can be deployed there. Therefore, the environment is extended with a checkbox for each possible condition. If the item's checkbox is checked, this means that the condition must be satisfied before a deployment, no check means the condition does not apply. Note that **all** checklist items are specified together on the environment type. In XL Deploy, you will select which condition applies to which environment.

Whether the condition is satisfied or not depends on the deployment package that is being deployed. Once a certain condition is met, the fact that this is the case is recorded on the deployment package itself so that the information travels along with the application code through the deployment pipeline.

Let's say that we want to use the Release Dashboard in a company that uses the following deployment pipeline for their PetClinic application:

* A _Development_ environment where applications are tested by the development team.
* A _Test_ environment where the test group does QA.
* An _Acceptance_ or pre-production environment where acceptance testing is done.
* A _Production_ environment where the application is live.

Suppose we want to use the following deployment checklists:

* _Development_: None, an application can always be deployed here.
* _Test_: There must be release notes packaged with the application so the test group knows what is new in the release.
* _Acceptance_: The test group must have done a performance test before the application lands here.
* _Production_: A change ticket number must be attached to all production deployments.

First, we change the environment. This XML snippet extends the `udm.Environment` with our checklist items:

    <type-modification type="udm.Environment">
      <property name="requiresReleaseNotes" description="Release notes are required"
                kind="boolean" required="false" category="Deployment Checklist" />
      <property name="requiresPerformanceTested" description="Performance testing is required"
                kind="boolean" required="false" category="Deployment Checklist" />
      <property name="requiresChangeTicketNumber" description="Change ticket number authorizing deployment is required"
                kind="boolean" required="false" category="Deployment Checklist" />
    </type-modification>

Note that the type of each property is `boolean` (meaning it is a checkbox that is checked (condition applies) or unchecked (condition does not apply)). All properties are also optional. It's recommended to group these properties into one or more categories. (Note: in XL Deploy 3.7.0 and 3.7.1, the properties **must** be put in the `Deployment Checklist` category)

In addition to extending the environment we also customize the `udm.Version`. (`udm.Version` encompasses both `udm.DeploymentPackage` and `udm.CompositePackage`)

	<type-modification type="udm.Version">
	  <property name="satisfiesReleaseNotes" description="Indicates the package contains release notes" 
	            kind="boolean" required="false" category="Deployment Checklist"/>
      <property name="rolesReleaseNotes" kind="set_of_string" hidden="true" default="senior-deployer" />
	  <property name="satisfiesPerformanceTested" description="Indicates the package has been performance tested"
	            kind="boolean" required="false" category="Deployment Checklist"/>
	  <property name="satisfiesChangeTicketNumber" description="Indicates the change ticket number authorizing deployment to production" 
	            kind="string" required="false" category="Deployment Checklist">
	    <rule type="regex" pattern="^[a-zA-Z]+-[0-9]+$" message="Ticket number should be of the form JIRA-[number]" />
	  </property>
	</type-modification>

The 'requires' properties on an `udm.Environment` always have to be of type boolean because this property means "Is this check enabled for this Environment?". After adding a 'requires' property on the `udm.Environment` in the synthetic.xml, you can use the UI to turn the checks on or off for an environment.

In contrast, 'satisfies' properties on `udm.Version` can be of type boolean, integer or string. Usually it will be a boolean, but for example in the case of a ticket number it will be a string, so you can enter the ID of the ticket. XL Deploy will just check if some value was entered. If that's the case, the requirement is satisfied.

It is also possible to assign security roles to checks. Adding a role to a particular check restricts the users who can satisfy this checklist item to the users that are a member of this role. The default value defines the actual roles and must be of type `set_of_string`. Multiple roles are specified as a comma-separated list. The 'roles' property should be placed on `udm.Version`. Roles are optional and when omitted everyone is allowed to modify this check. The admin user is always allowed to modify checks.

**Important:** Property names on the `udm.Environment` must use the _requires_ prefix and properties on the `udm.Version` must use the _satisfies_ prefix. If you assign specific roles to checks on `udm.Version`, the property must have the _roles_ prefix. In addition, _roles_ properties should be hidden (i.e. set `hidden="true"`).

## Complete synthetic.xml example

This is an example of a complete `synthetic.xml` configuration for a Release Dashboard:

	<synthetic xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	           xmlns="http://www.xebialabs.com/deployit/synthetic"
	           xsi:schemaLocation="http://www.xebialabs.com/deployit/synthetic synthetic.xsd">

		    <type-modification type="udm.Environment">
		      <property name="requiresReleaseNotes" description="Release notes are required"
		                kind="boolean" required="false" category="Deployment Checklist" />
		      <property name="requiresPerformanceTested" description="Performance testing is required"
		                kind="boolean" required="false" category="Deployment Checklist" />
		      <property name="requiresChangeTicketNumber" description="Change ticket number authorizing deployment is required"
		                kind="boolean" required="false" category="Deployment Checklist" />
		    </type-modification>

			<type-modification type="udm.Version">
			  <property name="satisfiesReleaseNotes" description="Indicates the package contains release notes" 
			            kind="boolean" required="false" category="Deployment Checklist"/>
              <property name="rolesReleaseNotes" kind="set_of_string" hidden="true" default="senior-deployer" />
			  <property name="satisfiesPerformanceTested" description="Indicates the package has been performance tested" 
			            kind="boolean" required="false" category="Deployment Checklist"/>
	          <property name="satisfiesChangeTicketNumber" description="Indicates the change ticket number authorizing deployment to production" 
	                    kind="string" required="false" category="Deployment Checklist">
	            <rule type="regex" pattern="^[a-zA-Z]+-[0-9]+$" message="Ticket number should be of the form JIRA-[number]" />
	          </property>
			</type-modification>

	</synthetic>

With your configuration in place, start the XL Deploy server.

## Configuring the deployment pipeline

Configuring the deployment pipeline for an application is done on the application CI itself. Here, you refer to a `release.DeploymentPipeline` CI (stored under the _Configuration_ root node) that specifies an ordered list of environments the application progresses through. The application must have a deployment pipeline associated with it before it can be used in the Release Dashboard.

Log into the XL Deploy GUI as an administrator and open the Repository Browser. Locate the application for which you want to set the deployment pipeline and open it in the editor. The application has a property called _deployment pipeline_ and allows the user to select a number of environments from the repository. Choose the environments you want in the deployment pipeline. Note that you can put the environments in the desired order by dragging the environments to the correct place in the list. Save the application CI when you are done.

## Configuring the environment checklists

Configuring the environment checklists is done on the environment CI itself. Here, you specify which of the available checks must be satisfied for an application version to be deployed to the environment.

Open the Repository Browser and locate the environment for which you want to set the deployment checklist and open it in the editor. The environment has a tab called **Deployment Checklist** that contains the checkboxes for each condition that you have set in the `synthetic.xml`. Check each condition that you want to verify for a deployment to the environment. Save the environment CI when you are done.
