package com.example.android.loconav;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.ActionBar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_register);

//        ActionBar actionBar= getSupportActionBar();
//        actionBar.setTitle(getResources());
        
        Button changeLang = findViewById(R.id.buttonChangeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phonenumber = "+" + code + number;

                Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);

            }
        });

    }

    private void showChangeLanguageDialog() {
        final String[] listItems= {"English","हिन्दी"};
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(RegisterActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i == 0)
                {
                    setLocale("en");
                    recreate();
                }
                else if(i == 1)
                {
                    setLocale("hi");
                    recreate();
                }

                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor= getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

    // @Override
   // protected void onStart() {
   //     super.onStart();

       // if (FirebaseAuth.getInstance().getCurrentUser() != null) {
        //    Intent intent = new Intent(this, nothingsogreat.class);
        //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

       //     startActivity(intent);
     //   }
 //   }
}