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
import android.widget.TextView;

import com.gmail.vanyadubik.capcalculator.R;

public class ResultActivity extends AppCompatActivity {

    public static final String TAX_SYSTEM_RESULT = "tax_system";
    public static final String GROUP_RESULT = "group";
    public static final String TAX_RESULT = "tax";
    public static final String INCOME_ALL_RESULT = "income_all";
    public static final String TAX_ALL_RESULT = "tax_all";
    public static final String TAX_ALL_PERCENT_RESULT = "tax_all_percent";
    public static final String TAX_SOC_RESULT = "tax_soc";
    public static final String TAX_INCOME_RESULT = "tax_income";
    public static final String TAX_MILITARY_RESULT = "tax_military";
    public static final String TAX_TAX_RESULT = "tax_tax";
    public static final String TAX_SINGLE_RESULT = "tax_single";

    private int height, width;
    private Button nextButton;
    private ConstraintLayout mConstraintLayout;
    private int duration = 300;
    private Transition sharedElementEnterTransition;
    private Transition.TransitionListener mTransitionListener;
    private TextView taxSystem, group, tax, incomeAll, taxAll, taxAllPercent,
            taxSoc, taxIncome, taxMilitary, taxTax, taxSingle;

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

        taxSystem = (TextView) findViewById(R.id.tax_system);
        group = (TextView) findViewById(R.id.group);
        tax = (TextView) findViewById(R.id.tax);
        incomeAll = (TextView) findViewById(R.id.income_all);
        taxAll = (TextView) findViewById(R.id.tax_all);
        taxAllPercent = (TextView) findViewById(R.id.tax_all_percent);
        taxSoc = (TextView) findViewById(R.id.tax_soc);
        taxIncome = (TextView) findViewById(R.id.tax_income);
        taxMilitary = (TextView) findViewById(R.id.tax_military);
        taxTax = (TextView) findViewById(R.id.tax_tax);
        taxSingle = (TextView) findViewById(R.id.tax_single);

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
            String taxSystemRes = extras.getString(TAX_SYSTEM_RESULT);
            taxSystem.setText(taxSystemRes == null || taxSystemRes.isEmpty() ? "--" : taxSystemRes);

            String groupRes = extras.getString(GROUP_RESULT);
            group.setText(groupRes == null || groupRes.isEmpty() ? "--" : groupRes);

            String taxRes = extras.getString(TAX_RESULT);
            tax.setText(taxRes == null || taxRes.isEmpty() ? "--" : taxRes);

            String zeroMoneyString = getResources().getString(R.string.zero_money);
            String zeroSymbolString = getResources().getString(R.string.zero_symbol);

            Double incomeAllRes = extras.getDouble(INCOME_ALL_RESULT);
            incomeAll.setText(incomeAllRes == null || incomeAllRes==0 ? zeroMoneyString : String.valueOf(incomeAllRes)+zeroSymbolString);

            Double taxAllRes = extras.getDouble(TAX_ALL_RESULT);
            taxAll.setText(taxAllRes == null || taxAllRes==0 ? zeroMoneyString : String.valueOf(taxAllRes)+zeroSymbolString);

            Double taxAllPercentRes = extras.getDouble(INCOME_ALL_RESULT);
            taxAllPercent.setText(getResources().getString(R.string.which_is)+" " + String.valueOf(taxAllPercentRes)+getResources().getString(R.string.of_income));

            Double taxSocRes = extras.getDouble(TAX_SOC_RESULT);
            taxSoc.setText(taxSocRes == null || taxSocRes==0 ? zeroMoneyString : String.valueOf(taxSocRes)+zeroSymbolString);

            Double taxIncomeRes = extras.getDouble(TAX_INCOME_RESULT);
            taxIncome.setText(taxIncomeRes == null || taxIncomeRes==0 ? zeroMoneyString : String.valueOf(taxIncomeRes)+zeroSymbolString);

            Double taxMilitaryRes = extras.getDouble(TAX_INCOME_RESULT);
            taxMilitary.setText(taxMilitaryRes == null || taxMilitaryRes==0 ? zeroMoneyString : String.valueOf(taxMilitaryRes)+zeroSymbolString);

            Double taxTaxRes = extras.getDouble(TAX_TAX_RESULT);
            taxTax.setText(taxTaxRes == null || taxTaxRes==0 ? zeroMoneyString : String.valueOf(taxTaxRes)+zeroSymbolString);

            Double taxSingleRes = extras.getDouble(TAX_SINGLE_RESULT);
            taxSingle.setText(taxSingleRes == null || taxSingleRes==0 ? zeroMoneyString : String.valueOf(taxSingleRes)+zeroSymbolString);
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
