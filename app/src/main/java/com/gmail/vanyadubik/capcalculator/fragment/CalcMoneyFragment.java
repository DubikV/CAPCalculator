package com.gmail.vanyadubik.capcalculator.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.activity.ResultActivity;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;
import com.gmail.vanyadubik.capcalculator.utils.LocaleTextWatcher;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;

import java.util.List;

import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.CALC_MONEY;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.COSTS_ADD_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.GROUP_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.INCOME_ALL_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.INCOME_BASE_ALL_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_ALL_PERCENT_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_ALL_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_INCOME_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_MILITARY_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_SINGLE_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_SOC_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_SYSTEM_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.TAX_TAX_RESULT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_FIRST_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_INCOME_TAX;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_LIVING_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_MILITARY_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SALARY_MIN;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SECOND_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_SSC_PERCENT;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC;
import static com.gmail.vanyadubik.capcalculator.common.Consts.PAR_THRID_GR_PERC_TAX;

public class CalcMoneyFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_amoynt_paiment;

    private View view;
    private EditText taxSystem, group, tax, incomeAll,
            costsAddPercent, costsAdd;
    private TextInputLayout groupTIL, taxTIL, incomeAllTIL,
            costsAddPercentTIL, costsAddTIL;
    private Button buttonCalculate;

    private List<String> listTaxSystem, listGroup, listTax;
    private int selectedtaxSystem, selectedGroup, selectedTax;

    public static CalcMoneyFragment getInstance() {

        Bundle args = new Bundle();
        CalcMoneyFragment fragment = new CalcMoneyFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        listTaxSystem = MockData.getListTaxSystem(getActivity());
        listGroup = MockData.getListGroup(getActivity());
        listTax = MockData.getListTax(getActivity());

        taxSystem = (EditText) view.findViewById(R.id.tax_system);
        groupTIL = (TextInputLayout) view.findViewById(R.id.group_til);
        group = (EditText) view.findViewById(R.id.group);
        taxTIL = (TextInputLayout) view.findViewById(R.id.tax_til);
        tax = (EditText) view.findViewById(R.id.tax);
        incomeAllTIL = (TextInputLayout) view.findViewById(R.id.income_all_til);
        incomeAll = (EditText) view.findViewById(R.id.income_all);
        initMoneyTextWather(incomeAll);


        costsAddPercentTIL = (TextInputLayout) view.findViewById(R.id.costs_add_percent_til);
        costsAddPercent = (EditText) view.findViewById(R.id.costs_add_percent);
        initPercentTextWather(costsAddPercent);
        costsAddTIL = (TextInputLayout) view.findViewById(R.id.costs_add_til);
        costsAdd = (EditText) view.findViewById(R.id.costs_add);
        initMoneyTextWather(costsAdd);
        final Switch usingAddCosts = (Switch) view.findViewById(R.id.using_add_costs);
        usingAddCosts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                costsAddPercentTIL.setVisibility(usingAddCosts.isChecked() ? View.VISIBLE : View.GONE);
                costsAddTIL.setVisibility(usingAddCosts.isChecked() ? View.VISIBLE : View.GONE);
            }
        });
        initMoneyTextWather(costsAdd);

        taxSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.showSelectionList(getActivity(),
                        getString(R.string.tax_system), listTaxSystem,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {
                                setSelectionTaxSystem(item);
                            }
                        });
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.showSelectionList(getActivity(),
                        getString(R.string.group), listGroup,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {
                                setSelectionGroup(item);
                            }
                        });
            }
        });


        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityUtils.showSelectionList(getActivity(),
                        getString(R.string.tax), listTax,
                        new ActivityUtils.ListItemClick() {
                            @Override
                            public void onItemClik(int item, String text) {
                                setSelectionTax(item);
                            }
                        });
            }
        });

        buttonCalculate = (Button) view.findViewById(R.id.button_calculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcResult();
            }
        });


        groupTIL.setVisibility(View.GONE);
        taxTIL.setVisibility(View.GONE);
        costsAddPercentTIL.setVisibility(View.GONE);
        costsAddTIL.setVisibility(View.GONE);

        setSelectionTaxSystem(0);


        return view;
    }

    private void initMoneyTextWather(final EditText view) {
        view.addTextChangedListener(new LocaleTextWatcher(view, "%.2f ₴"));
    }

    private void initPercentTextWather(final EditText view) {
        view.addTextChangedListener(new LocaleTextWatcher(view, "%.2f %%"));
    }

    private void setSelectionTaxSystem(int item){
        selectedtaxSystem = item;
        taxSystem.setText(listTaxSystem.get(item));
        incomeAll.setText("0,00");
        costsAddPercent.setText("0,00");
        costsAdd.setText("0,00");
        groupTIL.setVisibility(View.GONE);
        taxTIL.setVisibility(View.GONE);
        switch (item) {
            case 0:
                groupTIL.setVisibility(View.VISIBLE);
                setSelectionGroup(0);
                return;
            case 1:
                taxTIL.setVisibility(View.VISIBLE);
                setSelectionTax(0);
                return;
            case 2:
                return;
            default:
                return;
        }
    }

    private void setSelectionGroup(int item){
        selectedGroup= item;
        group.setText(listGroup.get(item));
        taxTIL.setVisibility(View.GONE);
        switch (item) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                taxTIL.setVisibility(View.VISIBLE);
                setSelectionTax(0);
                return;
            default:
                return;
        }
    }

    private void setSelectionTax(int item){
        selectedTax = item;
        tax.setText(listTax.get(item));
    }

    private void calcResult(){

        incomeAll.setError(null);
        boolean cancel = false;
        View focusView = null;

        if (getDoubleFromString(incomeAll.getText())==0.0) {
            incomeAll.setError(getString(R.string.error_field_required));
            focusView = incomeAll;
            cancel = true;
        }


        if (cancel) {

            focusView.requestFocus();

        } else {

            Double incomeBaseAll = getDoubleFromString(incomeAll.getText());

            Double costsAddResult = getDoubleFromString(costsAdd.getText());
            Double costsAddPercentResult = getDoubleFromString(costsAddPercent.getText());

            costsAddResult  = costsAddResult + ((incomeBaseAll*costsAddPercentResult)/100);

            Double incomeBaseAllResult  = incomeBaseAll + costsAddResult;

            Double minSalary = SharedStorage.getDouble(getActivity(), PAR_SALARY_MIN, 0.0);
            Double taxSocPercent = SharedStorage.getDouble(getActivity(), PAR_SSC_PERCENT, 0.0);
            Double taxIncomePercent = SharedStorage.getDouble(getActivity(), PAR_INCOME_TAX, 0.0);
            Double taxMilitaryPercent = SharedStorage.getDouble(getActivity(), PAR_MILITARY_PERCENT, 0.0);

            Double taxSocResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxSocResult = incomeBaseAllResult * (taxSocPercent / 100);
                    if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                        taxSocResult = minSalary * (taxSocPercent / 100);
                    }
                } else {
                    taxSocResult = incomeBaseAllResult * (taxSocPercent / 100);
                    if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                        taxSocResult = minSalary * (taxSocPercent / 100);
                    }
                }
            } else if (selectedtaxSystem == 2) {
                taxSocResult = incomeBaseAllResult * (taxSocPercent / 100);
            } else {
                taxSocResult = minSalary * (taxSocPercent / 100);
            }
            if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                taxSocResult = minSalary * (taxSocPercent / 100);
            }

            Double taxIncomeResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxIncomeResult = incomeBaseAllResult * (taxIncomePercent / 100);
                } else {
                    taxIncomeResult = incomeBaseAllResult * (taxIncomePercent / 100);
                }
            } else if (selectedtaxSystem == 2) {
                taxIncomeResult = incomeBaseAllResult * (taxIncomePercent / 100);
            }
            if (taxIncomeResult < 0.0) {
                taxIncomeResult = 0.0;
            }

            Double taxMilitaryResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxMilitaryResult = incomeBaseAllResult * (taxMilitaryPercent / 100);
                } else {
                    taxMilitaryResult = incomeBaseAllResult * (taxMilitaryPercent / 100);
                }
            } else if (selectedtaxSystem == 2) {
                taxMilitaryResult = incomeBaseAllResult * (taxMilitaryPercent / 100);
            }
            if (taxMilitaryResult < 0.0) {
                taxMilitaryResult = 0.0;
            }

            Double taxTaxResult = 0.0;
            if (selectedtaxSystem == 0) {
                if (selectedGroup == 2) {
                    if (selectedTax == 0) {
                        taxTaxResult = (incomeBaseAllResult / 1.2) * 0.2;
                    }
                }
            } else if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxTaxResult = (incomeBaseAllResult / 1.2) * 0.2;
                }
            }
            if (taxTaxResult < 0.0) {
                taxTaxResult = 0.0;
            }

            Double taxSingleResult = 0.0;
            if (selectedtaxSystem == 0) {
                if (selectedGroup == 0) {
                    taxSingleResult = SharedStorage.getDouble(getActivity(), PAR_LIVING_MIN, 0.0)
                            * (SharedStorage.getDouble(getActivity(), PAR_FIRST_GR_PERC, 0.0) / 100);
                } else if (selectedGroup == 1) {
                    taxSingleResult = SharedStorage.getDouble(getActivity(), PAR_SALARY_MIN, 0.0)
                            * (SharedStorage.getDouble(getActivity(), PAR_SECOND_GR_PERC, 0.0) / 100);
                } else if (selectedGroup == 2) {
                    if (selectedTax == 0) {
                        taxSingleResult = ((incomeBaseAllResult + taxSocResult)
                                /(1 - (SharedStorage.getDouble(getActivity(), PAR_THRID_GR_PERC_TAX, 0.0) / 100)))
                                - (incomeBaseAllResult + taxSocResult);
                    } else {
                        taxSingleResult = ((incomeBaseAllResult + taxSocResult)
                                /(1 - (SharedStorage.getDouble(getActivity(), PAR_THRID_GR_PERC, 0.0) / 100)))
                                - (incomeBaseAllResult + taxSocResult);
                    }
                }
            }
            if (taxSingleResult < 0.0) {
                taxSingleResult = 0.0;
            }

            Double taxAllResult = taxSocResult + taxIncomeResult + taxMilitaryResult + taxTaxResult + taxSingleResult;
            Double incomeAllResult = incomeBaseAllResult + taxAllResult;
            Double taxAllPercentResult = incomeAllResult == 0 ? 0.0 : (taxAllResult * 100) / incomeAllResult;

            Intent intent = new Intent(getActivity(), ResultActivity.class);
            intent.putExtra(TAX_SYSTEM_RESULT, taxSystem.isShown() ? listTaxSystem.get(selectedtaxSystem) : null);
            intent.putExtra(GROUP_RESULT, group.isShown() ? listGroup.get(selectedGroup) : null);
            intent.putExtra(TAX_RESULT, tax.isShown() ? listTax.get(selectedTax) : null);
            intent.putExtra(INCOME_BASE_ALL_RESULT, incomeBaseAll);
            intent.putExtra(INCOME_ALL_RESULT, incomeAllResult);
            intent.putExtra(TAX_ALL_RESULT, taxAllResult);
            intent.putExtra(TAX_ALL_PERCENT_RESULT, taxAllPercentResult);
            intent.putExtra(TAX_SOC_RESULT, taxSocResult);
            intent.putExtra(TAX_INCOME_RESULT, taxIncomeResult);
            intent.putExtra(TAX_MILITARY_RESULT, taxMilitaryResult);
            intent.putExtra(TAX_TAX_RESULT, taxTaxResult);
            intent.putExtra(TAX_SINGLE_RESULT, taxSingleResult);
            intent.putExtra(COSTS_ADD_RESULT, costsAddResult);
            intent.putExtra(CALC_MONEY, true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        getActivity(),
                        android.util.Pair.create((View) buttonCalculate, "bg"));
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }

        }

    }

    private Double getDoubleFromString(Editable s){
        String text = s.toString();
        String cleanString = text.replaceAll("[ руб$₴.%]", "");
        String doubleString = cleanString.replaceAll("[,]", ".");
        return Double.valueOf(String.valueOf(doubleString==null || doubleString.isEmpty() ? "0" : doubleString));
    }

}
