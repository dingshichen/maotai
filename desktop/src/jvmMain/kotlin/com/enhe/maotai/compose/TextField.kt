// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-05-18

package com.enhe.maotai.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LongTextField(label: MutableState<String>, text: MutableState<String>) {
    OutlinedTextField(
        modifier = Modifier.size(480.dp, 760.dp),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(label.value) },
    )
}