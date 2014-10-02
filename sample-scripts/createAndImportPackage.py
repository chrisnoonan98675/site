import os
import subprocess
import sys
import tempfile
import time

#################################################################################
#
#  ---------------------------------------------------------------------------
#  THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
#  KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
#  IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR 
#  PURPOSE. THIS CODE AND INFORMATION ARE NOT SUPPORTED BY XEBIALABS.
#  ---------------------------------------------------------------------------
#
#  CLI script to create a package based on a provided manifest and publish it
#  to an XL Deploy server.
#  
#  ---------------------------------------------------------------------------
#  For updated information, refer to the article about this script at
#  http://docs.xebialabs.com/create-deployment-package-and-publish.html 
#  ---------------------------------------------------------------------------
#
#################################################################################

manifestpath = sys.argv[1]
contentspath = sys.argv[2]
# version = sys.argv[3]
version = time.strftime('%Y%m%d.%H%M%S', time.localtime())
print "Creating and importing package for version", version, "of", contentspath

(darfilehandle, darpath) = tempfile.mkstemp(suffix = '.dar')
print "Creating DAR file", darpath
darfilehandle.close()
os.chdir(contentspath)
subprocess.call(['jar', 'cf', darpath, '.'])

newmanifestdir = tempfile.mkdtemp()
newmanifestpath = os.path.join(newmanifestdir, 'deployit-manifest.xml')
print "Creating version-specific deployit-manifest.xml file", newmanifestpath

newmanifestfile = open(newmanifestpath, 'w')
manifestfile = open(manifestpath, 'r')
for line in manifestfile:
    line = line.replace("@@VERSION@@", version) 
    newmanifestfile.write(line)
newmanifestfile.close()
manifestfile.close()

print "Adding version-specific deployit-manifest.xml file to DAR file"
os.chdir(newmanifestdir)
subprocess.call(['jar', 'uf', darpath, 'deployit-manifest.xml'])

print "Removing deployit-manifest.xml file", newmanifestpath
os.remove(newmanifestpath)

print "Importing DAR file", darpath
deployit.importPackage(darpath)

print "Removing DAR file", darpath
os.remove(darpath)
