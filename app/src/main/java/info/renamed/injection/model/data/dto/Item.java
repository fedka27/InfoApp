package info.renamed.injection.model.data.dto;

import java.io.Serializable;

public class Item implements Serializable {

    private Link link;
    private Spoiler spoiler;

    public Item(Spoiler spoiler) {
        this.spoiler = spoiler;
    }

    public Item(Link link) {
        this.link = link;
    }

    public boolean isSpoiler() {
        return spoiler != null;
    }

    public boolean isLink() {
        return link != null;
    }

    public Spoiler getSpoiler() {
        return spoiler;
    }

    public Link getLink() {
        return link;
    }

    public Object getItem() {
        if (isLink()) return getLink();
        else if (isSpoiler()) return getSpoiler();
        else return null;
    }
}
