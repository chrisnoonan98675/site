#!/bin/sh
#
# Shell script to start the XL Deploy Server
#

absdirname ()
{
  _dir="`dirname \"$1\"`"
  cd "$_dir"
  echo "`pwd`"
}

resolvelink() {
  _dir=`dirname "$1"`
  _dest=`readlink "$1"`
  case "$_dest" in
  /* ) echo "$_dest" ;;
  *  ) echo "$_dir/$_dest" ;;
  esac
}

# Get Java executable
if [ -z "$JAVA_HOME" ] ; then
  JAVACMD=java
else
  JAVACMD="${JAVA_HOME}/bin/java"
fi

# Get JVM options
if [ -z "$DEPLOYIT_SERVER_OPTS" ] ; then
  DEPLOYIT_SERVER_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
fi

# Get logging-related options
if [ -z "$DEPLOYIT_SERVER_LOG_OPTS" ] ; then
  DEPLOYIT_SERVER_LOG_OPTS="-Dlogback.configurationFile=conf/logback.xml -Dderby.stream.error.file=log/derby.log"
fi

# Get XL Deploy server home dir
if [ -z "$DEPLOYIT_SERVER_HOME" ] ; then
  self="$0"
  if [ -h "$self" ]; then
    self=`resolvelink "$self"`
  fi
  BIN_DIR=`absdirname "$self"`
  DEPLOYIT_SERVER_HOME=`dirname "$BIN_DIR"`
elif [ ! -d "$DEPLOYIT_SERVER_HOME" ] ; then
  echo "Directory $DEPLOYIT_SERVER_HOME does not exist"
  exit 1
fi

cd "$DEPLOYIT_SERVER_HOME"

# Build XL Deploy server classpath
DEPLOYIT_SERVER_CLASSPATH='conf:ext'
for each in `ls hotfix/*.jar lib/*.jar plugins/*.jar 2>/dev/null`
do
  if [ -f $each ]; then
    DEPLOYIT_SERVER_CLASSPATH=${DEPLOYIT_SERVER_CLASSPATH}:${each}
  fi
done

ls plugins/* > /dev/null 2>&1
if [ $? -eq 0 ]; then
  for expandedPluginDir in `ls plugins/*`
  do
    if [ -d $expandedPluginDir ]; then
      DEPLOYIT_SERVER_CLASSPATH=${DEPLOYIT_SERVER_CLASSPATH}:${expandedPluginDir}
    fi
  done
fi
PIDFILE="${DEPLOYIT_SERVER_HOME}/xl-deploy.pid"

case $1 in
   start)
      # Checked the PID file exists and check the actual status of process
      if [ -e $PIDFILE ]; then
           PID=`cat $PIDFILE`
           status=`ps ax | grep $PID | grep -v grep | wc -l`
           # If the status is SUCCESS then don't need to start again.
           if [ $status = "0" ]; then
              exit # Exit
              echo "$NAME Process is already running"
           fi
           echo "$NAME Process PID file exists"
      fi
      # Start the daemon.
      echo "Starting the process" "$NAME"
      # Run XL Deploy server
      $JAVACMD $DEPLOYIT_SERVER_OPTS $DEPLOYIT_SERVER_LOG_OPTS -classpath "${DEPLOYIT_SERVER_CLASSPATH}" com.xebialabs.deployit.DeployitBootstrapper "$@"
      echo $! > $PIDFILE
     ;;
   stop)
      # Stop the daemon.
      if [ -e $PIDFILE ]; then
         PID=`cat $PIDFILE`
         status=`ps ax | grep $PID | grep -v grep | wc -l`
         if [ "$status" = 0 ]; then
             echo "$NAME Process is not running"
             /bin/rm -rf $PIDFILE
         else
             kill $PID
             sleep 30
             status=`ps ax | grep $PID | grep -v grep | wc -l`
             if [ $status = "0" ]; then
                echo "$NAME Process is not running"
                /bin/rm -rf $PIDFILE
             else
                echo "$NAME Process did not stop"
             fi
         fi
      else
         echo "$NAME process is not running"
      fi
      ;;
   status)
      # Check the status of the process.
      if [ -e $PIDFILE ]; then
          PID=`cat $PIDFILE`
          status=`ps ax | grep $PID | grep -v grep | wc -l`
          if [ "$status" = 0 ]; then
             echo "$NAME Process is not running"
          else
             echo "$NAME Process is running"
          fi
      else
             echo "$NAME Process is not running (No pid file)"
      fi
      ;;
   *)
      # Invalid arguments, print usage message.
      echo "Usage $0 {start|stop|status}"
      exit 2
      ;;
esac

exit 0


