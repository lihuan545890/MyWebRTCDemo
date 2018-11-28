package org.webrtc.webrtcdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
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

    private FrameLayout llRemoteSurface;
    private FrameLayout llLocalSurface;
    private TextView tvStats;

    private final int aRxPortDefault = 11113;
    private final int aTxPortDefault =11113;
    private final int vRxPortDefault = 11111;
    private final int vTxPortDefault = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        tvStats = (TextView)findViewById(R.id.tvStats);

        contextRegistry = new NativeWebRtcContextRegistry();
        contextRegistry.register(this);

        mediaEngine = new MediaEngine(this);
        mediaEngine.setRemoteIp("127.0.0.1");
//        mediaEngine.setAudio(true);
//        mediaEngine.setAudioTxPort(aTxPortDefault);
//        mediaEngine.setAudioRxPort(aRxPortDefault);
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
        mediaEngine.setTrace(false);

//        mediaEngine.setIncomingVieRtpDump(false);
        mediaEngine.start();
        llLocalSurface = (FrameLayout)findViewById(R.id.llLocalView);
        llLocalSurface.addView(mediaEngine.getLocalSurfaceView());

        llRemoteSurface = (FrameLayout)findViewById(R.id.llRemoteView);
        llRemoteSurface.addView(mediaEngine.getRemoteSurfaceView());

    }

    @Override
    public void newStats(final String stats) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Log.i("WebRTCDemo", "stats: "+stats);
                tvStats.setText(stats);
            }
        });
    }

    @Override
    public void onDestroy() {
        mediaEngine.dispose();
        contextRegistry.unRegister();
        super.onDestroy();
    }

}
