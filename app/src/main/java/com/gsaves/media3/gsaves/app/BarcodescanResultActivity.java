package com.gsaves.media3.gsaves.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BarcodescanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcodescan_result);
        TextView scan_code = (TextView)findViewById(R.id.tv_scanned_code);
       // Bundle bundle = getIntent().getExtras();
        String code=getIntent().getStringExtra("contents");


        if(code!= null)
        {
            //TODO here get the string stored in the string variable and do
            // setText() on userName
            scan_code.setText(code);
        }
    }
}
