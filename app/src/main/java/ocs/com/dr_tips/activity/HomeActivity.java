package ocs.com.dr_tips.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.TipsHomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        TipsHomeFragment tipsHomeFragment = new TipsHomeFragment();
        setContentView(R.layout.activity_home);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainer,tipsHomeFragment,"tipsFragment");
        transaction.commit();

    }
}
