package com.doubleencore.sysinfoservice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TestActivity extends Activity implements View.OnClickListener
{
	private TextView mTextView;
	
	private SysInfoReader mInfoReader;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView) findViewById(R.id.text_output);
        findViewById(R.id.btn_read_cpu).setOnClickListener(this);
        findViewById(R.id.btn_read_cmdline).setOnClickListener(this);
        
        mInfoReader = new SysInfoReader();
    }
    
    @Override
    public void onClick(View v) {
    	String result = "";
    	switch (v.getId()) {
		case R.id.btn_read_cpu:
			result = mInfoReader.readCpuInfo();
			break;
		case R.id.btn_read_cmdline:
			result = mInfoReader.readCommandline();
			break;
		default:
			break;
		}
    	
    	mTextView.setText(result);
    }
}
