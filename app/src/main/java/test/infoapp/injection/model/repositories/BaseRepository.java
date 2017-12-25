package test.infoapp.injection.model.repositories;


import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

public class BaseRepository {
    protected Api api;
    protected ApiResponseMapper apiResponseMapper;

    public BaseRepository(Api api,
                          ApiResponseMapper apiResponseMapper) {
        this.api = api;
        this.apiResponseMapper = apiResponseMapper;
    }
}
