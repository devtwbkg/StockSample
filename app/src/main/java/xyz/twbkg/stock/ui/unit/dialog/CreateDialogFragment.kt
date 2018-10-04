package xyz.twbkg.stock.ui.unit.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.unit_dialog_fr.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseDialogFragment


class CreateDialogFragment : BaseDialogFragment(), TextView.OnEditorActionListener {

    interface CreateDialogListener {
        fun onFinishEditDialog(name: String = "", description: String = "")
    }

    lateinit var listener: CreateDialogListener


    private var name: String = ""
    private var description: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun restoreInstanceState(bundle: Bundle) {
    }

    override fun restoreArguments(bundle: Bundle) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.unit_dialog_fr, container)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_title_tv?.apply { text = "${getString(R.string.title_add)}: ${getString(R.string.title_unit)}" }
        input_description_edt?.apply {
            setOnEditorActionListener(this@CreateDialogFragment)
        }
        dialog.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

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
        input_name_edt?.text?.let { input ->
            name = input.toString()

        }
        input_description_edt?.text?.let { input ->
            description = input.toString()
        }

        if (name.isNotEmpty() && name.isNotBlank()) {
            listener.onFinishEditDialog(name, description)
            dismiss()
        } else {
            Toast.makeText(context, R.string.please_input_your_name, Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        fun newInstance(): CreateDialogFragment {
            val frag = CreateDialogFragment()
            val args = Bundle()
            frag.arguments = args
            return frag
        }
    }
}