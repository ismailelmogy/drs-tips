package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.adapter.TipsViewPagerAdapter;
import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.viewModel.HomeViewModel;


public class TipsHomeFragment extends Fragment {



    TipsViewPagerAdapter adapter;
    @BindView(R.id.pageNumber)
    TextView pageNumber;
    @BindView(R.id.totalPages)
    TextView totalPages;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.next)
    ImageButton next;
    @BindView(R.id.tipsViewPagerId)
    ViewPager viewPager;

    @Inject
    HomeViewModel homeViewModel;
    HashMap<String,Tip> tips = new HashMap<>();



    public TipsHomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tips_home, container, false);
        ButterKnife.bind(this,view);
        ((DrTipsApplication)getActivity().getApplication()).getComponent().inject(this);
        homeViewModel.getTips().subscribe(
                tip -> {
                    this.tips.putAll(tip);
                },
                throwable -> {
                    Log.d("Throwable", throwable.getMessage());
                },
                ()->{
                    adapter = new TipsViewPagerAdapter(getActivity(), tips);
                    viewPager.setAdapter(adapter);
                    pageNumber.setText(Integer.toString(viewPager.getCurrentItem()+1));
                    totalPages.setText(Integer.toString( viewPager.getOffscreenPageLimit()+1));
                    viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener(){
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            pageNumber.setText(Integer.toString(position+1));
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = viewPager.getCurrentItem();
                            if (position > 0) {
                                position--;
                                viewPager.setCurrentItem(position);
                            } else if (position == 0) {
                                viewPager.setCurrentItem(position);
                            }
                        }
                    });
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = viewPager.getCurrentItem();
                            if (position  < viewPager.getOffscreenPageLimit()) {
                                position++;
                                viewPager.setCurrentItem(position);
                            } else if (position == viewPager.getOffscreenPageLimit()) {
                                viewPager.setCurrentItem(position);
                            }

                        }
                    });
                });
        return view;
    }



}
