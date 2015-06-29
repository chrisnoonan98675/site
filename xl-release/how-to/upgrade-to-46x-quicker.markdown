---
title: Speeding up XL Release 4.6.x upgrade
categories:
- xl-release
subject: System administration
tags:
- upgrade
- system administration
---

Before XL Release 4.6.0 JCR version storage was used for some parts of XL Release functionality. It means that for every node in active JCR storage, like Release, Phase or Trigger, there was a version history kept in a different storage, with zero or more versions in it, depending on how many times a node was changed.
 
Since XL Release 4.6.0 version storage is not used anymore, and node versions are created in a different way when it is needed. The benefit of it is that XL Release becomes noticeable faster in create or update operations. Additionally, the repository takes almost twice less space. Therefore when you upgrade to XL Release 4.6.0 or higher there is an upgrader which will go through all version histories in your repository and remove them. This is a tedious job, and it takes time proportional to the size of your repository. In our tests it went with the rate of 80 nodes per second. For a repository of 20 Gb it took around 20 hours to upgrade.

If your repository is too big and you do not want to wait hours to upgrade, there is a workaround which can help speed up the process. It implies some manual actions and is not always applicable. So please read carefully further.

#### Prerequisites

You have standard XL Release repository configuration:
* The underlying database is Apache Derby.
* The version store is kept separately from the default JCR workspace.

To ensure these you could check if you have following snippet in your `<XLR_HOME>/conf/jackrabbit-repository.xml`:

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


#### Quicker upgrade steps

Procedure for the upgrade is following.

0. (Backup your installation of XL Release, as you always have to do.)
1. Prepare the upgrade to XLR 4.6+: copy old repository and configuration files to the new server, **but don't start it yet**.
2. Delete version store database and indexes. It is done by deleting following two folders:
  * `<XLR>/repository/version/db`
  * `<XLR>/repository/repository/index`
3. Start XL Release and type "yes" to run upgrades.

The `XLRelease460RemoveVersions` upgrade will still take a lot of time to run, as it has to find and process all nodes in the repository. However it will run a lot faster as it does not have to remove any version nodes.
