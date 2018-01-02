package com.brunchhex.passwordmanager;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;


public class SearchData extends AppCompatActivity {
    Database db;
    Security sec;
    LinearLayout CL;
    Toolbar toolbarData;
    String key, user="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        key = getIntent().getStringExtra("key");
        user = getIntent().getStringExtra("user");
        CL = findViewById(R.id.body_search);
        db = new Database(this);
        sec = new Security();
        toolbarData=findViewById(R.id.toolbar_search);
        toolbarData.setTitleTextColor(Color.WHITE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbarData.setElevation(5);
        }
        final EditText txt_search=findViewById(R.id.txt_search);
        Button btn_search=findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CL.removeAllViews();
                CL.addView(toolbarData);
                String email=txt_search.getText().toString();
                if (Objects.equals(email, ""))
                    return;
                Cursor data = db.searchByEmail(email);
                if (data.getCount() == 0) {
                    TextView txt_result = new TextView(SearchData.this);
                    txt_result.setText(getString(R.string.data_notfound));
                    txt_result.setTextColor(Color.RED);
                    txt_result.setGravity(Gravity.CENTER_HORIZONTAL);
                    txt_result.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    CL.addView(txt_result);
                } else {
                    while (data.moveToNext()) {
                        Details dt = new Details(SearchData.this);
                        dt.Id=data.getString(0);
                        dt.Username = data.getString(1);
                        try {
                            dt.Password = sec.decrypt(data.getString(2), key);
                        } catch (Exception e) {
                            return;
                        }
                        dt.Website = data.getString(3);
                        dt.Phone = data.getString(4);
                        dt.Email = data.getString(5);
                        dt.init();
                        CL.addView(dt);
                    }
                }
            }
        });
    }
}
