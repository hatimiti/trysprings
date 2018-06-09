package com.github.hatimiti.spring.di.proxymode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeServiceImpl implements ModeService {

    @Autowired
    ProxyTargetClassService proxyTargetClassService;

    @Autowired
    NoProxyService noProxyService;

    @Override
    public int countByNoProxy() {
        return noProxyService.count();
    }

    @Override
    public int countByProxy() {
        return proxyTargetClassService.count();
    }
}
