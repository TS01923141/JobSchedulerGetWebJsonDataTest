package com.example.tyc.jobschedulergetwebjsondatatest.Presenter;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by CarTek on 2017/12/29.
 */

public interface IFetchDataServicePresenter {

    void startJob();

    void stopJob();

    void initJobScheduler(JobInfo.Builder builder);
}
