package info.renamed.ui.main;

import info.renamed.injection.model.data.dto.Config;
import info.renamed.injection.model.managers.AdsManager;
import info.renamed.injection.model.repositories.ConfigRepository;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;

    private ConfigRepository configRepository;
    private AdsManager adsManager;

    private Config config;

    public MainPresenter(ConfigRepository configRepository,
                         AdsManager adsManager) {
        this.configRepository = configRepository;
        this.config = configRepository.getConfigSaved();
        this.adsManager = adsManager;
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
    public void onCategoryPressed() {
        adsManager.showAdsOfType(config, view);
    }

    @Override
    public void onStop() {

    }
}
