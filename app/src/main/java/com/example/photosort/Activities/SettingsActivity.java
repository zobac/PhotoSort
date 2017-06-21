package com.example.photosort.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.photosort.Containers.ImageArray;
import com.example.photosort.R;
import com.example.photosort.Containers.TagArray;

public class SettingsActivity extends AppCompatActivity {
    ImageArray imageArray;
    TagArray tagArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent= new Intent();
                intent.putExtra("ImageArray", imageArray);
                intent.putExtra("TagArray", tagArray);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
