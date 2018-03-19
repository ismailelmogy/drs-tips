package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import org.w3c.dom.Text;

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
    TextView ForgetPassword;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.emailerror)
    TextView EmailErrorText;
    @BindView(R.id.passworderror)
    TextView PasswordErrorText;
    private FirebaseAuth Auth;



    private LoginFragmentCommunicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container);
        ButterKnife.bind(this, view);
        setListeners();
        //make underline
        ForgetPassword.setText(Html.fromHtml(String.format(getString(R.string.forget_password))));
        validateEmail();
        validatePassword();
        Login();
        return view;
    }

    private void Login() {
        Auth = FirebaseAuth.getInstance();
        String inputEmail = email.getText().toString();
        final String inputPassword = password.getText().toString();
        login.setOnClickListener(view->{
            if (TextUtils.isEmpty(inputEmail)) {
                Toast.makeText(getContext(), "Enter email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(inputPassword)) {
                Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            //authenticate user
            Auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "signInWithEmail:success");
                                FirebaseUser user = Auth.getCurrentUser();
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
      });
    }

    private void validatePassword() {
         String passvalidate = password.getText().toString();
         password.addTextChangedListener(new TextWatcher() {
             public void afterTextChanged(Editable s) {
                 if(TextUtils.isEmpty(passvalidate) || passvalidate.length() < 6)
                  {
                     PasswordErrorText.setError("password Length is too short");
                 }
             }
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                 // other stuffs
             }
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 // other stuffs
             }
         });
    }

    private void validateEmail() {

        String emailvalidate = email.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (!emailvalidate.matches(emailPattern) || s.length() < 0)
                {
                    EmailErrorText.setText("invalid email");
                }
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
        ForgetPassword.setOnClickListener(view-> {
            if(communicator != null) {
                //TODO: Change it to Forget password Fragment
                communicator.launchFragment(new Fragment());
            }
        });
    }

    public interface LoginFragmentCommunicator {
        void launchFragment(Fragment fragment);
    }
}
