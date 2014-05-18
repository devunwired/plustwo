package com.doubleencore.sysinfoservice;

import android.app.Application;
import android.os.ServiceManager;

public class SysInfoApplication extends Application {

	private static final String REMOTE_NAME = "doubleencore.sysinfo";
	private SysInfoServiceImpl mService;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//Add our new custom service with ServiceManager
		mService = new SysInfoServiceImpl();
		ServiceManager.addService(REMOTE_NAME, mService);
	}
}
