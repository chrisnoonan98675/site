---
title: Periodically clean the repository with a CLI script
categories:
- xl-deploy
tags:
- repository
- cli
- script
---

When using a continuous integration system like Jenkins to do automatic deployments in XL Deploy, your repository may fill up quickly with build versions of your application. You will need some periodical cleanup to keep the XL Deploy repository at a manageable size.

At XebiaLabs, we use the XL Deploy [command line interface](http://docs.xebialabs.com/releases/latest/xl-deploy/climanual.html) (CLI) script below to do this job for us. It allows you to delete packages older than a certain number of days and that match a certain text or that reside in a certain directory. For example, you can tell it to 'delete all SNAPSHOT versions older than 7 days from Applications/PetClinic'.

Because deletion is a destructive operation, you may want to check what will be deleted, so we added a 'dry run' option that serves as a safety cap. With this option enabled, the script simply prints the packages that would be deleted, but doesn't actually delete them.

## Script

	from java.util import Calendar
	import sys

	def isWantedApplication(packageId):
	  if application is None:
		return True
	  if packageId.startswith(application):
		return True
	  return False

	if len(sys.argv) < 4:
	  print "Usage: cleanup.py [days history to keep] [version marker to remove] [dry run?]"
	  exit(1)

	days = sys.argv[1]
	versionMarker = sys.argv[2]
	dryRun = True
	if (sys.argv[3] == "False"):
	  dryRun = False

	if len(sys.argv) == 5:
	  application = sys.argv[4]
	else:
	  application = None

	now = Calendar.getInstance()
	now.add(Calendar.DAY_OF_MONTH, int(days) * -1)
	print "Base date is ",now.getTime()
	allPackages = repository.search('udm.DeploymentPackage', now)
	deletedPackage = 0
	missDeletedPackage = 0

	for packageId in allPackages:
	  if not isWantedApplication(packageId):
		continue
	  package = repository.read(packageId)
	  version=packageId.split('/')[-1]
	  if versionMarker in version:
		if bool(dryRun):
		  print "Dry run - will delete package", package.id
		  deletedPackage = deletedPackage + 1
		else:
		  print "Deleting package", package.id
		  try:
			repository.delete(package.id)
			deletedPackage = deletedPackage + 1
		  except:
			missDeletedPackage = missDeletedPackage +1

	print len(allPackages),"\tpackage(s) in the repository imported before", now.getTime()
	print deletedPackage,"\tdeleted package(s)"
	print missDeletedPackage,"\tcandidate package(s) but not deleted because it's still referenced"
	print "done"

## Usage

Save the script on a host that has the XL Deploy CLI installed, and then invoke it with the command:

    bin/cli.sh -f [path to cleanup script] [number of days] [verion marker] [dryrun:True or False] [Path in repository]

For example, to delete all PetClinic snapshot versions older than a week:

    bin/cli.sh -f cleanup-repository.py 7 SNAPSHOT False Applications/PetClinic

## In Jenkins

To automate clean-up, we created a job in Jenkins:

1. Install the XL Deploy CLI on the same server where XL Deploy is running.
1. Install the `cleanup-repository.py` script in the XL Deploy CLI directory.
1. Ensure that the Jenkins server can log into to the XL Deploy host using SSH. (Alternatively, install the CLI on the Jenkins server.)
1. Create a Jenkins job called 'Clean up XL Deploy repository'.
1. As the **Build Trigger**, use **Build periodically** and use a pattern like `0 0 * * *`, which will trigger the clean-up script every day at midnight.
1. As the **Build** step, add an **Execute shell command** for each application you would like to clean up. For example:

       ssh user@deployit "deployit-3.9.2-cli/bin/cli.sh -username admin -password XXXXXX -f ~/cleanup-repository.py -q 7 SNAPSHOT False Applications/PetClinic"

This means that the cleanup runs unattended, and you can find the logs and status of the jobs in the Jenkins console at your convenience.
