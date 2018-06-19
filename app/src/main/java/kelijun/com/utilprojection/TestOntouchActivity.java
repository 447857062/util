package kelijun.com.utilprojection;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestOntouchActivity extends AppCompatActivity {
    ConstraintLayout viewgroup_layout;
    Button button1;
    Button button2;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ontouch);
        viewgroup_layout = (ConstraintLayout) findViewById(R.id.viewgroup_layout);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        viewgroup_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestOntouchActivity.this, "viewgroup_layout", Toast.LENGTH_SHORT).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestOntouchActivity.this, "button1", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestOntouchActivity.this, "button2", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0);
            }
        });
        mHandlerThread=new HandlerThread("handlerThread");
        mHandlerThread.start();
        Looper looper=mHandlerThread.getLooper();
        mHandler=new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(TestOntouchActivity.this, "handleMessage", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
