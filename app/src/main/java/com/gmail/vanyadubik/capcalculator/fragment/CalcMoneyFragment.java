package com.gmail.vanyadubik.capcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;

import java.util.List;

public class CalcMoneyFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_amoynt_paiment;

    private View view;
    private EditText taxSystem, group, tax, incomeAll, incomeWithoutTax,
            thirdGrPerc, sSocContr, militaryPercent, incomeTax;
    private TextInputLayout groupTIL, taxTIL, incomeWithoutTaxTIL, incomeAllTIL;

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
        incomeWithoutTaxTIL = (TextInputLayout) view.findViewById(R.id.income_without_tax_til);
        incomeWithoutTax = (EditText) view.findViewById(R.id.income_without_tax);

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

        groupTIL.setVisibility(View.GONE);
        taxTIL.setVisibility(View.GONE);
        incomeWithoutTaxTIL.setVisibility(View.GONE);


//        secondGrPerc = (EditText) view.findViewById(R.id.second_group_percent);
//        thirdGrPercTax = (EditText) view.findViewById(R.id.third_group_percent_tax);
//        thirdGrPerc = (EditText) view.findViewById(R.id.third_group_percent);
//        sSocContr = (EditText) view.findViewById(R.id.single_soc_contr);
//        militaryPercent = (EditText) view.findViewById(R.id.military_percent);
//        incomeTax = (EditText) view.findViewById(R.id.income_tax);
//        Button setDefault = (Button) view.findViewById(R.id.button_default);
//        setDefault.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        setSelectionTaxSystem(0);


        return view;
    }


    private void setSelectionTaxSystem(int item){
        selectedtaxSystem = item;
        taxSystem.setText(listTaxSystem.get(item));
        incomeAll.setText("0.0");
        switch (item) {
            case 0:
                groupTIL.setVisibility(View.VISIBLE);
                taxTIL.setVisibility(View.GONE);
                setSelectionGroup(0);
                return;
            case 1:
                groupTIL.setVisibility(View.GONE);
                taxTIL.setVisibility(View.VISIBLE);
                setSelectionTax(0);
                return;
            case 2:
                groupTIL.setVisibility(View.GONE);
                taxTIL.setVisibility(View.GONE);
                return;
            default:
                groupTIL.setVisibility(View.GONE);
                taxTIL.setVisibility(View.GONE);
                return;
        }
    }

    private void setSelectionGroup(int item){
        selectedGroup= item;
        group.setText(listGroup.get(item));
        switch (item) {
            case 0:
                taxTIL.setVisibility(View.GONE);
                return;
            case 1:
                taxTIL.setVisibility(View.GONE);
                return;
            case 2:
                taxTIL.setVisibility(View.VISIBLE);
                setSelectionTax(0);
                return;
            default:
                taxTIL.setVisibility(View.GONE);
                return;
        }
    }

    private void setSelectionTax(int item){
        selectedTax = item;
        tax.setText(listTax.get(item));
        incomeWithoutTax.setText("0.0");
        switch (item) {
            case 0:
                incomeAllTIL.setHint(getActivity().getResources().getString(R.string.income_with_tax));
                incomeWithoutTaxTIL.setVisibility(View.VISIBLE);
                return;
            case 1:
                incomeAllTIL.setHint(getActivity().getResources().getString(R.string.income_amount));
                incomeWithoutTaxTIL.setVisibility(View.GONE);
                return;
            default:
                incomeWithoutTaxTIL.setVisibility(View.GONE);
                return;
        }


    }

}
