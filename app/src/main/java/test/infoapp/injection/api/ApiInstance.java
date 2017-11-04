package test.infoapp.injection.api;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import test.infoapp.R;
import test.infoapp.injection.api.converter_factory.GsonXmlConverter;
import test.infoapp.injection.model.data.api.Api;

public class ApiInstance {
    private static final int CONNECT_TIMEOUT_SECONDS = 60;
    private static final int READ_TIMEOUT_SECONDS = 60;
    private static final int WRITE_TIMEOUT_SECONDS = 60;

    public static Api getInstance(Context context) {

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getMultipleConverter())
                .client(getClient(context))
                .build()
                .create(Api.class);
    }

    private static Converter.Factory getMultipleConverter() {
        return new GsonXmlConverter(
                SimpleXmlConverterFactory.create(),
                GsonConverterFactory.create());
    }

    private static Cache getCache(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }

    public static OkHttpClient getClient(Context context) {
        return new OkHttpClient.Builder()
                .cache(getCache(context))
                .addInterceptor(getLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    private static Interceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

}
