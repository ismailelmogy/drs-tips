package ocs.com.dr_tips.dataLayer.impl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.dataLayer.ProfileEditAPI;
import ocs.com.dr_tips.networkService.HomeService;
import ocs.com.dr_tips.networkService.LoginService;
import ocs.com.dr_tips.networkService.ProfileEditService;
import ocs.com.dr_tips.util.RetrofitFactory;

/**
 * Created by Randa on 3/18/2018.
 */
@Module
public class DataLayerModule {

    @Provides
    @Singleton
    HomeService providesHomeService() {
        return RetrofitFactory.createService(HomeService.class);
    }

    @Provides
    @Singleton
    HomeAPI providesHomeAPI (HomeService service) {
        return new HomeAPIImpl(service);
    }

    @Provides
    @Singleton
    LoginService providesLoginService() {
        return RetrofitFactory.createService(LoginService.class);
    }

    @Provides
    @Singleton
    LoginAPI providesLoginAPI (LoginService service) {
        return new LoginAPIImpl(service);
    }

    @Provides
    @Singleton
    ProfileEditService providesProfileEditService() {
        return RetrofitFactory.createService(ProfileEditService.class);
    }

    @Provides
    @Singleton
    ProfileEditAPI providesProfileEditAPI (ProfileEditService service) {
        return new ProfileEditAPIImpl(service);
    }
}
