package ocs.com.dr_tips;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Randa on 3/18/2018.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject (DrTipsApplication app);
}
