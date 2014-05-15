# Inherit from the emulator product, which defines the base OS
$(call inherit-product, $(SRC_TARGET_DIR)/product/full.mk)

# Discard inherited values and use our own instead
PRODUCT_NAME := full_plustwo
PRODUCT_DEVICE := plustwo
PRODUCT_MODEL := PlusTwo Android Device

# Enable overlays
DEVICE_PACKAGE_OVERLAYS := $(LOCAL_PATH)/overlay

# Copy custom boot animation
PRODUCT_COPY_FILES += \
    $(LOCAL_PATH)/bootanimation/bootanimation.zip:system/media/bootanimation.zip

# Default disable the lock screen
PRODUCT_PROPERTY_OVERRIDES := \
    ro.lockscreen.disable.default=true
