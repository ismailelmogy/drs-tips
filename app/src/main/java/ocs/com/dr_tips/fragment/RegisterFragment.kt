package ocs.com.dr_tips.fragment


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import kotlinx.android.synthetic.main.fragment_register.*
import ocs.com.dr_tips.DrTipsApplication
import ocs.com.dr_tips.R
import ocs.com.dr_tips.activity.HomeActivity
import ocs.com.dr_tips.adapter.CountriesHintArrayAdapter
import ocs.com.dr_tips.model.Country
import ocs.com.dr_tips.model.User
import ocs.com.dr_tips.util.AppDataHolder
import ocs.com.dr_tips.util.DialogMaker
import ocs.com.dr_tips.util.Utils
import ocs.com.dr_tips.viewModel.LoginViewModel
import javax.inject.Inject


class RegisterFragment : DrsTipsBaseFragment() {

    private val registeredUser: User = User()
    private var countriesData: MutableList<Country> = ArrayList()

    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var imageLoader : ImageLoader
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as DrTipsApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_register, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarWithBackBtn(R.string.registration)

        if (auth.currentUser != null) {
            auth.currentUser?.let {
                emailEditText.setText(it.email)
                passwordView.visibility = GONE
                confirmPasswordView.visibility = GONE
                nameEditText.setText(it.displayName)
                imageLoader.displayImage(it.photoUrl.toString(), profilePhotoImageView, object : ImageLoadingListener {
                    override fun onLoadingStarted(imageUri: String?, view: View?) {

                    }

                    override fun onLoadingCancelled(imageUri: String?, view: View?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                        profilePhotoImageView.setImageResource(R.drawable.register_profile_pic)
                    }

                    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                        registeredUser.profilePictureInBase64 = loadedImage?.let { it1 -> Utils.encodeToBase64String(it1) }
                    }

                })
            }
        } else {
            profilePhotoImageView.setImageResource(R.drawable.register_profile_pic)
        }

        registerButton.setOnClickListener {
            if (checkInputs()) {
                //Set data inside User object
                registeredUser.isPaid = false
                registeredUser.name = nameEditText.text.toString()
                registeredUser.mobileNumber = mobileEditText.text.toString()
                if (!specialityEditText.text.isNullOrBlank()) {
                    registeredUser.speciality = specialityEditText?.text.toString()
                }
                if (!workPlaceEditText.text.isNullOrBlank()) {
                    registeredUser.workPlace = workPlaceEditText.text.toString()
                }
                if (!titleEditText.text.isNullOrBlank()) {
                    registeredUser.title = titleEditText.text.toString()
                }

                if (auth.currentUser == null) {
                    subscribeToRegister()
                } else {
                    subscribeToSaveData()
                }
            }
        }

        choosePhotoView.setOnClickListener {
            val imagePicker = PickImageDialogFragment.newInstance(object: PickImageDialogFragment.OnImagePickedListener{
                override fun onImagePicked(selectedImage: Bitmap) {
                    profilePhotoImageView.setImageBitmap(selectedImage)
                    registeredUser.profilePictureInBase64 = Utils.encodeToBase64String(selectedImage)
                }
            })
            imagePicker.show(childFragmentManager, "TAG")
        }

        loadCountriesSpinner()

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val selectedItem = countriesData.get(position)
                mobileEditText.setText(selectedItem.mobileCode)
            }
        }
    }

    private fun loadCountriesSpinner() {
        try {
            val data = loginViewModel.getCountriesDataFromJson(context)
            data.sortBy { it.name }
            data.add(0, Country(getString(R.string.country)))
            countriesData.addAll(data)
            val adapter = CountriesHintArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, data)
            countrySpinner.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun subscribeToSaveData() {
        showProgressDialog()
        loginViewModel.setUserData(registeredUser).subscribe({
            dismissProgressDialog()
            AppDataHolder.getInstance().loggedInUser = registeredUser
            startActivity(Intent(context, HomeActivity::class.java))
            activity?.finish()
        }, {
            dismissProgressDialog()
            DialogMaker.makeDialog(context, getString(R.string.register_failed), getString(R.string.ok)
                    , { dialogInterface, view -> dialogInterface.dismiss() }).show()
        })
    }

    private fun subscribeToRegister() {
        showProgressDialog()
        loginViewModel.registerWithEmailAndPassword(emailEditText.text.toString()
                , passwordEditText.text.toString()).subscribe(this::subscribeToSaveData, {
            dismissProgressDialog()
            if (it is FirebaseAuthUserCollisionException) {
                DialogMaker.makeDialog(context, getString(R.string.email_exists), getString(R.string.ok)
                        , { dialogInterface, view -> dialogInterface.dismiss() }).show()
            } else {
                DialogMaker.makeDialog(context, getString(R.string.register_failed), getString(R.string.ok)
                        , { dialogInterface, view -> dialogInterface.dismiss() }).show()
            }
        })
    }

    private fun checkInputs(): Boolean {
        val nameCheckResult = loginViewModel.checkName(nameEditText.text.toString())
        val emailCheckResult = loginViewModel.checkEmail(emailEditText.text.toString())
        val passwordCheckResult = loginViewModel.checkPassword(passwordEditText.text.toString())
        val passwordConfirmationCheckResult = loginViewModel.isPasswordConfirmed(passwordEditText.text.toString(),
                confirmPasswordEditText.text.toString())
        val mobileCheckResult = loginViewModel.checkMobileNumber(mobileEditText.text.toString())

        var isError = true


        if (nameCheckResult != R.string.ok) {
            nameErrorText.visibility = VISIBLE
            nameErrorText.setText(nameCheckResult)
            isError = false
        } else {
            nameErrorText.visibility = GONE
        }
        if (emailCheckResult != R.string.ok) {
            emailErrorText.visibility = VISIBLE
            emailErrorText.setText(emailCheckResult)
            isError = false
        } else {
            emailErrorText.visibility = GONE
        }
        if (auth.currentUser == null) {
            if (passwordCheckResult != R.string.ok) {
                passwordErrorText.visibility = VISIBLE
                passwordErrorText.setText(passwordCheckResult)
                isError = false
            } else {
                passwordErrorText.visibility = GONE
            }
            if (passwordConfirmationCheckResult != R.string.ok) {
                confirmPasswordErrorText.visibility = VISIBLE
                confirmPasswordErrorText.setText(passwordConfirmationCheckResult)
                isError = false
            } else {
                confirmPasswordErrorText.visibility = GONE
            }
        }
        if (mobileCheckResult != R.string.ok) {
            mobileErrorText.visibility = VISIBLE
            mobileErrorText.setText(mobileCheckResult)
            isError = false
        } else {
            mobileErrorText.visibility = GONE
        }
        if (countrySpinner.selectedItemPosition == 0){
            countryErrorText.visibility = VISIBLE
            countryErrorText.setText(R.string.country_not_selected)
            isError = false
        } else {
            countryErrorText.visibility = GONE
        }
        return isError
    }

}
