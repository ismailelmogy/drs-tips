package ocs.com.dr_tips.fragment

/**
 * Created by sherif on 05/12/17.
 */

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v13.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import kotlinx.android.synthetic.main.fragment_image_picker_dialog.*
import ocs.com.dr_tips.DrTipsApplication
import ocs.com.dr_tips.R
import ocs.com.dr_tips.util.Utils
import javax.inject.Inject

/**
 * Used to display dialog for user to select Camera or Gallery for selecting images
 */
class PickImageDialogFragment : DialogFragment() {
    private var mListener: OnImagePickedListener? = null
    private val CAMERA_REQUEST: Int = 1
    private val GALLERY_REQUEST: Int = 2
    @Inject
    lateinit var imageLoader: ImageLoader

    fun setOnActionPickedListenerListener(mListener: OnImagePickedListener) {
        this.mListener = mListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as DrTipsApplication).component.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_image_picker_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Btn_Dialog_Image_Picker_Camera.setOnClickListener {
            if (askForWriteExternalStoragePermission()) {
                openCamera()
            }
        }
        Btn_Dialog_Image_Picker_Gallery.setOnClickListener {
            if (askForWriteExternalStoragePermission()) {
                openGallery()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnImagePickedListener {
        fun onImagePicked(selectedImage: Bitmap)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), GALLERY_REQUEST)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST)
    }

    companion object {
        fun newInstance(listener: OnImagePickedListener): PickImageDialogFragment {
            val fragment = PickImageDialogFragment()
            fragment.setOnActionPickedListenerListener(listener)
            return fragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Utils.fixMediaDir()
                val profilePic = data?.extras?.get("data") as Bitmap
                mListener?.onImagePicked(profilePic)
                dismiss()
            } else if (requestCode == GALLERY_REQUEST) {
                val imageUri = data?.data
                imageLoader.loadImage(imageUri.toString(), object : ImageLoadingListener {
                    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                        mListener?.onImagePicked(loadedImage!!)
                        dismiss()
                    }

                    override fun onLoadingStarted(imageUri: String?, view: View?) {
                    }

                    override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    }

                    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    }

                })
            }
        }
    }

    fun askForWriteExternalStoragePermission(): Boolean {
        if ((ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE))||
                (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)) ) {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 1)
            return false
        }
        return true
    }
}

