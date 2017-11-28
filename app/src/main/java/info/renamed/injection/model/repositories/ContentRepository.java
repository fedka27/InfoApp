package info.renamed.injection.model.repositories;

import android.graphics.Color;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.renamed.injection.model.data.api.Api;
import info.renamed.injection.model.data.dto.ColorHelper;
import info.renamed.injection.model.data.dto.Config;
import info.renamed.injection.model.data.dto.ContentResponse;
import info.renamed.injection.model.data.dto.Item;
import info.renamed.injection.model.data.dto.Link;
import info.renamed.injection.model.data.dto.ListContent;
import info.renamed.injection.model.data.dto.ListItem;
import info.renamed.injection.model.data.dto.Spoiler;
import info.renamed.injection.model.data.dto.Style;
import info.renamed.injection.model.data.dto.StylesResponse;
import info.renamed.injection.model.data.dto.buttons.ButtonsResponse;
import info.renamed.injection.model.data.mapper.ApiResponseMapper;
import info.renamed.util.L;
import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.functions.Function3;

public class ContentRepository extends BaseRepository {
    private static final String TAG = ContentRepository.class.getSimpleName();
    private static final String KEY_CACHE_LIST_CONTENT = TAG + "_CACHE_LIST_CONTENT";

    private ConfigRepository configRepository;

    public ContentRepository(Api api,
                             ApiResponseMapper apiResponseMapper,
                             ConfigRepository configRepository) {
        super(api, apiResponseMapper);
        this.configRepository = configRepository;
    }

    public Observable<ListContent> content() {
        Config config = configRepository.getConfigSaved();

        Function3<ContentResponse, ButtonsResponse, StylesResponse, ListContent> function3 =
                (contentResponse, buttonsResponse, stylesResponse) -> parseContent(
                        contentResponse.getImageBg(),
                        contentResponse.getBgColor(),
                        contentResponse.getList(),
                        buttonsResponse.getLinks(),
                        stylesResponse.getStyles());

        return Observable.combineLatest(
                api.getContent(config.getContentUrl()).map(apiResponseMapper::map),
                api.getButtons(config.getContentButtons()).map(apiResponseMapper::map),
                api.getStyles(config.getConfigButtonUrl()).map(apiResponseMapper::map),
                function3)
                .startWith(getCachedListContent())
                .doOnNext(this::cacheListContent);
    }

    private void cacheListContent(ListContent listContent) {
        if (listContent == null) return;
        Paper.book().write(KEY_CACHE_LIST_CONTENT, listContent);
    }

    private ListContent getCachedListContent() {
        try {
            return Paper.book().read(KEY_CACHE_LIST_CONTENT,
                    new ListContent(
                            null,
                            ColorHelper.parseColor(Style.DEFAULT_COLOR, Color.BLACK),
                            Collections.emptyList()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ListContent(
                    null,
                    ColorHelper.parseColor(Style.DEFAULT_COLOR, Color.BLACK),
                    Collections.emptyList());
        }
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
