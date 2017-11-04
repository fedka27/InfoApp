package test.infoapp.ui.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class BaseWebViewFragment extends BaseFragment {
    private WebView webView;

    @NonNull
    protected abstract WebView getWebView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = getWebView();
        initWebView();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");

        webView.setWebViewClient(new BaseWebViewClient());

        webView.setOnKeyListener((view1, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && i == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
            return false;
        });

        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }

    protected void loadUrl(String url) {
        webView.loadDataWithBaseURL(url,
                null,
                "text/html; charset=utf-8",
                "utf-8",
                null);
    }

    protected void onStartLoadPage() {
        showProgress();
    }

    protected void onFinishLoadPage() {
        hideProgress();
    }

    protected boolean onUrlClick(Uri uri) {
        if (uri.toString().startsWith("mailto:")) {
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_SENDTO, uri), "Email"));
            return true;
        } else if (uri.toString().startsWith("tel:")) {
            //Handle telephony Urls
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_DIAL, uri), "Dial"));
            return true;
        }
        return false;
    }

    class BaseWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return onUrlClick(Uri.parse(url));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return onUrlClick(request.getUrl());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onFinishLoadPage();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            onStartLoadPage();
        }
    }
}
