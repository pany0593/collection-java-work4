package com.pany.util;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalUtil {
    private final ThreadLocal threadLocal=new ThreadLocal();
    public <T> T get(){
        return (T) threadLocal.get();
    }

    public void set(Object value){
        threadLocal.set(value);
    }

    public void remove(){
        threadLocal.remove();
    }
}
