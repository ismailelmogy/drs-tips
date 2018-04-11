package ocs.com.dr_tips.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.fragment_change_password.*
import ocs.com.dr_tips.DrTipsApplication
import ocs.com.dr_tips.R
import ocs.com.dr_tips.util.DialogMaker
import ocs.com.dr_tips.viewModel.LoginViewModel
import ocs.com.dr_tips.viewModel.ProfileEditViewModel
import javax.inject.Inject


class ChangePasswordFragment : DrsTipsBaseFragment() {

    @Inject
    lateinit var loginViewModel: LoginViewModel
    @Inject
    lateinit var profileEditViewModel: ProfileEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as DrTipsApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_change_password, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarWithBackBtn(R.string.change_password)
        oldPasswordTIL.editText?.transformationMethod = PasswordTransformationMethod()
        newPasswordTIL.editText?.transformationMethod = PasswordTransformationMethod()
        confirmPasswordTIL.editText?.transformationMethod = PasswordTransformationMethod()

        changePasswordButton.setOnClickListener {
            if (checkInputs()) {
                subscribeToChangePassword()
            }
        }
    }

    private fun subscribeToChangePassword() {
        showProgressDialog()
        profileEditViewModel.changePassword(oldPasswordTIL.editText?.text.toString(), newPasswordTIL.editText?.text.toString())
                .subscribe({
                    dismissProgressDialog()
                    activity?.onBackPressed()
                }, {
                    dismissProgressDialog()
                    val errorMessage = if (it is FirebaseAuthInvalidCredentialsException) {
                        getString(R.string.wrong_old_password)
                    } else {
                        getString(R.string.save_data_error)
                    }
                    DialogMaker.makeDialog(context, errorMessage, getString(R.string.ok), { dialogInterface: DialogInterface, view: Int ->
                        dialogInterface.dismiss()
                    }).show()
                })
    }

    private fun checkInputs(): Boolean {
        var isValid = true
        val passwordValidation = loginViewModel.checkPassword(oldPasswordTIL.editText?.text.toString())
        val newPasswordValidation = loginViewModel.checkPassword(newPasswordTIL.editText?.text.toString())
        val newPasswordConfirmationValidation = loginViewModel.isPasswordConfirmed(newPasswordTIL.editText?.text.toString()
                , confirmPasswordTIL.editText?.text.toString())

        if (passwordValidation != R.string.ok) {
            isValid = false
            oldPasswordTIL.error = getString(passwordValidation)
        } else {
            oldPasswordTIL.isErrorEnabled = false
        }
        if (newPasswordValidation != R.string.ok) {
            isValid = false
            newPasswordTIL.error = getString(newPasswordValidation)
        } else {
            newPasswordTIL.isErrorEnabled = false
        }
        if (newPasswordConfirmationValidation != R.string.ok) {
            isValid = false
            confirmPasswordTIL.error = getString(newPasswordConfirmationValidation)
        } else {
            confirmPasswordTIL.isErrorEnabled = false
        }
        return isValid
    }
}
