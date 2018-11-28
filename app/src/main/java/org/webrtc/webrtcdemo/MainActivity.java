package org.webrtc.webrtcdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements MediaEngineObserver{
    private NativeWebRtcContextRegistry contextRegistry = null;
    private MediaEngine mediaEngine = null;

    private LinearLayout llRemoteSurface;
    private LinearLayout llLocalSurface;
    private TextView tvStats;

    private final int aRxPortDefault = 11113;
    private final int aTxPortDefault =11113;
    private final int vRxPortDefault = 11111;
    private final int vTxPortDefault = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        tvStats = (TextView)findViewById(R.id.tvStats);
        contextRegistry = new NativeWebRtcContextRegistry();
        contextRegistry.register(this);

        mediaEngine = new MediaEngine(this);
        mediaEngine.setRemoteIp("127.0.0.1");
        mediaEngine.setAudio(true);
        mediaEngine.setAudioTxPort(aTxPortDefault);
        mediaEngine.setAudioRxPort(aRxPortDefault);
        mediaEngine.setAudioCodec(0);

        mediaEngine.setSpeaker(true);
        mediaEngine.setVideoRxPort(vRxPortDefault);
        mediaEngine.setVideoTxPort(vTxPortDefault);
        mediaEngine.setSendVideo(true);
        mediaEngine.setReceiveVideo(true);
        mediaEngine.setVideoCodec(VideoCodecInst.CodecType.H264.ordinal());
        mediaEngine.setResolutionIndex(4);
        mediaEngine.videoCodecsAsString();
        mediaEngine.setObserver(this);
        mediaEngine.start();
        llLocalSurface = (LinearLayout)findViewById(R.id.llLocalView);
        llLocalSurface.addView(mediaEngine.getLocalSurfaceView());
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) llLocalSurface.getLayoutParams();
        params.height = screenHeight* 1/2;
        llLocalSurface.setLayoutParams(params);

        llRemoteSurface = (LinearLayout)findViewById(R.id.llRemoteView);
        LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams) llRemoteSurface.getLayoutParams();
        params1.height = screenHeight* 1/2;

        llRemoteSurface.addView(mediaEngine.getRemoteSurfaceView());
        llRemoteSurface.setLayoutParams(params1);

    }

    @Override
    public void newStats(final String stats) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStats.setText(stats);
            }
        });
    }



}
