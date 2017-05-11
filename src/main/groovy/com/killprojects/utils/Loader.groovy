package com.killprojects.utils

import com.google.cloud.storage.Storage
import org.springframework.stereotype.Component

/**
 * Created by Vladimir on 11.05.2017.
 */
@Component
class Loader {
    private Storage storage

    File loadData(String nameOfFile, boolean enableAppEngine) {
        if (enableAppEngine) {
            //todo connect to appEngine
            return null
        } else {
            return new File(getClass().getResource("/$nameOfFile").path)
        }
    }

}
