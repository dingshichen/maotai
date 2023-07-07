import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.enhe.maotai.compose.LongTextField
import com.enhe.maotai.compose.TransButton
import com.enhe.maotai.service.feature
import com.enhe.maotai.service.trans
import com.enhe.sql.DBProduct

// 默认窗口尺寸
val WINDOW_HEIGHT = 1200.dp
val WINDOW_WIDTH = 900.dp

@Composable
@Preview
fun App() {
    val sql = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }
    val target = remember { mutableStateOf("result") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(text = feature(), modifier = Modifier.padding(20.dp))
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                LongTextField(remember { mutableStateOf("MySQL") }, sql)
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    TransButton("-> DM8") {
                        target.value = DBProduct.DM8.name
                        result.value = trans(sql.value, DBProduct.DM8)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TransButton("-> GaussDB") {
                        target.value = DBProduct.GaussDB.name
                        result.value = trans(sql.value, DBProduct.GaussDB)
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                LongTextField(target, result)
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(WINDOW_HEIGHT, WINDOW_WIDTH)
        ),
        title = "Maotai"
    ) {
        App()
    }
}
