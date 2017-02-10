package com.example.sailik.mytask_10_feb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mCameraButton;
    private ImageButton mGalleryButton;
    private Button mNextScreenButton;
    private ImageView mPictureImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraButton = (ImageButton) findViewById(R.id.cam_button);
        mGalleryButton = (ImageButton) findViewById(R.id.gallery_button);
        mNextScreenButton = (Button) findViewById(R.id.nextscreen_button);
        mPictureImageView = (ImageView) findViewById(R.id.picture_imageview);

        mCameraButton.setOnClickListener(this);
        mGalleryButton.setOnClickListener(this);
        mNextScreenButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.nextscreen_button:
                String value="https://pixabay.com/";
                Intent myIntent = new Intent(MainActivity.this, NextScreenActivity.class);
                myIntent.putExtra("message", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                break;

            case R.id.cam_button:
                final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                }
                break;
            case R.id.gallery_button:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent,RESULT_LOAD_IMG);

                break;

        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPictureImageView.setImageBitmap(photo);
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode != Activity.RESULT_OK){
            Toast.makeText(MainActivity.this,getResources().getString(R.string.cancel_toast),Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            mPictureImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        else if (requestCode == RESULT_LOAD_IMG && resultCode != RESULT_OK
                && null != data){
            Toast.makeText(this, getResources().getString(R.string.cancel_toast),
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, getResources().getString(R.string.error_toast), Toast.LENGTH_LONG)
                    .show();
        }
    }


}
