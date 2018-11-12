package com.community.jboss.visitingcard.VisitingCard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.community.jboss.visitingcard.Maps.MapsActivity;
import com.community.jboss.visitingcard.PickImageDialog;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;

public class VisitingCardActivity extends AppCompatActivity {
    ImageView user_icon;
    int IMAGE_FROM_GALLERY = 508;
    int IMAGE_FROM_CAMERA = 507;
    int CAMERA_PERMISSION_CODE = 102;
    int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);

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
        user_icon = findViewById(R.id.profile_image);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog pickImageDialog = new PickImageDialog(VisitingCardActivity.this);
                pickImageDialog.show();
            }
        });
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showCameraPhotoPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                startCameraActivity();
            }
        } else {
            startCameraActivity();
        }
    }
    private void startCameraActivity(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_FROM_CAMERA);
    }

    public void showGalleryPhotoPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                startGalleryActivity();
            }
        } else {
            startGalleryActivity();
        }
    }
    private void startGalleryActivity(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), IMAGE_FROM_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraActivity();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGalleryActivity();
            } else {
                Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            user_icon.setImageBitmap(photo);
        }
        else if(requestCode == IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK){
            Uri contentURI = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
            }
            catch (Exception e){
                Toast.makeText(this,"Failed to load the picture",Toast.LENGTH_SHORT).show();
                return;
            }
            user_icon.setImageBitmap(bitmap);
        }
    }

}
