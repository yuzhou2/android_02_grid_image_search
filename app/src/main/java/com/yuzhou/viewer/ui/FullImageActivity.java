package com.yuzhou.viewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuzhou.viewer.R;
import com.yuzhou.viewer.model.GoogleImage;

public class FullImageActivity extends AppCompatActivity
{
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent intent = getIntent();
        GoogleImage image = (GoogleImage) intent.getParcelableExtra("image");

        ImageView imageView = (ImageView) findViewById(R.id.ivFullImage);
        Picasso.with(this).load(image.getUnescapedUrl()).resize(image.getWidth(), image.getHeight()).into(imageView);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onDoubleTap(MotionEvent e)
            {
                finish();
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

}
