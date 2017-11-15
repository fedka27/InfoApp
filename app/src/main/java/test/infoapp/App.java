package test.infoapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.appodeal.ads.Appodeal;

import io.paperdb.Paper;
import test.infoapp.injection.AppComponent;
import test.infoapp.injection.AppModule;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.injection.DaggerAppComponent;
import test.infoapp.injection.manager.UtilsModule;
import test.infoapp.injection.presenters.PresenterModule;
import test.infoapp.injection.presenters.PresentersComponent;

public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();

        PresentersComponent presentersComponent = appComponent.plus(new PresenterModule());

        ComponentProvider.getInstance().init(appComponent, presentersComponent);

        Paper.init(this);

        Appodeal.disableNetwork(this, "facebook");
        Appodeal.disableNetwork(this, "vungle");
    }
}
