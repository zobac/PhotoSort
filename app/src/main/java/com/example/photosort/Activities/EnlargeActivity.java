package com.example.photosort.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.photosort.Containers.ImageArray;
import com.example.photosort.R;

public class EnlargeActivity extends AppCompatActivity {
    ImageArray imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);
        Intent intent = getIntent();
        imageArray = (ImageArray) intent.getSerializableExtra("ImageArray");

        BitmapFactory bitmapFactory= new BitmapFactory();
        Bitmap bitmap = bitmapFactory.decodeFile(imageArray.getCurrentId().getAbsolutePath());
        ImageView image = (ImageView) findViewById(R.id.enlargedImage);
        image.setImageBitmap(bitmap);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent= new Intent();
                intent.putExtra("ImageArray", imageArray);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
