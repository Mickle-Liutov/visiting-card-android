package com.community.jboss.visitingcard;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;

import com.community.jboss.visitingcard.VisitingCard.VisitingCardActivity;

public class PickImageDialog extends Dialog {

    public Activity m_activity;
    ConstraintLayout camera_container;
    ConstraintLayout gallery_container;
    int IMAGE_FROM_GALLERY = 508;
    int IMAGE_FROM_CAMERA = 507;

    public PickImageDialog(Activity a) {
        super(a);
        m_activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_image_dialog);
        camera_container = findViewById(R.id.camera_icon_container);
        gallery_container = findViewById(R.id.gallery_icon_container);
        camera_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((VisitingCardActivity)m_activity).showCameraPhotoPicker();
                dismiss();
            }
        });
        gallery_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((VisitingCardActivity)m_activity).showGalleryPhotoPicker();
                dismiss();
            }
        });
    }


}
