package com.github.ddekanski.spiceannotations.controller;

import com.octo.android.robospice.request.SpiceRequest;

public class Request<T> extends SpiceRequest<T> {

    private final Function<RestClient, T> func;

    private RestClient restClient;

    public Request(RestClient restClient, Class<T> clazz, Function<RestClient, T> func) {
        super(clazz);
        this.func = func;
        this.restClient = restClient;
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        return func.apply(restClient);
    }

    /** Emulate (such a support library for) {@code java.util.function.Function} */
    public interface Function<T,R> {
        R apply(T t);
    }
}
