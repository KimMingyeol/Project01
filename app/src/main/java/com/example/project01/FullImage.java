package com.example.project01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class FullImage extends Activity {
    ImageSwitcher Switch;
    ImageView images;
    TextView textView;
    float initialX;
    Adapter1 imageAdapter;
    private int position ;
    private final float slide_range = 100f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.full_image_view);
        textView = findViewById(R.id.image_location);
        Switch = (ImageSwitcher) findViewById(R.id.imageSwitcher);

        Intent i = getIntent();
        position = i.getExtras().getInt("id");
        imageAdapter = new Adapter1(this);

        images = (ImageView) findViewById(R.id.full_image_view);
        images.setImageResource(imageAdapter.mThumbIds[position]);
        textView.setText(String.valueOf(position+1)+" / "+String.valueOf(imageAdapter.mThumbIds.length));
    }

    public void SetTextVisible() {
        if(textView.getAlpha() == 0) {
            textView.animate().alpha(1.0f);
        }
        else {
            textView.animate().alpha(0);
        }
        /*
        if(textView.getVisibility() == View.INVISIBLE) {
            textView.setVisibility(View.VISIBLE);
        }
        else {
            textView.setVisibility(View.INVISIBLE);
        }
        */
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                float pos_delta = (finalX > initialX) ? finalX - initialX : initialX - finalX;
                // Log.d("Check [initial, final]: ", "["+String.valueOf(initialX)+", "+String.valueOf(finalX)+"]");
                // Log.d("Check image arr size: ", String.valueOf(imageAdapter.mThumbIds.length));
                if (pos_delta > slide_range) {
                    if (initialX > finalX) {
                        if (position == imageAdapter.mThumbIds.length-1) {
                            position = 0;
                            images.setImageResource(imageAdapter.mThumbIds[0]);
                            Switch.showPrevious();
                        } else {
                            position++;
                            images.setImageResource(imageAdapter.mThumbIds[position]);
                            Switch.showNext();
                        }
                    }
                    else
                    {
                        if (position > 0)
                        {
                            position= position-1;
                            images.setImageResource(imageAdapter.mThumbIds[position]);
                            Switch.showPrevious();
                        }
                        else
                        {
                            position = imageAdapter.mThumbIds.length-1;
                            images.setImageResource(imageAdapter.mThumbIds[position]);
                            Switch.showPrevious();
                        }
                    }
                    textView.setText(String.valueOf(position+1)+" / "+String.valueOf(imageAdapter.mThumbIds.length));
                }
                else
                {
                    SetTextVisible();
                }
                break;
        }
        return false;
    }
}