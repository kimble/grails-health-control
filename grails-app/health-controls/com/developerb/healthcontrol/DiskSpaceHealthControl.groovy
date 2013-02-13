package com.developerb.healthcontrol

import static com.developerb.healthcontrol.HealthLevel.FRAIL
import static com.developerb.healthcontrol.HealthLevel.HEALTHY
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize

import java.nio.file.FileSystems

/**
 *
 * @author Kim A. Betti <kim@developer-b.com>
 */
class DiskSpaceHealthControl implements HealthControl {

    static final int limitMb = 500

    String name = "Available disk space"
    String description = "Monitor available disk space"
    Long timeoutMillis = 500

    StateOfHealth execute() {
        def props = [:]
        def message = "Enough usable space on all file systems"
        def level = HEALTHY

        FileSystems.default.fileStores.each {
            def usableMb = Math.round(it.usableSpace / 1000 / 1000)
            props[it.name()] = usableMb + " MB"

            if (usableMb < limitMb) {
                message = "At least one filesystem running low on usable space.."
                level = FRAIL
            }
        }

        return new StateOfHealth(level, message, mapUsableSpaceOnFileSystems())
    }

    private static Map mapUsableSpaceOnFileSystems() {
        return FileSystems.default.fileStores.collectEntries {
            [ it.name(), byteCountToDisplaySize(it.usableSpace) ]
        }
    }

}
