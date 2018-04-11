package ocs.com.dr_tips;
import javax.inject.Singleton;
import dagger.Component;
import ocs.com.dr_tips.activity.HomeActivity;
import ocs.com.dr_tips.fragment.LoginFragment;
import ocs.com.dr_tips.fragment.PackagesDialogFragment;
import ocs.com.dr_tips.fragment.TipsHomeFragment;

/**
 * Created by Randa on 3/18/2018.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject (DrTipsApplication app);
    void inject (LoginFragment loginFragment);
    void inject (HomeActivity homeActivity);
    void inject (TipsHomeFragment tipsHomeFragment);
    void inject (PackagesDialogFragment packagesDialogFragment);
}
