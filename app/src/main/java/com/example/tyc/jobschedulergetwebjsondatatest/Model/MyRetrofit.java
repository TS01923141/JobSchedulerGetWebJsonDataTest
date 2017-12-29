package com.example.tyc.jobschedulergetwebjsondatatest.Model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by CarTek on 2017/12/28.
 */

public class MyRetrofit {
    //查詢資料條件式
    public interface MyDataService{
        //網址後查詢輸入在此
        @GET("data/2.5/forecast/daily")
        Call<JsonData> getData(@Query("q")String city, @Query("APPID")String apiKey, @Query("units")String units, @Query("cnt")String cnt);
    }


    // 以Singleton模式建立
    private static MyRetrofit mInstance = new MyRetrofit();
    private MyDataService myDataService;
    private MyRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")//這裡輸入網址
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myDataService = retrofit.create(MyDataService.class);
    }

    public static MyRetrofit getmInstance(){
        return mInstance;
    }
    public MyDataService getAPI(){
        return myDataService;
    }
}
