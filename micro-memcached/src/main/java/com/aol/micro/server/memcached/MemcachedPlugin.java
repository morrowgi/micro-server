package com.aol.micro.server.memcached;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

public class MemcachedPlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
            return PersistentSetX.of(ConfigureMemcached.class);
        }
}