package ocs.com.dr_tips.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import ocs.com.dr_tips.DrTipsApplication
import ocs.com.dr_tips.R
import ocs.com.dr_tips.util.AppDataHolder
import ocs.com.dr_tips.util.DialogMaker
import ocs.com.dr_tips.viewModel.LoginViewModel
import ocs.com.dr_tips.viewModel.ProfileEditViewModel
import javax.inject.Inject

class EditProfileFragment : DrsTipsBaseFragment() {

    @Inject
    lateinit var profileEditViewModel: ProfileEditViewModel
    @Inject
    lateinit var loginViewModel: LoginViewModel
    private lateinit var editProfileType: EditProfileEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as DrTipsApplication).component.inject(this)
        editProfileType = arguments?.getSerializable(TYPE) as EditProfileEnum
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_edit_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (editProfileType == EditProfileEnum.WORK_PLACE) {
            workPlaceView.visibility = VISIBLE
        } else if (editProfileType == EditProfileEnum.SPECIALITY) {
            specialityView.visibility = VISIBLE
        } else if (editProfileType == EditProfileEnum.EMAIL) {
            emailView.visibility = VISIBLE
        } else if (editProfileType == EditProfileEnum.NAME) {
            nameView.visibility = VISIBLE
        }

        editButton.setOnClickListener {
            val subscriptionBody = HashMap<String, String>()
            if (editProfileType == EditProfileEnum.WORK_PLACE) {
                subscriptionBody.put(editProfileType.getKey(), workPlaceEditText.text.toString())
                subscribeToEditProfile(subscriptionBody)
            } else if (editProfileType == EditProfileEnum.SPECIALITY) {
                subscriptionBody.put(editProfileType.getKey(), specialityEditText.text.toString())
                subscribeToEditProfile(subscriptionBody)
            } else if (editProfileType == EditProfileEnum.EMAIL && checkEmail()) {
                subscribeToEmailEdit(emailEditText.text.toString())
            } else if (editProfileType == EditProfileEnum.NAME && checkName()) {
                subscriptionBody.put(editProfileType.getKey(), nameEditText.text.toString())
                subscribeToEditProfile(subscriptionBody)
            }

        }
    }

    private fun subscribeToEmailEdit(newEmail: String) {
        showProgressDialog()
        profileEditViewModel.changeEmail(newEmail).subscribe({
            dismissProgressDialog()
            activity?.onBackPressed()
        }, {
            dismissProgressDialog()
            val errorMessage = if (it is FirebaseAuthUserCollisionException) {
                getString(R.string.used_email)
            } else {
                getString(R.string.save_data_error)
            }

            DialogMaker.makeDialog(context, errorMessage, getString(R.string.ok), { dialogInterface, i ->
                dialogInterface.dismiss()
            }).show()
        })
    }

    private fun subscribeToEditProfile(body: HashMap<String, String>) {
        showProgressDialog()
        profileEditViewModel.editProfile(body).subscribe({
            dismissProgressDialog()
            saveDataInDataHolder()
            activity?.onBackPressed()
        }, {
            DialogMaker.makeDialog(context, getString(R.string.save_data_error), getString(R.string.ok), { dialogInterface: DialogInterface, view: Int ->
                dialogInterface.dismiss()
            }).show()
        })
    }

    private fun saveDataInDataHolder() {
        if (editProfileType == EditProfileEnum.WORK_PLACE) {
            AppDataHolder.getInstance().loggedInUser.workPlace = workPlaceEditText.text.toString()
        } else if (editProfileType == EditProfileEnum.SPECIALITY) {
            AppDataHolder.getInstance().loggedInUser.speciality = specialityEditText.text.toString()
        } else if(editProfileType == EditProfileEnum.NAME){
            AppDataHolder.getInstance().loggedInUser.name = nameEditText.text.toString()
        }
    }

    private fun checkEmail(): Boolean {
        val emailCheckResult = loginViewModel.checkEmail(emailEditText.text.toString())
        if (emailCheckResult != R.string.ok) {
            emailErrorText.visibility = VISIBLE
            emailErrorText.setText(emailCheckResult)
            return false
        } else {
            emailErrorText.visibility = GONE
            return true
        }
    }

    private fun checkName(): Boolean {
        val nameCheckResult = loginViewModel.checkName(nameEditText.text.toString())
        if (nameCheckResult != R.string.ok) {
            nameErrorText.visibility = VISIBLE
            nameErrorText.setText(nameCheckResult)
            return false
        } else {
            nameErrorText.visibility = GONE
            return true
        }
    }

    companion object {
        private val TYPE = "type"

        fun newInstance(enumValue: EditProfileEnum): EditProfileFragment {
            val fragment = EditProfileFragment()
            val bundle = Bundle()
            bundle.putSerializable(TYPE, enumValue)
            fragment.arguments = bundle
            return fragment
        }

        enum class EditProfileEnum {
            WORK_PLACE {
                override fun getKey() = "work_place"
            },
            SPECIALITY {
                override fun getKey() = "speciality"
            },
            EMAIL {
                override fun getKey() = "email"
            },
            NAME {
                override fun getKey() = "name"
            };

            abstract fun getKey(): String
        }
    }
}
