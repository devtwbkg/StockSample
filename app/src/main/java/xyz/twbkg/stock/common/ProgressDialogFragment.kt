package xyz.twbkg.stock.common

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.*

import kotlinx.android.synthetic.main.progress_dialog_view.*
import xyz.twbkg.stock.R

class ProgressDialogFragment : DialogFragment(), UpdateTitleCallback {
    override fun updateTitle(message: Int) {
        this.message = message
        setupView(this.message)
    }

    private var message: Int = 0
    private var isCancel: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            restoreArguments(arguments!!)
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.progress_dialog_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(message)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(isCancel)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        // Disable the back button
        val keyListener = DialogInterface.OnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }
        dialog.setOnKeyListener(keyListener)
        return dialog
    }

    private fun setupView(message: Int) {
        message_tv?.text = getString(message)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MESSAGE, message)
        outState.putBoolean(KEY_CANCEL, isCancel)
    }

    private fun restoreInstanceState(bundle: Bundle) {
        message = bundle.getInt(KEY_MESSAGE)
        isCancel = bundle.getBoolean(KEY_CANCEL)
    }

    private fun restoreArguments(bundle: Bundle) {
        message = bundle.getInt(KEY_MESSAGE)
        isCancel = bundle.getBoolean(KEY_CANCEL)
    }

    companion object {
        private const val KEY_MESSAGE = "key_message"
        private const val KEY_CANCEL = "key_cantouch"

        fun newInstance(@StringRes message: Int, cancelable: Boolean): ProgressDialogFragment {
            val fragment = ProgressDialogFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_MESSAGE, message)
                putBoolean(KEY_CANCEL, cancelable)
            }
            return fragment
        }
    }
}