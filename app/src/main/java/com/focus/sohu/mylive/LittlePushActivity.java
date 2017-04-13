package com.focus.sohu.mylive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import static com.tencent.rtmp.TXLiveConstants.VIDEO_RESOLUTION_TYPE_320_480;
import static com.tencent.rtmp.TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;

/**
 * Created by yuanminjia212762 on 2017/4/6.
 */

public class LittlePushActivity extends Activity {
    private TXLivePushConfig mLivePushConfig;
    private TXLivePlayConfig mLivePlayConfig;
    private TXLivePusher mLivePusher;
    private TXCloudVideoView mCaptureView;
    private TXCloudVideoView mPlayerView;
    private TXLivePlayer mLivePlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.littlepush);
        findViewById(R.id.btn_link_little).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWatch();
            }
        });
        init();
        startPush();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRtmp();
    }
    public void init(){
        //小主播推流和预览初始化
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableAEC(true);
        mLivePushConfig.setHardwareAcceleration(true);
        mLivePushConfig.setVideoResolution(VIDEO_RESOLUTION_TYPE_320_480 ); // “小主播”不需要太高分辨率
        mLivePushConfig.setVideoBitrate(300); // 码率太高是种浪费
        mLivePushConfig.setAudioSampleRate(48000);  // 不要用其它的
        mLivePushConfig.setAudioChannels(1); // 单声道
        mLivePushConfig.setHardwareAcceleration(true);
        mLivePusher.setConfig(mLivePushConfig);
        //大主播画面播放初始化
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_little_little);
        mLivePlayConfig=new TXLivePlayConfig();
        mLivePlayConfig.enableAEC(true);                // 开启回音消除
        mLivePlayConfig.setAutoAdjustCacheTime(true);   // 极速模式 - 有明显的延迟修正表现
        mLivePlayConfig.setMinAutoAdjustCacheTime(0.2f); // 200ms
        mLivePlayConfig.setMaxAutoAdjustCacheTime(0.2f); // 200ms
        mLivePlayer = new TXLivePlayer(this);
        mLivePlayer.setConfig(mLivePlayConfig);
        mLivePlayer.enableHardwareDecode(true);     // 硬件解码
        mLivePlayer.setPlayerView(mPlayerView);
    }
    //小主播画面推流和预览
    public void startPush(){
        String url_little=getIntent().getExtras().getString("little")+"&mix=layer:s;session_id:1111;t_id:1";
        Log.d("test","little_push: "+url_little);
        mLivePusher.startPusher(url_little);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_main_little);
        mLivePusher.startCameraPreview(mCaptureView);
    }
    //大主播画面播放
    public void startWatch(){
        String url_main=getIntent().getExtras().getString("main").replaceAll("push","play")+"&session_id=2222";
        String url_main_play=new StringBuilder(url_main).insert(url_main.indexOf('?')+1,"bizid=5072&").toString();
        Log.d("test","main_play: "+url_main_play);
        mLivePlayer.startPlay(url_main_play, PLAY_TYPE_LIVE_RTMP_ACC);
    }
    //停止播放和推流
    public void stopRtmp() {
        mLivePusher.stopCameraPreview(true);
        mLivePusher.stopPusher();
        mLivePusher.setPushListener(null);
        mLivePlayer.stopPlay(true);
        mPlayerView.onDestroy();
    }
}
