package com.gmail.vanyadubik.capcalculator.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.gmail.vanyadubik.capcalculator.R;

public class ResultActivity extends AppCompatActivity {

    private int height, width;
    private Button nextButton;
    private ConstraintLayout mConstraintLayout;
    private int duration = 300;
    private Transition sharedElementEnterTransition;
    private Transition.TransitionListener mTransitionListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
        getSupportActionBar().setTitle(R.string.result_calс);

        nextButton = (Button) findViewById(R.id.next_button);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.bg);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(null);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
            sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();


            mTransitionListener = new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {}
                @Override
                public void onTransitionEnd(Transition transition) {
                    setAnim(mConstraintLayout, true);
                    setFab(nextButton, false);
                }
                @Override
                public void onTransitionCancel(Transition transition) {}
                @Override
                public void onTransitionPause(Transition transition) {}
                @Override
                public void onTransitionResume(Transition transition) {}
            };

            sharedElementEnterTransition.addListener(mTransitionListener);
        } else{
            nextButton.setVisibility(View.GONE);
            mConstraintLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        closeAnimation();
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        closeAnimation();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
           // openForResult = extras.getBoolean(ANIMALS_ACTIVITY_OPEN_FOR_RESULT);
        }
    }

    private void closeAnimation(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition.removeListener(mTransitionListener);
            setAnim(mConstraintLayout, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnim(final View myView, boolean isShow) {

        int cx = nextButton.getWidth() / 2;
        int cy = nextButton.getHeight() / 2;

        float finalRadius = (float) Math.hypot(width, height);

        int[] startingLocation = new int[2];
        nextButton.getLocationInWindow(startingLocation);

        Animator anim;
        if (isShow) {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (nextButton.getX() + cx), (int) (nextButton.getY() + cy), 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
        } else {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (nextButton.getX() + cx), (int) (nextButton.getY() + cy), finalRadius, 0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    myView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        anim.setDuration(duration);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setFab(final View myView, boolean isShow) {

        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, initialRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.VISIBLE);
                    finishAfterTransition();
                }
            });
            anim.setDuration(duration);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });
        }
        anim.start();

    }
}
