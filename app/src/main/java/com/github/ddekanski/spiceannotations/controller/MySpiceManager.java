package com.github.ddekanski.spiceannotations.controller;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;

public class MySpiceManager extends SpiceManager {

    private RestClient restClient;

    public MySpiceManager() {
        super(MySpiceService.class);
    }

    @Override
    public synchronized void start(Context context) {
        super.start(context);
        restClient = new RestClient_(context.getApplicationContext());
    }

    public <T> Request<T> newRequest(Class<T> clazz, Request.Function<RestClient, T> func) {
        return new Request<T>(restClient, clazz, func);
    }
}
