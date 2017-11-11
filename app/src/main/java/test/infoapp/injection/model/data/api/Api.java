package test.infoapp.injection.model.data.api;


import io.reactivex.Single;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import test.infoapp.injection.api.converter_factory.Json;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ContentResponse;
import test.infoapp.injection.model.data.dto.StylesResponse;

public interface Api {
    @GET("fedka27/InfoApp-api/master/config.json")
    @Json
    Single<Result<Config>> getConfig();

    @GET("fedka27/InfoApp-api/master/button_styles.json")
    @Json
    Single<Result<StylesResponse>> getStyles();

    @GET("{path}")
    @Json
    Single<Result<ContentResponse>> getContent(@Path("path") String content);

}

