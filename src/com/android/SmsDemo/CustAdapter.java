package com.android.SmsDemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustAdapter extends BaseAdapter 
{
	List<String>phone_Number = new ArrayList<String>();
	List<String>body = new ArrayList<String>();
	Context context;
        
	public CustAdapter(SmsDemoActivity smsDemoActivity, List<String> number,
			List<String> smsText) 
	{
      context = smsDemoActivity;
      phone_Number = number;
      body = smsText;
	}

	public CustAdapter(OpenSms openSms, List<String> number,
			List<String> smsText)
	{
		context = openSms;
	      phone_Number = number;
	      body = smsText;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return phone_Number.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
        LayoutInflater inflater = LayoutInflater.from(context);
        //View rowView = inflater.inflate(R.layout.sms, parent,flase);
        View rowView = inflater.inflate(R.layout.sms,parent,false);
        ImageView img = (ImageView) rowView.findViewById(R.id.smsimg);
        TextView text1 = (TextView) rowView.findViewById(R.id.smstxt);
        TextView text2 = (TextView) rowView.findViewById(R.id.smstxt2);
        
        img.setImageResource(R.drawable.sms);
        text1.setText(phone_Number.get(position));
        text2.setText(body.get(position));
        
		return rowView;
	}

}
