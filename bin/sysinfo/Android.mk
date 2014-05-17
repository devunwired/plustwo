LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := sysinfo.c
LOCAL_SHARED_LIBRARIES := libcutils
LOCAL_MODULE := sysinfo

include $(BUILD_EXECUTABLE)
