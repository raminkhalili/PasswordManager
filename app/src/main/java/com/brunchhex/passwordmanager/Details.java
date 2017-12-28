package com.brunchhex.passwordmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ramin on 27/12/17.
 */

public class Details extends LinearLayout {

    public String Username,Password,Email,Phone,Website,Id;
    public Details(Context context) {
        super(context);
    }
    public void init(){
        setOrientation(LinearLayout.VERTICAL);
        setPadding(5,5,5,5);
        setBackgroundColor(getResources().getColor(R.color.colorBGDetail));

        TextView txt_username=new TextView(getContext());
        TextView txt_email=new TextView(getContext());
        TextView txt_phone=new TextView(getContext());
        TextView txt_website=new TextView(getContext());
        Button btn_show=new Button(getContext());

        txt_username.setText(Username);
        txt_email.setText(Email);
        txt_phone.setText(Phone);
        txt_website.setText(Website);

        btn_show.setText("Show Password");

        addView(txt_username);
        addView(txt_website);
        addView(txt_email);
        addView(txt_phone);
        addView(btn_show);

        btn_show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dg=new AlertDialog.Builder(getContext());
                dg.setTitle("Your Password");
                dg.setMessage(Password);
                dg.show();
            }
        });
    }
}
