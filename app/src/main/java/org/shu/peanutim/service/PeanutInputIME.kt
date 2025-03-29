package org.shu.peanutim.service

import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import org.shu.peanutim.InputView


private const val TAG = "FastInputIME"


class PeanutInputIME : ComposeInputMethodService() {

    private var clickCount = 0

    @Composable
    override fun Content() {
        InputView{
            clickCount ++
            currentInputConnection.commitText(clickCount.toString(), 1)
        }
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        currentInputConnection
    }
}