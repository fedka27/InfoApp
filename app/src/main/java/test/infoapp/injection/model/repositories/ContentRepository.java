package test.infoapp.injection.model.repositories;

import java.util.List;

import io.reactivex.Single;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

public class ContentRepository extends BaseRepository {
    private ConfigRepository configRepository;

    public ContentRepository(Api api,
                             ApiResponseMapper apiResponseMapper,
                             ConfigRepository configRepository) {
        super(api, apiResponseMapper);
        this.configRepository = configRepository;
    }

    public Single<List<ListItem>> items() {
        return api.getContent(configRepository.getConfigSaved().getContent_url())
                .map(apiResponseMapper::map);
    }
}
