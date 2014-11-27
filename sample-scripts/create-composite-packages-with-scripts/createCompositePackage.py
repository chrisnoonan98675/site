# Script used to automatically create composite packages from the last (sorted) available version
# Nicolas Bouvrette, nbouvrette@expedia.com, December 2013

import sys
import datetime

#
# Functions
#

def sorted_copy(alist):
		indices = map(_generate_index, alist)
		decorated = zip(indices, alist)
		decorated.sort()
		return [ item for index, item in decorated ]

def _generate_index(str):
		"""
		Splits a string into alpha and numeric elements, which
		is used as an index for sorting"
		"""
		#
		# the index is built progressively
		# using the _append function
		#
		index = []
		def _append(fragment, alist=index):
				if fragment.isdigit(): fragment = int(fragment)
				alist.append(fragment)

		# initialize loop
		prev_isdigit = str[0].isdigit()
		current_fragment = ''
		# group a string into digit and non-digit parts
		for char in str:
				curr_isdigit = char.isdigit()
				if curr_isdigit == prev_isdigit:
						current_fragment += char
				else:
						_append(current_fragment)
						current_fragment = char
						prev_isdigit = curr_isdigit
		_append(current_fragment)
		return tuple(index)

def returnLastVersion(applicationName):
		versions = repository.search('udm.DeploymentPackage', applicationName)
		sortedVersions = sorted_copy(versions)
		if len(sortedVersions) > 0:
				return sortedVersions[len(sortedVersions)-1]
		else:
				return None

#
# Display usage syntax
#

if len(sys.argv) < 3:
		print ''
		print 'Usage: ' + sys.argv[0] + ' [compositePackage] [Application1] [Application2] [ApplicationN]'
		print ''
		print 'Creates a composite package from at least 2 applications.'
		print ''
		print '[compositePackage] - Location of your desired composite package.'
		print '[Application1] - First application location, part of the composite package (latest version will be picked).'
		print '[Application2] - Second application location, part of the composite package (latest version will be picked).'
		print '[ApplicationN] - Can be any number of application inside the same package.'
		print ''
		print 'The following example would create a composite package off 2 different applications:'
		print sys.argv[0] + ' Application-Composite Application1 Application2'
		print ''
		quit

#
# Run program if syntax is OK
#

# Display start time and arguments
print str(datetime.datetime.now()) + ' - Executing ' + sys.argv[0]

applications = ''

for a in range(len(sys.argv)):
		if a > 1:
				if len(applications):
						applications = applications + ', '
				applications = applications + sys.argv[a]

print 'Composite: ' + sys.argv[1] + '  Applications: ' + applications
print ''

# Find or create the Composite Package
try:
		repository.read('Applications/' + sys.argv[1])
except:
		print 'Composite package was not found... trying to create "Applications/' + sys.argv[1] + '".'
		try :
				compositePackage = repository.create(factory.configurationItem('Applications/' + sys.argv[1], 'udm.Application'))
		except:
				print 'Creation failure. Make sure that the location of your composite package already exists in the repository.'
		else:
				print 'Created "Applications/' + sys.argv[1] + '" successfully.'
else:
		print 'Found Composite package under "Applications/' + sys.argv[1] + '".'
		compositePackage = repository.read('Applications/' + sys.argv[1])

# Search for applications and versions
packages = []
for a in range(len(sys.argv)):
		if a > 1:
				application = returnLastVersion('Applications/' + sys.argv[a])
				if application is None:
						print 'Skipping "Applications/' + sys.argv[a] + '" since no version of the application could be found.'
				else:
						packages.append(application)

# Create Composite Package
if len(packages) == 0:
		print 'No packages version could be found. Skipping composite package creation.'
else:
		version = str(datetime.datetime.now()).replace(':', '').replace('-', '').replace(' ', '')
		print 'Creating composite package "' + str(compositePackage) + '/' + version + '".'
		compositePackageVersion = factory.configurationItem(str(compositePackage) + '/' + version, 'udm.CompositePackage')
		print 'Adding packages into "' + str(compositePackage) + '/' + version + '".'
		compositePackageVersion.packages = packages
		print 'Adding "' + str(compositePackage) + '/' + version + '" into the respository.'
		repository.create(compositePackageVersion)

print ''