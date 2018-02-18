package com.gmail.vanyadubik.capcalculator.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextWatcher
        implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;
    int length_before = 0;

    public MoneyTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        length_before = s.length();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        String s = editable.toString();
        if (s.isEmpty()) return;
        editText.removeTextChangedListener(this);
        String cleanString = s.replaceAll("[ руб$₴,.]", "");
        if (length_before > s.length()) {
            cleanString = cleanString.substring(0, cleanString.length()-1);
        }
        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        String formatted = NumberFormat.getCurrencyInstance(new Locale("ua", "UA")).format(parsed);
        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}