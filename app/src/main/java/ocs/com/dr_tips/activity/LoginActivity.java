package ocs.com.dr_tips.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCommunicator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        launchLoginFragment();
    }

    private void launchLoginFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,new LoginFragment()).commitAllowingStateLoss();
    }

    @Override
    public void launchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,new LoginFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
        Log.d("LOGIN_ACTIVITY", "launching fragments");
    }
}
