// @author  ding.shichen
// @email   foreverhuiqiao@126.com
// @date    2023-05-19

package com.enhe.maotai.compose

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.width(150.dp)) {
        Text(text)
    }
}