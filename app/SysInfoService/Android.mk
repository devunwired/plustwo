LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under,src)
LOCAL_JAVA_LIBRARIES := \
	core \
	framework

LOCAL_PACKAGE_NAME := SysInfoService

LOCAL_SDK_VERSION := current
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE)