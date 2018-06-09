package com.github.hatimiti.spring.di.proxymode;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.NO)
public class NoProxyServiceImpl implements NoProxyService {

    private int count;

    @Override
    public int count() {
        return ++count;
    }
}
