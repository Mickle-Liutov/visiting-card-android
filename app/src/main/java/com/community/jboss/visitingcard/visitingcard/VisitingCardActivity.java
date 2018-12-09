package com.community.jboss.visitingcard.visitingcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.community.jboss.visitingcard.about.AboutActivity;
import com.community.jboss.visitingcard.maps.MapsActivity;
import com.community.jboss.visitingcard.utils.Keys;

import java.io.IOException;

import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;

public class VisitingCardActivity extends AppCompatActivity {

    private ImageButton profile_img;
    TextView name_text;
    EditText name_edittext;
    TextView number_text;
    EditText number_edittext;
    TextView email_text;
    EditText email_edittext;
    TextView linkedin_text;
    EditText linkedin_edittext;
    TextView twitter_text;
    EditText twitter_edittext;
    TextView github_text;
    EditText github_edittext;
    ImageView edit_card;
    TextView button_save;
    TextView button_cancel;
    SharedPreferences sharedPref;

    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);
        initViews();
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        setUserData();
        edit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditing();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditing(false);
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishEditing(true);
            }
        });

        // TODO: Add a ImageView and a number of EditText to get his/her Visiting Card details (Currently authenticated User)

        // TODO: Add profileImage, Name, Email, PhoneNumber, Github, LinkedIn & Twitter Fields.

        // TODO: Clicking the ImageView should invoke an implicit intent to take an image using camera / pick an image from the Gallery.

        // TODO: Align FAB to Bottom Right and replace it's icon with a SAVE icon
        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Proceed to Maps Activity", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                                startActivity(toVisitingCard);
                            }
                        }).show();
            }
        });

    }

    private void initViews() {
        profile_img = findViewById(R.id.profile_img);
        name_text = findViewById(R.id.name_text);
        name_edittext = findViewById(R.id.name_edittext);
        number_text = findViewById(R.id.number_text);
        number_edittext = findViewById(R.id.number_edittext);
        email_text = findViewById(R.id.email_text);
        email_edittext = findViewById(R.id.email_edittext);
        linkedin_text = findViewById(R.id.linkedin_text);
        linkedin_edittext = findViewById(R.id.linkedin_edittext);
        twitter_text = findViewById(R.id.twitter_text);
        twitter_edittext = findViewById(R.id.twitter_edittext);
        github_text = findViewById(R.id.github_text);
        github_edittext = findViewById(R.id.github_edittext);
        edit_card = findViewById(R.id.edit_card);
        button_save = findViewById(R.id.button_save);
        button_cancel = findViewById(R.id.button_cancel);
    }

    private void setUserData() {
        name_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_NAME, "My Name"));
        email_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_EMAIL, "myemail@gmail.com"));
        number_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_NUMBER, "1111111111"));
        linkedin_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_LINKEDIN, "www.linkedin.com/profile"));
        twitter_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_TWITTER, "www.twitter.com/profile"));
        github_text.setText(sharedPref.getString(Keys.SHAREDPREF_USER_GITHUB, "www.github.com/profile"));
    }

    private void startEditing() {
        switchEditingVisibility();
        name_edittext.setText(name_text.getText());
        number_edittext.setText(number_text.getText());
        email_edittext.setText(email_text.getText());
        linkedin_edittext.setText(linkedin_text.getText());
        twitter_edittext.setText(twitter_text.getText());
        github_edittext.setText(github_text.getText());
    }

    private void finishEditing(boolean isCanceled) {
        if (isCanceled) {
            switchEditingVisibility();
            return;
        }

        if (checkInputSuitability()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(Keys.SHAREDPREF_USER_NAME, name_edittext.getText().toString());
            editor.putString(Keys.SHAREDPREF_USER_NUMBER, number_edittext.getText().toString());
            editor.putString(Keys.SHAREDPREF_USER_EMAIL, email_edittext.getText().toString());
            editor.putString(Keys.SHAREDPREF_USER_LINKEDIN, linkedin_edittext.getText().toString());
            editor.putString(Keys.SHAREDPREF_USER_TWITTER, twitter_edittext.getText().toString());
            editor.putString(Keys.SHAREDPREF_USER_GITHUB, github_edittext.getText().toString());
            editor.commit();
            setUserData();
            switchEditingVisibility();
        }
    }

    private boolean checkInputSuitability() {

        boolean isSuitable = true;
        if (TextUtils.isEmpty(name_edittext.getText())) {
            name_edittext.setError("Please, write your name");
            isSuitable = false;
        }

        if (TextUtils.isEmpty(number_edittext.getText())) {
            number_edittext.setError("Please, write your phone number");
            isSuitable = false;
        }

        if (TextUtils.isEmpty(email_edittext.getText())) {
            email_edittext.setError("Please, write your email");
            isSuitable = false;
        } else if (!email_edittext.getText().toString().contains("@")) {
            email_edittext.setError("Email is incorrect");
            isSuitable = false;
        }

        if (TextUtils.isEmpty(linkedin_edittext.getText())) {
            linkedin_edittext.setError("Please, write your LinkedIn link");
            isSuitable = false;
        } else if (!linkedin_edittext.getText().toString().contains("linkedin.com")) {
            linkedin_edittext.setError("LinkedIn link is incorrect");
            isSuitable = false;
        }

        if (TextUtils.isEmpty(twitter_edittext.getText())) {
            twitter_edittext.setError("Please, write your Twitter link");
            isSuitable = false;
        } else if (!twitter_edittext.getText().toString().contains("twitter.com")) {
            twitter_edittext.setError("Twitter link is incorrect");
            isSuitable = false;
        }

        if (TextUtils.isEmpty(github_edittext.getText())) {
            github_edittext.setError("Please, write your GitHub link");
            isSuitable = false;
        } else if (!github_edittext.getText().toString().contains("github.com")) {
            github_edittext.setError("GitHub link is incorrect");
            isSuitable = false;
        }

        return isSuitable;
    }

    private void switchEditingVisibility() {
        if (edit_card.getVisibility() == View.VISIBLE) {
            edit_card.setVisibility(View.GONE);
            button_save.setVisibility(View.VISIBLE);
            button_cancel.setVisibility(View.VISIBLE);

            name_text.setVisibility(View.GONE);
            number_text.setVisibility(View.GONE);
            email_text.setVisibility(View.GONE);
            linkedin_text.setVisibility(View.GONE);
            twitter_text.setVisibility(View.GONE);
            github_text.setVisibility(View.GONE);

            name_edittext.setVisibility(View.VISIBLE);
            number_edittext.setVisibility(View.VISIBLE);
            email_edittext.setVisibility(View.VISIBLE);
            linkedin_edittext.setVisibility(View.VISIBLE);
            twitter_edittext.setVisibility(View.VISIBLE);
            github_edittext.setVisibility(View.VISIBLE);

            name_edittext.setText(name_text.getText());
            number_edittext.setText(number_text.getText());
            email_edittext.setText(email_text.getText());
        } else {
            edit_card.setVisibility(View.VISIBLE);
            button_save.setVisibility(View.GONE);
            button_cancel.setVisibility(View.GONE);

            name_text.setVisibility(View.VISIBLE);
            number_text.setVisibility(View.VISIBLE);
            email_text.setVisibility(View.VISIBLE);
            linkedin_text.setVisibility(View.VISIBLE);
            twitter_text.setVisibility(View.VISIBLE);
            github_text.setVisibility(View.VISIBLE);

            name_edittext.setVisibility(View.GONE);
            number_edittext.setVisibility(View.GONE);
            email_edittext.setVisibility(View.GONE);
            linkedin_edittext.setVisibility(View.GONE);
            twitter_edittext.setVisibility(View.GONE);
            github_edittext.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE || requestCode == EZPhotoPick.PHOTO_PICK_CAMERA_REQUEST_CODE) {
            Bitmap pickedPhoto = null;
            try {
                pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
            } catch (IOException e) {
                e.printStackTrace();
            }
            profile_img.setImageBitmap(pickedPhoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(VisitingCardActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(VisitingCardActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.darktheme:
                SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean useDarkTheme = preferences.getBoolean(AboutActivity.PREF_DARK_THEME, false);
                if (!useDarkTheme) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, false);
                    editor.apply();
                }
                Intent restarter = getIntent();
                finish();
                startActivity(restarter);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void select_img(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select image source")
                .setCancelable(false)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EZPhotoPickConfig config = new EZPhotoPickConfig();
                        config.photoSource = PhotoSource.GALLERY; // or PhotoSource.CAMERA
                        config.isAllowMultipleSelect = false; // only for GALLERY pick and API >18
                        EZPhotoPick.startPhotoPickActivity(VisitingCardActivity.this, config);
                    }
                })
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EZPhotoPickConfig config = new EZPhotoPickConfig();
                        config.photoSource = PhotoSource.CAMERA; // or PhotoSource.CAMERA
                        config.isAllowMultipleSelect = false; // only for GALLERY pick and API >18
                        EZPhotoPick.startPhotoPickActivity(VisitingCardActivity.this, config);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
