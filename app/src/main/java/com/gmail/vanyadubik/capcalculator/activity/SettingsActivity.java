package com.gmail.vanyadubik.capcalculator.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;

import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_FIRST_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_INCOME_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_LIVING_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_MILITARY_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SALARY_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SECOND_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SSC_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC_TAX;

public class SettingsActivity extends AppCompatActivity {

    private EditText livingMin, salaryMin, firstGrPerc, secondGrPerc, thirdGrPercTax,
                     thirdGrPerc, sSocContr, militaryPercent, incomeTax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
        getSupportActionBar().setTitle(R.string.action_settings);

        livingMin = (EditText) findViewById(R.id.living_min);
        salaryMin = (EditText) findViewById(R.id.salary);
        firstGrPerc = (EditText) findViewById(R.id.first_group_percent);
        secondGrPerc = (EditText) findViewById(R.id.second_group_percent);
        thirdGrPercTax = (EditText) findViewById(R.id.third_group_percent_tax);
        thirdGrPerc = (EditText) findViewById(R.id.third_group_percent);
        sSocContr = (EditText) findViewById(R.id.single_soc_contr);
        militaryPercent = (EditText) findViewById(R.id.military_percent);
        incomeTax = (EditText) findViewById(R.id.income_tax);
        Button setDefault = (Button) findViewById(R.id.button_default);
        setDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.showQuestion(SettingsActivity.this, getString(R.string.questions_title_info),
                        getString(R.string.questions_data_set_default),
                        new ActivityUtils.QuestionAnswer() {
                            @Override
                            public void onPositiveAnsver() {
                                MockData.setDefaultParams(SettingsActivity.this);
                                initData();
                            }
                            @Override
                            public void onNegativeAnsver() {}

                            @Override
                            public void onNeutralAnsver() {}
                        });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {

            ActivityUtils.showQuestion(SettingsActivity.this, getString(R.string.questions_title_info),
                    getString(R.string.questions_data_save),
                    new ActivityUtils.QuestionAnswer() {
                        @Override
                        public void onPositiveAnsver() {
                            SharedStorage.setDouble(SettingsActivity.this, PAR_LIVING_MIN,
                                    getDoubleFromString(livingMin.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_SALARY_MIN,
                                    getDoubleFromString(salaryMin.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_FIRST_GR_PERC,
                                    getDoubleFromString(firstGrPerc.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_SECOND_GR_PERC,
                                    getDoubleFromString(secondGrPerc.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_THRID_GR_PERC,
                                    getDoubleFromString(thirdGrPerc.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_THRID_GR_PERC_TAX,
                                    getDoubleFromString(thirdGrPercTax.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_SSC_PERCENT,
                                    getDoubleFromString(sSocContr.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_MILITARY_PERCENT,
                                    getDoubleFromString(militaryPercent.getText()));
                            SharedStorage.setDouble(SettingsActivity.this, PAR_INCOME_TAX,
                                    getDoubleFromString(incomeTax.getText()));
                        }

                        @Override
                        public void onNegativeAnsver() {}

                        @Override
                        public void onNeutralAnsver() {}
                    });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    private void initData(){

        livingMin.setText(String.valueOf(SharedStorage.getDouble(this, PAR_LIVING_MIN, 0.0)));
        salaryMin.setText(String.valueOf(SharedStorage.getDouble(this, PAR_SALARY_MIN, 0.0)));
        firstGrPerc.setText(String.valueOf(SharedStorage.getDouble(this, PAR_FIRST_GR_PERC, 0.0)));
        secondGrPerc.setText(String.valueOf(SharedStorage.getDouble(this, PAR_SECOND_GR_PERC, 0.0)));
        thirdGrPercTax.setText(String.valueOf(SharedStorage.getDouble(this, PAR_THRID_GR_PERC_TAX, 0.0)));
        thirdGrPerc.setText(String.valueOf(SharedStorage.getDouble(this, PAR_THRID_GR_PERC, 0.0)));
        sSocContr.setText(String.valueOf(SharedStorage.getDouble(this, PAR_SSC_PERCENT, 0.0)));
        militaryPercent.setText(String.valueOf(SharedStorage.getDouble(this, PAR_MILITARY_PERCENT, 0.0)));
        incomeTax.setText(String.valueOf(SharedStorage.getDouble(this, PAR_INCOME_TAX, 0.0)));

    }

    private int getIntFromString(Editable s){
        String text = String.valueOf(s);
        return Integer.valueOf(String.valueOf(text==null || text.isEmpty() ? "0" : s));
    }
    private Double getDoubleFromString(Editable s){
        String text = String.valueOf(s);
        return Double.valueOf(String.valueOf(text==null || text.isEmpty() ? "0" : s));
    }
}