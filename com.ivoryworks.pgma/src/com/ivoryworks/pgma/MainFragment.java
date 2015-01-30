package com.ivoryworks.pgma;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;

public class MainFragment extends Fragment {

    private WebView mWebView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview);
        String html = "ルビタグの実験をします。</br><ruby><rb>循環参照</rb><rp>(</rp><rt>じゅんかんさんしょう</rt><rp>)</rp></ruby></br>できた！";
        mWebView.loadData(html, "text/html; charset=utf-8", "utf-8");
        return v;
    }
}
