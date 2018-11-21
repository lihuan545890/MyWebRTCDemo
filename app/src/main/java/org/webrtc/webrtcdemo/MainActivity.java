package org.webrtc.webrtcdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements MediaEngineObserver{
    private NativeWebRtcContextRegistry contextRegistry = null;
    private MediaEngine mediaEngine = null;

    private LinearLayout llRemoteSurface;
    private FrameLayout llLocalSurface;
    private TextView tvStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mediaEngine.setAudioCodec(0);

        mediaEngine.setSpeaker(true);
        mediaEngine.setSendVideo(true);
        mediaEngine.setReceiveVideo(true);

        mediaEngine.setVideoCodec(0);
        mediaEngine.setObserver(this);
        mediaEngine.start();
        llLocalSurface = (FrameLayout)findViewById(R.id.llLocalView);
        llLocalSurface.addView(mediaEngine.getLocalSurfaceView());
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) llLocalSurface.getLayoutParams();
        params.height = screenHeight* 1/2;
        llLocalSurface.setLayoutParams(params);


    }

    @Override
    public void newStats(final String stats) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStats.setText(stats);
                Log.i("MediaObserver", "stats: "+stats);
            }
        });
    }



}
