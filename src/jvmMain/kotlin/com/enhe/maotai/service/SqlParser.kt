// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-05-19

package com.enhe.maotai.service

import com.enhe.sql.DBProduct
import com.enhe.sql.Feature
import com.enhe.sql.MySQLTransfer
import com.enhe.sql.SQLTransfer

fun feature(): String {
    return "支持 MySQL 以下语句：" + Feature.getComment()
}

fun trans(sql: String, target: DBProduct): String {
    val transfer: SQLTransfer = MySQLTransfer()
    return transfer.transform(sql, target).texts.joinToString("\n\n")
}