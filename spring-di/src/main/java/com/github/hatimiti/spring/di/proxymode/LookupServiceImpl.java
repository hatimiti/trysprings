package com.github.hatimiti.spring.di.proxymode;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class LookupServiceImpl implements LookupService {

    @Override
    public int countByLookup() {
        return proxyTargetClassService().count();
    }

    @Lookup
    ProxyTargetClassService proxyTargetClassService() {
        return /* return dummy */null;
    }

}
