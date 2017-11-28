package info.renamed.injection.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.renamed.injection.model.data.api.Api;

@Module
public class ApiModule {
    @Provides
    @Singleton
    Api provideApiInterface(Context context) {
        return ApiInstance.getInstance(context);
    }

}
