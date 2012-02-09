package com.android.SmsDemo;

import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OpenSms extends Activity
{
	TextView text1,text2,t1,t2;
	CustAdapter adapter;
	SQLiteDatabase db;
	ListView list;
	String table_name = "smsdb";
	String phone_number="number";
	String sms_body="body";
	List<Integer>id1;
	List<String>number1;
	List<String>smsText1;
	TextView num;
	int sms_id=0;
	TextView body,all,count;
	int sms_count=0;
	Dialog d,d1;
@Override
protected void onCreate(Bundle savedInstanceState) 
{
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	count = (TextView) findViewById(R.id.sms_count);
	all = (TextView) findViewById(R.id.all_sms);
	all.setTextColor(Color.BLUE);
	count.setTextColor(Color.MAGENTA);
    count.setBackgroundColor(Color.TRANSPARENT);
	list = (ListView) findViewById(R.id.smslist);
	registerForContextMenu(list);

	init();
    
	
	list.setOnItemClickListener(new OnItemClickListener() 
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			num = (TextView) arg1.findViewById(R.id.smstxt);
		     body = (TextView) arg1.findViewById(R.id.smstxt2);
		     sms_id = id1.get(arg2);
		     callDialog();
		}
	});
	
	
	
	list.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) 
		{
			num = (TextView) arg1.findViewById(R.id.smstxt);
		     body = (TextView) arg1.findViewById(R.id.smstxt2);
		     sms_id = id1.get(arg2);

			return false;
		}
	});
	
}



@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) 
{
	super.onCreateContextMenu(menu, v, menuInfo);
	MenuInflater inflater=getMenuInflater();
	inflater.inflate(R.menu.menu1, menu);
}


@Override
public boolean onContextItemSelected(MenuItem item) 
{
switch (item.getItemId())
{
	case R.id.item1:
	  {
		          d1 = new Dialog(OpenSms.this);
		           d1.setContentView(R.layout.myshow);
		           t1= (EditText) d1.findViewById(R.id.sms1);
		           t2 = (EditText) d1.findViewById(R.id.sms2);
		           Button send = (Button) d1.findViewById(R.id.send);
		           t2.setMovementMethod(ScrollingMovementMethod.getInstance());
		           t1.setText(num.getText().toString());
		           d1.setTitle("Reply");
		           t2.setLines(8);
		           d1.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		           d1.show();
		           send.setOnClickListener(new OnClickListener()
		           {
					
					@Override
					public void onClick(View v) 
					{
					       SmsManager smsmgr = SmsManager.getDefault();
					       smsmgr.sendTextMessage(t1.getText().toString(), null, t2.getText().toString(), null, null);
					       d1.cancel();
					       Toast.makeText(getApplicationContext(), "SMS Sent!!", Toast.LENGTH_SHORT).show();
					}
				});
		          
		           
		           
		           
		  break;
	  }
	case R.id.item2:
	               break;
	case R.id.item3:
	             break;

	default:
		break;
}
	return super.onContextItemSelected(item);
}

@Override
public boolean onCreateOptionsMenu(Menu menu) 
{
MenuInflater inflater=getMenuInflater();
inflater.inflate(R.menu.filemgr_menu, menu);
return true;
}





@Override
public boolean onOptionsItemSelected(MenuItem item) 
{
switch (item.getItemId())
{
case (R.id.menu_reply):
	 
	                      Intent intent = new Intent(OpenSms.this,SmsDemoActivity.class);
                          startActivity(intent);
                          init();

	
	break;
case (R.id.menu_Forward):
			//Action for Encrypting the file.
	 Toast.makeText(getApplicationContext(), "ENCRYPT FILE", Toast.LENGTH_SHORT).show();
	break;


	
case (R.id.menu_Delete):
	      		//finish();
  

break;	
default:
		break;
}

	return super.onOptionsItemSelected(item);
}


@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	init();
}


public void init()
{
	sms_count=0;
	id1 = new ArrayList<Integer>();
	number1 = new ArrayList<String>();
	smsText1 = new ArrayList<String>();
	//Uri uri = Uri.parse("com.android.SmsDemo/databases/sms/smsdb");
	SQLiteDatabase db=openOrCreateDatabase("sms", SQLiteDatabase.OPEN_READWRITE, null);
	db.execSQL("create table if not exists smsdb(smsid integer,number text,body text)");
    Cursor c = db.query("smsdb", null, null, null, null, null, null);
    if(c.getCount()==0)
    {
    	Toast.makeText(getApplicationContext(), "in no cursor area", Toast.LENGTH_LONG).show();
    }
    else
    {
    c.moveToFirst();
    do
    {
    	number1.add(c.getString(1));
    	id1.add(c.getInt(0));
    	smsText1.add(c.getString(2));
    	sms_count++;
    	
    }while(c.moveToNext());
    }
    c.close();
    db.close();
    count.setText(""+sms_count);
    
    adapter = new CustAdapter(this,number1,smsText1); 
    list.setAdapter(adapter);
    
}


public void callDialog()
{
	d = new Dialog(OpenSms.this);
     d.setContentView(R.layout.myshow);
     TextView showText = (TextView) d.findViewById(R.id.sms2);
     TextView shownum = (TextView) d.findViewById(R.id.sms1);
//     Button add = (Button) d.findViewById(R.id.smsAdd);
     
     d.setTitle("SMS Content");
     showText.setBackgroundColor(Color.WHITE);
     showText.setMovementMethod(ScrollingMovementMethod.getInstance());
     showText.setTextColor(Color.BLACK);
     shownum.setText(num.getText().toString());
     showText.setText(body.getText().toString());
     d.show();

}

}
