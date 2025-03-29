package org.shu.peanutim

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun InputView(
    modifier: Modifier = Modifier,
    clickable: () -> Unit = {}
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(MaterialTheme.colorScheme.tertiary)
        .clickable{
            clickable()
        }
    ) {

    }

}

@Preview
@Composable
private fun InputViewPrev() {
    InputView()
}
