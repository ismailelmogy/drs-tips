package ocs.com.dr_tips;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

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

    @Singleton
    @Provides
    ImageLoader providesImageLoader(Context context){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(25)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
        return ImageLoader.getInstance();
    }
}
