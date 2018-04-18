package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.activity.LoginActivity;

public class SettingsFragment extends DrsTipsBaseFragment {
    @BindView(R.id.ic_change_pass)
    ImageView changePasswordIc;
    @BindView(R.id.ic_change_lang)
    ImageView changeLanguageIc;
    @BindView(R.id.ic_logout)
    ImageView logoutIc;
    @BindView(R.id.ic_payment_info)
    ImageView paymentInfoIc;
    @BindView(R.id.ic_contacr_us)
    ImageView contactUsIc;

    @OnClick(R.id.ic_logout)
    void Logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setToolbarWithBackBtn(R.string.settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}
