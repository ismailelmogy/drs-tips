package ocs.com.dr_tips.dataLayer.impl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.networkService.HomeService;
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
}
