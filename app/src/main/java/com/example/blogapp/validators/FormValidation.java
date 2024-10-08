package com.example.blogapp.validators;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FormValidation {
    public static void setTextWatcher(TextInputEditText textInputLayout, String errorMessage){
        textInputLayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence.toString().trim())){
                    textInputLayout.setError(errorMessage);
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public  static boolean  isInputEmpty(TextInputEditText textInputLayout, String errorMessage){
        String inputText = textInputLayout.getText().toString().trim();
        if(TextUtils.isEmpty(inputText)){
            textInputLayout.setError(errorMessage);
            return  true;
        } else {
            textInputLayout.setError(null);
            return false;
        }

    }
}
