package com.brunchhex.passwordmanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ramin on 27/12/17.
 */

public class AddData extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        final Database db=new Database(this);
        final Security sec=new Security();
        final EditText txt_username=(EditText)findViewById(R.id.txt_add_username);
        final EditText txt_email=(EditText)findViewById(R.id.txt_add_email);
        final EditText txt_phone=(EditText)findViewById(R.id.txt_add_phone);
        final EditText txt_web=(EditText)findViewById(R.id.txt_add_web);
        final EditText txt_password=(EditText)findViewById(R.id.txt_add_password);
        Button btn_add=(Button)findViewById(R.id.btn_add_data);
        final String key=getIntent().getStringExtra("key");
        final String user=getIntent().getStringExtra("user");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username,Email,Phone,Web,Password;
                Username=txt_username.getText().toString();
                Web=txt_web.getText().toString();
                Email=txt_email.getText().toString();
                Phone=txt_phone.getText().toString();
                try {
                    Password=sec.encrypt(txt_password.getText().toString(),key);
                    db.insertData(Username,Password,Web,Phone,Email,user);
                    AlertDialog.Builder dg=new AlertDialog.Builder(AddData.this);
                    dg.setTitle(":)");
                    dg.setMessage("Successful add data.");
                    dg.show();

                    AddData.this.finish();
                } catch (Exception e) {
                    AlertDialog.Builder dg=new AlertDialog.Builder(AddData.this);
                    dg.setTitle("Error");
                    dg.setMessage("Please check password and try.");
                    dg.show();
                    return;
                }

            }
        });

    }
}
