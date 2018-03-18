package ocs.com.dr_tips;

import android.support.multidex.MultiDexApplication;

/**
 * Created by Randa on 3/18/2018.
 */

public class DrTipsApplication extends MultiDexApplication {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);
    }

    public AppComponent getComponent() {
        return this.component;
    }
}
