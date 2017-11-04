package test.infoapp.injection.model.data.dto;

import android.support.annotation.StringDef;

import static test.infoapp.injection.model.data.dto.ListItemType.LINK;
import static test.infoapp.injection.model.data.dto.ListItemType.TEXT;

@StringDef(value = {LINK, TEXT})
public @interface ListItemType {
    String LINK = "link", TEXT = "text";
}
