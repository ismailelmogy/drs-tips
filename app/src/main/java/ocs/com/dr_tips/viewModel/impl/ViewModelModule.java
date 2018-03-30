package ocs.com.dr_tips.viewModel.impl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.viewModel.HomeViewModel;
import ocs.com.dr_tips.viewModel.LoginViewModel;

/**
 * Created by Randa on 3/18/2018.
 */
@Module
public class ViewModelModule {
    @Provides
    @Singleton
    HomeViewModel providesHomeViewModel(HomeAPI api){
        return new HomeViewModelImpl(api);
    }

    @Provides
    @Singleton
    LoginViewModel providesLoginViewModel(LoginAPI api) {
        return new LoginViewModelImpl(api);
    }
}
