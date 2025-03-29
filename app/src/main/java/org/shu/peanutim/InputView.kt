package org.shu.peanutim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun InputView(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(Color.Cyan)) {

    }

}
@Composable
fun CandidatesView(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Red)) {

    }

}

@Preview
@Composable
private fun InputViewPrev() {
    InputView()
}

@Preview
@Composable
private fun CandidatesViewPrev() {
    CandidatesView()
}