package com.focus.sohu.mylive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements View.OnClickListener {

    private String MainUrl;
    private String LittleUrl;
    public EditText et_main;
    public EditText et_little;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        Button main=(Button)findViewById(R.id.btn_main);
        Button little=(Button)findViewById(R.id.btn_little);
        Button audience=(Button)findViewById(R.id.btn_audience);
        main.setOnClickListener(this);
        little.setOnClickListener(this);
        audience.setOnClickListener(this);
        et_main= (EditText) findViewById(R.id.edit_main);
        et_little= (EditText) findViewById(R.id.edit_little);


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.btn_main:
                Intent intent1=new Intent(this,MainPushActivity.class);
                MainUrl= String.valueOf(et_main.getText().toString());
                LittleUrl= String.valueOf(et_little.getText().toString());
                intent1.putExtra("little",LittleUrl);
                intent1.putExtra("main",MainUrl);
                startActivity(intent1);
                break;
            case R.id.btn_little:
                Intent intent2=new Intent(this,LittlePushActivity.class);
                LittleUrl= String.valueOf(et_little.getText().toString());
                MainUrl= String.valueOf(et_main.getText().toString());
                intent2.putExtra("little",LittleUrl);
                intent2.putExtra("main",MainUrl);
                startActivity(intent2);
                break;
            case R.id.btn_audience:
                Intent intent3=new Intent(this,AudienceActivity.class);
                MainUrl= String.valueOf(et_main.getText().toString());
                LittleUrl= String.valueOf(et_little.getText().toString());
                intent3.putExtra("url_main",cal_url(MainUrl));
                intent3.putExtra("url_little",cal_url(LittleUrl));
                startActivity(intent3);
                break;
        }
    }

    //根据推流地址计算出观众端播放地址
    private String cal_url(String url){
        String s=url.substring(url.indexOf('?')-5,url.indexOf('?'));
        return "http://5072.liveplay.myqcloud.com/live/5072_test"+s+".flv";

    }
}
