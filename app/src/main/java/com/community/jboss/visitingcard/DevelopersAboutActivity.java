package com.community.jboss.visitingcard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.community.jboss.visitingcard.Networking.ContributersGetterApi;
import com.community.jboss.visitingcard.adapters.ContributersAdapter;

public class DevelopersAboutActivity extends AppCompatActivity {

    RecyclerView contributors_recycler;
    ProgressBar progressBar;
    TextView developed_by_text;
    TextView and_text;
    ImageView jboss_logo;
    ImageView github;

    ContributersAdapter contributors_recycler_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_devs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        jboss_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogJbossDescription dialog = new DialogJbossDescription(DevelopersAboutActivity.this);
                dialog.show();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/JBossOutreach");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        contributors_recycler.setLayoutManager(new LinearLayoutManager(this));
        new ContributersGetterApi(this).execute();

    }

    private void initViews(){
        contributors_recycler = findViewById(R.id.contributors_recycler);
        progressBar = findViewById(R.id.progress_bar);
        developed_by_text = findViewById(R.id.developed_by_text);
        and_text = findViewById(R.id.and_text);
        jboss_logo = findViewById(R.id.jboss_logo);
        github = findViewById(R.id.github);
    }

    public void receiveData(String[][] data) {
        progressBar.setVisibility(View.GONE);
        if (data != null) {
            contributors_recycler_adapter = new ContributersAdapter(data, this);
            contributors_recycler.setAdapter(contributors_recycler_adapter);
            contributors_recycler.invalidate();
        } else {
            Toast.makeText(this, "No connection to the server", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
