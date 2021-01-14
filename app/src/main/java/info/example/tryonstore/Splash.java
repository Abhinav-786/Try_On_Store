package info.example.tryonstore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends Activity {
    ImageView i;

    TextView textView;
    LinearLayout Revealdemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = findViewById(R.id.abhinav);
        Revealdemo = findViewById(R.id.splashmainlayout);
        Revealdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        i = findViewById(R.id.imageView);
        Handler handler = new Handler();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                makeCircularRevealAnimation(Revealdemo);
//            }
//        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("A");
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("B");
            }
        }, 450);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("H");
            }
        }, 600);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("I");
            }
        }, 750);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("N");
            }
        }, 900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("A");
            }
        }, 1050);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.append("V");
            }
        }, 1200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(Splash.this, Intro_mainactivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);
        handler.removeCallbacks(null);


    }

    private void makeCircularRevealAnimation(final View view) {
        final LinearLayout lay = findViewById(R.id.Splashlayout);
        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;
        float radius = Math.max(lay.getWidth(), lay.getHeight()) * 2.0f;
        if (lay.getVisibility() == View.VISIBLE) {
            Animator reveal = ViewAnimationUtils.createCircularReveal(lay, centerX, centerY, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    lay.setVisibility(View.INVISIBLE);
                }
            });
            reveal.setDuration(1500);
            reveal.start();


        } else {
            lay.setVisibility(View.VISIBLE);
            ViewAnimationUtils.createCircularReveal(lay, centerX, centerY, radius, 0).start();
        }
    }


}