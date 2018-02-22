package com.gmail.vanyadubik.capcalculator.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.fragment.CalcMoneyFragment;
import com.gmail.vanyadubik.capcalculator.fragment.CalcTaxFragment;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;
import com.michael.easydialog.EasyDialog;

import static com.gmail.vanyadubik.capcalculator.common.Consts.DATA_SET_DEFAULT;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean doubleBackToExitPressedOnce = false;

    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name_title));

        mFragmentManager = getSupportFragmentManager();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new CalcMoneyFragment()).commit();
        getSupportActionBar().setSubtitle(R.string.action_calc_money_short);
        description = getResources().getString(R.string.action_calc_money_description);
        initMockData();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.double_press_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_help){
            showBubbleMessage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (id == R.id.nav_calc_money) {
            mFragmentTransaction.replace(R.id.containerView, new CalcMoneyFragment()).commit();
            getSupportActionBar().setSubtitle(R.string.action_calc_money_short);
            description = getResources().getString(R.string.action_calc_money_description);
        } else if (id == R.id.nav_calc_tax) {
            mFragmentTransaction.replace(R.id.containerView, new CalcTaxFragment()).commit();
            getSupportActionBar().setSubtitle(R.string.action_calc_tax_short);
            description = getResources().getString(R.string.action_calc_tax_description);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(StartActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(StartActivity.this, InfoActivity.class));
        } else if (id == R.id.nav_exit) {
            ActivityUtils.showQuestion(StartActivity.this, getString(R.string.action_exit),
                    getString(R.string.questions_exit),
                    new ActivityUtils.QuestionAnswer() {
                        @Override
                        public void onPositiveAnsver() {
                            finish();
                        }
                        @Override
                        public void onNegativeAnsver() {}

                        @Override
                        public void onNeutralAnsver() {}
                    });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void showBubbleMessage(){
        View bubbleMessageView = this.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
        final EasyDialog bubbleMessage = new EasyDialog(StartActivity.this)
                .setLayout(bubbleMessageView)
                .setBackgroundColor(StartActivity.this.getResources().getColor(R.color.colorPrimary))
                .setLocationByAttachedView(toolbar)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(25, 25)
                .setOutsideColor(StartActivity.this.getResources().getColor(R.color.outside_color_dark_gray));
        TextView textTitle = (TextView) bubbleMessageView.findViewById(R.id.textTitle);
        textTitle.setText(description);
        ImageButton closeMessage = (ImageButton) bubbleMessageView.findViewById(R.id.closeMessage);
        closeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleMessage.dismiss();
            }
        });
        bubbleMessage.show();
    }

    private void initMockData(){
        if(!SharedStorage.getBoolean(this, DATA_SET_DEFAULT, false)){
            MockData.setDefaultParams(this);
            SharedStorage.setBoolean(this, DATA_SET_DEFAULT, true);
        }
    }
}
