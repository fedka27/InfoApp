package test.infoapp.ui.main;

import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.repositories.ConfigRepository;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    private ConfigRepository configRepository;

    private Config config;

    public MainPresenter(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        this.config = configRepository.getConfigSaved();
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        view.configureAds(config);
    }

    @Override
    public void onStop() {

    }
}
