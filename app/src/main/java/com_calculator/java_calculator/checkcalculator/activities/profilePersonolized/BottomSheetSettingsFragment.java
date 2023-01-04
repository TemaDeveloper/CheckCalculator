package com_calculator.java_calculator.checkcalculator.activities.profilePersonolized;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import com_calculator.java_calculator.checkcalculator.BuildConfig;
import com_calculator.java_calculator.checkcalculator.R;
import com_calculator.java_calculator.checkcalculator.api.Language;
import com_calculator.java_calculator.checkcalculator.api.SharedPrefManager;


public class BottomSheetSettingsFragment extends BottomSheetDialogFragment implements Language {

    //languages codes
    private String changeLanguage;
    private String english;
    private String ukranian;
    private String french;
    private String russian;
    //Widgets
    private LinearLayout linQuit;
    private Spinner spinnerLanguage;
    private RelativeLayout linFeedBack, linShareApp;
    //array
    private String[] languages;
    //bool checking of night theme
    private boolean isChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_settings, container, false);

        final LottieAnimationView lottieSwitcher = view.findViewById(R.id.switcher);
        linFeedBack = view.findViewById(R.id.lin_feed_back);
        linQuit = view.findViewById(R.id.lin_quit);
        linShareApp = view.findViewById(R.id.lin_share_app);

        if (SharedPrefManager.getInstance(getContext()).getMode() == false) {
            lottieSwitcher.setMinAndMaxProgress(.5f, 1f);
            lottieSwitcher.playAnimation();
            isChecked = false;
        } else {
            lottieSwitcher.setMinAndMaxProgress(0f, .5f);
            lottieSwitcher.playAnimation();
            isChecked = true;
        }


        lottieSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChecked == true) {
                    lottieSwitcher.setMinAndMaxProgress(.5f, 1.0f);
                    lottieSwitcher.playAnimation();
                    SharedPrefManager.getInstance(getContext()).setMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isChecked = false;
                } else {
                    lottieSwitcher.setMinAndMaxProgress(0f, .5f);
                    lottieSwitcher.playAnimation();
                    SharedPrefManager.getInstance(getContext()).setMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isChecked = true;
                }
            }
        });

        spinnerLanguage = view.findViewById(R.id.spinner_languages);
        changeLanguage = getContext().getResources().getString(R.string.language_changing);
        english = getContext().getResources().getString(R.string.english);
        ukranian = getContext().getResources().getString(R.string.ukranian);
        french = getContext().getResources().getString(R.string.french);
        russian = getContext().getResources().getString(R.string.russian);
        languages = new String[]{changeLanguage, english, ukranian, french, russian};

        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, languages);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterLanguage);
        spinnerLanguage.setSelection(0);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                setLanguage(parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        linShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check Calculator");
                    String shareMessage = "\n " + "Let me recommend" + " \n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "APP"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        linQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity(), R.style.ThemeDialog);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert_dialog_quit);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                MaterialButton btnNo = dialog.findViewById(R.id.btn_no), btnYes = dialog.findViewById(R.id.btn_yes);

                btnNo.setOnClickListener(v -> dialog.dismiss());


                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().moveTaskToBack(true);
                        getActivity().finish();
                        System.exit(0);
                        dialog.cancel();
                    }
                });

                dialog.show();


            }
        });

        linFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"freshart666@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send feedback"));
            }
        });

        return view;
    }

    @Override
    public void setLanguage() {
        //Do nothing
    }

    @Override
    public void setLanguage(AdapterView<?> parent, int position) {
        String selectedLanguage = parent.getItemAtPosition(position).toString();
        if (selectedLanguage.equals(english)) {
            setLocal(getActivity(), "en");
            getActivity().finish();
            startActivity(getActivity().getIntent());
        } else if (selectedLanguage.equals(ukranian)) {
            setLocal(getActivity(), "ua");
            getActivity().finish();
            startActivity(getActivity().getIntent());
        } else if (selectedLanguage.equals(french)) {
            setLocal(getActivity(), "fr");
            getActivity().finish();
            startActivity(getActivity().getIntent());
        } else if (selectedLanguage.equals(russian)) {
            setLocal(getActivity(), "ru");
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
        SharedPrefManager.getInstance(getContext()).setLanguage(selectedLanguage);
    }

    @Override
    public void setLocal(Activity activity, String langCode) {
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

}