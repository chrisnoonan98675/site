from com.xebialabs.deployit.plugin.api.reflect import Type 
def findApp(appNameCriteria):
  
  apps = proxies.getRepository().query(Type.valueOf("udm.Application"), None, appNameCriteria, None, None, 0, -1)
  apps = [str(appName.id) for appName in apps if appName.id.startswith("Application")]
  if apps:
    print "Found the following applications matching criteria %s" % (appNameCriteria)
    print apps
  else:
    print "No applications found for search criteria %s " % appNameCriteria


def findHost(hostNameCriteria,hostType):
  
  hosts = proxies.getRepository().query(Type.valueOf(hostType), None, hostNameCriteria, None, None, 0, -1)
  host = [str(hostName.id) for hostName in hosts if hostName.id.startswith("Infrastructure")]
  if host:
    print "Found the following hosts matching criteria %s" % (hostNameCriteria)
    print host
  else:
    print "No hosts found for search criteria %s " % hostNameCriteria

def findEnvironment(envNameCriteria):
  
  envs = proxies.getRepository().query(Type.valueOf("udm.Environment"), None, envNameCriteria, None, None, 0, -1)
  env = [str(env.id) for env in envs if env.id.startswith("Environments")]
  if env:
    print "Found the following Environments matching criteria %s" % (envNameCriteria)
    print env
  else:
    print "No Environments found for search criteria %s " % envNameCriteria
