package org.webrtc.webrtcdemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private NativeWebRtcContextRegistry contextRegistry = null;
    private MediaEngine mediaEngine = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextRegistry = new NativeWebRtcContextRegistry();
        contextRegistry.register(this);

        mediaEngine = new MediaEngine(this);
    }
}
