package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.activity.HomeActivity;
import ocs.com.dr_tips.activity.LoginActivity;

/**
 * Created by Randa on 3/18/2018.
 */

public class LoginFragment extends Fragment{


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
    private FirebaseAuth Auth;



    private LoginFragmentCommunicator communicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Auth = FirebaseAuth.getInstance();
        if (Auth.getCurrentUser() != null) {
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
        else
            startActivity(new Intent(getActivity(), LoginActivity.class));


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container,false);
        ButterKnife.bind(this, view);
        setListeners();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");


        //make underline
        forgetPassword.setText(Html.fromHtml(getString(R.string.forget_password)));
        validateEmailPass();
        Login();
        return view;
    }

    private void Login() {
        login.setOnClickListener(view-> {
            String inputEmail = email.getText().toString();
            String inputPassword = password.getText().toString();
            if (emailErrorText.getVisibility() != View.VISIBLE && PasswordErrorText.getVisibility() != View.VISIBLE) {
                //authenticate user
                Auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = Auth.getCurrentUser();
                                    Toast.makeText(getContext(), "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getActivity(), HomeActivity.class);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseAuthInvalidUserException)
                                        Toast.makeText(getContext(), R.string.unfound_user, Toast.LENGTH_SHORT).show();
                                    else if (exception instanceof FirebaseAuthInvalidCredentialsException)
                                        Toast.makeText(getContext(), R.string.invalid_password, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else
                Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();

        });
    }


    private void validateEmailPass() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty() || s.length() < 6)
                {
                    PasswordErrorText.setText("password Length is too short");
                    PasswordErrorText.setVisibility(View.VISIBLE);

                }
                else
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

                if (!s.toString().matches(emailPattern) || s.toString().isEmpty())
                {
                    emailErrorText.setText("invalid email");
                    emailErrorText.setVisibility(View.VISIBLE);

                }
                else
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
            communicator = (LoginFragmentCommunicator)context;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", "Plz implement my communicator");
        }

    }

    private void setListeners() {
        forgetPassword.setOnClickListener(view-> {
            if(communicator != null) {
                //TODO: Change it to Forget password Fragment
                ForgetPasswordFragment fragment=new ForgetPasswordFragment();
                communicator.launchFragment( fragment);
            }
        });
        register.setOnClickListener(view-> {
            if(communicator != null) {
                //TODO: Change it to Forget password Fragment
                RegisterFragment fragment=new RegisterFragment();
                communicator.launchFragment( fragment);
            }
        });
    }

    public interface LoginFragmentCommunicator {
        void launchFragment(Fragment fragment);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.menu,menu);
       super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_arabic:
                Toast.makeText(getContext(),"Arabic selected",Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
