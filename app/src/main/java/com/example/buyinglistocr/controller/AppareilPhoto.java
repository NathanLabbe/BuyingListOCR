package com.example.buyinglistocr.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.buyinglistocr.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppareilPhoto extends AppCompatActivity {

    // constant
    public static final int RETURN_TAKE_PHOTO = 1;

    // variables for access to graphic objects
    private Button mButtonTakePhoto;
    private ImageView mImageViewLookPhoto;
    private String mPhotoPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appareil_photo);
        initActivity();
    }

    /**
     * initialization of the activity
     */
    private void initActivity(){
        //recovery of graphic objects
        mButtonTakePhoto = findViewById(R.id.buttonTakePhoto);
        mImageViewLookPhoto = findViewById(R.id.affichagePhoto);
        //methods to manage events
        createOnClickBtnTakePhoto();
    }

    /**
     * click on button take photo
     */
    private void createOnClickBtnTakePhoto(){
        mButtonTakePhoto.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAPhoto();
            }
        });
    }



    /**
     * access to the camera and save in tmp file
     */
    private void takeAPhoto(){
        // intent creation to open a window to take the photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // test than camera can be used
        if(intent.resolveActivity(getPackageManager()) != null){
            // create a unique name
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo"+time,
                        ".jpg", photoDir);
                // save the complete path
                mPhotoPath = photoFile.getAbsolutePath();
                // create URI
                Uri photoUri = FileProvider.getUriForFile(AppareilPhoto.this,
                        AppareilPhoto.this.getApplicationContext().getPackageName()+".provider",
                        photoFile);
                // transfer URI to ident to save the photo in tmp
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                // open the activity with the intent
                startActivityForResult(intent, RETURN_TAKE_PHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    /**
     * return pf the camera call (startActivityForResult)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check the code
        if(requestCode==RETURN_TAKE_PHOTO && resultCode==RESULT_OK){
            // get the image
            Bitmap image = BitmapFactory.decodeFile(mPhotoPath);
            // display the photo
            mImageViewLookPhoto.setImageBitmap(image);
        }
    }
}
