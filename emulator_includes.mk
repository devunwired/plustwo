
# Copied from build/target/product/emulator.mk
# Modified to include different init files
#
# This file is included by other product makefiles to add all the
# emulator-related modules to PRODUCT_PACKAGES.
#

# Host modules
PRODUCT_PACKAGES += \
    emulator \
    emulator-x86 \
    emulator-arm \
    emulator-mips \
    emulator64-x86 \
    emulator64-arm \
    emulator64-mips \
    libOpenglRender \
    libGLES_CM_translator \
    libGLES_V2_translator \
    libEGL_translator \
    lib64OpenglRender \
    lib64GLES_CM_translator \
    lib64GLES_V2_translator \
    lib64EGL_translator

# Device modules
PRODUCT_PACKAGES += \
    egl.cfg \
    gralloc.goldfish \
    libGLESv1_CM_emulation \
    lib_renderControl_enc \
    libEGL_emulation \
    libGLESv2_enc \
    libOpenglSystemCommon \
    libGLESv2_emulation \
    libGLESv1_enc \
    qemu-props \
    qemud \
    camera.goldfish \
    camera.goldfish.jpeg \
    lights.goldfish \
    gps.goldfish \
    sensors.goldfish


PRODUCT_COPY_FILES += \
    device/generic/goldfish/fstab.goldfish:root/fstab.goldfish \
    device/doubleencore/plustwo/init.goldfish.rc:root/init.goldfish.rc \
    device/generic/goldfish/init.goldfish.sh:system/etc/init.goldfish.sh \
    device/generic/goldfish/ueventd.goldfish.rc:root/ueventd.goldfish.rc
