package info.renamed.injection.manager;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.renamed.injection.model.data.mapper.ApiResponseMapper;
import info.renamed.injection.model.interactors.ViewInteractor;
import info.renamed.util.connection.ConnectionUtil;
import info.renamed.util.connection.ConnectionUtilAbs;
import info.renamed.util.rx.RxSchedulers;
import info.renamed.util.rx.RxSchedulersAbs;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class UtilsModule {

    @Provides
    @Singleton
    RxSchedulersAbs provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

    @Provides
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
