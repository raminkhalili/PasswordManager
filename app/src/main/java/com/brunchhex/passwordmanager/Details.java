package com.brunchhex.passwordmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Details extends CardView {

    public String Username,Password,Email,Phone,Website,Id;
    public Details(Context context) {
        super(context);
    }
    public void init(){
        LinearLayout body=new LinearLayout(getContext());
        TextView txt_username=new TextView(getContext());
        TextView txt_email=new TextView(getContext());
        TextView txt_phone=new TextView(getContext());
        TextView txt_website=new TextView(getContext());
        Button btn_show=new Button(getContext());

        txt_username.setText(Username);
        txt_email.setText(Email);
        txt_phone.setText(Phone);
        txt_website.setText(Website);

        btn_show.setText(getContext().getString(R.string.show_password));
       body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
       body.setOrientation(LinearLayout.VERTICAL);
       body.setPadding(15,15,15,15);

        body.addView(txt_username);
        body.addView(txt_website);
        body.addView(txt_email);
        body.addView(txt_phone);
        body.addView(btn_show);
        addView(body);

        btn_show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dg=new AlertDialog.Builder(getContext());
                dg.setTitle(getContext().getString(R.string.your_password));
                dg.setMessage(Password);
                dg.show();
            }
        });
    }
}
