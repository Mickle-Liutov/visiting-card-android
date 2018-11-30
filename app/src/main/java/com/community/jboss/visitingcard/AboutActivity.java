package com.community.jboss.visitingcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    Button developers_button;
    TextView licenses;
    WebView licenseWebView;
    boolean isWebViewOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        developers_button = findViewById(R.id.about_developers_button);
        licenses = findViewById(R.id.licenses);
        licenseWebView = findViewById(R.id.license_webview);
        developers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent devsIntent = new Intent(AboutActivity.this,DevelopersAboutActivity.class);
                startActivity(devsIntent);
            }
        });
        licenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                licenseWebView.loadUrl("http://www.ws-i.org/licenses/sample_license.htm");
                licenseWebView.setVisibility(View.VISIBLE);
                isWebViewOpened = true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isWebViewOpened){
            licenseWebView.setVisibility(View.GONE);
            isWebViewOpened = false;
        }
        else{
            finish();
        }
    }
}
