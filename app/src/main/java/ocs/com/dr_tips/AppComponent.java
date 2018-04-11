package ocs.com.dr_tips;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;
import ocs.com.dr_tips.activity.LoginActivity;
import ocs.com.dr_tips.fragment.ChangePasswordFragment;
import ocs.com.dr_tips.fragment.LoginFragment;
import ocs.com.dr_tips.fragment.PickImageDialogFragment;
import ocs.com.dr_tips.fragment.RegisterFragment;

/**
 * Created by Randa on 3/18/2018.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject (DrTipsApplication app);
    void inject (LoginFragment loginFragment);

    void inject(LoginActivity loginActivity);

    void inject(RegisterFragment registerFragment);

    void inject(@NotNull PickImageDialogFragment pickImageDialogFragment);

    void inject(@NotNull ChangePasswordFragment changePasswordFragment);
}
