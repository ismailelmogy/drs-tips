package ocs.com.dr_tips.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth

import java.io.IOException
import java.util.HashMap

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_edit_profile.*
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

class EditProfileFragment1 : DrsTipsBaseFragment() {
    var profilePictureToBase64 = ""
    var registeredUer: User? = null
    var auth: FirebaseAuth? = null
    var appDataHolder: AppDataHolder? = null
    var unbinder: Unbinder? = null
    private var countriesList: MutableList<Country> = ArrayList()
    var layoutView: View? = null
    var profileEditViewModel: ProfileEditViewModel? = null
        @Inject set
    var loginViewModel: LoginViewModel? = null
        @Inject set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as DrTipsApplication).component.inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDataHolder = AppDataHolder.getInstance()
        auth = FirebaseAuth.getInstance()
        registeredUer = appDataHolder!!.loggedInUser
        var data = loginViewModel!!.getCountriesDataFromJson(context)
        countriesList.addAll(data)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (layoutView == null)
            layoutView = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        unbinder = ButterKnife.bind(this, layoutView!!)
        return layoutView
    }


    fun subscribeToUpdateProfilePic(profilePic: String) {
        showProgressDialog()
        val body = HashMap<String, String>()
        body.put(Constants.PROFILE_PIC_KEY, profilePic)
        profileEditViewModel!!.updateUserProfilePic(body).subscribe({
            dismissProgressDialog()
            profilePicImageView!!.setImageBitmap(Utils.decodeBitmapFromStr(profilePic))
            registeredUer!!.profilePictureInBase64 = profilePic
            appDataHolder!!.loggedInUser = registeredUer
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
            fetchCountryNameFromCode(registeredUer!!.countryId)
            val userName = registeredUer!!.name
            val mobile = registeredUer!!.mobileNumber
            val speciality = registeredUer!!.speciality
            val workPlace = registeredUer!!.workPlace
            val userEmail = auth!!.currentUser!!.email
            val packageId = registeredUer!!.packageId
            profilePictureToBase64 = registeredUer!!.profilePictureInBase64
            profileNameTextView!!.text = userName
            profileEmailTextView!!.text = userEmail
            showDataToView(profileMobileTextView, mobile)
            showDataToView(profilePlaceOfWorkTextView, workPlace)
            showDataToView(profileSpecialityTextView, speciality)
            profilePicImageView!!.setImageBitmap(Utils.decodeBitmapFromStr(profilePictureToBase64))
            if (packageId == 0)
                profileCurrentPlanTextView!!.setText(R.string.empty_field)
            else
                profileCurrentPlanTextView!!.text = R.string.package_name.toString() + " " + packageId
        }
    }

    fun showDataToView(textView: TextView?, data: String?) {
        if (data == null)
            textView!!.setText(R.string.empty_field)
        else
            textView!!.setText(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    fun fetchCountryNameFromCode(countryCode: String?) {
        if (countryCode != null) {
            for (country in countriesList) {
                if (country != null) {
                    if (country.countryCode == countryCode) {
                        showDataToView(profileCountryTextView, country.name)
                    }
                }
            }
        } else
            showDataToView(profileCountryTextView, null)


    }
}