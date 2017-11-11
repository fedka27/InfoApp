package test.infoapp.injection.model.repositories;

import java.util.List;
import java.util.Random;

import io.paperdb.Paper;
import io.reactivex.Single;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.injection.model.data.dto.StylesResponse;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

public class StylesRepository extends BaseRepository {
    private static final String TAG = StylesRepository.class.getSimpleName();
    private static final String KEY_STYLES = TAG + "_STYLES";

    private Random random;
    private StylesResponse stylesResponse;

    public StylesRepository(Api api, ApiResponseMapper apiResponseMapper) {
        super(api, apiResponseMapper);
        random = new Random();
    }

    public Single<StylesResponse> getStylesResponseSingle() {
        return api.getStyles()
                .map(apiResponseMapper::map)
                .onErrorReturn(throwable -> new StylesResponse())
                .doOnSuccess(this::save);
    }

    public List<Style> getStylesSaved() {
        if (stylesResponse == null) {
            stylesResponse = Paper.book().read(KEY_STYLES, new StylesResponse());
        }
        return stylesResponse.getStyles();
    }

    public void save(StylesResponse stylesResponse) {
        if (stylesResponse == null) {
            Paper.book().delete(KEY_STYLES);
            return;
        }
        Paper.book().write(KEY_STYLES, stylesResponse);
    }

    public Style getStyleById(int styleId) {
        List<Style> styles = getStylesSaved();

        for (Style style : styles) {
            if (style.getId() == styleId) {
                return style;
            }
        }

        if (!styles.isEmpty()) {

//            random style

//            int size = styles.size();
//            int index = random.nextInt(size);

//            def style
            return styles.get(0);
        }

        return Style.getDefaultStyle();
    }
}
