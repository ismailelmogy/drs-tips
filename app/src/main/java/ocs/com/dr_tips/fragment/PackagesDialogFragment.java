package ocs.com.dr_tips.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.adapter.TipsPackageViewPagerAdapter;
import ocs.com.dr_tips.model.TipsPackage;
import ocs.com.dr_tips.viewModel.HomeViewModel;


public class PackagesDialogFragment extends DialogFragment {

    @BindView(R.id.packageViewPagerId)
    ViewPager packageViewPager;
    @BindView(R.id.tabDots)
    TabLayout tabDots;

    @Inject
    HomeViewModel homeViewModel;
    HashMap<String,TipsPackage> packages = new HashMap<>();




    public PackagesDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packages_dialog, container, false);
        ButterKnife.bind(this,view);
        tabDots.setupWithViewPager(packageViewPager, true);
        ((DrTipsApplication)getActivity().getApplication()).getComponent().inject(this);
        homeViewModel.getPackages().subscribe(
                tipsPackage -> {
                    this.packages.putAll(tipsPackage);

                },
                throwable -> {
                    Log.d("Throwable", throwable.getMessage());
                },
                ()->{
                    //get  values
                    ArrayList<TipsPackage> tipsArrayList = new ArrayList<TipsPackage>(packages.values());
                    //set view pager adapter
                    TipsPackageViewPagerAdapter adapter = new TipsPackageViewPagerAdapter(getActivity(), tipsArrayList);
                    packageViewPager.setAdapter(adapter);

                });

        return view;
    }
}
