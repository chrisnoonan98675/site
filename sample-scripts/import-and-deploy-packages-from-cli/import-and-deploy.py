# requires deployments.py
def deployPackage(packagePath, targetEnvironment):
  print "Importing", packagePath
  deploymentPackage = deployit.importPackage(packagePath)
  deploymentPackagePath = deploymentPackage.id.split("/")
  appName = deploymentPackagePath[len(deploymentPackagePath) - 2]
  version = deploymentPackagePath[len(deploymentPackagePath) - 1]
  print "Upgrading application '%s' in environment '%s' to version '%s'" % (appName, targetEnvironment, version)
  startUpgrade(appName, version, targetEnvironment)
  return