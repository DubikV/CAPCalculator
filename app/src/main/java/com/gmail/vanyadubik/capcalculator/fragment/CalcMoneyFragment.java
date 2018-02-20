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

import java.util.List;

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

        Intent intent = new Intent(getActivity(), ResultActivity.class);
        intent.putExtra(TAX_SYSTEM_RESULT, taxSystem.isShown() ? listTaxSystem.get(selectedtaxSystem) : null);
        intent.putExtra(GROUP_RESULT, group.isShown() ? listGroup.get(selectedGroup) : null);
        intent.putExtra(TAX_RESULT, tax.isShown() ? listTax.get(selectedTax) : null);
        intent.putExtra(INCOME_ALL_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_ALL_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_ALL_PERCENT_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_SOC_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_INCOME_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_MILITARY_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_TAX_RESULT, getDoubleFromString(incomeAll.getText()));
        intent.putExtra(TAX_SINGLE_RESULT, getDoubleFromString(incomeAll.getText()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    getActivity(),
                    android.util.Pair.create((View) buttonCalculate, "bg"));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    private Double getDoubleFromString(Editable s){
        String text = s.toString();
        String cleanString = text.replaceAll("[ руб$₴.]", "");
        String doubleString = cleanString.replaceAll("[,]", ".");
        return Double.valueOf(String.valueOf(doubleString==null || doubleString.isEmpty() ? "0" : doubleString));
    }

}
