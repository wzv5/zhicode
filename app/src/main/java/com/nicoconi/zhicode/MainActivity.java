package com.nicoconi.zhicode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends Activity implements
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button btn_hongbao;
    private Button btn_donate;
    private Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_hongbao = (Button) findViewById(R.id.btn_hongbao);
        btn_donate = (Button) findViewById(R.id.btn_donate);
        switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setChecked(Util.isDisableNotification(this));
        btn_hongbao.setOnClickListener(this);
        btn_donate.setOnClickListener(this);
        switch1.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_hongbao) {
            Util.startAlipayWithZhicode(this, R.string.zhicode_hongbao);
        }
        else if (v == btn_donate) {
            Util.startAlipayWithZhicode(this, R.string.zhicode_donate);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Util.disableNotification(this);
        }
        else {
            Util.enableNotification(this);
        }
    }
}
