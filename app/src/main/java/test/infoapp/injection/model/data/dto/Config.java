package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Config implements Serializable {
    @SerializedName("config_button_url") private String config_button_url;
    @SerializedName("content_url") private String content_url;
    @SerializedName("isOffline") private boolean isOffline = true;
    @SerializedName("ads") private boolean ads = false;
    @SerializedName("ads_splash") private boolean ads_splash = false;
    @SerializedName("ads_interstetial") private int ads_interstetial = 50;
    @SerializedName("ads_video") private int ads_video = 50;
    @SerializedName("ads_banner") private boolean ads_banner = false;
    @SerializedName("ads_click_interval") private int ads_click_interval = 3;

    public Config(String content_url,
                  boolean isOffline,
                  boolean ads,
                  boolean ads_splash,
                  int ads_interstetial,
                  int ads_video,
                  boolean ads_banner,
                  int ads_click_interval) {
        this.content_url = content_url;
        this.isOffline = isOffline;
        this.ads = ads;
        this.ads_splash = ads_splash;
        this.ads_interstetial = ads_interstetial;
        this.ads_video = ads_video;
        this.ads_banner = ads_banner;
        this.ads_click_interval = ads_click_interval;
    }

    public Config() {

    }

    public String getContent_url() {
        return content_url;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public boolean isAds() {
        return ads;
    }

    public boolean isAds_splash() {
        return ads_splash;
    }

    public int getAds_interstetial() {
        return ads_interstetial;
    }

    public int getAds_video() {
        return ads_video;
    }

    public boolean isAds_banner() {
        return ads_banner;
    }

    public int getAds_click_interval() {
        return ads_click_interval;
    }
}
