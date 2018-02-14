package com.gmail.vanyadubik.capcalculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.vanyadubik.capcalculator.R;

public class CalcTaxFragment extends Fragment{
    private static  final int LAYOUT = R.layout.fragment_tax_paiment;

    private View view;

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

        return view;
    }

}
