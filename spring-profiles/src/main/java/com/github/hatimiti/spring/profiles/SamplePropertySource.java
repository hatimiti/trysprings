package com.github.hatimiti.spring.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/sample-${ENV}.properties")
public class SamplePropertySource {

    @Value("${param1}")
    public String param1;
    @Value("${param2}")
    public String param2;
}