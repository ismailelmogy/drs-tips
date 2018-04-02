package ocs.com.dr_tips.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.viewModel.HomeViewModel;

import static java.lang.Integer.parseInt;

/**
 * Created by Alamrawy on 3/26/2018.
 */

public class TipsViewPagerAdapter  extends PagerAdapter {
    private HashMap <String,Tip> tips;
    private Context context;
    private LayoutInflater layoutInflater;
    Set<String> keys;
    String[] keyArray;

    public TipsViewPagerAdapter(Context context , HashMap<String,Tip> tips){
        this.context=context;
        this.tips=tips;
        this.keys = tips.keySet();
        this.keyArray = keys.toArray(new String[keys.size()]);
    }
    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }
    @Override
    public Object instantiateItem(ViewGroup container , int position){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.tip,container,false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.tipImageId);
        TextView textView = (TextView) itemView.findViewById(R. id.tipTextId);
        imageView.setImageBitmap(tips.get(this.keyArray[position]).getBitmapImg());
        textView.setText(tips.get(this.keyArray[position]).getText());
        container.addView(itemView);
        return itemView;
    }





    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
