package xyz.twbkg.stock.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.error_dialog_view_fr.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseDialogFragment
import xyz.twbkg.stock.data.model.db.UnitMeasure


class ErrorDialogFragment : BaseDialogFragment() {
    interface ViewDialogListener {
        fun onViewDialog(unit: UnitMeasure)
    }

    lateinit var listener: ViewDialogListener


    private var message: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            restoreArguments(arguments)
        } else {
            restoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.apply {
            putString(ARG_MESSAGE, message)
        }
        super.onSaveInstanceState(outState)
    }

    override fun restoreInstanceState(bundle: Bundle) {
        bundle?.let {
            try {
                message = it.getString(ARG_MESSAGE, getString(R.string.error_unknown_error))
            } catch (e: Exception) {
            }
            try {
                message = getString(it.getInt(ARG_RES_MESSAGE))
            } catch (e: Exception) {
            }
        }
    }

    override fun restoreArguments(bundle: Bundle?) {
        bundle?.let {
            try {
                message = it.getString(ARG_MESSAGE, getString(R.string.error_unknown_error))
            } catch (e: Exception) {
            }
            try {
                message = getString(it.getInt(ARG_RES_MESSAGE))
            } catch (e: Exception) {
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.error_dialog_view_fr, container)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        message_tv?.apply { text = message }

        cancel_btn?.apply {
            cancel_btn?.apply {
                setOnClickListener { dialog.dismiss() }
            }
        }
    }

    companion object {

        private const val ARG_MESSAGE = "message"
        private const val ARG_RES_MESSAGE = "res_message"

        fun newInstance(message: String): ErrorDialogFragment {
            val frag = ErrorDialogFragment()
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }
            frag.arguments = args
            return frag
        }

        fun newInstance(message: Int): ErrorDialogFragment {
            val frag = ErrorDialogFragment()
            val args = Bundle().apply {
                putInt(ARG_RES_MESSAGE, message)
            }
            frag.arguments = args
            return frag
        }
    }
}