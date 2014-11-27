#######################################################################
# NOTICE: This script is provided as a sample only, with no warranty 
# expressed or implied, and is not supported by XebiaLabs.
#######################################################################
import sys

packagePath = sys.argv.pop(1)
print "Importing '%s'" % (packagePath)
deployit.importPackage(packagePath)