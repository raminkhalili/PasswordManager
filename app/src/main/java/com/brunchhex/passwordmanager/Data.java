package com.brunchhex.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Data extends AppCompatActivity {
    Database db;
    Security sec;
    LinearLayout CL;
    Toolbar toolbarData;
    String key, user="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        key = getIntent().getStringExtra("key");
        user = getIntent().getStringExtra("user");
        CL = findViewById(R.id.body);
        db = new Database(this);
        sec = new Security();
        toolbarData=findViewById(R.id.toolbar_data);
        toolbarData.setTitleTextColor(Color.WHITE);
        toolbarData.setTitle("Data");
        toolbarData.inflateMenu(R.menu.menu_data);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            toolbarData.setElevation(5);
        }
        toolbarData.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_new_data:
                        Intent add_data=new Intent(Data.this,AddData.class);
                        add_data.putExtra("user",user);
                        add_data.putExtra("key",key);
                        startActivity(add_data);
                        break;
                    case R.id.search_in_data:
                        Intent search=new Intent(Data.this,SearchData.class);
                        search.putExtra("user",user);
                        search.putExtra("key",key);
                        startActivity(search);
                        break;
                }
                return  true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        CL.removeAllViews();
        CL.addView(toolbarData);
        Cursor data = db.selectAllData(user);
        if (data.getCount() == 0) {
            TextView txt_result = new TextView(this);
            txt_result.setText(getString(R.string.data_notfound));
            txt_result.setTextColor(Color.RED);
            txt_result.setGravity(Gravity.CENTER_HORIZONTAL);
            txt_result.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            CL.addView(txt_result);
        } else {
            while (data.moveToNext()) {
                Details dt = new Details(this);
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
}