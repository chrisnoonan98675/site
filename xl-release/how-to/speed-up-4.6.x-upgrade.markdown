---
title: Speed up the XL Release 4.6.x upgrade
categories:
- xl-release
subject: System administration
tags:
- upgrade
- system administration
since:
- 4.6.0
---

Before XL Release 4.6.0, JCR version storage was used for some parts of XL Release functionality. This meant that for every node in active JCR storage (such as Release, Phase, or Trigger), XL Release stored a version history containing zero or more versions, depending on how many times a node was changed.
 
As of XL Release 4.6.0, XL Release does not use version storage and creates node versions in a different way when they are needed. This improves the performance of XL Release when performing create or update operations and greatly reduces the size of the repository.

However, this change means that when you upgrade to XL Release 4.6.0 or later, an upgrader goes through all version histories in your repository and removes them. The amount of time this takes is proportional to the size of your repository. For example, testing shows that for a repository of 20 GB, upgrading takes approximately 20 hours (at a rate of 80 nodes per second).

If your repository is large and you want to speed up the upgrade process, you can use a workaround. This workaround requires manual actions and is only available for certain configurations, so please read the instructions carefully.

## Prerequisites

You have standard XL Release repository configuration, which is:

* The underlying database is Apache Derby
* The version store is kept separately from the default JCR workspace

To review your configuration, check if your `<XLR_HOME>/conf/jackrabbit-repository.xml` contains the following lines:

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
            <param name="path" value="${rep.home}/version" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.DerbyPersistenceManager">
            <param name="url" value="jdbc:derby:${rep.home}/version/db;create=true" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${rep.home}/repository/index" />
        <param name="supportHighlighting" value="true" />
    </SearchIndex>

## Upgrade workaround steps

To upgrade XL Release:

1. Back up your installation of XL Release.
1. Copy your existing repository and configuration files to the new installation location, but *do not start XL Release yet*.
1. Manually delete the following folders, which contain the version store database and indexes:
  * `<XLR>/repository/version/db`
  * `<XLR>/repository/repository/index`
1. Start XL Release and type "yes" to run upgrades.

The `XLRelease460RemoveVersions` upgrader will still require some time to run because it must locate and process all nodes in the repository. However, it will run must faster because it does not have to remove any version nodes.
