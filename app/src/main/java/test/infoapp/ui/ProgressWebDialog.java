package test.infoapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.ui.base.BaseDialog;
import test.infoapp.util.L;

public class ProgressWebDialog extends BaseDialog {
    private static final String TAG = ProgressWebDialog.class.getSimpleName();
    @BindView(R.id.webview) WebView webview;

    private String link;

    public ProgressWebDialog(@NonNull Context context,
                             String link) {
        super(context);
        this.link = link;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_progress_web;
    }

    @Override
    protected void onViewCreated() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(link);
    }

    private boolean isUrlMarket(String url) {
        if (url.contains("play.google") || url.contains("market")) {

            try {
                Pattern pattern = Pattern.compile(".+\\bdetails\\?id=([^&]+)");
                Matcher matcher = pattern.matcher(url);

                if (matcher.find()) {
                    String appId = matcher.group(1);
                    L.d(TAG, "Open App - " + appId);
                    getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId)));
                } else {
                    L.d(TAG, "No match id of - " + url);
                    loadBrowserUrl(url);
                }

            } catch (Exception e) {
                loadBrowserUrl(url);
            }

            dismiss();

            return true;
        }

        dismiss();

        return false;
    }

    private void loadBrowserUrl(String url) {
        Uri uri = Uri.parse(url);

        L.d(TAG, "Load url - " + uri.toString());
        getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private class WebViewClient extends android.webkit.WebViewClient {

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return isUrlMarket(url);
//        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!isUrlMarket(url)) {
                L.d(TAG, "Page finished | Load url - " + url);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                getContext().startActivity(browserIntent);
            }
        }
    }
}