package com.github.hatimiti.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>〜 @Service の理解 〜
 * ■ 基本的に @Component と違いは無い
 * ■ Service 層のオブジェクトであることを明示する。
 * </pre> */
@Service("diAnnotationService")
//@Transactional
public class DIServiceImpl implements DIService {

    private DIRepository diRepository;

    @Autowired
    public DIServiceImpl(final DIRepository diRepository) {
        this.diRepository = diRepository;
    }

    @Override
    public String hello() {
        return this.diRepository.findAllUsers().toString();
    }
}
