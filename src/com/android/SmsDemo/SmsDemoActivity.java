package com.android.SmsDemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SmsDemoActivity extends Activity 
{
	TextView text1,text2;
	CustAdapter adapter;
	SQLiteDatabase db;
	ListView list;
	String table_name = "smsdb";
	String phone_number="number";
	String sms_body="body";
	List<Integer>id;
	List<String>number;
	List<String>smsText;
	TextView num;
	int sms_id=0;
	TextView body,all,count;
	 Dialog d ;
	int sms_count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       list = (ListView) findViewById(R.id.smslist);
       all = (TextView) findViewById(R.id.all_sms);
       all.setTextColor(Color.RED);
       count = (TextView) findViewById(R.id.sms_count);
       count.setTextColor(Color.MAGENTA);
       count.setBackgroundColor(Color.TRANSPARENT);
        init();
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
			     num = (TextView) arg1.findViewById(R.id.smstxt);
			     body = (TextView) arg1.findViewById(R.id.smstxt2);
			     sms_id = id.get(arg2);
	             callDialog();		     
	             
			}
        	
		});
        
    }
    
    public void callDialog()
    {
    	d = new Dialog(SmsDemoActivity.this);
	     d.setContentView(R.layout.show);
	     TextView showText = (TextView) d.findViewById(R.id.bodytext);
	     TextView shownum = (TextView) d.findViewById(R.id.bodynum);
	     Button add = (Button) d.findViewById(R.id.smsAdd);
	     
	     d.setTitle("SMS Content");
	     showText.setBackgroundColor(Color.WHITE);
	     showText.setMovementMethod(ScrollingMovementMethod.getInstance());
	     showText.setTextColor(Color.BLACK);
	     shownum.setText(num.getText().toString());
	     showText.setText(body.getText().toString());
	     d.show();
	     add.setOnClickListener(new OnClickListener() 
	     {
			
			@Override
			public void onClick(View v) {
				
				db = openOrCreateDatabase("sms",SQLiteDatabase.OPEN_READWRITE,null);
	 			db.execSQL("create table if not exists "+table_name+"(smsid integer,"+phone_number+" text,"+sms_body+" text)");
	 			ContentValues values = new ContentValues();
	 			values.put("smsid", sms_id);
	 			values.put(phone_number, num.getText().toString());
	 			values.put(sms_body, body.getText().toString());
	 			db.insert(table_name, null, values);
	 			db.close();
	 			getContentResolver().delete(Uri.parse("content://sms"),"_id ="+sms_id,null);
	 			Toast.makeText(getApplicationContext(), "query successful", Toast.LENGTH_LONG).show();
	 			d.cancel();
	 			
	 			init();
	 			//adapter.notifyDataSetChanged();
	 			
			}
		});
    }
    
    public void init()
    {
    	sms_count=0;
    	id = new ArrayList<Integer>();
    	number = new ArrayList<String>();
    	smsText = new ArrayList<String>();
    	Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = this.getContentResolver().query(uri, null, null, null, null);
        
        while(c.moveToNext())
        {
        	id.add(c.getInt(0));
        	number.add(c.getString(c.getColumnIndex("address")).toString());
        	smsText.add(c.getString(c.getColumnIndex("body")).toString());
        	sms_count++;
        }
        count.setText(""+sms_count);
        adapter = new CustAdapter(this,number,smsText); 
        list.setAdapter(adapter);
        
    }
   
}