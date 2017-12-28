package com.brunchhex.passwordmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_register,btn_about,btn_login;
    EditText txt_username,txt_password,txt_key;
    CheckBox chk_savepassword;
    Database db;
    Security sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_register=(Button) findViewById(R.id.btn_register);
        btn_login=(Button) findViewById(R.id.btn_login);
        btn_about=(Button) findViewById(R.id.btn_about);
        txt_username=(EditText)findViewById(R.id.txt_username);
        txt_password=(EditText)findViewById(R.id.txt_password);
        txt_key=(EditText)findViewById(R.id.txt_key);
        chk_savepassword=(CheckBox)findViewById(R.id.chk_savepassword);
        db=new Database(this);
        sec=new Security();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=txt_username.getText().toString();
                String password=txt_password.getText().toString();
                String encrypt_password="";
                String key=txt_key.getText().toString();
                if (username.length()<4 || username.length()>16) {
                    Toast.makeText(MainActivity.this, "username should between 4-16 characters.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length()<8 || password.length()>32) {
                    Toast.makeText(MainActivity.this, "password should between 8-32 characters.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (key.length()<4 || key.length()>32) {
                    Toast.makeText(MainActivity.this, "key should between 4-32 characters.", Toast.LENGTH_LONG).show();
                    return;
                }
                try{
                    encrypt_password=sec.encrypt(password,key);
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this,"Problem Encrypt Password :(",Toast.LENGTH_LONG).show();
                    return;
                }

                switch (db.insertUser(username,encrypt_password)){
                    case -1:
                        Toast.makeText(MainActivity.this,"username exists on database.",Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        Toast.makeText(MainActivity.this,"problem database please try again",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,"successfuly register  :)",Toast.LENGTH_LONG).show();
                        txt_username.setText("");
                        txt_password.setText("");
                        txt_key.setText("");
                        final AlertDialog alert=new AlertDialog.Builder(MainActivity.this).create();
                        alert.setTitle("Hello "+username);
                        alert.setMessage("Register Successful please login");
                        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
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
                    Toast.makeText(MainActivity.this, "Errrrr", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.loginUser(username,encrypt_password)){
                    Intent dataIntent=new Intent(MainActivity.this,Data.class);
                    dataIntent.putExtra("user",txt_username.getText().toString());
                    dataIntent.putExtra("key",txt_key.getText().toString());
                    startActivity(dataIntent);
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent=new Intent(MainActivity.this,About.class);
                startActivity(aboutIntent);
            }
        });

    }
}
