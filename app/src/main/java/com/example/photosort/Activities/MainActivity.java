package com.example.photosort.Activities;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.location.Location;
        import android.media.ExifInterface;
        import android.net.Uri;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.v4.content.FileProvider;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.content.Intent;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.example.photosort.Containers.ImageArray;
        import com.example.photosort.Containers.TagArray;
        import com.example.photosort.Utilities.DateProvider;
        import com.example.photosort.Utilities.ExifUtil;
        import com.example.photosort.R;
        import com.example.photosort.Utilities.LocationProvider;

        import java.io.File;
        import java.util.Date;
        import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public final String APP_TAG = "PhotosortApp";

    private static final int MAIN_ACTIVITY_CODE = 2404;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File currentfile;
    ImageArray imageArray = new ImageArray();
    TagArray tagArray = new TagArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImageArray();
        if(imageArray.getSize() > 0){
            setCurrentImage();
        }


        String[] tagsNames = tagArray.asArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, tagsNames);

        ListView listView = (ListView) findViewById(R.id.tagListView);
        listView.setAdapter(adapter);

    }

    private void setImageArray(){
        File mediaStorageDir = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        imageArray.clear();
        imageArray.setFilePath(mediaStorageDir);

        File[] files = mediaStorageDir.listFiles();
        if (files != null) {
            for (File file : files) {
                imageArray.add(file);
            }
        }
    }



    protected void setCurrentImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(imageArray.getCurrentId().getAbsolutePath());
        ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
        imageView.setImageBitmap(bitmap);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode == RESULT_OK && requestCode == MAIN_ACTIVITY_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                return;
            }
        }
        else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                writeExtifInfo();
                setImageArray();
                imageArray.setLastAsCurrent();
                setCurrentImage();

            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void scrollRight(View view){
        imageArray.increment();
        setCurrentImage();
    }

    public void scrollLeft(View view){
        imageArray.decrement();
        setCurrentImage();
    }

    public void enlargePhoto(View view){
        Intent intent = new Intent(this, EnlargeActivity.class);
        intent.putExtra("ImageArray", imageArray);
        startActivityForResult(intent, MAIN_ACTIVITY_CODE);
    }

    public void filterPhotos(View view){
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("TagArray", tagArray);
        intent.putExtra("ImageArray", imageArray);
        startActivityForResult(intent, MAIN_ACTIVITY_CODE);
    }

    public void snapPhoto(View view){
        photoFileName = String.valueOf(System.currentTimeMillis());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            currentfile = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", currentfile);
        }
        return null;
    }

    private void writeExtifInfo(){
        ExifUtil exifParser = new ExifUtil();
        exifParser.writeExif(currentfile.getPath(), ExifInterface.TAG_DATETIME, DateProvider.getDateString());

        // gotta get the gps info
        LocationProvider lp = new LocationProvider();
        String lat = lp.getLatitude();
        String lon = lp.getLongitude();

        exifParser.writeExif(currentfile.getPath(), ExifInterface.TAG_GPS_LATITUDE, lp.getLatitude());
        exifParser.writeExif(currentfile.getPath(), ExifInterface.TAG_GPS_LATITUDE_REF, lp.getNS());
        exifParser.writeExif(currentfile.getPath(), ExifInterface.TAG_GPS_LONGITUDE, lp.getLongitude());
        exifParser.writeExif(currentfile.getPath(), ExifInterface.TAG_GPS_LONGITUDE_REF, lp.getEW());
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
