package com.example.photosort.Containers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.photosort.Containers.ImageArray;

/**
 * Created by mikez on 5/10/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    ImageArray imageArray;
    public ImageAdapter(Context c, ImageArray ia) {
        mContext = c;
        imageArray = ia;
    }

    public int getCount() {
        return imageArray.getSize();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        BitmapFactory bitmapFactory= new BitmapFactory();
        Bitmap bitmap = bitmapFactory.decodeFile(imageArray.getItemAt(position).getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    public void clear(){
        this.imageArray.clear();
    }

}

