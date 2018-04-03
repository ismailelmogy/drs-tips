package ocs.com.dr_tips.fragment;

/**
 * Created by sherif on 05/12/17.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ocs.com.dr_tips.R;

/**
 * Used to display dialog for user to select Camera or Gallery for selecting images
 */
public class PickImageDialogFragment extends DialogFragment {
    Unbinder unbinder;
    private OnActionPickedListener mListener;

    public PickImageDialogFragment() {
        // Required empty public constructor
    }

    public static PickImageDialogFragment newInstance(OnActionPickedListener listener) {
        PickImageDialogFragment fragment = new PickImageDialogFragment();
        fragment.setOnActionPickedListenerListener(listener);
        return fragment;
    }

    @OnClick(R.id.Btn_Dialog_Image_Picker_Camera)
    public void openCamera() {
        if (mListener != null) {
            mListener.onActionPicked(true);
        }
        dismiss();
    }

    @OnClick(R.id.Btn_Dialog_Image_Picker_Gallery)
    public void openGallery() {
        if (mListener != null) {
            mListener.onActionPicked(false);
        }
        dismiss();
    }

    public void setOnActionPickedListenerListener(OnActionPickedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_picker_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnActionPickedListener {
        void onActionPicked(boolean isCameraChosen);
    }
}

