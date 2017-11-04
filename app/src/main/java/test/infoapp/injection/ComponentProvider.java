package test.infoapp.injection;


import test.infoapp.injection.presenters.PresentersComponent;

public class ComponentProvider {
    protected static volatile ComponentProvider instance;

    private AppComponent appComponent;
    private PresentersComponent presentersComponent;

    public static ComponentProvider getInstance() {
        if (instance == null) {
            synchronized (ComponentProvider.class) {
                if (instance == null) {
                    instance = new ComponentProvider();
                }
            }
        }
        return instance;
    }

    public void init(AppComponent appComponent,
                     PresentersComponent presentersComponent) {
        this.appComponent = appComponent;
        this.presentersComponent = presentersComponent;
    }

    public AppComponent getAppComponent() {
        checkInitialized(appComponent);
        return appComponent;
    }

    public PresentersComponent getPresentersComponent() {
        checkInitialized(presentersComponent);
        return presentersComponent;
    }

    private void checkInitialized(Object component) {
        if (component == null) {
            throw new ComponentProviderNotInitializedException();
        }
    }
}
