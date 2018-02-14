package com.gmail.vanyadubik.capcalculator.model;

import android.content.Context;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.vanyadubik.capcalculator.common.Consts.FIRST_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.INCOME_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.LIVING_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.MILITARY_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_FIRST_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_INCOME_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_LIVING_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_MILITARY_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SALARY_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SECOND_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SSC_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.SALARY_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.SECOND_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.SSC_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.THRID_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.THRID_GR_PERC_TAX;

public class MockData {

    public static void setDefaultParams(Context context) {
        SharedStorage.setDouble(context, PAR_LIVING_MIN, LIVING_MIN);
        SharedStorage.setDouble(context, PAR_SALARY_MIN, SALARY_MIN);
        SharedStorage.setDouble(context, PAR_FIRST_GR_PERC, FIRST_GR_PERC);
        SharedStorage.setDouble(context, PAR_SECOND_GR_PERC, SECOND_GR_PERC);
        SharedStorage.setDouble(context, PAR_THRID_GR_PERC, THRID_GR_PERC);
        SharedStorage.setDouble(context, PAR_THRID_GR_PERC_TAX, THRID_GR_PERC_TAX);
        SharedStorage.setDouble(context, PAR_SSC_PERCENT, SSC_PERCENT);
        SharedStorage.setDouble(context, PAR_MILITARY_PERCENT, MILITARY_PERCENT);
        SharedStorage.setDouble(context, PAR_INCOME_TAX, INCOME_TAX);
    }

    public static List<String> getListTaxSystem(Context context) {
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.tax_system_1));
        list.add(context.getString(R.string.tax_system_2));
        list.add(context.getString(R.string.tax_system_3));

        return list;
    }

    public static List<String> getListGroup(Context context) {
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.group_1));
        list.add(context.getString(R.string.group_2));
        list.add(context.getString(R.string.group_3));

        return list;
    }

    public static List<String> getListTax(Context context) {
        List<String> list = new ArrayList<>();
        list.add(context.getString(R.string.tax_1));
        list.add(context.getString(R.string.tax_2));

        return list;
    }

}
