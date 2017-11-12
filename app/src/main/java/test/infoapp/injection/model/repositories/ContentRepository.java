package test.infoapp.injection.model.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import test.infoapp.injection.model.data.api.Api;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.Item;
import test.infoapp.injection.model.data.dto.ListContent;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.injection.model.data.mapper.ApiResponseMapper;

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
                        buttonsResponse.getButtons(),
                        stylesResponse.getStyles()));
    }

    public ListContent parseContent(String imageBg, int colorBg,
                                    List<ListItem> listItems,
                                    List<Item> buttons,
                                    List<Style> styles) {
        Map<Long, Object> buttonsObjectMap = new HashMap<>();
        Map<Long, Style> stylesMap = new HashMap<>();

        for (Item item : buttons) {

            long id = item.getId();
            Object object = item.getItem();

            buttonsObjectMap.put(id, object);
        }

        for (Style style : styles) {
            stylesMap.put(style.getId(), style);
        }

        List<Item> items = new ArrayList<>();

        for (ListItem itemList : listItems) {
            Object object = buttonsObjectMap.get(itemList.getButtonId());

            Style style = stylesMap.get(itemList.getStyleId()) != null ?
                    stylesMap.get(itemList.getStyleId()) : Style.getDefaultStyle();

            if (object instanceof Item.Spoiler) {
                Item.Spoiler spoiler = (Item.Spoiler) object;
                items.add(new Item(new Item.Spoiler(spoiler, style)));
            } else if (object instanceof Item.Link) {
                Item.Link link = (Item.Link) object;
                items.add(new Item(new Item.Link(link, style)));
            }
        }

        return new ListContent(imageBg, colorBg, items);
    }
}
