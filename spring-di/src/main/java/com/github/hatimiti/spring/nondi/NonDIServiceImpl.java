package com.github.hatimiti.spring.nondi;

import org.springframework.stereotype.Service;

@Service("nonDIAnnotationService")
public class NonDIServiceImpl implements NonDIService {

    private final NonDIRepository nonDIRepository;

    public NonDIServiceImpl(NonDIRepository nonDIRepository) {
        this.nonDIRepository = nonDIRepository;
    }

    @Override
    public String hello() {
        return this.nonDIRepository.findAllUsers().toString();
    }
}
