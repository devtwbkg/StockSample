package xyz.twbkg.stock.ui.unit.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.unit_dialog_fr.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseDialogFragment


class UpdateDialogFragment : BaseDialogFragment(), TextView.OnEditorActionListener {
    interface UpdateDialogListener {
        fun onFinishEditDialog(name: String, description: String)
    }

    lateinit var listener: UpdateDialogListener


    private var name: String = ""
    private var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.let {
                restoreArguments(it)
            }
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.apply {
            putString("name", name)
            putString("description", description)
        }
        super.onSaveInstanceState(outState)
    }

    override fun restoreInstanceState(bundle: Bundle) {
        bundle?.let {
            name = it.getString(ARG_NAME)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun restoreArguments(bundle: Bundle) {
        bundle?.let {
            name = it.getString(ARG_NAME)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.unit_dialog_fr, container)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_title_tv?.apply { text = "${getString(R.string.title_edit)}: ${name}" }
        input_username_or_email_edt?.apply {
            setText(name)
        }
        input_password_edt?.apply {
            setText(description)
            setOnEditorActionListener(this@UpdateDialogFragment)
        }

        submit_btn?.apply {
            setOnClickListener {
                updateUnit()
            }
            cancel_btn?.apply {
                setOnClickListener { dialog.dismiss() }
            }
        }
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            updateUnit()
            dismiss()
            return true
        }
        return false
    }

    private fun updateUnit() {
        input_username_or_email_edt?.text?.let { input ->
            name = input.toString()

        }
        input_password_edt?.text?.let { input ->
            description = input.toString()
        }

        listener.onFinishEditDialog(name, description)
        dismiss()
    }

    companion object {

        private const val ARG_NAME = "name"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(name: String, description: String): UpdateDialogFragment {
            val frag = UpdateDialogFragment()
            val args = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_DESCRIPTION, description)
            }
            frag.arguments = args
            return frag
        }
    }
}