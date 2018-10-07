package xyz.twbkg.stock.ui.unit.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.unit_dialog_view_fr.*
import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseDialogFragment
import xyz.twbkg.stock.data.model.db.UnitMeasure


class ViewDialogFragment : BaseDialogFragment() {
    interface ViewDialogListener {
        fun onViewDialog(unit: UnitMeasure)
    }

    lateinit var listener: ViewDialogListener


    private var name: String = ""
    private var description: String = ""
    private lateinit var unit: UnitMeasure

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
            putParcelable(ARG_MODEL, unit)
        }
        super.onSaveInstanceState(outState)
    }

    override fun restoreInstanceState(bundle: Bundle) {
        bundle?.let { unit = it.getParcelable(ARG_MODEL) }
    }

    override fun restoreArguments(bundle: Bundle?) {
        bundle?.let { unit = it.getParcelable(ARG_MODEL) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.unit_dialog_view_fr, container)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog_title_tv?.apply { text = "${getString(R.string.title_edit)}: ${unit.name}" }
        name_tv?.apply { text = unit.name }
        description_tv?.apply { text = unit.no }

        submit_btn?.apply {
            cancel_btn?.apply {
                setOnClickListener { dialog.dismiss() }
            }
        }
    }

    companion object {

        private const val ARG_MODEL = "model"

        fun newInstance(unit: UnitMeasure): ViewDialogFragment {
            val frag = ViewDialogFragment()
            val args = Bundle().apply {
                putParcelable(ARG_MODEL, unit)
            }
            frag.arguments = args
            return frag
        }
    }
}