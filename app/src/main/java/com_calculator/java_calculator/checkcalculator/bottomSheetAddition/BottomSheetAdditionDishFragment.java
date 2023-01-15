package com_calculator.java_calculator.checkcalculator.bottomSheetAddition;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com_calculator.java_calculator.checkcalculator.R;

public class BottomSheetAdditionDishFragment extends BottomSheetDialogFragment {

    private static final String TAG = "DishAdditionFragment";
    public BottomSheetAdditionDishFragment.OnInputSelected mOnInputSelected;
    private ExtendedFloatingActionButton cancel, addDish;
    private TextInputEditText dishTitle, price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_addition_dish, container, false);
        init(view);

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dish_holder = dishTitle.getText().toString().trim();
                int price_holder = Integer.parseInt(price.getText().toString().trim());
                if(!dish_holder.equals(null) && !price.getText().toString().trim().equals(null)){
                    mOnInputSelected.sendInput(dish_holder, price_holder);
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
        cancel = view.findViewById(R.id.button_cancel);
        addDish = view.findViewById(R.id.button_add_dish);
        dishTitle = view.findViewById(R.id.text_input_edit_text_dish);
        price = view.findViewById(R.id.text_input_edit_text_price);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (BottomSheetAdditionDishFragment.OnInputSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendInput(String dish, int price);
    }

}