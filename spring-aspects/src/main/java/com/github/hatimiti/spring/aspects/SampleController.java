package com.github.hatimiti.spring.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/helloA")
    public String helloA() {
        return this.sampleService.xxxYyyZzz();
    }

    @GetMapping("/helloB")
    public String helloB() {
        return this.sampleService.yyyZzzXxx();
    }

    @GetMapping("/helloC")
    public String helloC() {
        return this.sampleService.zzzXxxYyy();
    }

    @GetMapping("/helloD")
    public String helloD() {
        return this.sampleService.zzzXxxYyy2();
    }

    @GetMapping("/helloE")
    public String helloE() {
        return this.sampleService.myAnnotation2();
    }
}
