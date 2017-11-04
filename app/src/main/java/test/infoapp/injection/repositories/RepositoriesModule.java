package test.infoapp.injection.repositories;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;

@Module
@Singleton
public class RepositoriesModule {
    @Provides
    @Singleton
    ConfigRepository provideConfigRepository(Api api,
                                             ApiResponseMapper apiResponseMapper) {
        return new ConfigRepository(api, apiResponseMapper);
    }

    @Provides
    @Singleton
    AdsManager provideAdsManager(ConfigRepository configRepository) {
        return new AdsManager(configRepository.getConfig().getAds_click_interval());
    }
}
