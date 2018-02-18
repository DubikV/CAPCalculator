package com.gmail.vanyadubik.capcalculator.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.logs.ReportFilesProvider;
import com.gmail.vanyadubik.capcalculator.logs.ReportHandler;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;
import com.gmail.vanyadubik.capcalculator.utils.PropertyUtils;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static com.gmail.vanyadubik.capcalculator.common.Consts.APPLICATION_PROPERTIES;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_FIRST_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_INCOME_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_LIVING_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_MILITARY_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SALARY_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SECOND_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SSC_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC_TAX;

public class InfoActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear);
        getSupportActionBar().setTitle(R.string.action_info);

        textView = (TextView) findViewById(R.id.textView);

        Button sendMail = (Button) findViewById(R.id.button_mail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail;

                try {
                    Properties properties = PropertyUtils.getProperties(APPLICATION_PROPERTIES, getApplicationContext());
                    mail = properties.getProperty("dev-mail");
                } catch (IOException e) {
                    throw new IllegalStateException("Cannot read auth properties", e);
                }

                Intent target = new Intent(Intent.ACTION_SEND_MULTIPLE);
                target.setType("text/plain");
                target.putExtra(Intent.EXTRA_EMAIL, new String[] {
                        mail});

                target.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));

                ArrayList<Uri> attachments = new ArrayList<Uri>();

                target.putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments);

                startActivity(Intent.createChooser(target, "Send Crash Report Using"));

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

        //textView.setText(String.valueOf(SharedStorage.getDouble(this, PAR_LIVING_MIN, 0.0)));

    }

}
