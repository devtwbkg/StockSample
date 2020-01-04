package xyz.twbkg.stock.ui.common

import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentManager
import android.view.View
import android.widget.Toast
import xyz.twbkg.stock.common.ErrorDialogFragment
import xyz.twbkg.stock.di.scpe.ActivityScoped


@ActivityScoped
class UIResolver {

//    @Inject constructor(var rootView: View)

    private lateinit var rootView: View
    private var fragmentManager: androidx.fragment.app.FragmentManager? = null
    private var persistentSnackbar: Snackbar? = null
    private lateinit var errorDialogFragment: ErrorDialogFragment

    fun provideRootView(
            rootView: View,
            fragmentManager: androidx.fragment.app.FragmentManager? = null
    ) {
        this.rootView = rootView
        this.fragmentManager = fragmentManager
    }

    fun showSnackBar(messageResource: Int) {
        Snackbar.make(rootView, messageResource, Snackbar.LENGTH_LONG).show()
    }

    fun showPersistentSnackBar(messageResource: Int) {
        persistentSnackbar = Snackbar.make(rootView, messageResource, Snackbar.LENGTH_INDEFINITE)
        persistentSnackbar?.show()
    }

    fun hidePersistentSnackBar() {
        persistentSnackbar?.dismiss()
        if (::errorDialogFragment.isInitialized) {
            errorDialogFragment.dismiss()
        }
    }

    fun showDialogError(message: String) {
        fragmentManager?.let {
            errorDialogFragment = ErrorDialogFragment.newInstance(message)
            if (::errorDialogFragment.isInitialized && !errorDialogFragment.isVisible) {
                errorDialogFragment.show(fragmentManager, ErrorDialogFragment::class.java.simpleName)
            }
        }
    }

    fun showDialogError(message: Int) {
        fragmentManager?.let {
            errorDialogFragment = ErrorDialogFragment.newInstance(message)
            if (::errorDialogFragment.isInitialized && !errorDialogFragment.isVisible) {
                errorDialogFragment.show(fragmentManager, ErrorDialogFragment::class.java.simpleName)
            }
        }
    }

    fun showToastShortMessage(message: Int) {
        Toast.makeText(rootView.context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToastLongMessage(message: Int) {
        Toast.makeText(rootView.context, message, Toast.LENGTH_LONG).show()
    }

    fun showToastLongMessage(message: String) {
        Toast.makeText(rootView.context, message, Toast.LENGTH_LONG).show()
    }
}