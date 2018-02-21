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
import com.gmail.vanyadubik.capcalculator.utils.LocaleTextWatcher;
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
        initMoneyTextWather(livingMin);
        salaryMin = (EditText) findViewById(R.id.salary);
        initMoneyTextWather(salaryMin);
        firstGrPerc = (EditText) findViewById(R.id.first_group_percent);
        initPercentTextWather(firstGrPerc);
        secondGrPerc = (EditText) findViewById(R.id.second_group_percent);
        initPercentTextWather(secondGrPerc);
        thirdGrPercTax = (EditText) findViewById(R.id.third_group_percent_tax);
        initPercentTextWather(thirdGrPercTax);
        thirdGrPerc = (EditText) findViewById(R.id.third_group_percent);
        initPercentTextWather(thirdGrPerc);
        sSocContr = (EditText) findViewById(R.id.single_soc_contr);
        initPercentTextWather(sSocContr);
        militaryPercent = (EditText) findViewById(R.id.military_percent);
        initPercentTextWather(militaryPercent);
        incomeTax = (EditText) findViewById(R.id.income_tax);
        initPercentTextWather(incomeTax);
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
        livingMin.setText(readDataFromStorage(PAR_LIVING_MIN));
        salaryMin.setText(readDataFromStorage(PAR_SALARY_MIN));
        firstGrPerc.setText(readDataFromStorage(PAR_FIRST_GR_PERC));
        secondGrPerc.setText(readDataFromStorage(PAR_SECOND_GR_PERC));
        thirdGrPercTax.setText(readDataFromStorage(PAR_THRID_GR_PERC_TAX));
        thirdGrPerc.setText(readDataFromStorage(PAR_THRID_GR_PERC));
        sSocContr.setText(readDataFromStorage(PAR_SSC_PERCENT));
        militaryPercent.setText(readDataFromStorage(PAR_MILITARY_PERCENT));
        incomeTax.setText(readDataFromStorage(PAR_INCOME_TAX));
    }

    private String readDataFromStorage(String nameParam){
        return String.format("%.2f", SharedStorage.getDouble(this, nameParam, 0.0));
    }

    private void initMoneyTextWather(final EditText view) {
        view.addTextChangedListener(new LocaleTextWatcher(view, "%.2f ₴"));
    }

    private void initPercentTextWather(final EditText view) {
        view.addTextChangedListener(new LocaleTextWatcher(view, "%.2f %%"));
    }

    private Double getDoubleFromString(Editable s){
        String text = s.toString();
        String cleanString = text.replaceAll("[ руб$₴.%]", "");
        String doubleString = cleanString.replaceAll("[,]", ".");
        return Double.valueOf(String.valueOf(doubleString==null || doubleString.isEmpty() ? "0" : doubleString));
    }
}
