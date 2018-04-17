package ocs.com.dr_tips.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.EditProfileFragment1;

public class HomeActivity extends  AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, new EditProfileFragment1()).commit();
    }
}
