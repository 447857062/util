package kelijun.com.utilprojection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findViewById(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this,Main2Activity.class));
            }
        });

    }
    public void titleMode(View v){
        findViewById(R.id.iv_avatar).setVisibility(View.INVISIBLE);
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
    }

    public void avatarMode(View v ){

        findViewById(R.id.iv_avatar).setVisibility(View.VISIBLE);
        findViewById(R.id.tv_title).setVisibility(View.INVISIBLE);
    }
}
