package com.brunchhex.passwordmanager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by ramin on 23/12/17.
 */

public class About extends AppCompatActivity{
    Typeface t_f;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        switch (getIntent().getStringExtra("lang")){
            case "English":
                t_f= Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf");
                break;
            case "Persian":
                t_f=Typeface.createFromAsset(getAssets(),"fonts/vazir.ttf");
                break;
            case "Kurdi":
                t_f=Typeface.createFromAsset(getAssets(),"fonts/chimen.ttf");
                break;
        }
        TextView a_t,a_te,d_t,d_te,w_t,w_te;
        a_t=findViewById(R.id.about_title_app);
        a_te=findViewById(R.id.about_text_app);
        d_t=findViewById(R.id.about_title_developer);
        d_te=findViewById(R.id.about_text_developer);
        w_t=findViewById(R.id.about_title_website);
        w_te=findViewById(R.id.about_text_website);
        a_t.setTypeface(t_f);
        a_te.setTypeface(t_f);
        d_t.setTypeface(t_f);
        d_te.setTypeface(t_f);
        w_t.setTypeface(t_f);
        w_te.setTypeface(t_f);




    }
}
