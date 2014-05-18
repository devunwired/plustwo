package com.doubleencore.sysinfoservice;

import android.os.RemoteException;

import com.doubleencore.services.sysinfo.ISysInfoService;

public class SysInfoServiceImpl extends ISysInfoService.Stub {

	private SysInfoReader mInfoReader;
	
	public SysInfoServiceImpl() {
		mInfoReader = new SysInfoReader();
	}
	
	@Override
	public String readCommmandline() throws RemoteException {
		return mInfoReader.readCommandline();
	}

	@Override
	public String readCpuInfo() throws RemoteException {
		return mInfoReader.readCpuInfo();
	}

	@Override
	public String readMemInfo() throws RemoteException {
		return mInfoReader.readMemInfo();
	}

}
