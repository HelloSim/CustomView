package com.sim.drawview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sim.draw_view.views.CircleProgressView;

public class CircleProgressViewActivity extends Activity implements View.OnClickListener {

    private CircleProgressView circle_progress;
    private TextView tv_progress;
    private Button btn_start;
    private Button btn_reset;

    private int currentProgress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_view);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        circle_progress = (CircleProgressView) findViewById(R.id.circle_progress);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        btn_start.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                handler.postDelayed(runnable, 0);
                break;
            case R.id.btn_reset:
                handler.removeCallbacks(runnable);
                handler.removeCallbacksAndMessages(null);
                currentProgress = 0;
                circle_progress.setCurrentProgress(currentProgress);
                tv_progress.setText("0");
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            circle_progress.setCurrentProgress(currentProgress);
            currentProgress++;
            if (currentProgress <= 100)
                handler.postDelayed(this, 500);
        }
    };

}
