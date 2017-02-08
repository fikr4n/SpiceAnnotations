package com.github.ddekanski.spiceannotations.controller;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.github.ddekanski.spiceannotations.model.User;

@Rest(rootUrl = "https://api.github.com/", converters = { MappingJackson2HttpMessageConverter.class })
public interface RestClient {

    @Get("users/{username}")
    User getUser(@Path CharSequence username);

}