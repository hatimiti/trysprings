package com.github.hatimiti.spring.di.proxymode;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProxyTargetClassServiceImpl implements ProxyTargetClassService {

    private int count;

    @Override
    public int count() {
        return ++count;
    }
}
