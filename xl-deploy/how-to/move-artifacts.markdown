---
title: Move artifacts from the file system to a database
categories:
- xl-deploy
subject:
- Artifacts
tags:
- artifacts
- database
- migrate
since: XL Deploy 8.2.0
---

XL Deploy can be configured to store and retrieve artifacts in two local storage repository formats:

* `file`: The artifacts are stored on and retrieved from the file system.
* `db`: The artifacts are stored in and retrieved from a relational database management system (RDBMS).

XL Deploy can only use one local artifact repository at any time. The configuration option `xl.repository.artifacts.type` can be set to either `file` or `db` to select the storage repository.

## Moving artifacts

When XL Deploy starts, it checks if any artifacts are stored in a storage format that is not configured. If artifacts are detected, XL Deploy checks the `xl.repository.artifacts.allow-move` configuration option to see if the detected artifacts should be moved.

If `xl.repository.artifacts.allow-move` is set to the default `false` setting, XL Deploy does not start and throws an error, assuming that a mistake was made in the configuration. The configuration can be adjusted by either choosing another local artifact repository type, or by setting the `xl.repository.artifacts.allow-move` option to `true`. After the configuration change, you must restart XL Deploy.

If `xl.repository.artifacts.allow-move` is set to `true`, XL Deploy starts up and moves the artifacts to the configured local artifact repository. XL Deploy uses both the configured local artifact repository (e.g. “db”) and the not-configured local artifact repository (e.g. “file”) to retrieve artifacts during the move process. This makes the artifacts that are waiting to be moved, available to the system.

The process of migrating artifacts moves the data in small batches with pauses between every two batches. This allows the system to be used for normal tasks during the process.

### Handling errors and process restart

If an artifact cannot be moved because an error occurs, a report is written in the log file and the process continues.
When XL Deploy is restarted during the process of moving the artifacts, the startup sequence as described above will be re-executed. If the `xl.repository.artifacts.allow-move` option is set to `true`, the move process will start again. Any artifacts that failed during the previous run, will be re-processed.

When the move process has completed successfully and all artifacts have been moved, a report is written in the log file and the `xl.repository.artifacts.allow-move` option can be (re)set to `false`.
If the artifacts are moved from the file system, empty folders are left in the configured `xl.repository.artifacts.root`. These have no impact and can be deleted manually.

Files can remain on the file system, but are not detected as artifacts. This happens when files are no longer in use by the system, but have not been removed (example: files used to be part of application versions that are no longer used). The files can be removed after creating a backup.
