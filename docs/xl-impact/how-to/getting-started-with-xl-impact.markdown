---
title: Getting started with XL Impact
categories:
- xl-impact
subject:
- Getting Started
tags:
- projects
- getting started
---

XL Impact is a DevOps analytics application that is used with XL Release to analyze the data from the tools you use across your delivery pipeline. XL Impact enables you to improve the effectiveness of your teams to produce better software products.

## Understanding XL Impact concepts

### Projects in XL Impact

XL Impact collects hard data correlated from tools such as XL Release, Jenkins, Jira, and GitHub and provides information limited to a defined scope such as a project, a team, or a product. To define a scope, you need to create new project.

During the project definition you can configure:

*  The pieces of data that are relevant to your project (scope). For example, you can filter by the GitHub repository, the XL Release template name, or by the Jenkins job name.
* The **Release to customer** for this project (Select either XL Release or Jenkins build).
* The **Jira issue done state** for this project refers to a subset of all the possible Jira issue states that are considered as `done` (e.g. completed, done, resolved).
* The **Production issue** for this project. This is to distinguish between normal product issues (product development according to roadmap) and issues reported by customers (complaints). This allows you to create your definition of production issue for you project. This filter helps XL Impact identify production issues. Everything that matches this filter is considered a production issue, everything that does not match this filter is not considered a production issue.

To create a project, go to **Projects** and click **Create project**. To edit a project, click the pencil icon.

There are four major types of filter blocks:

* **Data Set Filter**
* **Release to Customer Filter**
* **Jira Final Status Filter**
* **Jira Production Issue Filter**

Each filter block contains one or more **Data Source** blocks.

You can select a data source from the drop down list:

* XLR Releases
* Commit
* Pull Request
* Branch
* Jenkins Build
* Jira Issue
* Jira Issue Version

For each **Data Source** block you can add or remove filter rows. In a filter row you can define a **Field**, select an **Operator**, and specify the values you want to use. On the right side of the page, charts are displayed showing how many pieces of data are filtered by the filter you configured and distribution of these pieces on a timeline.

#### Release to Customer filter

For a more detailed description of the Release to Customer concept, refer to [The mechanics of the Release to Customer Filter](/xl-impact/concept/release-to-customer-filter.markdown).

The **Release to Customer** filter can be either a release from XL Release or a Jenkins Job. It is important that XL Impact expects a release to customer to occur multiple times and at similar intervals (weekly, bi-weekly, monthly, quarterly). In the XL Release example, the releases to customer are versions 7.0, 7.1, 7.2 and so on.

For each **Release to Customer**, XL Impact collects all the downstream releases from XL Release (child releases) and Jenkins jobs, obtaining a subgraph of related events (Releases and Jenkins jobs that were used to make a
release to customer).

Inside this subgraph, XL Impact searches for references to commits (mentioned by either the Releases or the Jenkins jobs).

XL Impact walks through the commit tree, starting from the commits found on the previous step, through parents, to the very first commit in each repository. These commits are marked as "known to this release to customer".

##### Sample scenario

For example, if a team is working on a feature and commits to a branch, and the branch is not merged into the master branch when master is released, then the commits of this branch are not marked as "known to this release to customer".

The commits that are known to release "N" and not known to release "N - 1" (the release previous to N) are marked as "commits that belong to release N" which means that "these commits were released with release N".

The Jira issues and pull requests are considered to be a part of the release "through commits". Each entity can belong to only one release (the last one). If a Jira issue is reused after being released, it will be reassigned to a new release.

#### Jira Issues and Jira Issue Versions

In XL Impact, Jira issues are variable, as opposed to other processed entities that are invariable such as commits and Jenkins jobs. As a result, XL Impact analyzes not only the Jira issues, but also the Jira issue versions (or Jira issue changes).

When XL Impact tries to determine "how many issues were done during the week", it queries "how many changes to Jira issue versions occurred during a week". The query must follow the conditions: before a change happened, the issue did not match the filter "Jira issue done state" according to project definition; after the change, the issue matched that filter.
For some criteria, you must configure filters for both Jira issue and Jira issue versions.

#### Time period on insights and metric tiles

The time period for the insights and the metric tiles displayed in the dashboard is configured on the **Options** page of project setup.
On the chart timeline the time period is displayed and on metric tiles the time period is implicit.

Two time periods are calculated for the "report date":

* Current period: starting from "report date minus the period length" to "report date"
* Previous period: starting from "report date minus two times the period length" to "report date minus the period length"

#### Create a new project

To create a new project in XL Impact:

1. Go to the **Projects** tab and click **Create Project**.
1. Specify the **Project Name** and **Project Description**.
1. Under the **Data** section, you can configure the **Data Set Filter**. To add a new data source, click **Add Data Source** or the **+** icon and select a data source from the drop down list.
1. To track the project progress, go to the **Options** section and specify the number of days for one period of development.
1. Click **Save Project**.
