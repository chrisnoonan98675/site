---
title: Trigger a release from a Nexus update
---

The XL Release Nexus trigger plugin allows XL Release to interact with Sonatype Nexus repositories. The plugin uses the Nexus Server REST API to communicate and fetch information from Nexus repositories.

A Nexus trigger periodically polls a Nexus server for new versions of a specific artifact and triggers a release if a new version is published to the repository being watched.

## Compatibility

The plugin is compatible with Nexus 2. The plugin does not support Nexus 3.0.0.

## Features

* Periodically polls a Nexus repository
* Triggers a release when an artifact is published

## Set up a Nexus server

To set up a Nexus server:

1. In XL Release, go to **Settings** > **Configuration** and click **Add Repository** under **Nexus: Server**.
2. In the **Title** box, enter the name of the Nexus server.
3. In the **URL** box, enter address where the server is reachable (for example, `http://domain:8081/nexus`).
4. In the **Username** and **Password** boxes, enter the server log-in user ID and password.
5. Click **Save** to save the server.

    ![Nexus Server](../images/nexus-configuration-details.png)

## Add a Nexus trigger to a template

To create a Nexus trigger:

1. Add a trigger to the template, as described in [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).
2. In the **Group Id** box, enter the group ID of the artifact.
3. In the **Artifact Id** box, enter the artifact ID of the artifact.
4. In the **Version** box, enter the version of the artifact.

    Here, you can use `LATEST` (default), `RELEASE`, and snapshot versions (such as `1.0-SNAPSHOT`). Specify `LATEST` to trigger on both release and snapshot versions of a particular artifact. With `RELEASE`, only release versions will be tracked. Triggering on redeployments of release versions (such as 1.0.0) is not supported.

5. In the **Packaging** box, optionally enter the packaging type of the artifact (such as `jar` or `war`).
6. In the **Classifier** box, optionally enter the classifier of the artifact.
7. In the **Extension** box, optionally enter the artifact's extension.
8. If you want to suppress errors when an artifact is not found for the given GAV coordinates, select **Trigger On Initial Publish**. Select this option when the artifact was never published to Nexus and you want to trigger a release on the initial publish.
9. In the **Username** and **Password** boxes, enter the log-in user ID and password to use to connect to the server.  If set, these will override the credentials defined in the Nexus server configuration.
10. Finish saving the trigger, as described in  [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).

## Output properties

The output properties of the Nexus trigger are:

* **Artifact Version**: Latest artifact version retrieved from the Nexus repository
* **Artifact Base Version**: Latest artifact base version retrieved from the Nexus repository; for releases, this is the same as the `artifactVersion`, while for snapshots (such as `1.0-SNAPSHOT`), it excludes the additional qualifiers
* **Artifact Snapshot Build Number**: Latest artifact snapshot build number retrieved from the Nexus repository
* **Artifact Repository Path**: Artifact path relative to the selected Nexus repository
