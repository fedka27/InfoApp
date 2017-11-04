package test.infoapp.injection.model.data.api;


import io.reactivex.Single;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import test.infoapp.injection.api.converter_factory.Json;
import test.infoapp.injection.model.data.dto.Config;

public interface Api {
    @GET("master/content.json")
    @Json
    Single<Result<Config>> getConfig();

}

