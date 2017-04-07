---
title: Create custom tiles
no_index: true
---

In XL Release you can customize the [release dashboard](/xl-release/how-to/using-the-release-dashboard.html) by adding new tiles. This topic describes how to create custom tiles.

## Dashboard tile structure

A dashboard tile is based on the following concepts:

* It displays a snippet of specific information about a release on the release dashboard.
* It can be configured. You can define properties of a tile which can be set by a different user per each tile instance. One release dashboard can have several instances of the same tile type with different configurations. Example: [Task progress tile](/xl-release/concept/release-dashboard-tiles.html#task-progress)
* It contains a server side Jython script that prepares the information which will be displayed by the tile.
* It can be displayed in a full screen details view.

This tutorial describes the basic steps needed to create a **Latest commits** tile and uses all the concepts mentioned above. The purpose of the tile is to display latest commits from a GitHub repository. The tile will use a Jython script to fetch the commits and it will contain HTML and CSS code to display them in the XL Release GUI.

**Note** When creating XL Release plugins it is better to start writing code in the `XL_RELEASE_SERVER_HOME/ext/` directory than to package your plugin in a JAR file right away. This allows you to apply changes by refreshing your browser and does not require a restart of the XL Release server. The changes to Jython, HTML, CSS, and possibly to the JavaScript code are applied on every browser refresh. This results in a shorter development cycle. The changes to `synthetic.xml` will still require a restart of the XL Release server. When the code is ready, you can package it in a JAR file of your plugin.

## Configure a tile

To create a new tile, the first step is defining the tile model in `XL_RELEASE_SERVER_HOME/ext/synthetic.xml`:

    <type type="acme.LatestCommitsTile" label="Latest commits" extends="xlrelease.Tile">
        <!-- Path to the HTML template of the dashboard view of the tile -->
        <property name="uri" hidden="true" default="acme/LatestCommitsTile/latest-commits-tile-summary-view.html" />
        <!-- Title of the tile, this property is predefined in the parent type, but here you override its default value -->
        <property name="title" description="Display name of the tile" default="Latest commits"/>

        <!-- Add custom tile properties here -->
        <property name="repositoryId" category="input" required="true" default="octocat/Hello-World"
                  description="GitHub repository ID in format ':owner/:repo', for example 'octocat/Hello-World'." />
        <property name="branch" category="input" required="true" default="master"
                  description="Repository branch from which to list the commits." />
        <property name="accessToken" category="input" required="false" password="true"
                  description="GitHub OAuth token to use for authentication." />
    </type>

Explanation of the XML snippet example:

* The new tile is created by defining a new type extending from `xlrelease.Tile`. The type label appears in the *Add tile* dialog when you configure the release dashboard.
* The `uri` is a hidden property that must point to an HTML template used to display the tile on a dashboard. In XL Release all web resources are located under the `web/` folder on the classpath. In this example, you will create a file by path `web/acme/LatestCommitsTile/latest-commits-tile-summary-view.html` in the `XL_RELEASE_SERVER_HOME/ext` folder.
* There are multiple configuration properties defined for the tile. A property appears in the tile configuration screen if it has `category="input"`. You can also specify the description to explain how to use the property, and a default value.

In this example you can configure from which GitHub repository and from which branch you can get commits. You can also get an access token for authentication that is needed if the repository is not public. For more information about defining properties and what property types are supported, refer to [Create custom task types](/xl-release/how-to/create-custom-task-types.html#property-element).

When the `synthetic.xml` file is ready you can restart XL Release, open a dashboard of any release or template, click *Configure*, and then click *Add tile* to see the new **Latest commits** tile type .

If you add the new tile, the error `Status code: 404 GET static/6.2.0/acme/LatestCommitsTile/latest-commits-tile-summary-view.html` appears. This error occurs because you did not create the HTML template yet. If you click *Configure* on the tile, you can see the properties of the tile:

![Custom tile configuration](../images/custom-tile/custom-tile-configuration.png)

## Add an HTML template

As of XL Release 6.2.0 you can use [AngularJS 1.5.8](https://code.angularjs.org/1.5.8/docs/guide) code in the HTML templates of tiles. The version of AgularJS can be changed in future versions.

XL Release provides a default controller (JavaScript logic) that you can use. This controller triggers the  execution of the tile Jython script on the server side, gets the result, and makes it available for your HTML template.

You can create a file `XL_RELEASE_SERVER_HOME/ext/web/acme/LatestCommitsTile/latest-commits-tile-summary-view.html` with the following content:

    <!-- Tile uses the default AngularJS tile controller which comes from XL Release -->
    <!-- The variables of the controller will be referenced as vm.* within the <div> -->
    <div data-ng-controller="xlrelease.dashboard.XlrDefaultTileController as vm" class="latest-commits-tile-summary">

        <!-- Display text "Loading..." if vm.loading == true -->
        <div data-ng-if="vm.loading" style="text-align: center">Loading...</div>

        <!-- When vm.loading == false the data is ready to be displayed, accessible by vm.data -->
        <div data-ng-if="!vm.loading">
            {% raw %}{{ vm.data }}{% endraw %}
        </div>
    </div>

Explanation of the HTML snippet example:

* A `<div>` is defined and linked to the XL Release default tile controller: `<div data-ng-controller="xlrelease.dashboard.XlrDefaultTileController as vm">`. The controller can be accessed using an alias `vm` within the contents of this `<div>`.
* The controller has two fields: `loading` and `data`. When the tile appears on a page, the controller sets `loading = true` and calls the server to execute the tile Jython script. The controller inserts the result into the `data` property and sets `loading = false`.
* The `data-ng-if` attribute from AngularJS is used to display different snippets when `vm.loading` is `true` or `false`. In the beginning you can see the *Loading...* text.
* The `{% raw %}{{ vm.data }}{% endraw %}` expression from AngularJS is replaced with the value of the `data` property of the controller.

This is the first version of the HTML template for this tile. You can use it to test the Jython script by displaying the results on the page. After you create that Jython script and when the correct data is available, you can make modifications to the HTML template for a better display.

If you refresh your browser, you can see the error `Can't find script in class-path under : acme/LatestCommitsTile.py`. This occurs because the script is not yet created. Note that the new HTML template was loaded without the restart of the XL Release server.

## Create the Jython script

XL Release searches for the Jython script of a tile based on the tile type definition. In this example, XL Release will search for `acme/LatestCommitsTile.py` in the classpath.

Create the file `XL_RELEASE_SERVER_HOME/ext/acme/LatestCommitsTile.py` with following content:

    import json
    from xlrelease.HttpRequest import HttpRequest

    if accessToken is not None and accessToken.strip() == "":
        accessToken = None

    url = "/repos/%s/commits?sha=%s%s" % \
          (repositoryId, branch, "&access_token=" + accessToken if accessToken else "")

    print "Querying commits from GitHub API by URL %s" % url

    request = HttpRequest({"url": "https://api.github.com"})
    response = request.get(url, contentType='application/json')

    if response.status != 200:
        raise Exception("Request to GitHub failed with status %s, response %s" % (response.status, response.response))

    commits = json.loads(response.response)

    data = {
        "tile_configuration": {
            "repositoryId": repositoryId,
            "branch": branch
        },
        "commits": commits
    }

XL Release replaces the following attributes inside the tile Jython script:
* All input properties of the tile: you can see that `repositoryId`, `branch`, and `accessToken` are accessed directly in the script.
* Release or template where the tile is present as the `release` attribute. It can also be used to show the release related information in the tile (Not in this example).
* XL Release public API, for example: `releaseApi`, `phaseApi`.

This script example sends a request to the GitHub API to retrieve the latest commits on a specified repository or branch, optionally adding the access token as a request parameter. You can see what the API returns by executing the following [cURL](https://en.wikipedia.org/wiki/CURL) command:

    curl -v https://api.github.com/repos/octocat/Hello-World/commits?sha=test

The result of the tile script execution must be placed into the `data` attribute of the script. XL Release takes the `data` attribute from the script context and returns it back to the front end code of the tile. In this example, the `data` is a dictionary with the commits retrieved from GitHub and with the configuration properties of the tile. These will be used to display where the commits came from on the dashboard.

If you refresh the browser, you can see the raw data returned by the script in your tile:

![Latest commits tile raw data](../images/custom-tile/custom-tile-raw-data-displayed.png)

Note that XL Release server restart was not required in this case.

### Disable caching when creating a tile

Changes in the tile script are not always applied when refreshing the browser page. This is caused by the server side caching of the tile execution results. XL Release caches the tile script result per tile input parameters, so if multiple users open the same release, the external resource (GitHub in this case) will not be called multiple times. This increases the speed of dashboard rendering and protects external resources from getting too many requests from XL Release. For example: GitHub has a rate limit in the API, so without caching, the tile would not work for many users.

By default, script results are cached for 5 minutes. You can override caching settings in the type definition of the tile:

    <type type="acme.LatestCommitsTile" label="Latest commits" extends="xlrelease.Tile">
        <property name="cacheEnabled" kind="boolean" hidden="true" default="true" description="True if tile data should be cached."/>
        <property name="userSpecificCache" kind="boolean" hidden="true" default="false" description="True if tile data should be cached per user."/>
        <property name="expirationTime" kind="integer" hidden="true" default="300" description="Expiration time for a tile cache (in seconds)."/>
        <property name="maxCacheEntries" kind="integer" hidden="true" default="500" description="Maximum cache entries."/>
        ...

While developing a tile you can temporarily disable the caching using the `cacheEnabled` property. The script will be executed on every browser page refresh, so you can see your changes immediately. Make sure you enable the caching when the tile development is finished.

### Set default values for required variables

If an input property uses variables, those variables are replaced before being passed to the script. If a user sets the `branch` to `${myBranch}` in the tile configuration, and the variable `${myBranch}` has the value "master" in the release, you can see `branch == "master"` in the tile script. For a template, the default value of the variable is used.

If a tile property contains a required variable which does not have a value, the execution of the tile script fails with an error. This occurs less frequently in a release dashboard, because usually the required variables are set when creating a release.

In a template a required variable can be empty more often. If you want to use the dashboard, it is recommended to set the default values for these variables in templates.

## Display the data

You can enhance the `latest-commits-tile-summary-view.html` template to display the data. Update the contents using the following example:

    <!-- Import the custom stylesheet to the page -->
    <link rel="stylesheet" href="static/0/acme/LatestCommitsTile/latest-commits-tile.css" type="text/css">

    <div data-ng-controller="xlrelease.dashboard.XlrDefaultTileController as vm" class="latest-commits-tile-summary">
        <div data-ng-if="vm.loading" style="text-align: center">Loading...</div>

        <div data-ng-if="!vm.loading">
            <h4>Latest commits in {% raw %}{{ vm.data.tile_configuration.repositoryId }}{% endraw %}/{% raw %}{{ vm.data.tile_configuration.branch }}{% endraw %}:</h4>
            <ul>
                <!-- Iterate through "vm.data.commits", generating a new <li> for each commit -->
                <li data-ng-repeat="commitData in vm.data.commits" class="commit">
                    <!-- Print the commit message, stripping it to maximum of 40 characters -->
                    <span class="commit-message">{% raw %}{{ commitData.commit.message | limitTo : 40 }}{% endraw %}</span>
                    <span class="commit-author">({% raw %}{{ commitData.commit.author.name }}{% endraw %})</span>
                </li>
            </ul>
        </div>
    </div>

The first line of the template imports custom CSS to the page to keep the styling separate from the HTML content. You can create the stylesheet file later.

The template also contains a list of elements each representing one commit. In AngularJS you can iterate through the data and repeatedly display an HTML element using the attribute `data-ng-repeat="commitData in vm.data.commits"`. For each commit a `<li>` element is generated, and within each of them a specific commit can be accessible as `{% raw %}{{ commitData }}{% endraw %}`.

You can create a file `XL_RELEASE_SERVER_HOME/ext/web/acme/LatestCommitsTile/latest-commits-tile.css` using the following content:

    .latest-commits-tile-summary ul {
        margin-left: 5px;
    }
    .latest-commits-tile-summary .commit {
        padding: 2px 0;
        list-style-type: none;
    }
    .latest-commits-tile-summary .commit .commit-author {
        font-size: smaller;
    }

If you refresh the page, the tile looks like this:

![Latest commits tile summary view](../images/custom-tile/custom-tile-summary-view.png)

## Display data in details view

The content box of a dashboard tile is small and can be expanded only horizontally. To allow a user to see more information, you can define the details view of a tile to match the size of a dashboard. You can switch from the summary view to the details view by clicking anywhere on a tile.

The details view is enabled when you override the `detailsUri` property of your tile with a non-empty default value:

    <type type="acme.LatestCommitsTile" ...>
        ...
        <!-- Path to the HTML template of the details view of the tile -->
        <property name="detailsUri" hidden="true" default="acme/LatestCommitsTile/latest-commits-tile-details-view.html" />
        ...

If you restart XL Release server, you can click on the tile to go the details view. You can create the HTML template for this view in `XL_RELEASE_SERVER_HOME/ext/web/acme/LatestCommitsTile/latest-commits-tile-details-view.html`:

    <div data-ng-controller="xlrelease.dashboard.XlrDefaultTileController as vm" class="latest-commits-tile-details">
        <div data-ng-if="!vm.loading">
            <h3>Latest commits in repository {% raw %}{{ vm.data.tile_configuration.repositoryId }}{% endraw %}, branch {% raw %}{{ vm.data.tile_configuration.branch }}{% endraw %}</h3>

            <table class="table table-rounded table-striped">
                <thead>
                <tr>
                    <th>User</th>
                    <th>Message</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <tr data-ng-repeat="commitData in vm.data.commits">
                    <td><a href="{% raw %}{{commitData.author.html_url}}{% endraw %}" target="_blank_">{% raw %}{{ commitData.commit.author.name }}{% endraw %}</a></td>
                    <td><a href="{% raw %}{{commitData.html_url}}{% endraw %}" target="_blank_">{% raw %}{{ commitData.commit.message }}{% endraw %}</a></td>
                    <td>{% raw %}{{ commitData.commit.author.date | date }}{% endraw %}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div data-ng-if="vm.loading" style="text-align: center">Loading...</div>
    </div>

As in the summary view, this template also uses the AngularJS controller to fetch the same data from a Jython script and make it available in the HTML template. The only difference is that more data is displayed on the page using this view.

The data is rendered into a `<table>` element. The styling used is from the [Bootstrap CSS](http://getbootstrap.com/css/#tables) library which is available on XL Release pages. As per XL Release 6.2.0 the version of Bootstrap CSS is 3.3.6, with the possibility to change it in future versions.

To see the latest commits rendered as a table, refresh the page and click on the tile.

![Latest commits tile details view](../images/custom-tile/custom-tile-details-view.png)
