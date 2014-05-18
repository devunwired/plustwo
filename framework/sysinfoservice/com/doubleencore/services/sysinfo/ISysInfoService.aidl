package com.doubleencore.services.sysinfo;

/**
 * Interface for accessing system information service
 */
interface ISysInfoService {
	String readCpuInfo();
	String readMemInfo();
	String readCommmandline();
}