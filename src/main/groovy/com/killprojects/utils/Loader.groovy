package com.killprojects.utils

import com.google.cloud.storage.BlobId
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.stereotype.Component

/**
 * Created by Vladimir on 11.05.2017.
 */
@Component
class Loader {
    public static final String BUCKET = "bucket6558873"

    private final Storage storage

    Loader() {
        storage = StorageOptions.getDefaultInstance().getService()
    }

    InputStream loadData(String nameOfFile, boolean enableAppEngine) {
        if (enableAppEngine) {
            def bytes = storage.readAllBytes(BlobId.of(BUCKET, nameOfFile))
            return new ByteArrayInputStream(bytes)
        } else {
            return new FileInputStream(new File(getClass().getResource("/$nameOfFile").path))
        }
    }

    private File createFile(InputStream stream) {
        def fileWithData = new File("data.txt")
        if (fileWithData.file) {
            fileWithData.delete()
        }
        fileWithData.createNewFile()
        fileWithData.append(stream)
        fileWithData
    }

}
