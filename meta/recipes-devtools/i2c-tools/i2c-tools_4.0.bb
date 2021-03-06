SUMMARY = "Set of i2c tools for linux"
HOMEPAGE = "https://i2c.wiki.kernel.org/index.php/I2C_Tools"
SECTION = "base"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "${KERNELORG_MIRROR}/software/utils/i2c-tools/${BP}.tar.gz \
           file://0001-lib-Module.mk-Add-missing-dependencies.patch \
           file://0001-tools-Module.mk-Add-missing-dependencies.patch \
           file://0001-i2c-tools-eeprog-Module.mk-Add-missing-dependency.patch \
"

SRC_URI[md5sum] = "d92a288d70f306d3895e3a7e9c14c9aa"
SRC_URI[sha256sum] = "5b60daf6f011de0acb61de57dba62f2054bb39f19961d67e0c91610f071ca403"

inherit autotools-brokensep

do_compile_prepend() {
    sed -i 's#/usr/local#/usr#' ${S}/Makefile
    echo "include eeprog/Module.mk" >> ${S}/Makefile
}

do_install_append() {
    install -d ${D}${includedir}/linux
    install -m 0644 include/linux/i2c-dev.h ${D}${includedir}/linux/i2c-dev-user.h
    rm -f ${D}${includedir}/linux/i2c-dev.h
}

PACKAGES =+ "${PN}-misc"
FILES_${PN}-misc = "${sbindir}/i2c-stub-from-dump \
                        ${bindir}/ddcmon \
                        ${bindir}/decode-edid \
                        ${bindir}/decode-dimms \
                        ${bindir}/decode-vaio \
                       "
RDEPENDS_${PN}-misc = "${PN} perl perl-module-posix \
                       perl-module-constant perl-module-file-basename \
                       perl-module-fcntl perl-module-strict perl-module-vars \
                      "
