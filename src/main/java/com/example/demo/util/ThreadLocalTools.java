package com.example.demo.util;


import java.util.UUID;

public class ThreadLocalTools {

    private static final ThreadLocal<String> SHARDING_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * tenant_key.
     * @return ThreadLocal
     */
    public static ThreadLocal<String> getStringThreadLocal() {
        return SHARDING_THREAD_LOCAL;
    }

    public String getTenantKeyStr(){
        return UUID.randomUUID().toString();
    }

}
