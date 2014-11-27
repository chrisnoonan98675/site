# XL Deploy CLI script to deploy a composite package in groups
# 2014-05-01 - Tom Batchelor
# 2014-06-05 - Tom Batchelor - Update to use Composites of Composites as the groups
# 2014-06-10 - Tom Batchelor - Minor refactoring and addition of documenation items, added exit code
# Version 3
#
#
# Usage
#
# cli.sh -host <XLDeployHost> -username <username> -password <password> -f $PWD/compositeGroupDeployer.py -- -p <package> -e <environment>
#
# Example execution:
#
# cli.sh -username admin -password deploy -source /Users/tom/Documents/Egnyte/Shared/XebiaLabs/POC/ATP\ \(Airline\ Tariff\ Publishing\ Company\)/CompositDeployScript/compositeGroupDeployer.py -- -p "Composite/Test_Package" -e "WAS/WAS8ND"
#
# Package: Composite/Test_Package
# Environment: WAS/WAS8ND
# Group: ABE
# Deploying:
# /Environments/WAS/WAS8ND
# PetClinic-ear
# Applications/PetClinic-ear/1.0
# Group: BRIAN
# Deploying:
# /Environments/WAS/WAS8ND
# PetPortal
# Applications/PetPortal/1.0
# Deployment of: /Applications/Composite/Test_Package to environment: /Environments/WAS/WAS8ND completed successfully

import collections, time, getopt, sys


##############################################################
#
# Safely read a CI from XL Deploy. Return None if the ci does
# no exist
#
# Param - id - string - Repository path to the CI
#
# Returns - ci - ConfigurationItem - CI or None if doesn't exist
#
##############################################################
def readCi(id):
    try:
        return repository.read(id)
    except:
        return None

##############################################################
#
# Prints output from all steps of a task, either running or complete
# to the console
#
# Param - taskInfo - TaskInfo - TaskInfo object
# Param - verbose - boolean - Verbose output or not
#
# Returns - No return value
#
##############################################################
def printSteps(taskInfo, verbose=False):
    for i in range(taskInfo.nrOfSteps):
        info = tasks.step(taskInfo.id, i+1)
        if info.state == 'PENDING':
            print "%s: %s %s [NOT EXECUTED]" % (str(i+1), info.description, info.state)
        else:
            print "%s: %s %s %s %s" % (str(i+1), info.description, info.state, info.startDate, info.completionDate)
            if verbose:
                print info.log
        print

##############################################################
#
# Returns the Application Name from a package path
#
# Param - package - string - Repository path to the package
#
# Returns - appName - string - Application name
#
##############################################################
def getAppFromPackagePath(package):
    return package.rpartition('/')[0].rpartition('/')[2]

##############################################################
#
# Asynchronously starts a deployment to an environemnt. This function
# automatically performs and Initial or an Upgrade depending
# on the state of the target environment.
#
# Param - deployEnv - string - Repository path to the deployment
#                           environment
# Param - package - string - Repository path to the deployment
#                           package
#
# Returns - taskID - string -  Task ID for the executing task
#
##############################################################
def asyncDeployUpdate(deployEnv, package):
    appName = getAppFromPackagePath(package)
    
    # Read Environment
    environment = repository.read(deployEnv)
    print
    print 'Deploying:'
    print deployEnv
    print appName
    print package
    # Deployment
    existingDeployment = readCi("%s/%s" % (environment.id, appName))
    if existingDeployment is not None:
        # Upgrade
        print 'Performing upgrade'
        deploymentRef = deployment.prepareUpgrade(package, existingDeployment.id)
        deploymentRef = deployment.prepareAutoDeployeds(deploymentRef)
    else:
        # Initial
        print 'Performing initial deployment'
        deploymentRef = deployment.prepareInitial(package,environment.id)
        deploymentRef = deployment.generateAllDeployeds(deploymentRef)
    deploymentRef = deployment.validate(deploymentRef)
    taskID = deployment.deploy(deploymentRef).id
    deployit.startTask(taskID)
    return taskID

##############################################################
#
# Check if all taskID is a list have completed. Once completed
# returns the lists of successes and failures
#
# Param - running_tasks - list - List of taskIDs of running tasks
#
# Returns - successful - list - List of taskIDs of successful tasks
# Returns - failed - list - List of taskIDs of failed tasks
#
##############################################################
def checkDeploymentCompletion(running_tasks):
    successful = []
    failed = []
    while len(running_tasks) != 0:
        for taskID in running_tasks:
            status = deployit.retrieveTaskInfo(taskID).state
            if status == 'EXECUTED':
                taskInfo = deployit.retrieveTaskInfo(taskID)
                print "Final task state: %s after %s of %s steps" % (taskInfo.state, min(taskInfo.currentStepNr + 1, taskInfo.nrOfSteps), taskInfo.nrOfSteps)
                printSteps(taskInfo, True)
                tasks.archive(taskID)
                successful.append(taskID)
                running_tasks.remove(taskID)
            elif status == 'STOPPED':
                taskInfo = deployit.retrieveTaskInfo(taskID)
                printSteps(taskInfo, True)
                print "Final task state: %s after %s of %s steps" % (taskInfo.state, min(taskInfo.currentStepNr + 1, taskInfo.nrOfSteps), taskInfo.nrOfSteps)
                deployit.cancelTask(taskID)
                failed.append(taskID)
                running_tasks.remove(taskID)
        time.sleep(5)
    return successful, failed

##############################################################
#
# Will start the deployment of muliple packages in parallel to
# a target envrionment
#
# Param - packageName - string - Repository path a composite
#                               package containing the candiate
#                               packages for this group
# Param - environmentName - string - Repository path to the
#                           deployment environment
#
# Returns - current_tasks - string - TaskIDs of the running tasks
#
##############################################################
def deploy_composite(packageName, environmentName):
    composite = repository.read(packageName)
    childNames = composite.packages
    current_tasks = []
    for childName in childNames:
        child = repository.read(childName)
        taskID = asyncDeployUpdate(environmentName, child.id)
        current_tasks.append(taskID)
    return current_tasks

##############################################################
#
# MAIN
#
##############################################################

# Vars
packageName = None
environmentName = None

# Parse out arguments
try:
    opts, args = getopt.getopt(sys.argv[1:],'hp:e:',['package=','environment='])
except getopt.GetoptError:
    print 'cli.sh -host <XLDeployHost> -username <username> -password <password> -f $PWD/compositeGroupDeployer.py -- -p <package> -e <environment>'
    sys.exit(2)
for opt, arg in opts:
    if opt == '-h':
        print 'cli.sh -host <XLDeployHost> -username <username> -password <password> -f $PWD/compositeGroupDeployer.py -- -p <package> -e <environment>'
        sys.exit(-1)
    elif opt in ("-p", "--package"):
        packageName = arg
    elif opt in ("-e", "--environment"):
        environmentName = arg

# Print Vars
print 'Package: ' + packageName
print 'Environment: ' + environmentName

# Add roots to params
packageName = 'Applications/' + packageName
environmentName = 'Environments/' + environmentName

# Read package and create groups
comp_app = repository.read(packageName)
groupNames = comp_app.packages

#Deploy groups. Group members are in parallel to each other, each group is sequential
successState = True
for groupName in groupNames:
    print
    print "Starting deployment group: " + groupName
    running_tasks = deploy_composite(groupName, environmentName)
    #Check if worked.
    successful, failed = checkDeploymentCompletion(running_tasks)
    if len(failed) != 0:
        print 'Tasks failed in group: ' + groupName
        print 'Failed task IDs are:'
        print failed
        successState = False
        break
    print
    print 'Deployment group: ' + groupName + ' complete.'

if successState == True:
    print 'Deployment of: ' + packageName + ' to environment: ' + environmentName + ' completed successfully'
    sys.exit(0)
else:
    print 'Deployment of: ' + packageName + ' to environment: ' + environmentName + ' did NOT completed successfully'
    sys.exit(1)
