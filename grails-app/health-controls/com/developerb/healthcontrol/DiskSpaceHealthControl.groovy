package com.developerb.healthcontrol

import static com.developerb.healthcontrol.HealthLevel.FRAIL
import static com.developerb.healthcontrol.HealthLevel.HEALTHY
import static org.apache.commons.io.FileUtils.byteCountToDisplaySize

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
        def message = "Enough usable space on all file systems"
        def level = HEALTHY

        def usableSpaceMap = File.listRoots().collectEntries {
            def usableMb = Math.round(it.usableSpace / 1000 / 1000)

            if (usableMb < limitMb) {
                message = "At least one filesystem running low on usable space.."
                level = FRAIL
            }

            [ it.absolutePath, byteCountToDisplaySize(it.usableSpace) ]
        }

        return new StateOfHealth(level, message, usableSpaceMap)
    }
}

