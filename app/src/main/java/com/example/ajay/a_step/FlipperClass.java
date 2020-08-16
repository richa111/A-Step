package com.example.ajay.a_step;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class FlipperClass extends Activity {

    ViewFlipper vf;
    float lastx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipper_class);
        vf=(ViewFlipper)findViewById(R.id.viewfipper);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastx=event.getX();
                break;

            case MotionEvent.ACTION_UP:
                float currentX=event.getX();

                if(lastx<currentX)
                {
                    if(vf.getDisplayedChild()==0)
                    {
                       break;
                    }
                    vf.setInAnimation(this,R.anim.in_from_left);
                    vf.setOutAnimation(this,R.anim.out_to_right);
                    vf.showNext();
                }
                if(lastx>currentX)
                {
                    if(vf.getDisplayedChild()==1)
                    {
                        break;
                    }
                    vf.setInAnimation(this,R.anim.in_from_right);
                    vf.setOutAnimation(this,R.anim.out_to_left);
                    vf.showPrevious();
                }
                break;
        }
        return false;
    }


    public void skip(View view)
    {
        startActivity(new Intent(FlipperClass.this,TermConditions.class));
    }

}
