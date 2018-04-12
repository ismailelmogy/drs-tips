package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.R;


public class HomeFragment extends Fragment {
    @BindView(R.id.show_packages)
    Button showPackages;


    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        PackagesDialogFragment packagesDialogFragment= new PackagesDialogFragment();
        showPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packagesDialogFragment.show(manager,"packagesDialogFragment");
            }
        });

        return view;
    }
}
