# Inherit from the same point as the emulator product, which defines the base OS
$(call inherit-product, $(SRC_TARGET_DIR)/product/aosp_base_telephony.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/board/generic/device.mk)

include $(LOCAL_PATH)/emulator_includes.mk

# Discard inherited values and use our own instead
PRODUCT_NAME := full_plustwo
PRODUCT_DEVICE := plustwo
PRODUCT_BRAND := DoubleEncore
PRODUCT_MODEL := PlusTwo Android Device

# Enable overlays
DEVICE_PACKAGE_OVERLAYS := $(LOCAL_PATH)/overlay

# Copy custom boot animation
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/bootanimation/bootanimation.zip:system/media/bootanimation.zip

# Default disable the lock screen
PRODUCT_PROPERTY_OVERRIDES := \
    ro.lockscreen.disable.default=true
    
PRODUCT_PACKAGES += EncoreLauncher \
    sysinfo

