---
title: Configure profile risk settings
categories:
- xl-release
subject:
- Settings
tags:
- settings
- risk
- system administration
- threshold
---

## Set the global risk threshold

The Global Risk Threshold determines when a release is marked as `Attention Needed` or `At Risk` based on the risk score of the release, and is used for every risk profile.
To edit the Global Risk Threshold, you must have the [Admin global permission](/xl-release/how-to/configure-permissions.html).
Go to **Settings** > **Risk settings** and configure the Global Risk Threshold by setting the limit values for `On Track`, `Attention Needed`, and `At Risk`.

![Global Risk Threshold](../images/global-risk-threshold.png)

## Manage risk profiles

In XL Release, you can create or edit risk profiles to assign different values to the Risk Assessors and apply the custom risk profiles to your templates and releases.
The **Default risk profile** is a risk profile guideline provided by XL Release containing the default risk scores for the risk assessors. You cannot edit the default risk profile and it is assigned to all templates and releases if no other custom risk profiles are specified. You can view the details of the **Default risk profile**, create an exact copy of it and edit the copied risk profile.

For more information about the risk score and the risk assessors, refer to [Using risk awareness in XL Release](/xl-release/how-to/using-the-risk-aware-view.html).

To create, edit, or delete a risk profile, you must have the [Edit Risk Profile global permission](/xl-release/how-to/configure-permissions.html).

To create a new risk profile, click **New Risk Profile** and specify a name for the risk profile. You can change the score for each Risk Assessor in the list or disable it by clicking the **Disable** icon next to it. You cannot add new Risk Assessors; you can only enable Risk Assessors that were previously disabled.

To save the risk profile, click **Create risk profile**.

![Create risk profile](../images/new-risk-profile.png)

**Note:** You can assign a risk profile to a template or a release, or change the risk profile after a release has started. If no custom risk profile is assigned during the release template creation or at start of the release, the Default risk profile is assigned.
