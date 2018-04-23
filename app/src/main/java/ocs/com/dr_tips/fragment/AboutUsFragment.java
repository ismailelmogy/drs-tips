package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.viewModel.HomeViewModel;

public class AboutUsFragment extends DrsTipsBaseFragment {

    @BindView(R.id.about_text)
    TextView aboutTextView;

    @Inject
    HomeViewModel homeViewModel;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this,view);
        showProgressDialog();
        ((DrTipsApplication)getActivity().getApplication()).getComponent().inject(this);
        homeViewModel.getAboutUsContent().subscribe(
               content -> {
                   dismissProgressDialog();
                    this.aboutTextView.setText(content);
                },
                throwable -> {
                   dismissProgressDialog();
                    Log.d("Throwable", throwable.getMessage());
                });

        return view;
    }

}
