package com.doubleencore.services.sysinfo;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

public class SysInfoManager {
	private static final String TAG = "SysInfoManager";
	private static final String REMOTE_NAME = "doubleencore.sysinfo";
	private ISysInfoService mService;
	
	private static SysInfoManager sInstance;
	public static synchronized SysInfoManager getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new SysInfoManager(context.getApplicationContext());
		}
		
		return sInstance;
	}

	//Disable direct instantiation
	private SysInfoManager(Context context) {
		mService = ISysInfoService.Stub.asInterface(
				ServiceManager.getService(REMOTE_NAME));
	}
	
	public String readCommandline() {
		try {
			return mService.readCommmandline();
		} catch (RemoteException e) {
			Slog.e(TAG, "Unable to connect to remote service");
			return null;
		}
	}
	
	public String readCpuInfo() {
		try {
			return mService.readCpuInfo();
		} catch (RemoteException e) {
			Slog.e(TAG, "Unable to connect to remote service");
			return null;
		}
	}
	
	public String readMemInfo() {
		try {
			return mService.readMemInfo();
		} catch (RemoteException e) {
			Slog.e(TAG, "Unable to connect to remote service");
			return null;
		}
	}
}
