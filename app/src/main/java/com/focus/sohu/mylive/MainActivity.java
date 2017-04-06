package com.focus.sohu.mylive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

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

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.btn_main:
                Intent intent1=new Intent(this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_little:
                Intent intent2=new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_audience:
                Intent intent3=new Intent(this,MainActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
