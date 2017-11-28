package test.infoapp.injection.model.data.api;


import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Url;
import test.infoapp.injection.api.converter_factory.Json;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ContentResponse;
import test.infoapp.injection.model.data.dto.StylesResponse;
import test.infoapp.injection.model.data.dto.buttons.ButtonsResponse;

public interface Api {
    @GET
    @Json
    Observable<Result<Config>> getConfig(@Url String url);

    @GET
    @Json
    Observable<Result<StylesResponse>> getStyles(@Url String stylesUrl);

    @GET
    @Json
    Observable<Result<ContentResponse>> getContent(@Url String contentUrl);

    @GET
    @Json
    Observable<Result<ButtonsResponse>> getButtons(@Url String buttonsUrl);
}

