package xyz.twbkg.stock.ui.authen.signin


import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.login_fr.*

import xyz.twbkg.stock.R
import xyz.twbkg.stock.application.BaseFragment
import xyz.twbkg.stock.common.ProgressDialogFragment
import xyz.twbkg.stock.ui.unit.UnitActivity
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginContract.View {


    @Inject
    lateinit var presenter: LoginPresenter
    private lateinit var dialog: ProgressDialogFragment

    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fr, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addListener()

        presenter.isLoggedIn()
    }

    private fun addListener() {
        submit_btn?.apply {
            setOnClickListener { doSignIn() }
        }
    }

    private fun doSignIn() {
        var usernameOrEmail = input_username_or_email_edt.text.toString().trim() ?: ""
        var password = input_password_edt.text.toString().trim() ?: ""

        presenter.signIn(usernameOrEmail, password)
    }

    override fun showLoading() {
        dialog = ProgressDialogFragment.newInstance(R.string.please_wait, false)
        if (!dialog.isVisible) {
            dialog.show(fragmentManager, ProgressDialogFragment::class.java.simpleName)
        }
    }

    override fun hideLoading() {
        if (::dialog.isInitialized) {
            dialog.dismiss()
        }
    }

    override fun enableButton() {
        signup_btn?.isEnabled = true
        submit_btn?.isEnabled = true
    }

    override fun disableButton() {
        signup_btn?.isEnabled = false
        submit_btn?.isEnabled = false
    }

    override fun navigationToMainActivity() {
        Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
        startActivity(Intent(context, UnitActivity::class.java))
        activity?.finish()
    }

    override fun invalidUserOrEmail(message: Int) {
        input_username_or_email_edt?.requestFocus()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun invalidPassword(message: Int) {
        input_password_edt?.requestFocus()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(@StringRes message: Int) {
        showSnackBar(getString(message))
    }

    private fun showSnackBar(message: String) {
        snackbar = Snackbar.make(
                root_view,
                message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) {
                    doSignIn()
                }
        snackbar?.show()
    }

    companion object {

        @JvmStatic
        fun newInstance() = LoginFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}
