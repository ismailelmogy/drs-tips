package ocs.com.dr_tips.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCommunicator{
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else
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
    }
}
