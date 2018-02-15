package com.gmail.vanyadubik.capcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.vanyadubik.capcalculator.R;
import com.gmail.vanyadubik.capcalculator.model.MockData;
import com.gmail.vanyadubik.capcalculator.utils.ActivityUtils;

import java.util.List;

public class CalcTaxFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_tax_paiment;

    private View view;
    private EditText taxSystem, group, tax, incomeAll, incomeWithoutTax,
            costsAll, costsWithoutTax;
    private TextInputLayout groupTIL, taxTIL, incomeWithoutTaxTIL, incomeAllTIL,
            costsAllTIL, costsWithoutTaxTIL;
    private CardView costsCard;

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
        incomeWithoutTaxTIL = (TextInputLayout) view.findViewById(R.id.income_without_tax_til);
        incomeWithoutTax = (EditText) view.findViewById(R.id.income_without_tax);
        costsCard = (CardView) view.findViewById(R.id.costs_card);
        costsAllTIL = (TextInputLayout) view.findViewById(R.id.costs_til);
        costsAll = (EditText) view.findViewById(R.id.costs);
        costsWithoutTaxTIL = (TextInputLayout) view.findViewById(R.id.costs_without_tax_til);
        costsWithoutTax = (EditText) view.findViewById(R.id.costs_without_tax);

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
        costsCard.setVisibility(View.GONE);
        costsWithoutTaxTIL.setVisibility(View.GONE);

        setSelectionTaxSystem(0);


        return view;
    }


    private void setSelectionTaxSystem(int item){
        selectedtaxSystem = item;
        taxSystem.setText(listTaxSystem.get(item));
        incomeAll.setText("0.0");
        costsAll.setText("0.0");
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
        incomeWithoutTax.setText("0.0");
        costsWithoutTax.setText("0.0");
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

}
