package test.infoapp.injection.repositories;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.injection.model.repositories.ContentRepository;

@Module
@Singleton
public class RepositoriesModule {

    @Provides
    @Singleton
    ContentRepository provideContentRepository(Api api,
                                               ApiResponseMapper apiResponseMapper,
                                               ConfigRepository configRepository) {
        return new ContentRepository(api, apiResponseMapper, configRepository);
    }

    @Provides
    @Singleton
    ConfigRepository provideConfigRepository(Api api,
                                             ApiResponseMapper apiResponseMapper) {
        return new ConfigRepository(api, apiResponseMapper);
    }

    @Provides
    @Singleton
    AdsManager provideAdsManager(ConfigRepository configRepository) {
        return new AdsManager(configRepository.getConfigSaved().getAds_click_interval());
    }
}
