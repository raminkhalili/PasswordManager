package com.brunchhex.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btn_register,btn_about,btn_login;
    EditText txt_username,txt_password,txt_key;
    Database db;
    Security sec;
    Spinner lng;
    SharedPreferences data;
    String lang="en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data=getSharedPreferences("Settings",0);
        lang=data.getString("Language","");
        int pos=0;
        switch (lang)
        {
            case "en":
                pos=0;
                break;
            case "fa":
                pos=1;
                break;
            case "ku":
                pos=2;
                break;
        }
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        db=new Database(this);
        sec=new Security();

        btn_register= findViewById(R.id.btn_register);
        btn_login= findViewById(R.id.btn_login);
        btn_about= findViewById(R.id.btn_about);
        txt_username=findViewById(R.id.txt_username);
        txt_password=findViewById(R.id.txt_password);
        txt_key=findViewById(R.id.txt_key);
        lng= findViewById(R.id.spn_language);
        lng.setSelection(pos);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txt_username.getText().toString();
                String password=txt_password.getText().toString();
                String encrypt_password;
                String key=txt_key.getText().toString();
                if (username.length()<4 || username.length()>16) {
                    txt_username.setError(getString(R.string.username_length));
                    return;
                }
                if (password.length()<8 || password.length()>32) {
                    txt_password.setError(getString(R.string.password_length));
                    return;
                }
                if (key.length()<4 || key.length()>32) {
                    txt_key.setError(getString(R.string.key_length));
                    return;
                }
                try{
                    encrypt_password=sec.encrypt(password,key);
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,getString(R.string.problem_encrypt_password)+"\n"+getString(R.string.again),Toast.LENGTH_LONG).show();
                    return;
                }
                switch (db.insertUser(username,encrypt_password)){
                    case -1:
                        Toast.makeText(MainActivity.this,getString(R.string.username_exists),Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        Toast.makeText(MainActivity.this,getString(R.string.database_problem)+"\n"+getString(R.string.again),Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,getString(R.string.successful_register),Toast.LENGTH_LONG).show();
                        txt_username.setText("");
                        txt_password.setText("");
                        txt_key.setText("");
                        final AlertDialog alert=new AlertDialog.Builder(MainActivity.this).create();
                        alert.setTitle(getString(R.string.hello)+username);
                        alert.setMessage(getString(R.string.please_login));
                        alert.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert.dismiss();
                            }
                        });
                        alert.show();
                        break;
                }


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txt_username.getText().toString();
                String password=txt_password.getText().toString();
                String key=txt_key.getText().toString();
                String encrypt_password;
                try{
                    encrypt_password=sec.encrypt(password,key);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, getString(R.string.problem_process)+"\n"+getString(R.string.again), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.loginUser(username,encrypt_password)){
                    Intent dataIntent=new Intent(MainActivity.this,Data.class);
                    dataIntent.putExtra("user",txt_username.getText().toString());
                    dataIntent.putExtra("key",txt_key.getText().toString());
                    dataIntent.putExtra("lang",lang);
                    startActivity(dataIntent);
                    Toast.makeText(MainActivity.this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent=new Intent(MainActivity.this,About.class);
                aboutIntent.putExtra("lang",lang);
                startActivity(aboutIntent);
            }
        });
        lng.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor=data.edit();
                switch (lng.getSelectedItem().toString()){
                    case "English":
                        editor.putString("Language","en");
                        editor.commit();
                        break;
                    case "Persian":
                        editor.putString("Language","fa");
                        editor.commit();
                        break;
                    case "Kurdi":
                        editor.putString("Language","ku");
                        editor.commit();
                        break;
                        default:
                            editor.putString("Language","en");
                            editor.commit();
                            break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
