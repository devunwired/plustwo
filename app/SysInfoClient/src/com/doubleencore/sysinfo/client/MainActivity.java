package com.doubleencore.sysinfo.client;

import com.doubleencore.services.sysinfo.SysInfoManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	private TextView mTextView;
	
	private SysInfoManager mManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTextView = (TextView) findViewById(R.id.text_output);
        findViewById(R.id.btn_read).setOnClickListener(this);
        
        mManager = SysInfoManager.getInstance(this);
    }
    
    @Override
    public void onClick(View v) {
    	String result = "";
    	switch (v.getId()) {
		case R.id.option_cpu:
			result = mManager.readCpuInfo();
			break;
		case R.id.option_mem:
			result = mManager.readMemInfo();
			break;
		case R.id.option_cmdline:
			result = mManager.readCommandline();
			break;
		default:
			break;
		}
    	
    	mTextView.setText(result);
    }
}
