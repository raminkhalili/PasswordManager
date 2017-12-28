package com.brunchhex.passwordmanager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class Database extends SQLiteOpenHelper  {

     public static final String DATABASE_NAME="PasswordManager.db";

     Database(Context context) {

         super(context, DATABASE_NAME, null, 1);
         SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_users(username nvarchar(50) primary key ,password nvarchar(255) not null)");
        db.execSQL("create table tbl_data(id integer primary key autoincrement,username nvarchar(255),password nvarchar(255),website nvarchar(255),phone nvarchar(50),email nvarchar(255),user nvarchar(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tbl_users");
        db.execSQL("drop table if exists tbl_data");
        onCreate(db);
    }

    public boolean alreadyUsername(String Username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select username from tbl_users where username=\""+Username+"\"",null);
        if (res.getCount()<1)
            return false;
        else
            return true;
    }


    public int insertUser(String Username,String Password){
        Username=Username.toLowerCase();
        if (this.alreadyUsername(Username))
            return -1;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",Username);
        values.put("password",Password);
        long result =db.insert("tbl_users",null , values);
        if (result==-1)
            return 0;
        else
            return 1;
    }
    public boolean loginUser(String Username,String Password){
        Username=Username.toLowerCase();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur=db.rawQuery("select username from tbl_users where username='"+Username+"' and password='"+Password+"'",null);
        if (cur.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean insertData(String Username,String Password,String Website,String Phone,String Email,String User){
        User=User.toLowerCase();
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",Username);
        values.put("password",Password);
        values.put("website",Website);
        values.put("phone",Phone);
        values.put("email",Email);
        values.put("user",User);
        long res=db.insert("tbl_data",null,values);
        if (res==-1)
            return false;
        else
            return true;
    }

    public boolean deleteDate(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete("tbl_data","id=?",new String[]{id});
        if (res==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor selectData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res;
        res=db.rawQuery("select id,username,website from tbl_data",null);
        return res;
    }
    public Cursor selectAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=null;
        res=db.rawQuery("select * from tbl_data",null);
        return res;
    }
    public Cursor selectFullData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res;
        res=db.rawQuery("select * from tbl_data where id='?'",new String[]{id});
        return res;
    }

}
