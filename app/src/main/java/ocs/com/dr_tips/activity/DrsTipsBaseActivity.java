package ocs.com.dr_tips.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import ocs.com.dr_tips.R;

public class DrsTipsBaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
        progressDialog.setCancelable(false);

    }

    protected void showProgressDialog() {
        if(progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void dismissProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void setToolBarWithBackBtn(@StringRes int titleRes) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(titleRes);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
