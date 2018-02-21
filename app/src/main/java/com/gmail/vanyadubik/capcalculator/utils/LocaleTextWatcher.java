package com.gmail.vanyadubik.capcalculator.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Locale;

public class LocaleTextWatcher implements TextWatcher {

    private final EditText editText;
    private String formatType;
    private String current = "";
    private boolean isDeleting;

    protected int max_length = Integer.MAX_VALUE;
    /**
     * @param editText
     * @param formatType String formatting style like "%.2f $"
     */
    public LocaleTextWatcher(EditText editText, String formatType) {
        this.editText = editText;
        this.formatType = formatType;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (after <= 0 && count > 0) {
            isDeleting = true;
        } else {
            isDeleting = false;
        }
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);
            String clean_text = s.toString().replaceAll("[^\\d]", "");
            editText.setText(clean_text);
            editText.addTextChangedListener(this);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}


    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);
            String clean_text = s.toString().replaceAll("[^\\d]", "");
            if (isDeleting && s.length() > 0 && !Character.isDigit(s.charAt(s.length() - 1))) {
                clean_text = deleteLastChar(clean_text);
            }

            double v_value = 0;
            if (clean_text != null && clean_text.length() > 0) {
                v_value = Double.parseDouble(clean_text);

                String s_value = Double.toString(Math.abs(v_value / 100));
                int integerPlaces = s_value.indexOf('.');
                if (integerPlaces > max_length) {
                    v_value = Double.parseDouble(deleteLastChar(clean_text));
                }
            }

            String formatted_text = String.format(new Locale("ua", "UA"), formatType, v_value / 100);

            current = formatted_text;
            editText.setText(formatted_text);
            editText.setSelection(formatted_text.length());
            editText.addTextChangedListener(this);
        }

    }


    public static String deleteLastChar(String clean_text) {
        if (clean_text.length() > 0) {
            clean_text = clean_text.substring(0, clean_text.length()-1);
        } else {
            clean_text = "0";
        }
        return clean_text;
    }


    public int getMax_length() {
        return max_length;
    }


    public void setMax_length(int max_length) {
        this.max_length = max_length;
    }

}