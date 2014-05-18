LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under,src)
LOCAL_JAVA_LIBRARIES := com.doubleencore.services.sysinfo
LOCAL_PACKAGE_NAME := SysInfoClient

include $(BUILD_PACKAGE)