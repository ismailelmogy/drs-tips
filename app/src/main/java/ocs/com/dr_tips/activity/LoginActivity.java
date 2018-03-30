package ocs.com.dr_tips.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentCommunicator{
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private FirebaseAuth auth;
    private ActivityResultCallback activityResultCallback;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultCallback != null) {
            activityResultCallback.onActivityResultCalled(requestCode, resultCode, data);
        }
    }

    @Override
    public void setActivityResultCallback(ActivityResultCallback callback) {
        activityResultCallback = callback;
    }

    public interface ActivityResultCallback {
        void onActivityResultCalled(int requestCode, int resultCode, Intent data);
    }
}
