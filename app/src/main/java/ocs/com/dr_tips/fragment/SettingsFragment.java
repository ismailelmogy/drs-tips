package ocs.com.dr_tips.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ocs.com.dr_tips.R;
import ocs.com.dr_tips.activity.LoginActivity;
import ocs.com.dr_tips.util.DialogMaker;
import ocs.com.dr_tips.util.PreferenceHelper;

import static ocs.com.dr_tips.util.Constants.ARABIC;
import static ocs.com.dr_tips.util.Constants.ENGLISH;

public class SettingsFragment extends DrsTipsBaseFragment {
    @BindView(R.id.ic_change_pass)
    ImageView changePasswordIc;
    @BindView(R.id.ic_change_lang)
    ImageView changeLanguageIc;
    @BindView(R.id.ic_logout)
    ImageView logoutIc;
    @BindView(R.id.ic_payment_info)
    ImageView paymentInfoIc;
    @BindView(R.id.ic_contacr_us)
    ImageView contactUsIc;
    private String language;
    private Dialog changeLanguageDialog;
    private LinearLayout arabicChangeLanguageLinearLayout,
            englishChangeLanguageLinearLayout;
    private Button cancelChangeLanguageDialogButton;


    @Inject
    PreferenceHelper preferenceHelper;

    @OnClick(R.id.ic_logout)
    void Logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    public SettingsFragment() {
        // Required empty public constructor
    }


    @OnClick(R.id.ic_change_lang)
    void changeLanguage() {
        language = preferenceHelper.getLanguage();
        changeLanguageDialog = DialogMaker.makeDialog(getActivity(), R.layout.dialog_change_language);
        arabicChangeLanguageLinearLayout = changeLanguageDialog.findViewById(R.id.arabic_change_language_linear_layout);
        englishChangeLanguageLinearLayout = changeLanguageDialog.findViewById(R.id.english_change_language_linear_layout);
        cancelChangeLanguageDialogButton = changeLanguageDialog.findViewById(R.id.dilog_change_language_cancel_button);

        cancelChangeLanguageDialogButton.setOnClickListener(v -> changeLanguageDialog.dismiss());
        arabicChangeLanguageLinearLayout.setOnClickListener(v ->
        {
            language = ARABIC;
            saveLanguage();
        });
        englishChangeLanguageLinearLayout.setOnClickListener(v ->
        {
            language = ENGLISH;
            saveLanguage();
        });

        changeLanguageDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        changeLanguageDialog.show();
    }


    public static void setLocale(Context context, String lang) {
        Configuration config = context.getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            Locale locale;
            if (lang.equals(ENGLISH))
                locale = new Locale(lang, "US");
            else
                locale = new Locale(lang);
            config.locale = locale;
            Locale.setDefault(locale);
            config.setLocale(locale);
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public void saveLanguage() {
        preferenceHelper.setLanguage(language);
        setLocale(getContext(), language);
        changeLanguageDialog.dismiss();
        getActivity().recreate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setToolbarWithBackBtn(R.string.settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
