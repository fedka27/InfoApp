package info.renamed.injection.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import info.renamed.R;
import info.renamed.injection.api.converter_factory.GsonXmlConverter;
import info.renamed.injection.model.data.api.Api;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiInstance {
    private static final int CONNECT_TIMEOUT_SECONDS = 60;
    private static final int READ_TIMEOUT_SECONDS = 60;
    private static final int WRITE_TIMEOUT_SECONDS = 60;

    public static Api getInstance(Context context) {

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getMultipleConverter())
                .client(getClient())
                .build()
                .create(Api.class);
    }

    private static Converter.Factory getMultipleConverter() {
        return new GsonXmlConverter(
                SimpleXmlConverterFactory.create(),
                GsonConverterFactory.create());
    }

    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

}
