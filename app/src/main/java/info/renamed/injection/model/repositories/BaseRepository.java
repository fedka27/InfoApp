package info.renamed.injection.model.repositories;


import info.renamed.injection.model.data.api.Api;
import info.renamed.injection.model.data.mapper.ApiResponseMapper;

public class BaseRepository {
    protected Api api;
    protected ApiResponseMapper apiResponseMapper;

    public BaseRepository(Api api,
                          ApiResponseMapper apiResponseMapper) {
        this.api = api;
        this.apiResponseMapper = apiResponseMapper;
    }
}
