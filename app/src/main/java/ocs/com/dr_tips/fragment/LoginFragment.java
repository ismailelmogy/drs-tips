package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.activity.HomeActivity;
import ocs.com.dr_tips.activity.LoginActivity;
import ocs.com.dr_tips.util.AppDataHolder;
import ocs.com.dr_tips.util.DialogMaker;
import ocs.com.dr_tips.viewModel.LoginViewModel;

/**
 * Created by Randa on 3/18/2018.
 */

public class LoginFragment extends DrsTipsBaseFragment {
    @BindView(R.id.forgetPassword)
    TextView forgetPassword;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.emailerror)
    TextView emailErrorText;
    @BindView(R.id.passworderror)
    TextView PasswordErrorText;
    @BindView(R.id.fab_btn)
    FloatingActionButton register;

    @Inject
    LoginViewModel viewModel;
    private FirebaseAuth auth;
    private CallbackManager callbackManager;
    private LoginFragmentCommunicator communicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DrTipsApplication)getActivity().getApplication()).getComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setListeners();
        auth = FirebaseAuth.getInstance();
        setupFacebookLogin();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //make underline
        forgetPassword.setText(Html.fromHtml(getString(R.string.forget_password)));
        validateEmailPass();
        Login();
        return view;
    }

    private void Login() {
        login.setOnClickListener(view -> {
            String inputEmail = email.getText().toString();
            String inputPassword = password.getText().toString();
            if (emailErrorText.getVisibility() != View.VISIBLE && PasswordErrorText.getVisibility() != View.VISIBLE) {
                //authenticate user
                showProgressDialog();
                auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                        .addOnCompleteListener(task -> {
                            dismissProgressDialog();
                            if (task.isSuccessful()) {
                                getUserData();
                            } else {
                                // If sign in fails, display a message to the user.
                                Exception exception = task.getException();
                                if (exception instanceof FirebaseAuthInvalidUserException)
                                    Toast.makeText(getContext(), R.string.unfound_user, Toast.LENGTH_SHORT).show();
                                else if (exception instanceof FirebaseAuthInvalidCredentialsException)
                                    Toast.makeText(getContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else
                Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();

        });
    }

    @OnClick(R.id.facebook)
    void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this
                , Arrays.asList(getResources().getStringArray(R.array.fb_login_permissions)));
    }

    @OnClick(R.id.fab_btn)
    void register(){
        communicator.launchFragment(new RegisterFragment());
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                subscribeToFacebookLogin(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), R.string.facebook_login_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                DialogMaker.makeDialog(getContext(), getString(R.string.facebook_login_error), getString(R.string.ok),
                        (dialogInterface, i) -> dialogInterface.dismiss()).show();
            }
        });
    }

    private void subscribeToFacebookLogin(AccessToken accessToken) {

        viewModel.loginWithFacebook(accessToken).subscribe(this::getUserData, throwable -> {
            DialogMaker.makeDialog(getContext(), getString(R.string.facebook_login_error)
                    , getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss()).show();
            Log.e("DRERROR",throwable.toString());
        });
    }

    private void getUserData() {
        if(auth.getCurrentUser() != null) {
            showProgressDialog();
            viewModel.getUserData(auth.getCurrentUser().getUid()).subscribe(user -> {
                dismissProgressDialog();
                if (user != null){
                    AppDataHolder.getInstance().setLoggedInUser(user);
                    startActivity(new Intent(getContext(),HomeActivity.class));
                    getActivity().finish();
                } else {
                    communicator.launchFragment(new RegisterFragment());
                }
            },throwable -> {
                //TODO: handle errors
                dismissProgressDialog();
                DialogMaker.makeDialog(getContext(), getString(R.string.login_failed), getString(R.string.ok),
                        (dialogInterface, i) -> dialogInterface.dismiss()).show();
                Log.e("DRERROR",throwable.toString());
            });
        } else {
            DialogMaker.makeDialog(getContext(), getString(R.string.login_failed), getString(R.string.ok),
                    (dialogInterface, i) -> dialogInterface.dismiss()).show();
        }
    }

    private void validateEmailPass() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.length() < 6) {
                    PasswordErrorText.setText("password Length is too short");
                    PasswordErrorText.setVisibility(View.VISIBLE);

                } else
                    PasswordErrorText.setVisibility(View.GONE);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!s.toString().matches(emailPattern) || s.toString().isEmpty()) {
                    emailErrorText.setText("invalid email");
                    emailErrorText.setVisibility(View.VISIBLE);

                } else
                    emailErrorText.setVisibility(View.GONE);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communicator = (LoginFragmentCommunicator) context;
            communicator.setActivityResultCallback(this::onActivityResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", "Plz implement my communicator");
        }

    }

    private void setListeners() {
        forgetPassword.setOnClickListener(view -> {
            if (communicator != null) {
                //TODO: Change it to Forget password Fragment
                ForgetPasswordFragment fragment = new ForgetPasswordFragment();
                communicator.launchFragment(fragment);
            }
        });
        register.setOnClickListener(view -> {
            if (communicator != null) {
                //TODO: Change it to Forget password Fragment
                RegisterFragment fragment = new RegisterFragment();
                communicator.launchFragment(fragment);
            }
        });

    }

    public interface LoginFragmentCommunicator {
        void launchFragment(Fragment fragment);

        void setActivityResultCallback(LoginActivity.ActivityResultCallback callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_arabic:
                Toast.makeText(getContext(), "Arabic selected", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
