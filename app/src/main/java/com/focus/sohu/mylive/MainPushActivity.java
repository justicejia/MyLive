package com.focus.sohu.mylive;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * Created by yuanminjia212762 on 2017/4/6.
 */

public class MainPushActivity extends Activity {
    private TXLivePushConfig mLivePushConfig;
    private  TXLivePusher mLivePusher;
    TXCloudVideoView mCaptureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        startPush();
    }

    public void init(){
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);
    }

    public void startPush(){
        String rtmpPushUrl = "rtmp://2157.livepush.myqcloud.com/live/xxxxxx";
        mLivePusher.startPusher(rtmpPushUrl);
        mCaptureView = (TXCloudVideoView) findViewById(R.id.video_main);
        mLivePusher.startCameraPreview(mCaptureView);
    }

    public void startWatch(){

    }
}
