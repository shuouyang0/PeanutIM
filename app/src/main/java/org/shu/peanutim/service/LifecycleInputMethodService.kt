package org.shu.peanutim.service

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner

/**
 * 当首次切换到该输入法时，
 * onCreate --> onStartInput --> onCreateInputView --> onStartInputView --> onWindowShown
 *
 *      收起键盘，但焦点仍然在输入框
 *      onFinishInputView --> onWindowHidden
 *
 *          点击输入框，键盘显示
 *          onStartInputView --> onWindowShown
 *
 *      回到桌面
 *      onFinishInputView --> onWindowHidden --> onFinishInput --> onStartInput
 *
 *      切换到其他输入法
 *      onDestroy --> onFinishInputView --> onFinishInput
 *
 */
private const val TAG = "LifecycleInputMethodSer"
open class LifecycleInputMethodService: InputMethodService(), LifecycleOwner {

    private val _lifecycle = LifecycleRegistry(this)
    override val lifecycle: Lifecycle
        get() = _lifecycle

    /**
     * 切换系统输入法为当前输入法时，调用一次
     */
    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        super.onCreate()
        window.window?.decorView?.setViewTreeLifecycleOwner(this)
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
        return super.onCreateInputView()
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
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        super.onWindowShown()
    }

    /**
     * 每次隐藏键盘时调用
     */
    override fun onWindowHidden() {
        Log.d(TAG, "onWindowHidden: ")
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onWindowHidden()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        _lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        Log.d(TAG, "onFinishInputView: ")
        super.onFinishInputView(finishingInput)
    }

    /**
     * 每次推出输入状态（键盘隐藏但是输入光标仍然在输入框中，此时这个还没调用）
     */
    override fun onFinishInput() {
        Log.d(TAG, "onFinishInput: ")
        super.onFinishInput()
    }

}