package kelijun.com.utilprojection;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = (TextView) findViewById(R.id.textView);
        ConstraintLayout layout= (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        new QBadgeView(this)
                .bindTarget(layout)
                .setBadgeGravity(Gravity.END|Gravity.TOP)
                .setBadgeNumber(12);
    }
}
