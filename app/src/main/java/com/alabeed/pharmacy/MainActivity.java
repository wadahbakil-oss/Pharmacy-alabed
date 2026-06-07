package com.alabeed.pharmacy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = findViewById(R.id.webView);
        
        // إعدادات WebView
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        
        // تفعيل JavaScript Interface
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        
        // منع فتح الروابط خارج التطبيق
        webView.setWebViewClient(new WebViewClient());
        
        // تحميل الصفحة
        // من GitHub Pages:
        webView.loadUrl("https://wadahbakil-oss.github.io/Pharmacy-alabed/");
        
        // أو من ملف محلي:
        // webView.loadUrl("file:///android_asset/index.html");
    }
    
    // التعامل مع زر الرجوع
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    // JavaScript Interface
    public class WebAppInterface {
        Context mContext;
        
        WebAppInterface(Context c) {
            mContext = c;
        }
        
        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
        
        @JavascriptInterface
        public void openWhatsApp(String phone, String message) {
            try {
                String url = "https://wa.me/967" + phone + "?text=" + Uri.encode(message);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "❌ واتساب غير مثبت", Toast.LENGTH_SHORT).show();
            }
        }
        
        @JavascriptInterface
        public void openSMS(String phone, String message) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:+967" + phone));
                intent.putExtra("sms_body", message);
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "❌ لا يمكن فتح الرسائل", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
