package test.infoapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.yandex.metrica.YandexMetrica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.ui.base.BaseDialog;
import test.infoapp.util.L;

public class ProgressWebDialog extends BaseDialog {
    private static final String TAG = ProgressWebDialog.class.getSimpleName();
    @BindView(R.id.webview) WebView webview;

    private String linkTitle;
    private String link;

    public ProgressWebDialog(@NonNull Context context,
                             String linkTitle,
                             String link) {
        super(context);
        this.linkTitle = linkTitle;
        this.link = link;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_progress_web;
    }

    @Override
    protected void onViewCreated() {
        webview.getSettings().setLoadsImagesAutomatically(false);
        webview.getSettings().setBlockNetworkImage(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(link);

        // TODO: 15.11.2017 Yandex Metrica. Если нужно в начале  цепочки отправлять событие
//        eventLink(link);
    }

    private boolean isUrlMarketOpen(String url) {
        if (url.contains("play.google") || url.contains("market")) {


            try {
                Pattern pattern = Pattern.compile(".+\\bdetails\\?id=([^&]+)");
                Matcher matcher = pattern.matcher(url);

                if (matcher.find()) {
                    String appId = matcher.group(1);
                    L.d(TAG, "Open App - " + appId);
                    loadBrowserUrl(true, appId);
                    return true;
                }

            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    private void loadBrowserUrl(boolean isMarket, String urlOrAppId) {
        if (isMarket) urlOrAppId = "market://details?id=" + urlOrAppId;

        // TODO: 15.11.2017 Yandex Metrica. Если нужно в конце цепочки отправлять событие
        eventLink(urlOrAppId);

        Uri uri = Uri.parse(urlOrAppId);

        L.d(TAG, "Load url - " + uri.toString());
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void eventLink(String param) {
        YandexMetrica.reportEvent(getContext()
                .getString(R.string.metrica_event_click_link,
                        linkTitle, param));

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webview != null) webview.stopLoading();
    }

    private class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!isUrlMarketOpen(url)) {
                L.d(TAG, "Page finished | Load url - " + url);
                loadBrowserUrl(false, url);
            }
            dismiss();
        }
    }
}