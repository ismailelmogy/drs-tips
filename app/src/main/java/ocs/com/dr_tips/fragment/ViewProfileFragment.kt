package ocs.com.dr_tips.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth

import java.util.HashMap

import javax.inject.Inject

import butterknife.ButterKnife
import butterknife.Unbinder
import kotlinx.android.synthetic.main.fragment_view_profile.*
import ocs.com.dr_tips.DrTipsApplication
import ocs.com.dr_tips.R
import ocs.com.dr_tips.model.Country
import ocs.com.dr_tips.model.User
import ocs.com.dr_tips.util.AppDataHolder
import ocs.com.dr_tips.util.Constants
import ocs.com.dr_tips.util.DialogMaker
import ocs.com.dr_tips.util.Utils
import ocs.com.dr_tips.viewModel.LoginViewModel
import ocs.com.dr_tips.viewModel.ProfileEditViewModel

/**
 * Created by Rawy on 11/04/2018.
 */

class ViewProfileFragment : DrsTipsBaseFragment() {
    private var profilePictureToBase64 = ""
    private lateinit var registeredUer: User
    private var auth: FirebaseAuth? = null
    var countriesList: List<Country> = ArrayList()
    @Inject
    lateinit var profileEditViewModel: ProfileEditViewModel
    @Inject
    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as DrTipsApplication).component.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        registeredUer = AppDataHolder.getInstance().loggedInUser
        countriesList = loginViewModel.getCountriesDataFromJson(context)
        putUserDataToProfileViews()
        profilePicImageView.setOnClickListener {
            val pickImageDialogFragment = PickImageDialogFragment()
            pickImageDialogFragment.setOnActionPickedListenerListener(object : PickImageDialogFragment.OnImagePickedListener {
                override fun onImagePicked(selectedImage: Bitmap) {
                    profilePictureToBase64 = Utils.encodeToBase64String(selectedImage)
                    subscribeToUpdateProfilePic(profilePictureToBase64)
                }
            })
            pickImageDialogFragment.show(childFragmentManager, Constants.TAG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_view_profile, container, false)


    fun subscribeToUpdateProfilePic(profilePic: String) {
        showProgressDialog()
        val body = HashMap<String, String>()
        body.put(Constants.PROFILE_PIC_KEY, profilePic)
        profileEditViewModel.updateUserProfilePic(body).subscribe({
            dismissProgressDialog()
            profilePicImageView.setImageBitmap(Utils.decodeBitmapFromStr(profilePic))
            registeredUer.profilePictureInBase64 = profilePic
            AppDataHolder.getInstance().loggedInUser = registeredUer
            DialogMaker.makeDialog(context, getString(R.string.profile_pic_saved_success), getString(R.string.ok)
            ) { dialogInterface, i -> dialogInterface.dismiss() }.show()
        }) { throwable ->
            if (throwable is FirebaseNetworkException) {
                dismissProgressDialog()
                DialogMaker.makeDialog(context, getString(R.string.update_profile_pic_failed), getString(R.string.ok)
                ) { dialogInterface, i -> dialogInterface.dismiss() }.show()
            } else {
                dismissProgressDialog()
                DialogMaker.makeDialog(context, getString(R.string.save_data_error), getString(R.string.ok)
                ) { dialogInterface, i -> dialogInterface.dismiss() }.show()
            }


        }
    }


    fun putUserDataToProfileViews() {
        if (registeredUer != null) {
            fetchCountryNameFromCode(registeredUer.countryId)
            val userName = registeredUer.name
            val mobile = registeredUer.mobileNumber
            val speciality = registeredUer.speciality
            val workPlace = registeredUer.workPlace
            val userEmail = auth!!.currentUser!!.email
            val packageId = registeredUer.packageId
            profilePictureToBase64 = registeredUer.profilePictureInBase64
            profileNameTextView.text = userName
            profileEmailTextView.text = userEmail
            profileMobileTextView.setText(mobile)
            profilePlaceOfWorkTextView.setText(workPlace)
            profileSpecialityTextView.setText(speciality)
            profilePicImageView!!.setImageBitmap(Utils.decodeBitmapFromStr(profilePictureToBase64))
            if (packageId == 0)
                profileCurrentPlanTextView.setText(R.string.empty_field)
            else
                profileCurrentPlanTextView.text = R.string.package_name.toString() + " " + packageId
        }
    }


    fun TextView.setText(data: String?) {
        if (data == null)
            this.setText(R.string.empty_field)
        else
            this.text = data
    }


    fun fetchCountryNameFromCode(countryCode: String?) {
        if (countryCode != null) {
            for (country in countriesList) {
                if (country != null) {
                    if (country.countryCode == countryCode) {
                       profileCountryTextView.setText(country.name)
                    }
                }
            }
        } else
            profileCountryTextView.setText(null);

    }
}