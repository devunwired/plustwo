PlusTwo Sample Device Target
=======

This repository contains the sample code for building a custom Android device target discussed in "Customizing Android for Fun and Profit".

To build this target:

1. Initialize an AOSP source tree for android-4.4.2_r1
2. Clone this repository under the `device/` directory of the AOSP tree
3. `source build/envsetup.sh`
4. `lunch full_plustwo-eng`
5. `make`

Parts of this sample copied from AOSP are licensed under the Apache Open Source License, version 2.  The remaining code is provided under the MIT Open Source License.