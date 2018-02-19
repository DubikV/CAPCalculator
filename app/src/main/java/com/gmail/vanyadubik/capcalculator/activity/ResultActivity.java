package com.gmail.vanyadubik.capcalculator.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.vanyadubik.capcalculator.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private static final String NAME_SCREENSCHOT_FILE = "tax_result_screenshot.jpeg";

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
    private View container;

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

        container = (View) findViewById(R.id.container);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_share_result){

            Bitmap screen = getScreenShot(container);

            shareImage(store(screen, NAME_SCREENSCHOT_FILE));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeAnimation();

        File file = new File(getScreenshotsDirPath(), NAME_SCREENSCHOT_FILE);
        if(file.exists()) file.delete();

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

            setTextFromExtras(incomeAll,  extras, INCOME_ALL_RESULT);
            setTextFromExtras(taxAll,  extras, TAX_ALL_RESULT);

            Double taxAllPercentRes = extras.getDouble(TAX_ALL_PERCENT_RESULT);
            String datastring = NumberFormat.getNumberInstance(new Locale("ua", "UA")).format(taxAllPercentRes);
            taxAllPercent.setText(getResources().getString(R.string.which_is)+" "+datastring+getResources().getString(R.string.of_income));

            setTextFromExtras(taxSoc,  extras, TAX_SOC_RESULT);
            setTextFromExtras(taxIncome,  extras, TAX_INCOME_RESULT);
            setTextFromExtras(taxMilitary,  extras, TAX_MILITARY_RESULT);
            setTextFromExtras(taxTax,  extras, TAX_TAX_RESULT);
            setTextFromExtras(taxSingle,  extras, TAX_SINGLE_RESULT);
        }
    }

    private void setTextFromExtras(TextView textView,  Bundle extras, String nameParam){
        String zeroMoneyString = getResources().getString(R.string.zero_money);
        Double data = extras.getDouble(nameParam);

        String datastring = NumberFormat.getCurrencyInstance(new Locale("ua", "UA")).format(data);

        textView.setText(datastring == null || datastring.isEmpty() ? zeroMoneyString : datastring);

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

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private static String getScreenshotsDirPath(){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        return dirPath;
    }

    private static File store(Bitmap bm, String fileName){
        File file = new File(getScreenshotsDirPath(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void shareImage(File file){
        if(file==null) return;
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}
