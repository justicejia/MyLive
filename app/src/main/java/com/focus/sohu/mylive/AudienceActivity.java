package com.focus.sohu.mylive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;


/**
 * Created by yuanminjia212762 on 2017/4/6.
 */

public class AudienceActivity extends Activity {
    private TXCloudVideoView mPlayerView;
    private TXCloudVideoView mPlayerView_little;
    private TXLivePlayer mLivePlayer;
    private TXLivePlayer mLivePlayer_little;
    private TXLivePlayConfig mPlayConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience);
        init();
        startWatch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRtmp();
    }
    public void init(){
        mPlayConfig=new TXLivePlayConfig();
        mPlayConfig.enableAEC(false);
        mPlayConfig.setAutoAdjustCacheTime(true);
        mPlayConfig.setMaxAutoAdjustCacheTime(1);
        mPlayConfig.setMinAutoAdjustCacheTime(1);
        //初始化播放器（小主播画面）
        mPlayerView_little = (TXCloudVideoView) findViewById(R.id.video_audience_little);
        mLivePlayer_little = new TXLivePlayer(this);
        mLivePlayer_little.setConfig(mPlayConfig);
        mLivePlayer_little.enableHardwareDecode(true);
        mLivePlayer_little.setPlayerView(mPlayerView_little);
        //初始化大主播播放画面
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_audience_main);
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.enableHardwareDecode(true);
        mLivePlayer.setPlayerView(mPlayerView);

        Button btn_request= (Button) findViewById(R.id.btn_request);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AudienceActivity.this,LittlePushActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //开始播放
    public void startWatch(){
        String url_little=getIntent().getExtras().getString("url_little");
        String url_main=getIntent().getExtras().getString("url_main");
        Log.d("test","url_little: "+url_little);
        Log.d("test","url_main: "+url_main);
        mLivePlayer.startPlay(url_main,TXLivePlayer.PLAY_TYPE_VOD_HLS);
        mLivePlayer_little.startPlay(url_little, TXLivePlayer.PLAY_TYPE_VOD_HLS);
    }
    //停止播放和推流
    public void stopRtmp() {
        mLivePlayer.stopPlay(true);
        mPlayerView.onDestroy();
        mLivePlayer_little.stopPlay(true);
        mPlayerView_little.onDestroy();
    }
}
