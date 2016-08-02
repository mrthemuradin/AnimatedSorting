package com.example.mrmuradin.animationsorting;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TestActivity extends AppCompatActivity {

    private TextView tvSneaky1;
    private TextView tvStartAnim;
    private TextView tvStopAnim;

    private TranslateAnimation anim;
    private EndAnimationListener animationListener;

    private float xStart = 0.0f;
    private float yStart = 0.0f;
    private float xEnd = 1.0f;
    private float yEnd = 1.0f;
    private int parentWidth;

    private boolean isNeedToStop;

    private final static int DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvSneaky1 = (TextView) findViewById(R.id.tv_Sneaky1);

        tvStartAnim = (TextView) findViewById(R.id.tv_Start);
        tvStopAnim = (TextView) findViewById(R.id.tv_Stop);

//        fallingAnimation = AnimationUtils.loadAnimation(this, R.anim.falling_down);
//        upAnimation = AnimationUtils.loadAnimation(this, R.anim.falling_up);

//        fallingAnimation.setAnimationListener(animationFallingListener);
//        upAnimation.setAnimationListener(animationUpListener);

        animationListener = new EndAnimationListener();

        tvStartAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anim = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, xStart,
                        Animation.RELATIVE_TO_PARENT, xEnd,
                        Animation.RELATIVE_TO_PARENT, yStart,
                        Animation.RELATIVE_TO_PARENT, yEnd);
                anim.setDuration(DURATION);
                anim.setFillAfter(true);
                anim.setAnimationListener(animationListener);

                tvSneaky1.startAnimation(anim);
                tvSneaky1.setVisibility(View.VISIBLE);
                isNeedToStop = false;
            }
        });

        tvStopAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvSneaky1.clearAnimation();
                isNeedToStop = true;

            }
        });

        SneakyButtonClickListener mListener = new SneakyButtonClickListener();
        tvSneaky1.setOnClickListener(mListener);



    }

    @Override
    protected void onPause() {
        super.onPause();
        tvSneaky1.clearAnimation();

    }

    class EndAnimationListener implements Animation.AnimationListener {

        private float xNextStart;
        private float xNextEnd;
        private float yNextStart;
        private float yNextEnd;

        private float maxDeltaX = 0.65f;
        private float maxDeltaY = 0.8f;
        private float minDeltaX = 0.1f;
        private float minDeltaY = 0.0f;

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            Random r = new Random();

            xNextStart = xEnd;
            yNextStart = yEnd;

            xNextEnd = r.nextFloat()*(maxDeltaX-minDeltaX) + minDeltaX;

//            Toast.makeText(getApplicationContext(), "xNextStart = " + xNextStart +
//            ", xNextEnd = " + xNextEnd, Toast.LENGTH_SHORT).show();

            yNextEnd = r.nextFloat()*(maxDeltaY-minDeltaY) + minDeltaY;

            anim = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, xNextStart,
                    Animation.RELATIVE_TO_PARENT, xNextEnd,
                    Animation.RELATIVE_TO_PARENT, yNextStart,
                    Animation.RELATIVE_TO_PARENT, yNextEnd);
            anim.setDuration(DURATION);
            anim.setFillAfter(true);
            anim.setAnimationListener(animationListener);

            xStart = xNextStart;
            yStart = yNextStart;
            xEnd = xNextEnd;
            yEnd = yNextEnd;

            if (!isNeedToStop) tvSneaky1.startAnimation(anim);
            else {
                xStart = 0.0f;
                yStart = 0.0f;
                xEnd = 1.0f;
                yEnd = 1.0f;
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class SneakyButtonClickListener implements View.OnClickListener {

        View parent = (View) tvSneaky1.getParent();

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "tvWidth = " + tvSneaky1.getWidth()
                    + ", parentWidth = " + parent.getWidth(), Toast.LENGTH_SHORT).show();
        }
    }


}
