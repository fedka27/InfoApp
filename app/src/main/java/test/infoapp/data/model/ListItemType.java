package test.infoapp.data.model;

import android.support.annotation.StringDef;

import static test.infoapp.data.model.ListItemType.LINK;
import static test.infoapp.data.model.ListItemType.TEXT;

@StringDef(value = {LINK, TEXT})
public @interface ListItemType {
    String LINK = "link", TEXT = "text";
}
