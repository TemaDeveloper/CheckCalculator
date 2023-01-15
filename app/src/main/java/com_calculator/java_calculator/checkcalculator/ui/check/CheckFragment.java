package com_calculator.java_calculator.checkcalculator.ui.check;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import com_calculator.java_calculator.checkcalculator.adapters.AdapterCheck;
import com_calculator.java_calculator.checkcalculator.adapters.AdapterRestaurant;
import com_calculator.java_calculator.checkcalculator.models.Check;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.models.Restaurant;
import com_calculator.java_calculator.checkcalculator.adapters.RecyclerViewBuilder;
import com_calculator.java_calculator.checkcalculator.bottomSheetAddition.BottomSheetAdditionCheckFragment;
import com_calculator.java_calculator.checkcalculator.databinding.FragmentCheckBinding;


public class CheckFragment extends Fragment implements BottomSheetAdditionCheckFragment.OnInputSelected, RecyclerViewBuilder {

    //binding
    private FragmentCheckBinding binding;
    //widgets
    private ViewPager2 recyclerViewChecks;
    private ExtendedFloatingActionButton fabAddCheck;
    private TabLayout tabIndicator;
    //list
    private ArrayList<Check> checks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);
        setUpTabsWithViewPager();
        transformViewPager();
        buildRecyclerView();
        fabAddCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetAdditionCheckFragment bottomSheetDialogAdditionCheckFragment = new BottomSheetAdditionCheckFragment();
                bottomSheetDialogAdditionCheckFragment.setTargetFragment(CheckFragment.this, 1);
                bottomSheetDialogAdditionCheckFragment.show(getFragmentManager(), "CheckAdditionFragment");
            }
        });

        return root;
    }

    private void transformViewPager(){
        recyclerViewChecks.setClipToPadding(false);
        recyclerViewChecks.setClipChildren(false);
        recyclerViewChecks.setOffscreenPageLimit(3);
        recyclerViewChecks.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(16));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        recyclerViewChecks.setPageTransformer(compositePageTransformer);
    }

    private void setUpTabsWithViewPager(){
        for(int i = 0; i < checks.size(); i++){
            tabIndicator.addTab(tabIndicator.newTab().setText(""));
        }

        recyclerViewChecks.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabIndicator.selectTab(tabIndicator.getTabAt(position));
            }
        });
    }

    private void init(View root){
        //widgets
        recyclerViewChecks = root.findViewById(R.id.recycler_view_checks);
        fabAddCheck = root.findViewById(R.id.fab_add_check);
        tabIndicator =  root.findViewById(R.id.tab_indicator);
        //arrayList
        checks = new ArrayList<>();
        checks.add(new Check("Parmezan", "22.10.21", false));
        checks.add(new Check("Cafe coffee only here", "12.09.21", false));
        checks.add(new Check("Shaurma Bistro", "11.08.21", false));
        checks.add(new Check("NicePriceCafe", "06.01.21", false));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void sendInput(String check) {
        checks.add(new Check(check, "Today", true));
        tabIndicator.addTab(tabIndicator.newTab().setText(""));
        Collections.rotate(checks, 1);
        buildRecyclerView();
    }

    @Override
    public void buildRecyclerView() {
        recyclerViewChecks.setAdapter(new AdapterCheck(getContext(), checks));
    }
}