---
no_index: true
title: Using the CRaSH tool
---

When there is a problem with the Jackrabbit JCR repository underlying a XebiaLabs product (for example, the repository is corrupted), it may be useful to browse the repository contents using a repository browser tool called CRaSH.

## Start the repository browser

To use the repository browser, start Jackrabbit JCR and the CRaSH shell by executing the following command in `XL_SERVER_HOME`:

    ./bin/run.sh -support-mode

You will see a message similar to the following:

    2016-07-28 16:27:44.697 [main] {} INFO  c.x.xlplatform.config.ConfigLoader$ - Loading xl-release.conf
    2016-07-28 16:27:44.823 [main] {} INFO  c.x.xlrelease.XLReleaseBootstrapper - XL Release version 6.0.0 (built at 16-07-28 13:00:50)
    2016-07-28 16:27:44.825 [main] {} INFO  c.x.xlrelease.XLReleaseBootstrapper - (c) 2012-2016 XebiaLabs, Inc.

    2016-07-28 16:27:44.949 [main] {} INFO  c.x.xlrelease.XLReleaseBootstrapper - Reading configuration file from: /Users/ilx/work/xebialabs/xl-release/package/build/distributions/xl-release-6.0.0-server/conf/xl-release-server.conf
    *** We're going to attempt to start only the JCR repository with CRaSH shell enabled. ***
    ***      Changes that you save to the JCR repository may corrupt it permanently.      ***
    ***           It is HIGHLY recommended to make a backup before you proceed.           ***
    ***                 Are you sure you wish to continue?                                ***
    Please enter 'yes' if you wish to continue. [no] >

Type `yes` and press ENTER.

You will see a message similar to the following:

       _____     ________                 _______    ____ ____
     .'     `.  |        `.             .'       `. |    |    | 1.3.2
    |    |    | |    |    |  .-------.  |    |    | |    |    |
    |    |____| |    `   .' |   _|    |  .    '~_ ` |         |
    |    |    | |    .   `.  .~'      | | `~_    `| |         |
    |    |    | |    |    | |    |    | |    |    | |    |    |
     `._____.'  |____|____| `.________|  `._____.'  |____|____|

    Follow and support the project on http://www.crashub.org
    Welcome to localhost + !
    It is Thu Jul 28 16:28:16 CEST 2016 now
    %

### Connect to the repository

Now you can connect to the repository with the command `repo use`:

    % repo use
    identifier.stability : identifier.stability.indefinite.duration
    jcr.repository.name : Jackrabbit
    jcr.repository.vendor : Apache Software Foundation
    jcr.repository.vendor.url : http://jackrabbit.apache.org/
    jcr.repository.version : 2.12.1
    ....
    query.full.text.search.supported : true
    query.joins : query.joins.inner.outer
    query.stored.queries.supported : true
    query.xpath.doc.order : false
    query.xpath.pos.index : true
    write.supported : true

### Indicate which Jackrabbit workspace to use

After you connect to the repository, indicate the Jackrabbit workspace to use with the command `ws login -u jcr_admin -p jcr_admin default`:

    % ws login -u jcr_admin -p jcr_admin default
    Connected to workspace default
    %

### Navigate the repository

After you connect to the repository and indicate which workspace to use, you can navigate the repository and list or modify its contents:

    % ls
    /
    +-properties
    | +-jcr:mixinTypes: [rep:AccessControllable]
    | +-jcr:primaryType: rep:root
    +-children
      +-/jcr:system
      +-/rep:policy
      +-/Environments
      +-/Configuration
      +-/Applications
      +-/Infrastructure
      +-/Folders
      +-/$configuration
      +-/tasks

    %

    [//]: # ## Start the repository browser with XL Release
    [//]: #
    [//]: # You can start XL Release and the CRaSH shell Telnet server together as follows:
    [//]: #
    [//]: #     ./bin/run.sh
    [//]: #
    [//]: # After XL Release starts, connect to the CRaSH shell Telnet server:
    [//]: #
    [//]: #     > telnet 127.0.0.1 5000                                                                                                                                                                                                                       [16:17:36]
    [//]: #     Trying 127.0.0.1...
    [//]: #     Connected to localhost.
    [//]: #     Escape character is '^]'.
    [//]: #
    [//]: #        _____     ________                 _______    ____ ____
    [//]: #      .'     `.  |        `.             .'       `. |    |    | 1.3.2
    [//]: #     |    |    | |    |    |  .-------.  |    |    | |    |    |
    [//]: #     |    |____| |    `   .' |   _|    |  .    '~_ ` |         |
    [//]: #     |    |    | |    .   `.  .~'      | | `~_    `| |         |
    [//]: #     |    |    | |    |    | |    |    | |    |    | |    |    |
    [//]: #      `._____.'  |____|____| `.________|  `._____.'  |____|____|
    [//]: #
    [//]: #     Follow and support the project on http://www.crashub.org
    [//]: #     Welcome to localhost + !
    [//]: #     It is Thu Jul 28 16:38:21 CEST 2016 now
    [//]: #
    [//]: #     %
    [//]: #
    [//]: # Now you can issue the commands described above.
    [//]: #
    [//]: #
    [//]: #
    [//]: # **Note:** Repository browser tool starts jetty server on the same port as XebiaLabs product, ssh server on predefined port 2000 and telnet server on predefined port 5000.
    [//]: #
    [//]: # **Note:** SSH server is not usable at the time of this writing.

## Get more information about commands

To see information about CRaSH commands, execute `man <COMMAND_NAME>`:

    % man ls
    NAME
           ls - list the content of a node

    SYNOPSIS
           ls [-d | --depth] [-h | --help] <path>

    STREAM
           ls <java.lang.Void, java.lang.Object>

    DESCRIPTION
           The ls command displays the content of a node. By default it lists the content of the
           current node, however it also accepts a path argument that can be absolute or relative.

           [/]% ls
           /
           +-properties
           | +-jcr:primaryType: nt:unstructured
           | +-jcr:mixinTypes: [exo:owneable,exo:privilegeable]
           | +-exo:owner: '__system'
           | +-exo:permissions: [any read,*:/platform/administrators read]
           +-children
           | +-/workspace
           | +-/contents
           | +-/Users
           | +-/gadgets
           | +-/folder

    PARAMETERS
           [-d | --depth]
               The depth of the printed tree

           [-h | --help]
               Display this help message

           <path>
               The path of the node content to list


    %
