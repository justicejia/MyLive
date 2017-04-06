package com.focus.sohu.mylive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpush);
        init();
        startWatch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRtmp();
    }
    public void init(){
        //初始化播放器（小主播画面）
        mPlayerView_little = (TXCloudVideoView) findViewById(R.id.video_audience_little);
        mLivePlayer_little = new TXLivePlayer(this);
        mLivePlayer_little.enableHardwareDecode(true);     // 硬件解码
        mLivePlayer.setPlayerView(mPlayerView_little);
        //初始化大主播播放画面
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_audience_main);
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.enableHardwareDecode(true);     // 硬件解码
        mLivePlayer.setPlayerView(mPlayerView);

        Button btn= (Button) findViewById(R.id.btn_request);
        btn.setOnClickListener(new View.OnClickListener() {
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
        String flvUrl = "http://2157.liveplay.myqcloud.com/live/2157_xxxx.flv";  //大主播的推流地址
        mLivePlayer.startPlay(flvUrl,TXLivePlayer.PLAY_TYPE_LIVE_FLV);
        String flvUrl_little = "http://2157.liveplay.myqcloud.com/live/2157_xxxx.flv";  //小主播的推流地址
        mLivePlayer.startPlay(flvUrl_little, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
    }
    //停止播放和推流
    public void stopRtmp() {
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
        mLivePlayer_little.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView_little.onDestroy();
    }
}
