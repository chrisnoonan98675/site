---
title: Install XL Deploy as a service
subject:
- System administration
categories:
- xl-deploy
tags:
- system administration
- service
since:
- 4.5.x
---

You want to install XL Deploy as a service, so that it is always up and running, even after a server reboot? Follow these steps to get it working.

## Linux approach 1: LSB `init` scripts

1. Download [the LSB init script `xl-deploy.init`](sample-scripts/xl-deploy.init).
1. Ensure that the LSB libraries have been installed; for example, by using `yum install readhat-lsb`.
1. Edit the `xl-deploy.init` script to set the following:
    * `JAVA_HOME`: Java home path; for example, `/opt/java7`
    * `XLD_SRV_HOME`: XL Deploy path; for example, `/opt/xebialabs/xl-deploy-4.5.2-server`
    * `USERID`: Linux user that will run XL Deploy; for example, `xebia`

1. Install `xl-deploy.init` by copying it to `/etc/init.d`.

        cp xl-deploy.init /etc/init.d/xl-deploy.init
        chmod +x /etc/init.d/xl-deploy.init

1. Configure `xl-deploy.init` to run at server start-up as follows:

        # systemctl enable xl-deploy.init

1. Download [the custom XL Deploy server script `xl-deploy.sh`](sample-scripts/xl-deploy.sh) and install it in the `XLD_SRV_HOME` directory.
1. Ensure there are execute permissions on the server start script.

        chmod +x xl-deploy.sh

1. Verify the functionality by starting and stopping XL Deploy from the command line as follows:

        systemctl start xl-deploy.init
        systemctl status xl-deploy.init
        systemctl stop xl-deploy.init

## Linux approach 2: YAJSW `init` script

### Prerequisite

You must have `java` available on your PATH.

### Installation

1. Download YAJSW from [http://sourceforge.net/projects/yajsw/](http://sourceforge.net/projects/yajsw/).
1. Extract the downloaded ZIP file to a directory of your choice. We'll refer to this directory as `$YAJSW`.
1. Download [this `wrapper.conf` file](sample-scripts/wrapper.conf) and store it in `$YAJSW/yajsw-stable-$VERSION/conf`.
1. In the downloaded `wrapper.conf` file, update the `wrapper.working.dir` property with the location of your XL Deploy server.
1. Go to `$YAJSW/yajsw-stable-$VERSION/bin` and execute `chmod +x *.sh`.
1. Ensure the XL Deploy server is not running.
1. Run `$YAJSW/yajsw-stable-$VERSION/bin/installDaemon.sh`.
1. You can now start the XL Deploy service with `sudo service deployit start`.

**Note:** If you encounter a `There is an incompatible JNA native library installed on this system` error, verify that the `libjna-java` package is not installed. This package is installed when Jenkins is running on the same machine, and it prevents YAJSW from working properly. After the package is removed, YAJSW will work and the error message will disappear.

## Windows approach 1: YAJSW

### Prerequisite

You must have `java` available on your PATH.

### Installation

1. Download YAJSW from [http://sourceforge.net/projects/yajsw/](http://sourceforge.net/projects/yajsw/).
1. Extract the downloaded ZIP file to a directory of your choice. We'll refer to this directory as `$YAJSW`.
1. Download [this `wrapper.conf` file](sample-scripts/wrapper.conf) and store it in `$YAJSW\yajsw-stable-$VERSION\conf`.
1. In the downloaded `wrapper.conf` file, update the `wrapper.working.dir` property with the location of your XL Deploy server.
1. Go to `$YAJSW/yajsw-stable-$VERSION/bat`.
1. Ensure that the XL Deploy server is not running.
1. Run `$YAJSW/yajsw-stable-$VERSION/bat/installService.bat`.
1. You can now start the XL Deploy service from the Windows services screen.

## Windows approach 2: `srvany.exe`

### Prerequisite

You must have `java` available on your PATH.

### Installation

1. Download the [Windows 200x Resource Kit](http://www.microsoft.com/downloads/details.aspx?FamilyID=9D467A69-57FF-4AE7-96EE-B18C4790CFFD) for Windows 2003.
1. Install the Windows 200x Resource Kit.
1. Create the service entry (named XL-Deploy in this example).

      ![Service entry](images/windows_srvany_1.png)

1. Click the **Start** menu, type `regedit`, and press ENTER to start the Registry Editor.
1. Find the `HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\XL-Deploy` registry key.
1. Click **Edit** > **Add Key**, type `Parameters`, and click **OK**.
1. Double-click **Parameters** > **Edit** and select **Add Value**.
1. Add the value `Application` and the data type `REG_SZ`.
1. Click **OK**.
1. Set the string to `<xl-deploy path>\bin\server.cmd`.
1. Click **OK**.
1. Double-click **Parameters** > **Edit** and select **Add Value**.
1. Add the value `AppDirectory` and the data type `REG_SZ`.
1. Click **OK**.
1. Set the string to `<xl-deploy path>\bin`.
1. Click **OK**.

      ![Windows registry](images/windows_srvany_2.png)

1. Go to Windows services and start the XL Deploy service.

      ![Windows services](images/windows_srvany_3.png)
      
      
## Windows approach 3: `nssm`

### Prerequisite

You must have `java` available on your PATH.

### Installation

1. Download the [NSSM](http://nssm.cc/) Applications.
1. Install XL-Deploy service as follows:

		nssm.exe install XL_Deploy F:\xebialabs\xld451\xl-deploy-4.5.1-server\bin\server.cmd
	
1. You should get a notification back that the service was installed as follows:
		
		Service "XL_Deploy" installed successfully!
		
1. Now you can go to the `Services` application and start/stop your new `XL-Deploy` service

	![Service entry](images/windows_nssm_1.png)
		
1. It is possible to also edit service parameters from a GUI using the command as follows:

		nssm.exe edit XL_Deploy
		
1. A widonw will open with all of the configurable options for the `XL-Deploy` service as follows:

	![NSSM service editor](images/windows_nssm_2.png)

