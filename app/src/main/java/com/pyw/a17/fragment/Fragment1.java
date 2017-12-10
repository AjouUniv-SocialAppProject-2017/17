package com.pyw.a17.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pyw.a17.R;

/**
 * Created by EOM on 2015-08-20.
 */
public class Fragment1 extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        String title = getArguments().getString("menu_title");
        String urlAddr = null;

        webView = (WebView)rootView.findViewById(R.id.webView);

        switch(title) {
            case "Ruby" : {
                urlAddr = "https://www.ruby-lang.org/ko/libraries/";
                break;
            }
            case "AWS" : {
                urlAddr = "https://www.inflearn.com/course/aws-%EC%95%84%EB%A7%88%EC%A1%B4-%EC%9B%B9%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B0%80%EC%9E%85%EB%B6%80%ED%84%B0-%ED%99%9C%EC%9A%A9%EA%B9%8C%EC%A7%80/";
                break;
            }
            case "WEB" : {
                urlAddr = "http://poiemaweb.com";
                break;
            }
            case "Python" : {
                urlAddr = "http://byteofpython-korean.sourceforge.net/byte_of_python.html";
                break;
            }
            case "C/C++" : {
                urlAddr = "https://www.inflearn.com/course/c%EC%96%B8%EC%96%B4-%EB%91%90%EB%93%A4%EB%82%99%EC%84%9C/";
                break;
            }
        }

        // 웹뷰에서 자바스크립트실행가능
        webView.getSettings().setJavaScriptEnabled(true);

        // 구글홈페이지 지정
        webView.loadUrl(urlAddr);

        // WebViewClient 지정
        webView.setWebViewClient(new WebViewClientClass());

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
