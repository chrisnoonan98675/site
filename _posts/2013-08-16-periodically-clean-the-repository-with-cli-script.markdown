---
title: Periodically cleaning the XL Deploy repository with a CLI script
categories:
- xl-deploy
tags:
- repository
- cli
---

When using a continuous integration system like Jenkins to do automatic deployments in XL Deploy, your repository may fill up quickly with build versions of your application. You will need some periodical cleanup to keep the XL Deploy repository at a manageable size.

At XebiaLabs, we use [this command-line interface (CLI) script](/sample-scripts/periodically-clean-the-repository-with-cli-script/cleanup-repository.py) to do this job for us. It allows you to delete packages older than a certain number of days and that match a certain text, or that reside in a certain directory. For example, you can tell it to "delete all SNAPSHOT versions older than seven days from Applications/PetClinic".

Because deletion is a destructive operation, you may want to revise what is about to be deleted, so we added a dry-run option that serves as a safety cap. With this option enabled, the script simply prints the packages that would be deleted, but doesn't actually delete them.

## Usage

Save the script on a host that has the XL Deploy CLI installed, and then invoke it with the command:

    bin/cli.sh -f [path to cleanup script] [number of days] [version marker] [dryrun:True or False] [Path in repository]

For example, to delete all PetClinic snapshot versions older than a week:

    bin/cli.sh -f cleanup-repository.py 7 SNAPSHOT False Applications/PetClinic

## In Jenkins

To have automatic cleanup, we have set up a scheduled job in Jenkins. 

Steps:

1. Install the XL Deploy CLI on the same server where XL Deploy is running.
1. Install the `cleanup-repository.py` script in the XL Deploy CLI directory
1. Ensure the Jenkins server can log into to the XL Deploy host using SSH. (Alternatively, install the CLI on the Jenkins server.)
1. Create a Jenkins job called *Clean up XL Deploy repository*.
1. As build trigger, use *Build periodically* and put in a pattern like `0 0 * * *` that will trigger the cleanup script every day at midnight.
1. As build step, add an execute shell command for each application you would like to clean up. For example:

        ssh user@deployit "deployit-3.9.2-cli/bin/cli.sh -username admin -password XXXXXX -f ~/cleanup-repository.py -q 7 SNAPSHOT False Applications/PetClinic"

This way the cleanup runs unattended, and we can find the logs and status of the jobs in the Jenkins console at our convenience.
