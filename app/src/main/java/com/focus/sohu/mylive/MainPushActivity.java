package com.focus.sohu.mylive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import static com.tencent.rtmp.TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
import static com.tencent.rtmp.ugc.TXRecordCommon.VIDEO_RESOLUTION_360_640;

/**
 * Created by yuanminjia212762 on 2017/4/6.
 */

public class MainPushActivity extends Activity {
    private TXLivePushConfig mLivePushConfig;
    private TXLivePlayConfig mLivePlayConfig;
    private  TXLivePusher mLivePusher;
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
        //初始化推流预览
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.enableAEC(true);
        mLivePushConfig.setHardwareAcceleration(true);
        mLivePushConfig.setVideoResolution(VIDEO_RESOLUTION_360_640); // 秀场直播最流行的分辨率
        mLivePushConfig.setVideoBitrate(800); // 这是适合360p的码率，如果想用更高的分辨率，就要需要更高的码率来配合
        mLivePushConfig.setAudioSampleRate(48000);  // 不要用其它的
        mLivePushConfig.setAudioChannels(1); // 单声道
        mLivePushConfig.setHardwareAcceleration(true);
        mLivePusher.setConfig(mLivePushConfig);
        //初始化播放器（小主播画面）
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
    //大主播画面推流和预览开始
    public void startPush(){
        String rtmpPushUrl = "rtmp://5072.livepush.myqcloud.com/live/5072_test11658?txSecret=4a4a63d2965f232242bb64581796a7c6&txTime=697C6E15&record=hls&mix=layer:s;session_id:1000;t_id:1"; //这里需要填入大主播的推流地址
        mLivePusher.startPusher(rtmpPushUrl);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_main);
        mLivePusher.startCameraPreview(mCaptureView);
    }
    //小主播画面开始播放
    public void startWatch(){
        String flvUrl = "rtmp://5072.livepush.myqcloud.com/live/5073_test11658?txSecret=4a4a63d2965f232242bb64581796a7c6&txTime=697C6E15&record=hls&session_id=1000";  //这里需要填入小主播的推流地址
        mLivePlayer.startPlay(flvUrl, PLAY_TYPE_LIVE_RTMP_ACC); //低延时链路播放
    }
    //停止播放和推流
    public void stopRtmp() {
        //销毁推流
        mLivePusher.stopCameraPreview(true); //停止摄像头预览
        mLivePusher.stopPusher();            //停止推流
        mLivePusher.setPushListener(null);   //解绑 listener
        //销毁播放器
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
    }
}
