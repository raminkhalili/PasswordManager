package com.brunchhex.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ramin on 23/12/17.
 */

public class Data extends AppCompatActivity {
    Database db;
    Security sec;
    LinearLayout CL;
    String key, user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        key = getIntent().getStringExtra("key");
        user = getIntent().getStringExtra("user");
        CL = findViewById(R.id.body);
        db = new Database(this);
        sec = new Security();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CL.removeAllViews();
        Button btn_add = new Button(this);
        btn_add.setText("Add new Data");
        btn_add.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addData = new Intent(Data.this, AddData.class);
                addData.putExtra("user", user);
                addData.putExtra("key", key);
                startActivity(addData);
                Toast.makeText(Data.this, "Welcome back", Toast.LENGTH_LONG).show();
            }
        });
        CL.addView(btn_add);
        Cursor data = db.selectAllData();
        if (data.getCount() == 0) {
            TextView txt_result = new TextView(this);
            txt_result.setText("Data not Found please Add Data");
            txt_result.setTextColor(Color.RED);
            txt_result.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            CL.addView(txt_result);
            return;
        } else {
            while (data.moveToNext()) {

                Details dt = new Details(this);
                dt.Id=data.getString(0).toString();
                dt.Username = data.getString(1).toString();
                try {
                    dt.Password = sec.decrypt(data.getString(2).toString(), key);
                } catch (Exception e) {
                    return;
                }
                dt.Website = data.getString(3).toString();
                dt.Phone = data.getString(4).toString();
                dt.Email = data.getString(5).toString();
                dt.init();
                CL.addView(dt);

            }
        }
    }
}