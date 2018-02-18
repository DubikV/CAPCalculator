package com.gmail.vanyadubik.capcalculator.app;


import android.app.Application;


import com.gmail.vanyadubik.capcalculator.logs.ReportHandler;
import com.gmail.vanyadubik.capcalculator.utils.PropertyUtils;

import java.io.IOException;
import java.util.Properties;

import static com.gmail.vanyadubik.capcalculator.common.Consts.APPLICATION_PROPERTIES;

public class CAPCalculatorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initReportHandler();

    }


    private void initReportHandler(){
        String mail;

        try {
            Properties properties = PropertyUtils.getProperties(APPLICATION_PROPERTIES, this);
            mail = properties.getProperty("dev-mail");
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read auth properties", e);
        }

        ReportHandler.install(this, mail);

    }

}
