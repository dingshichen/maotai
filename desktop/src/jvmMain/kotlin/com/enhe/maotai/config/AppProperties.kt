// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-07-07

package com.enhe.maotai.config

import java.io.FileInputStream
import java.util.Properties

object AppProperties {

    private var property = Properties()

    fun init() {
        property.load(FileInputStream("gradle.properties"))
    }

    fun getProperty(key: String): String = property.getProperty(key)
}