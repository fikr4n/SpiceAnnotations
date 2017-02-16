package com.github.ddekanski.spiceannotations.controller;

import android.content.Context;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.listener.RequestListener;

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

    public <T> Object executeRequest(
            Class<T> clazz, Request.Function<RestClient, T> func, Object requestCacheKey,
            long cacheExpiryDuration, RequestListener<T> requestListener) {
        Request<T> request = new Request<>(restClient, clazz, func);
        execute(request, requestCacheKey, cacheExpiryDuration, requestListener);
        return requestCacheKey;
    }

    public <T> void executeRequest(Class<T> clazz, Request.Function<RestClient, T> func,
                                   RequestListener<T> requestListener) {
        Request<T> request = new Request<>(restClient, clazz, func);
        execute(request, requestListener);
    }
}
