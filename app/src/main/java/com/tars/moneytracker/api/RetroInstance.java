package com.tars.moneytracker.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
        private static Retrofit retrofit=null;

        private static String baseUrl="http://192.168.0.102:8080";

        public static RetroInterface getRetro(){
            if(retrofit==null){
                retrofit=new Retrofit.Builder().baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()).build();

            }
            return retrofit.create(RetroInterface.class);

        }
    }
