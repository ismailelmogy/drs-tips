package ocs.com.dr_tips.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.activity.LoginActivity;

/**
 * Created by Randa on 3/18/2018.
 */

public class LoginFragment extends Fragment{
    @BindView(R.id.hello_fragment)
    TextView helloFragmentTv;

    @BindView(R.id.register_now_tv)
    TextView registerNow;

    private LoginFragmentCommunicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setListeners();
        return view;
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
        registerNow.setOnClickListener(view-> {
            if(communicator != null) {
                //TODO: Change it to Register Fragment
                communicator.launchFragment(new Fragment());
            }
        });
    }

    public interface LoginFragmentCommunicator {
        void launchFragment(Fragment fragment);
    }
}
