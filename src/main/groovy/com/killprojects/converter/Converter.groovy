package com.killprojects.converter

/**
 * Created by Vladimir on 11.05.2017.
 */
interface Converter<T> {
    List<T> convert (InputStream file)
    InputStream convert (List<T> list)
}