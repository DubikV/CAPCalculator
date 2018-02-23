package com.gmail.vanyadubik.capcalculator.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.activity.ResultActivity;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;
import com.gmail.vanyadubik.capcalculator.utils.LocaleTextWatcher;
import com.gmail.vanyadubik.capcalculator.utils.SharedStorage;

import java.util.List;
import java.util.Locale;

import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.GROUP_RESULT;
import static com.gmail.vanyadubik.capcalculator.activity.ResultActivity.INCOME_ALL_RESULT;
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

public class CalcTaxFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_tax_paiment;

    private View view;
    private EditText taxSystem, group, tax, incomeAll, incomeWithoutTax,
            costsAll, costsWithoutTax;
    private TextInputLayout groupTIL, taxTIL, incomeWithoutTaxTIL, incomeAllTIL,
            costsAllTIL, costsWithoutTaxTIL;
    private CardView costsCard;
    private Button buttonCalculate;

    private List<String> listTaxSystem, listGroup, listTax;
    private int selectedtaxSystem, selectedGroup, selectedTax;

    public static CalcTaxFragment getInstance() {

        Bundle args = new Bundle();
        CalcTaxFragment fragment = new CalcTaxFragment();
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
        incomeAll.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private boolean isDeleting;
            protected int max_length = Integer.MAX_VALUE;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (after <= 0 && count > 0) {
                    isDeleting = true;
                } else {
                    isDeleting = false;
                }
                if (!s.toString().equals(current)) {
                    incomeAll.removeTextChangedListener(this);
                    String clean_text = s.toString().replaceAll("[^\\d]", "");
                    incomeAll.setText(clean_text);
                    incomeAll.addTextChangedListener(this);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    incomeAll.removeTextChangedListener(this);
                    String clean_text = s.toString().replaceAll("[^\\d]", "");
                    if (isDeleting && s.length() > 0 && !Character.isDigit(s.charAt(s.length() - 1))) {
                        clean_text = LocaleTextWatcher.deleteLastChar(clean_text);
                    }

                    double v_value = 0;
                    if (clean_text != null && clean_text.length() > 0) {
                        v_value = Double.parseDouble(clean_text);

                        String s_value = Double.toString(Math.abs(v_value / 100));
                        int integerPlaces = s_value.indexOf('.');
                        if (integerPlaces > max_length) {
                            v_value = Double.parseDouble(LocaleTextWatcher.deleteLastChar(clean_text));
                        }
                    }

                    String formatted_text = String.format(new Locale("ua", "UA"), "%.2f ₴", v_value / 100);

                    current = formatted_text;
                    incomeAll.setText(formatted_text);
                    incomeAll.setSelection(formatted_text.length());
                    incomeAll.addTextChangedListener(this);
                }

                incomeWithoutTax.setText(String.format("%.2f", getDoubleFromString(incomeAll.getText()) / 1.2));
            }
        });
        incomeWithoutTaxTIL = (TextInputLayout) view.findViewById(R.id.income_without_tax_til);
        incomeWithoutTax = (EditText) view.findViewById(R.id.income_without_tax);
        initMoneyTextWather(incomeWithoutTax);
        costsCard = (CardView) view.findViewById(R.id.costs_card);
        costsAllTIL = (TextInputLayout) view.findViewById(R.id.costs_til);
        costsAll = (EditText) view.findViewById(R.id.costs);
        initMoneyTextWather(costsAll);
        costsWithoutTaxTIL = (TextInputLayout) view.findViewById(R.id.costs_without_tax_til);
        costsWithoutTax = (EditText) view.findViewById(R.id.costs_without_tax);
        initMoneyTextWather(costsWithoutTax);

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
        incomeWithoutTaxTIL.setVisibility(View.GONE);
        costsCard.setVisibility(View.GONE);
        costsWithoutTaxTIL.setVisibility(View.GONE);

        setSelectionTaxSystem(0);


        return view;
    }

    private void initMoneyTextWather(final EditText view) {
        view.addTextChangedListener(new LocaleTextWatcher(view, "%.2f ₴"));
    }

    private void setSelectionTaxSystem(int item){
        selectedtaxSystem = item;
        taxSystem.setText(listTaxSystem.get(item));
        incomeAll.setText("0,00");
        costsAll.setText("0,00");
        groupTIL.setVisibility(View.GONE);
        taxTIL.setVisibility(View.GONE);
        costsCard.setVisibility(View.GONE);
        incomeWithoutTaxTIL.setVisibility(View.GONE);
        costsWithoutTaxTIL.setVisibility(View.GONE);
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
                costsCard.setVisibility(View.VISIBLE);
                return;
            default:
                return;
        }
    }

    private void setSelectionGroup(int item){
        selectedGroup= item;
        group.setText(listGroup.get(item));
        incomeWithoutTaxTIL.setVisibility(View.GONE);
        costsWithoutTaxTIL.setVisibility(View.GONE);
        taxTIL.setVisibility(View.GONE);
        costsCard.setVisibility(View.GONE);
        switch (item) {
            case 0:
                return;
            case 1:
                return;
            case 2:
                taxTIL.setVisibility(View.VISIBLE);
                costsCard.setVisibility(View.VISIBLE);
                setSelectionTax(0);
                return;
            default:
                return;
        }
    }

    private void setSelectionTax(int item){
        selectedTax = item;
        tax.setText(listTax.get(item));
        incomeWithoutTax.setText("0,00");
        costsWithoutTax.setText("0,00");
        incomeWithoutTaxTIL.setVisibility(View.GONE);
        switch (item) {
            case 0:
                incomeAllTIL.setHint(getActivity().getResources().getString(R.string.income_with_tax));
                costsAllTIL.setHint(getActivity().getResources().getString(R.string.costs_with_tax));
                incomeWithoutTaxTIL.setVisibility(View.VISIBLE);
                if(selectedtaxSystem!=0) {
                    costsWithoutTaxTIL.setVisibility(View.VISIBLE);
                }
                costsCard.setVisibility(View.VISIBLE);
                if(selectedtaxSystem!=0) {
                    costsAllTIL.setHint(getActivity().getResources().getString(R.string.costs_amount));
                }
                incomeWithoutTax.setText(String.format("%.2f", getDoubleFromString(incomeAll.getText()) / 1.2));
                return;
            case 1:
                incomeAllTIL.setHint(getActivity().getResources().getString(R.string.income_amount));
                costsAllTIL.setHint(getActivity().getResources().getString(R.string.costs_amount));
                if(selectedtaxSystem!=0) {
                    costsWithoutTaxTIL.setVisibility(View.GONE);
                }else{
                    costsCard.setVisibility(View.GONE);
                }
                return;
            default:
                return;
        }
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


            Double incomeAllResult = getDoubleFromString(incomeAll.getText());

            Double costsAllResult = getDoubleFromString(costsAll.getText());
            Double costsWithoutTaxResult = getDoubleFromString(costsWithoutTax.getText());

            Double livinMin = SharedStorage.getDouble(getActivity(), PAR_LIVING_MIN, 0.0);
            Double minSalary = SharedStorage.getDouble(getActivity(), PAR_SALARY_MIN, 0.0);
            Double taxSocPercent = SharedStorage.getDouble(getActivity(), PAR_SSC_PERCENT, 0.0);
            Double taxIncomePercent = SharedStorage.getDouble(getActivity(), PAR_INCOME_TAX, 0.0);
            Double taxMilitaryPercent = SharedStorage.getDouble(getActivity(), PAR_MILITARY_PERCENT, 0.0);
            Double firstGrPerc = (SharedStorage.getDouble(getActivity(), PAR_FIRST_GR_PERC, 0.0) / 100);
            Double secondGrPerc = (SharedStorage.getDouble(getActivity(), PAR_SECOND_GR_PERC, 0.0) / 100);
            Double thirdGrTaxPerc = SharedStorage.getDouble(getActivity(), PAR_THRID_GR_PERC_TAX, 0.0);
            Double thirdGrPerc = SharedStorage.getDouble(getActivity(), PAR_THRID_GR_PERC, 0.0);

            Double taxSocResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxSocResult = (((incomeAllResult - costsAllResult) / 1.2) - costsWithoutTaxResult) * (taxSocPercent / 100);
                    if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                        taxSocResult = minSalary * (taxSocPercent / 100);
                    }
                } else {
                    taxSocResult = (incomeAllResult - costsAllResult) * (taxSocPercent / 100);
                    if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                        taxSocResult = minSalary * (taxSocPercent / 100);
                    }
                }
            } else if (selectedtaxSystem == 2) {
                taxSocResult = (incomeAllResult - costsAllResult) * (taxSocPercent / 100);
                if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                    taxSocResult = minSalary * (taxSocPercent / 100);
                }
            } else {
                taxSocResult = minSalary * (taxSocPercent / 100);
            }
            if (taxSocResult < (minSalary * (taxSocPercent / 100))) {
                taxSocResult = minSalary * (taxSocPercent / 100);
            }

            Double taxIncomeResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxIncomeResult = (((incomeAllResult - costsAllResult) / 1.2) - costsWithoutTaxResult - taxSocResult) * (taxIncomePercent / 100);
                } else {
                    taxIncomeResult = ((incomeAllResult - costsAllResult) - taxSocResult) * (taxIncomePercent / 100);
                }
            } else if (selectedtaxSystem == 2) {
                taxIncomeResult = ((incomeAllResult - costsAllResult) - taxSocResult) * (taxIncomePercent / 100);
            }
            if (taxIncomeResult < 0.0) {
                taxIncomeResult = 0.0;
            }

            Double taxMilitaryResult = 0.0;
            if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxMilitaryResult = (((incomeAllResult - costsAllResult) / 1.2) - costsWithoutTaxResult - taxSocResult) * (taxMilitaryPercent / 100);
                } else {
                    taxMilitaryResult = ((incomeAllResult - costsAllResult) - taxSocResult) * (taxMilitaryPercent / 100);
                }
            } else if (selectedtaxSystem == 2) {
                taxMilitaryResult = ((incomeAllResult - costsAllResult) - taxSocResult) * (taxMilitaryPercent / 100);
            }
            if (taxMilitaryResult < 0.0) {
                taxMilitaryResult = 0.0;
            }

            Double taxTaxResult = 0.0;
            if (selectedtaxSystem == 0) {
                if (selectedGroup == 2) {
                    if (selectedTax == 0) {
                        taxTaxResult = ((incomeAllResult - costsAllResult) / 1.2) * 0.2;
                    }
                }
            } else if (selectedtaxSystem == 1) {
                if (selectedTax == 0) {
                    taxTaxResult = ((incomeAllResult - costsAllResult) / 1.2) * 0.2;
                }
            }
            if (taxTaxResult < 0.0) {
                taxTaxResult = 0.0;
            }

            Double taxSingleResult = 0.0;
            if (selectedtaxSystem == 0) {
                if (selectedGroup == 0) {
                    taxSingleResult = livinMin * (firstGrPerc / 100);
                } else if (selectedGroup == 1) {
                    taxSingleResult = minSalary * (secondGrPerc / 100);
                } else if (selectedGroup == 2) {
                    if (selectedTax == 0) {
                        taxSingleResult = incomeAllResult * (thirdGrTaxPerc / 100);
                    } else {
                        taxSingleResult = incomeAllResult * (thirdGrPerc / 100);
                    }
                }
            }
            if (taxSingleResult < 0.0) {
                taxSingleResult = 0.0;
            }

            Double taxAllResult = taxSocResult + taxIncomeResult + taxMilitaryResult + taxTaxResult + taxSingleResult;
            Double taxAllPercentResult = incomeAllResult == 0 ? 0.0 : (taxAllResult * 100) / incomeAllResult;

            Intent intent = new Intent(getActivity(), ResultActivity.class);
            intent.putExtra(TAX_SYSTEM_RESULT, taxSystem.isShown() ? listTaxSystem.get(selectedtaxSystem) : null);
            intent.putExtra(GROUP_RESULT, group.isShown() ? listGroup.get(selectedGroup) : null);
            intent.putExtra(TAX_RESULT, tax.isShown() ? listTax.get(selectedTax) : null);
            intent.putExtra(INCOME_ALL_RESULT, incomeAllResult);
            intent.putExtra(TAX_ALL_RESULT, taxAllResult);
            intent.putExtra(TAX_ALL_PERCENT_RESULT, taxAllPercentResult);
            intent.putExtra(TAX_SOC_RESULT, taxSocResult);
            intent.putExtra(TAX_INCOME_RESULT, taxIncomeResult);
            intent.putExtra(TAX_MILITARY_RESULT, taxMilitaryResult);
            intent.putExtra(TAX_TAX_RESULT, taxTaxResult);
            intent.putExtra(TAX_SINGLE_RESULT, taxSingleResult);

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
