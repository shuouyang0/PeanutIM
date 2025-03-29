package org.shu.peanutim.service

import android.util.Log
import android.view.View
import androidx.compose.ui.platform.ComposeView
import org.shu.peanutim.InputView


private const val TAG = "FastInputIME"


class PeanutInputIME : ComposeInputMethodService() {

    override fun onCreateInputView(): View {
        Log.d(TAG, "onCreateInputView: ")
        val inputView = ComposeView(this)
        inputView.setContent { InputView() }
        return inputView
    }

}