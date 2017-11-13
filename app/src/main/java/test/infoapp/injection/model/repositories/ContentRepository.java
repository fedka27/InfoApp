package test.infoapp.injection.model.repositories;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.Item;
import test.infoapp.injection.model.data.dto.Link;
import test.infoapp.injection.model.data.dto.ListContent;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.dto.Spoiler;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;
import test.infoapp.util.L;

public class ContentRepository extends BaseRepository {
    private static final String TAG = ContentRepository.class.getSimpleName();

    private ConfigRepository configRepository;

    public ContentRepository(Api api,
                             ApiResponseMapper apiResponseMapper,
                             ConfigRepository configRepository) {
        super(api, apiResponseMapper);
        this.configRepository = configRepository;
    }

    public Single<ListContent> content() {
        Config config = configRepository.getConfigSaved();

        return Single.zip(
                api.getContent(config.getContentUrl()).map(apiResponseMapper::map),
                api.getButtons(config.getContentButtons()).map(apiResponseMapper::map),
                api.getStyles(config.getConfigButtonUrl()).map(apiResponseMapper::map),
                (contentResponse, buttonsResponse, stylesResponse) -> parseContent(
                        contentResponse.getImageBg(),
                        contentResponse.getBgColor(),
                        contentResponse.getList(),
                        buttonsResponse.getLinks(),
                        stylesResponse.getStyles()));
    }

    public ListContent parseContent(String imageBg, int colorBg,
                                    List<ListItem> listItems,
                                    List<Link> links,
                                    List<Style> styles) {
        Map<Long, Link> linkMap = new HashMap<>();
        Map<Long, Style> styleMap = new HashMap<>();

        for (Link link : links) {
            linkMap.put(link.getId(), link);
        }

        for (Style style : styles) {
            styleMap.put(style.getId(), style);
        }

        List<Item> items = new ArrayList<>();

        for (ListItem itemList : listItems) {
            Style style = styleMap.get(itemList.getStyleId());

            if (style == null) style = Style.getDefaultStyle();

            if (itemList.isSpoiler()) {
                Spoiler spoiler = itemList.getSpoiler();

//                spoiler.setStyle(style);

                items.add(new Item(new Spoiler(spoiler, style)));
            } else {
                @Nullable Link link = linkMap.get(itemList.getButtonId());

                if (link == null) {
                    L.e(TAG, "Not found 'link' of buttonId-" + itemList.getButtonId());
                    continue;
                }

//                link.setStyle(style);

                items.add(new Item(new Link(link, style)));
            }
        }

        return new ListContent(imageBg, colorBg, items);
    }
}
