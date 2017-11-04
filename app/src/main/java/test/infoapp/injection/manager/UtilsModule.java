package test.infoapp.injection.manager;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;
import test.infoapp.injection.model.interactors.ViewInteractor;
import test.infoapp.util.connection.ConnectionUtil;
import test.infoapp.util.connection.ConnectionUtilAbs;
import test.infoapp.util.rx.RxSchedulers;
import test.infoapp.util.rx.RxSchedulersAbs;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    RxSchedulersAbs provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    ApiResponseMapper provideApiResponseMapper(ConnectionUtilAbs connectionUtil) {
        return new ApiResponseMapper(connectionUtil);
    }

    @Provides
    @Singleton
    ConnectionUtilAbs provideConnectionUtil(Context context) {
        return new ConnectionUtil(context);
    }

    @Provides
    @Singleton
    ViewInteractor provideViewInteractor() {
        return new ViewInteractor();
    }

}
