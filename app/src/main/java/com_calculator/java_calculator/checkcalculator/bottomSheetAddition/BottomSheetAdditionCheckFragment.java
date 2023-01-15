package com_calculator.java_calculator.checkcalculator.bottomSheetAddition;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com_calculator.java_calculator.checkcalculator.R;


public class BottomSheetAdditionCheckFragment extends BottomSheetDialogFragment {

    private static final String TAG = "CheckAdditionFragment";
    public BottomSheetAdditionCheckFragment.OnInputSelected mOnInputSelected;
    //widgets
    private TextInputEditText restaurant;
    private ExtendedFloatingActionButton cancel, addCheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_check, container, false);
        init(view);
        addCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restaurant_holder = restaurant.getText().toString().trim();
                if(!restaurant_holder.equals(null)){
                    mOnInputSelected.sendInput(restaurant_holder);
                    getDialog().dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void init(View view){
        restaurant = view.findViewById(R.id.text_input_edit_text_restaurant);
        cancel = view.findViewById(R.id.button_cancel);
        addCheck = view.findViewById(R.id.button_add_check);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (BottomSheetAdditionCheckFragment.OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendInput(String check);
    }
}