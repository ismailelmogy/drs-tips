package ocs.com.dr_tips.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import ocs.com.dr_tips.R;

public class DrsTipsBaseFragment extends Fragment {
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext(), R.style.ProgressDialogTheme);
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

    protected void setToolbarWithBackBtn(@StringRes int titleRes) {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            setToolbar();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titleRes);
        }
    }
    protected void setToolbarWithBackBtn(String title) {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            setToolbar();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }


    private void setToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }
}
