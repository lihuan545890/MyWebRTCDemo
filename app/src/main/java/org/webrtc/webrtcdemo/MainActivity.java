package org.webrtc.webrtcdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private NativeWebRtcContextRegistry contextRegistry = null;
    private MediaEngine mediaEngine = null;

    private LinearLayout llRemoteSurface;
    private LinearLayout llLocalSurface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        contextRegistry = new NativeWebRtcContextRegistry();
        contextRegistry.register(this);

        mediaEngine = new MediaEngine(this);
        mediaEngine.setRemoteIp("127.0.0.1");
        mediaEngine.setSpeaker(true);
        mediaEngine.setSendVideo(true);
        mediaEngine.setReceiveVideo(true);

        mediaEngine.setVideoCodec(0);
        mediaEngine.start();
        llLocalSurface = (LinearLayout)findViewById(R.id.llLocalView);
        llLocalSurface.addView(mediaEngine.getLocalSurfaceView());
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) llLocalSurface.getLayoutParams();
        params.height = screenHeight* 1/2;
        llLocalSurface.setLayoutParams(params);
    }
}
