package com.killprojects.utils

/**
 * Created by Vladimir on 13.05.2017.
 */
class FileUtil {
    public static File createFile(InputStream stream, String path) {
        def fileWithData = new File(path)
        if (fileWithData.file) {
            fileWithData.delete()
        }
        fileWithData.createNewFile()
        fileWithData.append(stream)
        fileWithData
    }
}
