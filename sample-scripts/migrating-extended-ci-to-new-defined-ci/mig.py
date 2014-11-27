# Levent Tutar
# ltutar@xebia.com

# If the deployable is not deployed,
# then you can delete it and recreate it with the new type

# If the deployable is deployed but not selected as member of any environment,
# then you can delete the deployed,
# then delete the deployable,
# then recreate the deployable with the new type
# then recreate the deployed with the new type

# If the deployable is deployed and selected as member of any environment,
# then you can delete the deployed,
# then delete the deployable,
# then recreate the deployable with the new type
# then recreate the deployed with the new type
# then add the deployed as member of the environment

import java
import sys
from os import path


oldType="jbossdm.QueueSpec"
oldDeployedType="jbossdm.Queue"
newType="jbossdmx.QueueSpec"
newDeployedType="jbossdmx.Queue"


deployedApps = repository.search('udm.DeployedApplication')
print ""
print "The deployed applications are: " + str(deployedApps)

oldDeployeds = set(repository.search(oldDeployedType)) - set(repository.search(newDeployedType))
print ""
print "The following are of old deployed type " + oldDeployedType + ":"
print oldDeployeds

# The following array contains the deployeds with the new CI type
deployedNewDeployedsBuffer = []
# Let's instantiate all the new deployeds and store them in the array
for i in oldDeployeds:
  readDeployed = repository.read(i)
  new_lp_deployed = factory.configurationItem(readDeployed.id, newDeployedType, readDeployed._ci.syntheticProperties)
  deployedNewDeployedsBuffer.append(new_lp_deployed)

lps = set(repository.search(oldType)) - set(repository.search(newType))
print ""
print "The following are of old deployable type " + oldType + ":"
print lps

print ""

# Let's create an empty array to store our deployeds for later
deployedBuffer = []
# Let's create an empty array of arrays to store our deployeds and its container for later
deployedAppsBuffer = []

# Let's create an empty array to store our deployables for later
new_lp_buffer = []

for lpRef in lps:
    print ""
    print "Looping for old deployable type: " + lpRef
    lp=repository.read(lpRef)
    new_lp = factory.configurationItem(lp.id, newType, lp._ci.syntheticProperties)
    # If needed, add more properties to new_lp here.
    new_lp_buffer.append(new_lp)
    deploymentPackageId = path.dirname(lp.id)
    for d in deployedApps:
        print "  Looping for deployed application: " + d
        dApp = repository.read(d)
        if dApp.version == deploymentPackageId:
            print "    Deployed application " + d + " has our " + dApp.version
            deployeds = dApp.values['deployeds']
            # Let's make a copy of the deployeds
            deployedsLeft = deployeds[:]
            isDeployed = "false"
            print "    isDeployed: " + isDeployed
            for deployed in deployeds:
                readDeployed = repository.read(deployed)
                # Let's find out the deployeds that are deployed member of the environment
                if readDeployed.type == oldDeployedType:
                    readDeployedDeployable = repository.read(readDeployed.id)
                    if readDeployedDeployable.deployable == lpRef:
                        isDeployed = "true"
                        print "      isDeployed: " + isDeployed
                        print "      Deployed application " + d + " has " + readDeployed.id + " as deployed with the old deployed type " + readDeployed.type
                        deployedsLeft.remove(deployed)

                        # We'll create the new deployed
                        new_lp_deployed = factory.configurationItem(readDeployed.id, newDeployedType, readDeployed._ci.syntheticProperties)
                        deployedBuffer.append(new_lp_deployed)
                        print "      deployedBuffer updated: " + str(deployedBuffer)
                        deployedNewDeployedsBuffer.remove(new_lp_deployed)
                        print "      deployedNewDeployedsBuffer updated: " + str(deployedNewDeployedsBuffer)
                        deployedAppsBuffer.append([new_lp_deployed, d])
                        print "      deployedAppsBuffer updated: " + str(deployedAppsBuffer)

            if isDeployed == "true":
                print "    deployeds left " + str(deployedsLeft)
                # Let's remove the deployeds that have our oldType
                dApp.values['deployeds'] = deployedsLeft
                dApp.update()
                print "    deployeds left are updated"

print "We are outside the for loop with deployables"
print "targeted deployedBuffer: " + str(deployedBuffer)
print "untargeted deployedNewDeployedsBuffer: " + str(deployedNewDeployedsBuffer)

# Let's delete the old deployeds under the infrastructure.
print ""
print "Let's delete the targeted old deployeds under the infrastructure: " + str(deployedBuffer)
for i in deployedBuffer:
    repository.delete(i.id)
    print i.id + " is deleted"


# Let's delete the old deployeds under the infrastructure.
print ""
print "Let's delete the untargeted old deployeds under the infrastructure: " + str(deployedNewDeployedsBuffer)
for i in deployedNewDeployedsBuffer:
    repository.delete(i.id)
    print i.id + " is deleted"

print ""
print "Let's delete old deployable types:"
for i in lps:
    repository.delete(i)
    print i + " is removed"

print ""
print "Let's create the new deployable types:"
for new_lp in new_lp_buffer:
    repository.create(new_lp)
    new_lp = repository.read(new_lp.id)
    print new_lp.id + " is created and has the type " + new_lp.type

print ""
print "Let's update the deployeds of the deployed application of the environment:"
print str(deployedAppsBuffer)
for i in deployedAppsBuffer:
    repository.create(i[0])
    print i[0].id + " is created"
    dAppTemp = repository.read(i[1])
    deployedTemp = dAppTemp.values['deployeds']
    deployedTemp.add(i[0].id)
    repository.update(dAppTemp)
    print "The deployeds are: " + str(deployedTemp)

print ""
print "Let's create the deployeds that are not targeted to an environment but exist"
print str(deployedNewDeployedsBuffer)
for i in deployedNewDeployedsBuffer:
    repository.create(i)

lps = set(repository.search(oldDeployedType)) - set(repository.search(newDeployedType))
print ""
print "The following are of old deployed type " + oldDeployedType + ":"
print lps
print ""
lps = set(repository.search(oldType)) - set(repository.search(newType))
print ""
print "The following are of old deployable type " + oldType + ":"
print lps
print ""


