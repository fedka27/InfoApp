package test.infoapp.injection.model.repositories;

import io.reactivex.Single;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.dto.ContentResponse;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

public class ContentRepository extends BaseRepository {
    private ConfigRepository configRepository;

    public ContentRepository(Api api,
                             ApiResponseMapper apiResponseMapper,
                             ConfigRepository configRepository) {
        super(api, apiResponseMapper);
        this.configRepository = configRepository;
    }

    public Single<ContentResponse> content() {
        return api.getContent(configRepository.getConfigSaved().getContent_url())
                .map(apiResponseMapper::map);
    }
}
