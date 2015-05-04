---
title: Introduction to the XL Release Nexus plugin
categories:
- xl-release
subject:
- Nexus plugin
tags:
- plugin
- nexus
---

The Nexus plugin is an XL Release plugin that allows XL Release to interact with Sonatype Nexus repositories. 
The plugin uses the Nexus Server REST API to communicate and fetch information from Nexus repositories.

## Features

* Trigger on published artifact

## Usage

In order to use the Nexus plugin, you need to create a **Nexus Server**. Nexus Servers are defined globally in the Configuration page.

![Nexus Server](../images/nexus-configuration-details.png)

A Nexus Server has the following properties:

* **Title**: The title of the server
* **Url**: The address where the Nexus application is reachable, e.g. http://domain:8081/nexus
* **Username**: The login user ID on the server
* **Password**: The login password on the server

### Trigger on published artifact

The trigger periodically polls a Nexus server for new versions of a specific artifact and triggers a release if a new version is published to the repository being watched.

Input properties:

* **Group ID**: Group ID of the artifact (required)
* **Artifact ID**: Artifact ID of the artifact (required)
* **Version**: Version of the artifact, supports resolving of 'LATEST'(default), 'RELEASE' and snapshot versions (e.g. '1.0-SNAPSHOT'). Specify 'LATEST' to trigger on both, release and snapshot versions of a particular artifact. With 'RELEASE' only release versions will be tracked. Triggering on re-deployments of release versions (e.g. 1.0.0) is not supported (required)

* **Packaging**: Packaging type of the artifact (e.g. 'jar', 'war')
* **Classifier**: Classifier of the artifact
* **Extension**: Extension of the artifact
* **Trigger On Initial Publish**: Should suppress error when artifact not found for the given GAV coordinates. Set this to true when artifact was never published to Nexus and you want to trigger a release on the initial publish. Default value is 'false'

* **Server**: Sonatype Nexus server to poll (required)
* **Repository ID**: Repository ID to watch, e.g. releases, snapshots, apache-snapshots (required)
* **Username**: Custom login Username to override global server configuration
* **Password**: Custom login Password to override global server configuration

Output properties:

* **Artifact Version**: Latest artifact version retrieved from Nexus repository
* **Artifact Base Version**: Latest artifact base version retrieved from Nexus repository. For Releases same as 'artifactVersion', for Snapshots base version (i.e. 1.0-SNAPSHOT) without additional qualifiers.
* **Artifact Snapshot Build Number**: Latest artifact snapshot build number retrieved from Nexus repository
* **Artifact Repository Path**: Artifact path relative to the selected Nexus repository
