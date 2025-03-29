package org.shu.peanutim.service

import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import org.shu.peanutim.InputView
import org.shu.peanutim.ui.theme.PeanutIMTheme

open class ComposeInputMethodService : LifecycleInputMethodService(), ViewModelStoreOwner,
    SavedStateRegistryOwner {

    private val _controller = SavedStateRegistryController.create(this)
    override val savedStateRegistry: SavedStateRegistry
        get() = _controller.savedStateRegistry

    private val _store = ViewModelStore()
    override val viewModelStore
        get() = _store

    override fun onCreate() {
        _controller.performRestore(null)
        super.onCreate()
        window.window?.decorView?.let {
            it.setViewTreeViewModelStoreOwner(this)
            it.setViewTreeSavedStateRegistryOwner(this)
        }
    }

    override fun onCreateInputView(): View {
        val inputView = ComposeView(this)
        inputView.setContent{
            PeanutIMTheme {
                Content()
            }
        }
        return inputView
    }

    @Composable
    open fun Content(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _store.clear()
    }
}