package com_calculator.java_calculator.checkcalculator.api;

import android.app.Activity;
import android.widget.AdapterView;

public interface Language {
    void setLanguage();
    void setLanguage(AdapterView<?> parent, int position);
    void setLocal(Activity activity, String langCode);
}
