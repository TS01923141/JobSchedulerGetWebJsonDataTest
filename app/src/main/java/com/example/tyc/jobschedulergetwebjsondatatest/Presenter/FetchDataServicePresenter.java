package com.example.tyc.jobschedulergetwebjsondatatest.Presenter;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.tyc.jobschedulergetwebjsondatatest.Model.JsonData;
import com.example.tyc.jobschedulergetwebjsondatatest.Model.MyScheduler;
import com.example.tyc.jobschedulergetwebjsondatatest.View.IMainActivity;
import com.example.tyc.jobschedulergetwebjsondatatest.View.MainActivity;

import android.os.Handler;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * Created by CarTek on 2017/12/29.
 */

public class FetchDataServicePresenter implements IFetchDataServicePresenter {
    IMainActivity mainActivity;
    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    public FetchDataServicePresenter(IMainActivity mainActivity){
        this.mainActivity = mainActivity;
        ComponentName componentName = new ComponentName(this.mainActivity.getContext(), MyScheduler.class);
        JobInfo.Builder builder =new JobInfo.Builder(JOB_ID, componentName);

        initJobScheduler(builder);
    }

    @Override
    public void startJob(){
        jobScheduler.schedule(jobInfo);
    }
    @Override
    public void stopJob(){
        jobScheduler.cancel(JOB_ID);
    }

    /**
     * 設定JobScheduler執行限制條件
     * @param builder
     */
    public void initJobScheduler(JobInfo.Builder builder){
        //API功能:https://developer.android.com/reference/android/app/job/JobInfo.Builder.html
        builder.setPeriodic(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(false);

        jobInfo = builder.build();
        jobScheduler = (JobScheduler) mainActivity.getContext().getSystemService(JOB_SCHEDULER_SERVICE);
    }
}
