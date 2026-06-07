public class MainActivity extends AppCompatActivity {
    WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true); // مهم لـ localStorage
        
        // تفعيل JavaScript Interface
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        
        // تحميل الصفحة
        webView.loadUrl("https://wadahbakil-oss.github.io/Pharmacy-alabed/");
    }
    
    // JavaScript Interface
    public class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) { mContext = c; }
        
        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
        
        @JavascriptInterface
        public void openWhatsApp(String phone, String message) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/967" + phone + "?text=" + Uri.encode(message)));
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "واتساب غير مثبت", Toast.LENGTH_SHORT).show();
            }
        }
        
        @JavascriptInterface
        public void openSMS(String phone, String message) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:+967" + phone));
            intent.putExtra("sms_body", message);
            mContext.startActivity(intent);
        }
    }
}
