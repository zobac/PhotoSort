package com.example.photosort.Activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;

import com.example.photosort.Containers.ImageAdapter;
import com.example.photosort.Containers.ImageArray;
import com.example.photosort.Filters.DateFilter;
import com.example.photosort.Filters.LocationFilter;
import com.example.photosort.R;
import com.example.photosort.Containers.TagArray;
import com.example.photosort.Utilities.DateProvider;
import com.example.photosort.Utilities.ExifUtil;
import com.example.photosort.Utilities.LocationProvider;

import java.io.File;
import java.util.Date;
import java.util.HashMap;


public class FilterActivity extends AppCompatActivity {
    TagArray tagArray;
    ImageArray imageArray;
    private static HashMap<File, Location> fileLocations = new HashMap<File, Location>();
    private static HashMap<File, Date> fileDates = new HashMap<File, Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_filter);

//        tagArray = (TagArray) intent.getSerializableExtra("TagArray");
//        String[] tagsNames = tagArray.asArray();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_multiple_choice, tagsNames);

//        GridView tagGrid = (GridView) findViewById(R.id.tagGrid);
//        tagGrid.setAdapter(adapter);
//        for (int i = 0; i < adapter.getCount(); i++) {
//            tagGrid.setItemChecked(i, false);
//        }

        imageArray = (ImageArray) intent.getSerializableExtra("ImageArray");
         parseExifData();
        setCurrentImages(imageArray.asArray());

        SeekBar dateFilterBar = (SeekBar) findViewById(R.id.dateFilterBar);
        dateFilterBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                filterByDate();
            }
        });

        SeekBar locationFilterBar = (SeekBar) findViewById(R.id.locationFilterBar);
        locationFilterBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                filterByLocation();
            }
        });
    }


    private void setCurrentImages(File[] files){
        ImageArray tempImageArray = new ImageArray();
        for(File file : files){
            tempImageArray.add(file);
        }

        GridView imageGrid = (GridView) findViewById(R.id.photoGrid);
        imageGrid.setAdapter(new ImageAdapter(this, tempImageArray));

    }

    private void parseExifData(){
        ExifUtil exifUtil = new ExifUtil();

        for(File file : imageArray.asArray()){
            String str = exifUtil.readExif(file.getAbsolutePath());
            String[] tags = str.split("\n");
            String dateString = tags[1].split(": ")[1];
            if(!dateString.contentEquals("null")){
                Date date = DateProvider.getDate(dateString);
                fileDates.put(file, date);
            }

            String latitude = tags[2].split(": ")[1];
            String latRef = tags[3].split(": ")[1];
            String longitude = tags[4].split(": ")[1];
            String  lonRef= tags[5].split(": ")[1];

            if(!longitude.contentEquals("null")
                    && !lonRef.contentEquals("null")
                    && !latitude.contentEquals("null")
                    && !latRef.contentEquals("null")){
                Location location = LocationProvider.getLocation(latitude, latRef, longitude, lonRef);
                fileLocations.put(file, location);
            }
        }
    }


    private void filterByDate() {
        SeekBar dateFilterBar = (SeekBar) findViewById(R.id.dateFilterBar);
        int months = dateFilterBar.getProgress();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int year = datePicker.getYear();
        int month = datePicker.getMonth();

        ImageArray files = new ImageArray();
        for(File file : imageArray.asArray()){
            if(DateFilter.inRange(year, month, months, fileDates.get(file))){
                files.add(file);
            }
        }
        setCurrentImages(files.asArray());
    }

    private void filterByLocation(){
        SeekBar locationFilterBar = (SeekBar) findViewById(R.id.locationFilterBar);
        int radius = locationFilterBar.getProgress();

        EditText longText = (EditText) findViewById(R.id.longitudeValue);
        Double longitude = Double.parseDouble(longText.getText().toString());

        EditText latText = (EditText) findViewById(R.id.latitudeValue);
        Double latitude = Double.parseDouble(latText.getText().toString());


        ImageArray files = new ImageArray();
        for(File file : imageArray.asArray()){
            if(LocationFilter.inRadius(latitude, longitude, radius, fileLocations.get(file))){
                files.add(file);
            }
        }
        setCurrentImages(files.asArray());
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

    public void onCheckbox(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.dateCheckbox:
                if (checked){
                    findViewById(R.id.datePicker).setVisibility(View.VISIBLE);
                    SeekBar seekBar = (SeekBar) findViewById(R.id.dateFilterBar);
                    seekBar.setVisibility(View.VISIBLE);
                    seekBar.setProgress(50);
                }

                else{
                    findViewById(R.id.datePicker).setVisibility(View.GONE);
                    findViewById(R.id.dateFilterBar).setVisibility(View.GONE);
                }
                break;

            case R.id.locationCheckbox:
                if (checked){
                    findViewById(R.id.latitudeText).setVisibility(View.VISIBLE);
                    findViewById(R.id.latitudeValue).setVisibility(View.VISIBLE);
                    findViewById(R.id.longitudeText).setVisibility(View.VISIBLE);
                    findViewById(R.id.longitudeValue).setVisibility(View.VISIBLE);
                    SeekBar seekBar = (SeekBar) findViewById(R.id.locationFilterBar);
                    seekBar.setVisibility(View.VISIBLE);
                    seekBar.setProgress(0);
                }

                else{
                    findViewById(R.id.latitudeText).setVisibility(View.GONE);
                    findViewById(R.id.latitudeValue).setVisibility(View.GONE);
                    findViewById(R.id.longitudeText).setVisibility(View.GONE);
                    findViewById(R.id.longitudeValue).setVisibility(View.GONE);
                    findViewById(R.id.locationFilterBar).setVisibility(View.GONE);
                }
                break;
        }
    }


}

