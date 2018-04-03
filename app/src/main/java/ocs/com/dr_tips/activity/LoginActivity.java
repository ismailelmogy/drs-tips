package ocs.com.dr_tips.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.LoginFragment;
import ocs.com.dr_tips.fragment.RegisterFragment;
import ocs.com.dr_tips.util.AppDataHolder;
import ocs.com.dr_tips.util.DialogMaker;
import ocs.com.dr_tips.viewModel.LoginViewModel;

public class LoginActivity extends DrsTipsBaseActivity implements LoginFragment.LoginFragmentCommunicator{
    private static final String TAG = "tag";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private FirebaseAuth auth;
    private ActivityResultCallback activityResultCallback;

    @Inject
    LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((DrTipsApplication)getApplication()).getComponent().inject(this);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            subscribeToGetUserData();
        }
        else
            launchLoginFragment();
    }

    private void launchLoginFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,new LoginFragment(),TAG).commitAllowingStateLoss();
    }

    private void subscribeToGetUserData() {
        if(auth.getCurrentUser() != null) {
            showProgressDialog();
            loginViewModel.getUserData(auth.getCurrentUser().getUid()).subscribe(user -> {
                dismissProgressDialog();
                if (user != null){
                    AppDataHolder.getInstance().setLoggedInUser(user);
                    startActivity(new Intent(this,HomeActivity.class));
                    finish();
                } else {
                    launchFragment(new RegisterFragment());
                }
            },throwable -> {
                //TODO: handle errors
                dismissProgressDialog();
                DialogMaker.makeDialog(this, getString(R.string.login_failed), getString(R.string.ok),
                        (dialogInterface, i) -> dialogInterface.dismiss()).show();
                Log.e("DRERROR",throwable.toString());
                launchLoginFragment();
            });
        } else {
            DialogMaker.makeDialog(this, getString(R.string.login_failed), getString(R.string.ok),
                    (dialogInterface, i) -> dialogInterface.dismiss()).show();
            launchLoginFragment();
        }
    }

    @Override
    public void launchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment,TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(TAG) instanceof LoginFragment) {
            super.onBackPressed();
        }
        else {
            launchLoginFragment();
        }
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
