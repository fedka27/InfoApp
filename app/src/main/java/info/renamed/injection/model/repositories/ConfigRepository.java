package info.renamed.injection.model.repositories;

import android.content.res.Resources;

import info.renamed.R;
import info.renamed.injection.model.data.api.Api;
import info.renamed.injection.model.data.dto.Config;
import info.renamed.injection.model.data.mapper.ApiResponseMapper;
import io.paperdb.Paper;
import io.reactivex.Observable;

public class ConfigRepository extends BaseRepository {
    private static final String TAG = ConfigRepository.class.getSimpleName();
    private static final String KEY_CONFIG = TAG + "_CONFIG";
    private String urlConfig;

    public ConfigRepository(Api api, ApiResponseMapper apiResponseMapper, Resources resources) {
        super(api, apiResponseMapper);
        this.urlConfig = resources.getString(R.string.base_url_config);
    }

    public Config getConfigSaved() {
        return Paper.book().read(KEY_CONFIG, new Config());
    }

    public void saveConfig(Config config) {
        if (config == null) {
            Paper.book().delete(KEY_CONFIG);
            return;
        }
        Paper.book().write(KEY_CONFIG, config);
    }

    public Observable<Config> getConfig() {
        return api.getConfig(urlConfig)
                .map(apiResponseMapper::map)
                .onErrorReturn(throwable -> new Config())
                .doOnNext(this::saveConfig);
    }

}
