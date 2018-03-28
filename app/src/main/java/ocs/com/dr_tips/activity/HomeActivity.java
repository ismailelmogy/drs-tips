package ocs.com.dr_tips.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.About_usFragment;
import ocs.com.dr_tips.fragment.HomeFragment;
import ocs.com.dr_tips.fragment.ProfileFragment;
import ocs.com.dr_tips.fragment.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_item1:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.action_item2:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                    case R.id.action_item3:
                        selectedFragment = About_usFragment.newInstance();
                        break;
                    case R.id.action_item4:
                        selectedFragment = SettingsFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, selectedFragment);
                transaction.commit();
                return true;
            }
        });
        //Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
         getUserId();
    }
    public void getUserId(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                Log.wtf("Info"," UserId : "+firebaseUser.getUid()+" , DisplayName"+firebaseUser.getDisplayName());

            }
        };

    }
}
