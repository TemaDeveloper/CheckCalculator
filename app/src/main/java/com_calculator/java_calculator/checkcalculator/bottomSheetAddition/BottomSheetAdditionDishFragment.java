package com_calculator.java_calculator.checkcalculator.bottomSheetAddition;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com_calculator.java_calculator.checkcalculator.R;

public class BottomSheetAdditionDishFragment extends BottomSheetDialogFragment {

    private static final String TAG = "DishAdditionFragment";
    public BottomSheetAdditionDishFragment.OnInputSelected mOnInputSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_addition_dish, container, false);

        return view;
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
        void sendInput(String dish);
    }

}