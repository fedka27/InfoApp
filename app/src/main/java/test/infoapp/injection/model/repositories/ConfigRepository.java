package test.infoapp.injection.model.repositories;

import io.paperdb.Paper;
import test.infoapp.data.model.Config;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

public class ConfigRepository extends BaseRepository {
    private static final String TAG = ConfigRepository.class.getSimpleName();
    private static final String KEY_CONFIG = TAG + "_CONFIG";

    public ConfigRepository(Api api, ApiResponseMapper apiResponseMapper) {
        super(api, apiResponseMapper);
    }

    public Config getConfig() {
        return Paper.book().read(KEY_CONFIG, new Config());
    }

    public void setConfig(Config config) {
        if (config == null) {
            Paper.book().delete(KEY_CONFIG);
            return;
        }
        Paper.book().write(KEY_CONFIG, config);
    }
}
