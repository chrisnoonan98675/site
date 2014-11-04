# this utility function dumps the detail of task
#  id taskId
#  detail if > 0, the dump shows the step log too.
def dumpTask(id, detail = 0):
  t = deployit.retrieveTaskInfo(id)
  print 'Task --', t.label, '--'
  print 'State: ',t.state, t.currentStepNr,'/',t.nrOfSteps
  for i in range(t.nrOfSteps):
    response = proxies.taskRegistry.getStepInfo(id,i+1,None)
    stepinfo = response.entity
    log = stepinfo.log
    print id,'#',i+1,'\t',stepinfo.state,'\t',stepinfo.description
    if detail > 0: 
      print log

