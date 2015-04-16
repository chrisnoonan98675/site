---
title: Automatically purge packages according to a user defined policy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- policy
- purge
- package
- repository
- schedule
---

To automatically purge old deployment packages according to a policy create a new `policy.PackageRetentionPolicy`:

1. Click **Repository** in XL Deploy
2. Right-click on the **Configuration** root and select `policy.PackageRetentionPolicy`
3. Populate the required unique identifier and specify a regular expression which matches all packages to which the policy should apply
4. Define the number of packages to retain per application
5. (Optional) By default automatic policy execution is enabled and will run according to the Crontab schedule defined under the Schedule tab

![Package retention policy](images/system-admin-package-retention-policy.png)

We can create multiple package retention policies here, e.g.:

**ReleasePackagePolicy**

* `pattern` - ^Applications/.*/\d{1,8}(?:\.\d{1,6})?(?:\.\d{1,6})?(?:-\d+)?$
* `packageRetention` - 30
* `schedule` - 0 0 18 * * *

**SnapshotPackagePolicy**

* `pattern` - ^Applications/.*/\d{1,8}(?:\.\d{1,6})?(?:\.\d{1,6})?(?:-\d+)?-SNAPSHOT$
* `packageRetention` - 10
* `schedule` - 0 0 18 30 * *

Please notice that these policies execute independently, so take care to define a regex expression to exclude packages covered by other policies.

The `policy.PackageRetentionPolicy` removes all packages except the last N packages matching the regular expression. Packages are sorted using lexicographical ordering and all deployed packages are automatically excluded from removal.
If a deployed package is also one of the last N package, it will occupy the slot with no difference to other packages.

### Example

An application has the following packages defined: **1.0, 2.0, 3.0, 3.0-SNAPSHOT, 4.0, 5.0**.

Package **1.0** is deployed to PROD environment and **4.0** to DEV environment.

Assuming a `policy.PackageRetentionPolicy` with a retention of last 3 packages and a release regex, the packages to be removed will be: **2.0**.



**Note**: Running a policy doesn't automatically reclaim disk space freed up by removing the packages. Please see [Reclaim disk space on an XL Deploy server](reclaim-disk-space-on-an-xl-deploy-server.html) for more details on that topic.

**Tip**: The policy can also be executed manually by right-clicking the policy CI and selecting **Execute job now**.