LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Rishikesh38;protocol=ssh;branch=master file://S98lddmodules-scull-start-stop.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "0497c47c332879509cb607d718aa3f275868d747"

S = "${WORKDIR}/git/scull"

inherit module

MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE:append_task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules-scull-start-stop.sh"

FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/5.15.91-yocto-standard/
	install -m 0755 ${S}/scull_load ${D}${bindir}/
    install -m 0755 ${S}/scull_unload ${D}${bindir}/
	install -m 0755 ${WORKDIR}/S98lddmodules-scull-start-stop.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/scull.ko ${D}/${base_libdir}/modules/5.15.91-yocto-standard/
}