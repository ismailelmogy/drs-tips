package ocs.com.dr_tips.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.model.TipsPackage;

/**
 * Created by Alamrawy on 4/5/2018.
 */

public class TipsPackageViewPagerAdapter extends PagerAdapter {
    private ArrayList<TipsPackage> tipsPackage;
    private Context context;

    public TipsPackageViewPagerAdapter(Context context,ArrayList<TipsPackage> tipsPackage){
        this.context=context;
        this.tipsPackage=tipsPackage;

    }
    @Override
    public int getCount() {
        return tipsPackage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }
    @Override
    public Object instantiateItem(ViewGroup container , int position){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.tips_package,container,false);
        TextView textView = (TextView) itemView.findViewById(R.id.packageDescriptionId);
        textView.setText(tipsPackage.get(position).getDescription());
        container.addView(itemView);
        return itemView;
    }





    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
