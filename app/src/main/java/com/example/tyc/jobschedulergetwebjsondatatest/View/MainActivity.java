package com.example.tyc.jobschedulergetwebjsondatatest.View;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyc.jobschedulergetwebjsondatatest.Model.JsonData;
import com.example.tyc.jobschedulergetwebjsondatatest.Model.MyScheduler;
import com.example.tyc.jobschedulergetwebjsondatatest.Presenter.FetchDataServicePresenter;
import com.example.tyc.jobschedulergetwebjsondatatest.Presenter.IFetchDataServicePresenter;
import com.example.tyc.jobschedulergetwebjsondatatest.R;

public class MainActivity extends AppCompatActivity implements IMainActivity {
    public static Handler handler;
    IFetchDataServicePresenter fetchDataServicePresenter;

    @Override
    public Context getContext(){
        return this;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView location = findViewById(R.id.location);
        final TextView max = findViewById(R.id.max);
        final TextView min = findViewById(R.id.min);

        fetchDataServicePresenter = new FetchDataServicePresenter(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0) {
//                    Log.i("getMessage", "");
                    JsonData jsonData = (JsonData) msg.obj;
                    location.setText("地區:台北");
                    max.setText("最高溫:" + jsonData.getList().get(0).getTemp().getMax());
                    min.setText("最低溫:" + jsonData.getList().get(0).getTemp().getMin());
                }
            }
        };
    }

    public void startJob(View view) {
        fetchDataServicePresenter.startJob();
        Toast.makeText(this,"Job Start...", Toast.LENGTH_SHORT).show();
    }

    public void stopJob(View view) {
        fetchDataServicePresenter.stopJob();
        Toast.makeText(this,"Job Stop...", Toast.LENGTH_SHORT).show();
    }

}
