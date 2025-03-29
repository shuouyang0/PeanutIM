package org.shu.peanutim

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodSubtype
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

private const val TAG = "FastInputIME"

/**
 * 当首次切换到该输入法时，
 * onCreate --> onStartInput --> onCreateInputView --> onStartInputView --> onWindowShown  
 *      收起键盘，但焦点仍然在输入框
 *      onFinishInputView --> onWindowHidden
 *          点击输入框，键盘显示
 *          onStartInputView --> onWindowShown
 *      回到桌面
 *      onFinishInputView --> onWindowHidden --> onFinishInput --> onStartInput
 *      切换到其他输入法
 *      onDestroy --> onFinishInputView --> onFinishInput
 * 
 */
class FastInputIME : InputMethodService(), LifecycleOwner, ViewModelStoreOwner,SavedStateRegistryOwner {
    /**
     * 切换系统输入法为当前输入法时，调用一次
     */
    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        super.onCreate()
        window.window?.decorView?.let {
            it.setViewTreeLifecycleOwner(this)
            it.setViewTreeViewModelStoreOwner(this)
            it.setViewTreeSavedStateRegistryOwner(this)
        }
    }

    /**
     * 每次进入输入状态（当光标停留在输入框中就是输入状态，无论键盘是否显示）
     */
    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        Log.d(TAG, "onStartInput: ")
        super.onStartInput(attribute, restarting)
    }

    /**
     * 创建一次
     */
    override fun onCreateInputView(): View {
        Log.d(TAG, "onCreateInputView: ")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        val inputView = ComposeView(this)
        inputView.setContent { InputView() }
        return inputView
    }

    override fun onStartInputView(editorInfo: EditorInfo?, restarting: Boolean) {
        Log.d(TAG, "onStartInputView: ")
        super.onStartInputView(editorInfo, restarting)
    }

    /**
     * 每次显示键盘时调用
     */
    override fun onWindowShown() {
        Log.d(TAG, "onWindowShown: ")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        super.onWindowShown()
    }

    /**
     * 每次隐藏键盘时调用
     */
    override fun onWindowHidden() {
        Log.d(TAG, "onWindowHidden: ")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onWindowHidden()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        viewModelStore.clear()
        super.onDestroy()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        Log.d(TAG, "onFinishInputView: ")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onFinishInputView(finishingInput)
    }

    /**
     * 每次推出输入状态（键盘隐藏但是输入光标仍然在输入框中，此时这个还没调用）
     */
    override fun onFinishInput() {
        Log.d(TAG, "onFinishInput: ")
        super.onFinishInput()
    }

    private val lifecycleRegistry = LifecycleRegistry(this)
    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
    private val savedStateRegistryController = SavedStateRegistryController.create(this)
    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry
    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()

}