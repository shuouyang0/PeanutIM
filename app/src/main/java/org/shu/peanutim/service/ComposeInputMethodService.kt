package org.shu.peanutim.service

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

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

    override fun onDestroy() {
        super.onDestroy()
        _store.clear()
    }
}