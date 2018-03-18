package ocs.com.dr_tips;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ocs.com.dr_tips.dataLayer.impl.DataLayerModule;
import ocs.com.dr_tips.util.PreferenceHelper;
import ocs.com.dr_tips.viewModel.impl.ViewModelModule;

/**
 * Created by Randa on 3/18/2018.
 */
@Module(includes = {DataLayerModule.class, ViewModelModule.class})
public class AppModule {
    private Application app;

    AppModule(Application app) {
        this.app = app;
    }

    @Singleton
    @Provides
    Context providesContext () {
        return app;
    }

    @Singleton
    @Provides
    PreferenceHelper providesPreferenceHelper(Context context) {
        return  new PreferenceHelper(context);
    }
}
