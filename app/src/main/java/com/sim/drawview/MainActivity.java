package com.sim.drawview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_circle_progress_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    private void findViews() {
        btn_circle_progress_view = findViewById(R.id.btn_circle_progress_view);
    }

    private void setListeners() {
        btn_circle_progress_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_circle_progress_view:
                Intent intent = new Intent(this, CircleProgressViewActivity.class);
                startActivity(intent);
                break;
        }
    }

}