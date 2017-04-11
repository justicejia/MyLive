package com.focus.sohu.mylive;

import android.app.Activity;
import android.os.Bundle;

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
        setContentView(R.layout.mainpush);
        init();
        startPush();
        startWatch();
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
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_little);
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
    //小主播画面推流和预览开始
    public void startPush(){
        String rtmpPushUrl = "rtmp://5073.livepush.myqcloud.com/live/5073_test11685?txSecret=d8e451247f3702ea2b8edd674461bff7&txTime=697C6E15&record=hls&mix=layer:s;session_id:1000;t_id:1";  //小主播的推流地址
        mLivePusher.startPusher(rtmpPushUrl);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_main);
        mLivePusher.startCameraPreview(mCaptureView);
    }
    //大主播画面开始播放
    public void startWatch(){
        String flvUrl = "rtmp://5072.livepush.myqcloud.com/live/5072_test11685?txSecret=d8e451247f3702ea2b8edd674461bff7&txTime=697C6E15&record=hls&record=hls&session_id=1000";  //大主播的推流地址
        mLivePlayer.startPlay(flvUrl, PLAY_TYPE_LIVE_RTMP_ACC);
    }
    //停止播放和推流
    public void stopRtmp() {
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
    }
}
