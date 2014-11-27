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