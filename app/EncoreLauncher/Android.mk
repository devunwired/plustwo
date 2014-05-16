LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := EncoreLauncher

LOCAL_SDK_VERSION := current

LOCAL_CERTIFICATE := shared
LOCAL_PRIVILEGED_MODULE := true

LOCAL_OVERRIDES_PACKAGES := Launcher2

include $(BUILD_PACKAGE)
