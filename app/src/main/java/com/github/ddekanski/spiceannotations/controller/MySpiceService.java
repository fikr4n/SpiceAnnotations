package com.github.ddekanski.spiceannotations.controller;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson2.Jackson2ObjectPersisterFactory;

public class MySpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager manager = new CacheManager();
        Jackson2ObjectPersisterFactory persister = new Jackson2ObjectPersisterFactory(getApplication());
        manager.addPersister(persister);
        return manager;
    }

}
