package com.example.tyc.jobschedulergetwebjsondatatest.Model;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Message;
import android.util.Log;

import com.example.tyc.jobschedulergetwebjsondatatest.Model.JsonData;
import com.example.tyc.jobschedulergetwebjsondatatest.Model.List;
import com.example.tyc.jobschedulergetwebjsondatatest.Model.MyRetrofit;
import com.example.tyc.jobschedulergetwebjsondatatest.Model.Temp;
import com.example.tyc.jobschedulergetwebjsondatatest.View.MainActivity;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CarTek on 2017/12/28.
 */

public class MyScheduler extends JobService {
    private final static String KEY_API = "0899273c10ddbaae0059f020ae69e421";
    private JsonData jsonData;private List list;private Temp temp;
    static String location = "Taipei";

    @Override
    public boolean onStartJob(final JobParameters params) {
        Flowable.just(params)
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
                .subscribe(new Consumer<JobParameters>() {
                               @Override
                               public void accept(JobParameters jobParameters) throws Exception {
                                   fetchData(jobParameters);
                                   Log.i("Current Thread",Thread.currentThread().getName());
                               }
                           }
                );
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 取得資料並傳至MainActivity
     * @param params
     */
    public void fetchData(final JobParameters params){
        MyRetrofit.MyDataService myDataService;
        myDataService = MyRetrofit.getmInstance().getAPI();
        Call<JsonData> call = myDataService.getData(location, KEY_API , "metric", "1");
        Log.i("WebSite of Json","http://api.openweathermap.org/data/2.5/forecast/daily"+"?q="+location+"&APPID="+KEY_API+"&units=metric&cnt=1");
        Log.i("call", String.valueOf(call));
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                jsonData = response.body();
//                Log.i("jsonData.max", String.valueOf(jsonData.getList().get(0).getTemp().getMax()));
//                Log.i("response.max", String.valueOf(response.body().getList().get(0).getTemp().getMax()));

                Message message = new Message();
                message.what=0;
                message.obj=jsonData;
                MainActivity.handler.sendMessage(message);
                jobFinished(params,false);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.i("fetchData:onFailure",t.getMessage());
            }
        });
    }
}
