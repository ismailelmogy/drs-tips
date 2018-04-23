package ocs.com.dr_tips.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ocs.com.dr_tips.DrTipsApplication;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.fragment.AboutUsFragment;
import ocs.com.dr_tips.fragment.HomeFragment;
import ocs.com.dr_tips.fragment.ProfileFragment;
import ocs.com.dr_tips.fragment.SettingsFragment;
import ocs.com.dr_tips.fragment.TipsHomeFragment;
import ocs.com.dr_tips.viewModel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private FirebaseAuth auth;
    @Inject
    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((DrTipsApplication)getApplication()).getComponent().inject(this);
       getSupportActionBar().setTitle("Home");
        GetUserData();
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                   GetUserData();
                   break;
                case R.id.profile:
                   launchFragment( new ProfileFragment());
                    break;
                case R.id.info:
                   launchFragment( new AboutUsFragment());
                    break;
                case R.id.settings:
                    launchFragment( new SettingsFragment());
                    break;
            }

            return true;
        });
        //Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }

       public void GetUserData(){
           auth = FirebaseAuth.getInstance();
               FirebaseUser firebaseUser = auth.getCurrentUser();
               if (firebaseUser!= null) {
                   String userId = firebaseUser.getUid();
                   Log.d("Info"," UserId : "+firebaseUser.getUid()+" , DisplayName"+firebaseUser.getDisplayName());
                   homeViewModel.getUserData(userId).subscribe(user -> {
                           if(user.getIsVerified()==false){
                               launchFragment( new HomeFragment());
                           }
                           else
                               launchFragment( new TipsHomeFragment());

                   },throwable -> {
                       Log.e("Drtips ERROR",throwable.getMessage());
                       Toast.makeText(this,"Error occured",Toast.LENGTH_SHORT).show();
                   });
               }
               else {
                   Log.e("Drtips ERROR","Firebase user = null");
                   Toast.makeText(this,"User not found",Toast.LENGTH_SHORT).show();

               }

        }

    public void launchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment)
                .commitAllowingStateLoss();
    }
}
