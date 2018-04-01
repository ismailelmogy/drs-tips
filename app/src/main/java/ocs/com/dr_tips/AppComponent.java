package ocs.com.dr_tips;
import javax.inject.Singleton;
import dagger.Component;
import ocs.com.dr_tips.activity.HomeActivity;
import ocs.com.dr_tips.activity.LoginActivity;
import ocs.com.dr_tips.fragment.HomeFragment;
import ocs.com.dr_tips.fragment.LoginFragment;
import ocs.com.dr_tips.fragment.TipsHomeFragment;

/**
 * Created by Randa on 3/18/2018.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject (DrTipsApplication app);
    void inject(LoginActivity loginActivity);
    void inject (LoginFragment loginFragment);
    void inject (HomeActivity homeActivity);
    void inject (HomeFragment homeFragment);
    void inject(TipsHomeFragment tipsHomeFragment);


}
