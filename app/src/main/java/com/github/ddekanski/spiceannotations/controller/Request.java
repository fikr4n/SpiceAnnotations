package com.github.ddekanski.spiceannotations.controller;

import android.os.Parcelable;

import com.github.ddekanski.spiceannotations.BuildConfig;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Request<T> extends SpiceRequest<T> {

    private final Function<RestClient, T> func;

    private RestClient restClient;

    @SuppressWarnings("WeakerAccess")
    public Request(RestClient restClient, Class<T> clazz, Function<RestClient, T> func) {
        super(clazz);
        this.func = func;
        this.restClient = restClient;
        if (BuildConfig.DEBUG)
            ensureLightLambda(func.getClass());
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        return func.apply(restClient);
    }

    private static void ensureLightLambda(Class<?> cls) {
        for (Field f : cls.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }

            Class<?> type = f.getType();
            if (!type.isPrimitive() && !Serializable.class.isAssignableFrom(type) &&
                    !Parcelable.class.isAssignableFrom(type)) {
                throw new IllegalArgumentException("Request function must not reference non " +
                        "primitive/Serializable/Parcelable object: " + f);
            }
        }

        Class<?> superclass = cls.getSuperclass();
        if (superclass != Object.class && superclass != null) {
            ensureLightLambda(superclass);
        }
    }

    /** Emulate (such a support library for) {@code java.util.function.Function} */
    public interface Function<T,R> {
        R apply(T t);
    }
}
