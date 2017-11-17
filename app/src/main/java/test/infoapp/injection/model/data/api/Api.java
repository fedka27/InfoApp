package test.infoapp.injection.model.data.api;


import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import test.infoapp.injection.api.converter_factory.Json;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ContentResponse;
import test.infoapp.injection.model.data.dto.StylesResponse;
import test.infoapp.injection.model.data.dto.buttons.ButtonsResponse;

public interface Api {
    @GET("fedka27/InfoApp-api/master/config.json")
    @Json
    Observable<Result<Config>> getConfig();

    @GET("{path}")
    @Json
    Observable<Result<StylesResponse>> getStyles(@Path("path") String stylesUrl);

    @GET("{path}")
    @Json
    Observable<Result<ContentResponse>> getContent(@Path("path") String contentUrl);

    @GET("{path}")
    @Json
    Observable<Result<ButtonsResponse>> getButtons(@Path("path") String buttonsUrl);
}

