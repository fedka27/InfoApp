package info.renamed.injection.model.data.dto;

import android.support.annotation.StringDef;

import static info.renamed.injection.model.data.dto.ListItemType.LINK;
import static info.renamed.injection.model.data.dto.ListItemType.TEXT;

@StringDef(value = {LINK, TEXT})
public @interface ListItemType {
    String LINK = "link", TEXT = "text";
}
